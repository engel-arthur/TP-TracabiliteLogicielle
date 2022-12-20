package org.arthuro.logging;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogParser {
    private Path logFilePath;
    private Path outputFilePath;

    private JsonElement lastUserId;

    public LogParser(Path logFilePath, Path outputFilePath) {
        this.logFilePath = logFilePath;
        this.outputFilePath = outputFilePath;
    }

    public void parse() throws IOException {
        //if (isOutputFileEmpty()) {
            initializeOutputFile();
        //}

        try (BufferedReader bufferedReader = Files.newBufferedReader(logFilePath)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (!line.isEmpty())
                    parseLine(line);
            }
            bufferedReader.close();

        } catch (Exception e) {e.printStackTrace();}
    }

    private boolean isOutputFileEmpty() {
        return outputFilePath.toFile().length() == 0;
    }

    public void initializeOutputFile() throws IOException {
        File outputFile = outputFilePath.toFile();
        String jsonToWrite = """
                {
                    "users" : [
                    ]
                }
                """;
        FileUtils.writeStringToFile(outputFile, jsonToWrite, "UTF-8", false);
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
        //String action;
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
        JsonObject user = new JsonObject();
        user.add("id", userId);
        user.add("name", userName);
        user.add("age", userAge);
        user.add("mail", userMail);
        user.addProperty("max_price", 0);

        if(operation.getAsString().equals("READ")) {
            user.addProperty("read_operations", 1);
            user.addProperty("write_operations", 0);
        }
        else if(operation.getAsString().equals("WRITE")) {
            user.addProperty("read_operations", 0);
            user.addProperty("write_operations", 1);
        }

        addJsonObjectToOutputFile("users", user);
    }

    private JsonObject getJsonUserFromId(JsonElement userId) throws FileNotFoundException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputFilePath.toFile())).getAsJsonObject();
        JsonArray usersArray = jsonFileAsJsonObject.get("users").getAsJsonArray();

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
            updateUserInJsonFile(user.get("id"), "read_operations", new JsonPrimitive(user.get("read_operations").getAsInt() + 1));
        }
        else if(operation.equals("WRITE")) {
            updateUserInJsonFile(user.get("id"), "write_operations", new JsonPrimitive(user.get("write_operations").getAsInt() + 1));

        }
        else {
            //TODO créer exception
            System.err.println("Mauvaise opération (incrementUserOperation)");
        }

    }

    private void updateMaxPrice(JsonElement userId, int price) throws IOException {
        JsonObject user = getJsonUserFromId(userId);
        if(price > user.get("max_price").getAsInt()) {
            updateUserInJsonFile(userId, "max_price", new JsonPrimitive(price));
        }
    }

    private void addJsonObjectToOutputFile(String jsonArrayToInsertObjectIntoName, JsonObject objectToInsert) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputFilePath.toFile())).getAsJsonObject();
        JsonArray jsonArrayToInsertObjectInto = jsonFileAsJsonObject.get(jsonArrayToInsertObjectIntoName).getAsJsonArray();

        jsonArrayToInsertObjectInto.add(objectToInsert);

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputFilePath.toFile(), jsonFileContentsToWrite, "UTF-8", false);
    }

    private void updateUserInJsonFile(JsonElement userId, String elementToUpdate, JsonElement newValue) throws IOException {
        JsonObject jsonFileAsJsonObject = JsonParser.parseReader(new FileReader(outputFilePath.toFile())).getAsJsonObject();
        JsonArray usersArray = jsonFileAsJsonObject.get("users").getAsJsonArray();

        for(JsonElement user : usersArray) {
            JsonObject userObject = user.getAsJsonObject();
            if(userObject.get("id").equals(userId))
                userObject.add(elementToUpdate, newValue);
        }

        String jsonFileContentsToWrite = jsonFileAsJsonObject.toString();
        FileUtils.writeStringToFile(outputFilePath.toFile(), jsonFileContentsToWrite, "UTF-8", false);
    }
}
