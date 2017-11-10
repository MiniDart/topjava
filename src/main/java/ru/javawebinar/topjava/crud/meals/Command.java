package ru.javawebinar.topjava.crud.meals;

public enum Command {CREATE,GET,UPDATE,DELETE;
public static Command getCommand(String name){
    if (name.equals("create")) return CREATE;
    if (name.equals("get")) return GET;
    if (name.equals("update")) return UPDATE;
    if (name.equals("delete")) return DELETE;
    return null;
}
}
