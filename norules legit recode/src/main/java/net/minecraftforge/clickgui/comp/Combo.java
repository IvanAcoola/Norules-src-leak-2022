package net.minecraftforge.clickgui.comp;

import net.minecraftforge.client.Module;
import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class Combo extends Comp {

    public Combo(double x, double y, Clickgui parent, Module module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, parent.posX + x - 70, parent.posY + y, parent.posX + x, parent.posY + y + 10) && mouseButton == 0) {
            int max = setting.getOptions().size();
            if (parent.modeIndex + 1 >= max) {
                parent.modeIndex = 0;
            } else {
                parent.modeIndex++;
            }
            setting.setValString(setting.getOptions().get(parent.modeIndex));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                offset += 10;
                if (offset < 0) {
                    offset = 0;
                }
                if (offset > 120) {
                    offset = 120;
                }
            } else if (wheel > 0) {
                offset -= 10;
                if (offset < 0) {
                    offset = 0;
                }
                if (offset > 120) {
                    offset = 120;
                }
            }
        }

        Gui.drawRect((int) (parent.posX + x - 70), (int) (parent.posY + y + offset), (int) (parent.posX + x), (int) (parent.posY + y + 10  + offset),setting.getValBoolean() ? new Color(230,10,230).getRGB() : new Color(30,30,30).getRGB());
        Minecraft.getMinecraft().fontRenderer.drawString(setting.getName() + ": " + setting.getValString(), (int)(parent.posX + x - 69), (int)(parent.posY + y + 12  + offset), new Color(200,200,200).getRGB());
    }
}
