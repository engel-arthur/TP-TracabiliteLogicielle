package org.arthuro.logging;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogParser {
    public static final String CHARSET_NAME = StandardCharsets.UTF_8.name();
    public static final String MAX_PRICE_KEY = "max_price";
    public static final String READ_OPERATIONS_KEY = "read_operations";
    public static final String WRITE_OPERATIONS_KEY = "write_operations";
    public static final String USERS_KEY = "users";
    public static final String PROFILE_READ_KEY = "profile_read";
    public static final String PROFILE_WRITE_KEY = "profile_write";

    public static final String PROFILE_MAX_PRICE_KEY = "profile_max_price";

    public int maxPrice = 0;

    private final Path logFilePath;
    private final Path outputUsersFilePath;
    private final Path outputProfilesFilePath;

    private JsonElement lastUserId;

    public LogParser(Path logFilePath, Path outputUsersFilePath, Path outputProfilesFilePath) {
        this.logFilePath = logFilePath;
        this.outputUsersFilePath = outputUsersFilePath;
        this.outputProfilesFilePath = outputProfilesFilePath;
    }

    public void parse() throws IOException {

        initializeOutputFiles();

        try (BufferedReader bufferedReader = Files.newBufferedReader(logFilePath)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (!line.isEmpty())
                    parseLine(line);
            }

        } catch (Exception e) {e.printStackTrace();}
    }

    public void initializeOutputFiles() throws IOException {
        File outputUsersFile = outputUsersFilePath.toFile();
        File outputProfilesFile = outputProfilesFilePath.toFile();

        String usersJsonPattern = """
                {
                    "users" : [
                    ]
                }
                """;
        String profilesJsonPattern = """
                {
                    "profile_read" : [
                    ],
                    "profile_write" : [
                    ],
                    "profile_max_price" : -1
                }
                """;

        FileUtils.writeStringToFile(outputUsersFile, usersJsonPattern, CHARSET_NAME, false);
        FileUtils.writeStringToFile(outputProfilesFile, profilesJsonPattern, CHARSET_NAME, false);
    }

    private void parseLine(String line) throws IOException {
        String lineWithOnlyJson = line.split(" - ")[1];

        JsonElement lineAsJsonElement = JsonParser.parseString(lineWithOnlyJson);
        JsonObject lineAsJsonObject = lineAsJsonElement.getAsJsonObject();

        JsonElement userElement = lineAsJsonObject.get("user");
        JsonElement operationElement = lineAsJsonObject.get("op");
        JsonElement priceElement = lineAsJsonObject.get("price");

        if (userElement != null && operationElement != null) {
            handleUserOperation(userElement, operationElement);
        } else if (priceElement != null) {
            updateMaxPrice(lastUserId, priceElement.getAsInt());
        }
    }

    private void handleUserOperation(JsonElement userElement, JsonElement operationElement) throws IOException {
        JsonArray userArray = userElement.getAsJsonArray();

        JsonElement userId = userArray.get(0);
        JsonElement userName = userArray.get(1);
        JsonElement userAge = userArray.get(2);
        JsonElement userMail = userArray.get(3);

        JsonObject user = getJsonUserFromId(userId);
        if (user != null) {
            incrementUserOperationCount(user, operationElement.getAsString());
        } else {
            createUser(userId, userName, userAge, userMail, operationElement);
        }
        lastUserId = userId;
    }

    private void createUser(JsonElement userId, JsonElement userName, JsonElement userAge, JsonElement userMail, JsonElement operation) throws IOException {

        String profileName = null;
        JsonObject user = new JsonObject();
        user.add("id", userId);
        user.add("name", userName);
        user.add("age", userAge);
        user.add("mail", userMail);
        user.addProperty(MAX_PRICE_KEY, 0);

        if(operation.getAsString().equals("READ")) {

            user.addProperty(READ_OPERATIONS_KEY, 1);
            user.addProperty(WRITE_OPERATIONS_KEY, 0);
            profileName = PROFILE_READ_KEY;
        }
        else if(operation.getAsString().equals("WRITE")) {

            user.addProperty(READ_OPERATIONS_KEY, 0);
            user.addProperty(WRITE_OPERATIONS_KEY, 1);
            profileName = PROFILE_WRITE_KEY;
        }

        addJsonObjectToOutputFile(USERS_KEY, user);
        addUserToProfile(userId, profileName);
    }

    private JsonObject getJsonUserFromId(JsonElement userId) throws FileNotFoundException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputUsersFilePath.toFile())).getAsJsonObject();
        JsonArray usersArray = jsonFileAsJsonObject.get(USERS_KEY).getAsJsonArray();

        for(JsonElement arrayItem : usersArray) {
            JsonObject arrayItemObject = arrayItem.getAsJsonObject();
            if (arrayItemObject.get("id").equals(userId)) {
                return arrayItemObject;
            }
        }
        return null;
    }

    private void incrementUserOperationCount(JsonObject user, String operation) throws IOException {
        if(operation.equals("READ")) {
            updateUserInJsonFile(user.get("id"), READ_OPERATIONS_KEY, new JsonPrimitive(user.get(READ_OPERATIONS_KEY).getAsInt() + 1));
        }
        else if(operation.equals("WRITE")) {
            updateUserInJsonFile(user.get("id"), WRITE_OPERATIONS_KEY, new JsonPrimitive(user.get(WRITE_OPERATIONS_KEY).getAsInt() + 1));

        }
        else {
            //TODO créer exception
            System.err.println("Mauvaise operation (incrementUserOperation)");
        }
    }

    private void updateMaxPrice(JsonElement userId, int price) throws IOException {
        JsonObject user = getJsonUserFromId(userId);

        if((user != null) && (price > user.get(MAX_PRICE_KEY).getAsInt())) {

            updateUserInJsonFile(userId, MAX_PRICE_KEY, new JsonPrimitive(price));
            if (price > maxPrice) {
                updatePriceProfileInJsonFile(userId);
                maxPrice = price;
            }
        }
    }

    private void updatePriceProfileInJsonFile(JsonElement userId) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputProfilesFilePath.toFile())).getAsJsonObject();
        jsonFileAsJsonObject.add(PROFILE_MAX_PRICE_KEY, userId);

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputProfilesFilePath.toFile(), jsonFileContentsToWrite, CHARSET_NAME, false);
    }

    private void addJsonObjectToOutputFile(String jsonArrayToInsertObjectIntoName, JsonObject objectToInsert) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputUsersFilePath.toFile())).getAsJsonObject();
        JsonArray jsonArrayToInsertObjectInto = jsonFileAsJsonObject.get(jsonArrayToInsertObjectIntoName).getAsJsonArray();

        jsonArrayToInsertObjectInto.add(objectToInsert);

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputUsersFilePath.toFile(), jsonFileContentsToWrite, CHARSET_NAME, false);
    }

    private void updateUserInJsonFile(JsonElement userId, String elementToUpdate, JsonElement newValue) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputUsersFilePath.toFile())).getAsJsonObject();
        JsonArray usersArray = jsonFileAsJsonObject.get(USERS_KEY).getAsJsonArray();

        for(JsonElement user : usersArray) {

            JsonObject userObject = user.getAsJsonObject();
            if(userObject.get("id").equals(userId)) {

                userObject.add(elementToUpdate, newValue);
                if (!elementToUpdate.equals(MAX_PRICE_KEY)) {

                    updateUserProfileInJsonFile(userId, userObject);
                }
            }
        }

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputUsersFilePath.toFile(), jsonFileContentsToWrite, CHARSET_NAME, false);
    }

    private void updateUserProfileInJsonFile(JsonElement userId, JsonObject userObject) throws IOException {
        if (userObject.get(READ_OPERATIONS_KEY).getAsInt() > userObject.get(WRITE_OPERATIONS_KEY).getAsInt()) {

            if (getUserOperationProfile(userId.getAsInt()).equals("")) {

                addUserToProfile(userId, PROFILE_READ_KEY);
            }
        }
        else if (userObject.get(WRITE_OPERATIONS_KEY).getAsInt() > userObject.get(READ_OPERATIONS_KEY).getAsInt()) {

            if (getUserOperationProfile(userId.getAsInt()).equals("")) {

                addUserToProfile(userId, PROFILE_WRITE_KEY);
            }
        }
        else {

            removeUserFromProfile(userId, getUserOperationProfile(userId.getAsInt()));
        }
    }

    private void addUserToProfile(JsonElement userId, String profile) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputProfilesFilePath.toFile())).getAsJsonObject();
        JsonArray profileJsonArray = jsonFileAsJsonObject.get(profile).getAsJsonArray();

        profileJsonArray.add(userId);

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputProfilesFilePath.toFile(), jsonFileContentsToWrite, CHARSET_NAME, false);
    }

    private void removeUserFromProfile(JsonElement userId, String profile) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputProfilesFilePath.toFile())).getAsJsonObject();
        JsonArray profileJsonArray = jsonFileAsJsonObject.get(profile).getAsJsonArray();

        profileJsonArray.remove(userId);

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputProfilesFilePath.toFile(), jsonFileContentsToWrite, CHARSET_NAME, false);
    }

    private String getUserOperationProfile(int userId) throws FileNotFoundException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputProfilesFilePath.toFile())).getAsJsonObject();
        JsonArray readProfileJsonArray = jsonFileAsJsonObject.get(PROFILE_READ_KEY).getAsJsonArray();
        JsonArray writeProfileJsonArray = jsonFileAsJsonObject.get(PROFILE_WRITE_KEY).getAsJsonArray();

        if (checkIfUserIsInArray(userId, readProfileJsonArray)) {
            return PROFILE_READ_KEY;
        }
        if (checkIfUserIsInArray(userId, writeProfileJsonArray)) {
            return PROFILE_WRITE_KEY;
        }
        return "";
    }

    private boolean checkIfUserIsInArray(int userId, JsonArray jsonArray) {
        for(JsonElement user : jsonArray) {

            if (user.getAsInt() == userId) {

                return true;
            }
        }
        return false;
    }
}