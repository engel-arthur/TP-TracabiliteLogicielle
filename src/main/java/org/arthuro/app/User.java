package org.arthuro.app;


public class User {
    private static int currentId = 0;
    private int id;
    private String name;
    private int age;
    private String email;
    private String password;

    public User(String name, int age, String email, String password) {
        id = currentId;
        currentId++;

        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    private User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
