package net.minecraftforge.clickgui.comp;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.Clickgui;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Module;
import net.minecraftforge.utils.RenderUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Comp {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;
    private double animationWidth = 0;
    private float textHoverAnimate = 0f;
    private float currentValueAnimate = 0f;

    public Slider(double x, double y, int offset_drag, Clickgui parent, Module module, Setting setting) {
        this.x = x;
        this.y = y;
        this.offset_drag = offset_drag;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, (int) (parent.posX + x - 72), (int) (parent.posY + y + 10), (int) (parent.posX + x - 54 + renderWidth2), (int) (parent.posY + y + 50)) && mouseButton == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        double min = setting.getMin();
        double max = setting.getMax();
        double l = 90;

        renderWidth = (l) * (setting.getValDouble() - min) / (max - min);
        renderWidth2 = (l) * (setting.getMax() - min) / (max - min);

        double diff = Math.min(l, Math.max(0, mouseX - (parent.posX + x - 70)));
        if (dragging) {
            if (diff == 0) {
                setting.setValDouble(setting.getMin());
            } else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 1);
                setting.setValDouble(newValue);
            }
        }


        final double amountWidth = ((setting.getValDouble()) - setting.getMin())
                / (setting.getMax() - setting.getMin());

//        textHoverAnimate = RenderUtil.moveUD(textHoverAnimate, 2, 0.000542f);
        currentValueAnimate = RenderUtil.moveUD(currentValueAnimate, (float) amountWidth, 0.000542f);
        boolean hover = isHovered((int) (parent.posX + x - 79), (int) (parent.posY + y + 10), (int) (parent.posX + x - 65 + renderWidth2), (int) (parent.posY + y + 50));

        GL11.glPushMatrix();
        Clickgui.prepareScissorBox((int) (parent.posX + x - 90), (int) (parent.posY + 4), (int) (parent.posX + x - 20 + renderWidth2), (int) (parent.posY + 225));
        GL11.glEnable(3089);

        RenderUtil.drawRoundedRect((int) (parent.posX + x - 72), (int) (parent.posY + y + 10 + offset - offset_drag), (int) (parent.posX + x - 54 + renderWidth2), (int) (parent.posY + y + 50 + offset - offset_drag), 5, hover ? new Color(15, 15, 15, 250).brighter() : new Color(38, 38, 38, 255).darker());
        RenderUtil.drawRoundedRect((int) (parent.posX + x - 63), (int) (parent.posY + y + 28 + offset - offset_drag), (int) (parent.posX + x - 63 + renderWidth2), (int) (parent.posY + y + 39 + offset - offset_drag), 5, new Color(52, 50, 52));
        RenderUtil.drawRoundedRect((int) (parent.posX + x - 63), (int) (parent.posY + y + 28 + offset - offset_drag), (int) (parent.posX + x - 53 + (renderWidth / 1.13f * currentValueAnimate)), (int) (parent.posY + y + 39 + offset - offset_drag), 5, new Color(124, 18, 176));
        ForgeInternalHandler.SANS_SMALL.drawStringWithShadow(setting.getName() + ": " + (float) (roundToPlace(setting.getValDouble() * currentValueAnimate, 1)), (int) (parent.posX + x - 63), (int) (parent.posY + y + 12 + offset - offset_drag), -1, 1.05f);
//        RenderUtil.drawRoundedRect((int) (parent.posX + x - 70), (int) (parent.posY + y + 32), (int) (parent.posX + x - 70 + renderWidth2), (int) (parent.posY + y + 42), 2, new Color(124, 18, 176).darker());
//        RenderUtil.drawRoundedRect((int) (parent.posX + x - 70), (int) (parent.posY + y + 32), (int) (parent.posX + x - 70 + renderWidth), (int) (parent.posY + y + 42), 2, new Color(124, 18, 176));
//        Main.SANS_SMALL.drawStringWithShadow(setting.getName() + ": " + setting.getValDouble(),(int)(parent.posX + x - 70),(int)(parent.posY + y + 17), -1, 1.05f);

        GL11.glDisable(3089);
        GL11.glPopMatrix();

    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
