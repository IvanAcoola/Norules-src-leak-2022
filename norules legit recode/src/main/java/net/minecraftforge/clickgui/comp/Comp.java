package net.minecraftforge.clickgui.comp;

import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class Comp {

    public double x, y, x2, y2;
    public Clickgui parent;
    public Module module;
    public Setting setting;
    public int offset_drag;
    public int offset;
    private double renderWidth3;

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void drawScreen(int mouseX, int mouseY) {
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                offset_drag += 16;
                if (offset_drag < 0) {
                    offset_drag = 0;
                }
                if (offset_drag > 105) {
                    offset_drag = 105;
                }
            } else if (wheel > 0) {
                offset_drag -= 16;
                if (offset_drag < 0) {
                    offset_drag = 0;
                }
                if (offset_drag > 105) {
                    offset_drag = 105;
                }
            }
        }

    }

    public boolean isInside(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    private static ScaledResolution sr() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static int getMouseX() {
        return Mouse.getX() * sr().getScaledWidth() / Minecraft.getMinecraft().displayWidth;
    }

    public static int getMouseY() {
        return sr().getScaledHeight() - Mouse.getY() * sr().getScaledHeight() / Minecraft.getMinecraft().displayHeight - 1;
    }

    public static boolean isHovered(float x, float y, float width, float height) {
        return getMouseX() > x && getMouseX() < width && getMouseY() > y && getMouseY() < height;
    }

}
