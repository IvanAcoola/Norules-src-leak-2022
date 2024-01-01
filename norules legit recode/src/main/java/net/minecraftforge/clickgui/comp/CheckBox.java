package net.minecraftforge.clickgui.comp;


import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Module;
import net.minecraftforge.utils.RenderUtil;

import java.awt.*;

public class CheckBox extends Comp {

    public int offset_drag;

    public CheckBox(double x, double y, Clickgui parent, Module module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        boolean hover = isHovered((int) (parent.posX + x - 80), (int) (parent.posY + y + 15 + offset - offset_drag), (int) (parent.posX + x + 25), (int) (parent.posY + y + 34 + offset - offset_drag));

        RenderUtil.drawRoundedRect((int) (parent.posX + x - 70), (int) (parent.posY + y + 15  + offset - offset_drag), (int) (parent.posX + x + 35), (int) (parent.posY + y + 34 + offset - offset_drag), 5, hover ? new Color(15, 15, 15, 250).brighter() : new Color(38, 38, 38, 255).darker());
        RenderUtil.drawRoundedRect((int) (parent.posX + x - 65), (int) (parent.posY + y + 20 + offset - offset_drag), (int) (parent.posX + x + 10 - 64), (int) (parent.posY + y + 30 + offset - offset_drag), 5, setting.getValBoolean() ? new Color(124, 18, 176) : new Color(30, 30, 31).brighter());
        ForgeInternalHandler.SANS_SMALL.drawStringWithShadow(setting.getName(), (int) (parent.posX + x - 50), (int) (parent.posY + y + 19 - offset_drag), new Color(200, 200, 200).getRGB(), 1.05f);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, (int) (parent.posX + x - 70), (int) (parent.posY + y + 15), (int) (parent.posX + x + 35), (int) (parent.posY + y + 34)) && mouseButton == 0) {
            setting.setValBoolean(!setting.getValBoolean());
        }
    }

}
