package me.nrules.font;

public class IFont {

    public static FontRenderer SOURCE_SMALL;
    public static FontRenderer SANS_SMALL;
    public static FontRenderer SANS_SETTINGS;
    public static FontRenderer SANS_MIDDLE;

    public static void initFonts() {
        SANS_SMALL = new FontRenderer("C://RemachineScript_Personal_Use.ttf", 0.45f, 0f);
        SANS_SETTINGS = new FontRenderer("C:/BodoniFLF-Bold.ttf", 0.45F, 0f);
//        SANS_MIDDLE = new FontRenderer("Noto Sans CJK TC", 0.45F, 0f);
//        SOURCE_SMALL = new FontRenderer("Source Han Sans", 0.26f, 0f);
    }

}
