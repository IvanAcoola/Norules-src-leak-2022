package net.minecraftforge.utils;

import java.awt.*;

public class ColorUtil {

    public static int getBrightness(Color color, float brightness) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public static int fade(Color color, int delay) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L + delay) / 1000.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return Color.HSBtoRGB(hsb[0], hsb[2], hsb[2]);
    }

}
