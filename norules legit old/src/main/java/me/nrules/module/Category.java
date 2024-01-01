package me.nrules.module;

public enum Category {
    COMBAT("COMBAT"),
    MOVEMENT("MOVEMENT"),
    RENDER("RENDER"),
    PLAYER("PLAYER"),
    FUN("FUN"),
    GHOST("GHOST"),
    CONFIG("CONFIG"),
    MISC("MISC");

    public String name;
    public double x;
    public double y;
    public double width;
    public double height;

    Category(String name) {
        this.name = name;
    }
}
