package ru.gb.spring.my_timesheet.model;

public enum RoleName {

    ADMIN("admin"), USER("user"), ANONYMOUS("anonymous"), REST("rest");

    private final String name;

    RoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
