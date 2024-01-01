package net.minecraftforge.client;

public enum Category {
    Combat("Combat"),
    Movement("Movement"),
    Render("Render"),
    Player("PLAYER"),
    Misc("Misc");

    public String name;
    public double x;
    public double y;
    public double width;
    public double height;

    Category(String name) {
        this.name = name;
    }
}
