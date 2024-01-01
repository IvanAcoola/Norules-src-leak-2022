package me.nrules.newclickgui.components.sub;

import me.nrules.clickgui.settings.Setting;
import me.nrules.newclickgui.components.Component;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaturationSlider extends Component {

    private boolean hovered;

    private Setting set;


    private int x;
    private int y;
    private boolean dragging = false;

    private double renderWidth;

    public SaturationSlider(Setting set, int x, int y) {
        this.set = set;
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderComponent() {

        final float hue = (float) Color.WHITE.getRGB();
        int color = Color.HSBtoRGB(hue, 1.0F, 1.0F);
        final int drag = (int) (this.set.getValDouble() / this.set.getMax() * 12);
        RenderUtils.drawGradientSideways(x, y + 13, x + (int) 90, y + 5 + 16, -1, color);
        GL11.glPushMatrix();
        GL11.glColor3d(255, 255, 255);
        Gui.drawRect(x + 2 + (int) renderWidth - 2, y + 11, x + (int) renderWidth + 3, y + 10 + 12, this.hovered ? -1 : -1);
        GL11.glPopMatrix();

        Minecraft.getMinecraft().fontRenderer.drawString(this.set.getName().toLowerCase() + ": " + this.set.getValDouble(), x, y + 2, -1);


    }


    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);


        double diff = Math.min(88, Math.max(0, mouseX - this.x));

        double min = 0;
        double max = 1;

        renderWidth = (88) * (set.getValDouble() - min) / (max - min);

        if (dragging) {
            if (diff == 0) {
                set.setValDouble(0);
            } else {
                double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
                set.setValDouble(newValue);
            }
        }
    }


    private static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButtonD(mouseX, mouseY) && button == 0) {
            dragging = true;
        }
        if (isMouseOnButtonI(mouseX, mouseY) && button == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    public boolean isMouseOnButtonD(int x, int y) {
        if (x > this.x && x < this.x + (12 / 2 + 1) && y > this.y && y < this.y + 23) {
            return true;
        }
        return false;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        if (x > this.x + 12 / 2 && x < this.x + 90 && y > this.y && y < this.y + 18) {
            return true;
        }
        return false;
    }
}
