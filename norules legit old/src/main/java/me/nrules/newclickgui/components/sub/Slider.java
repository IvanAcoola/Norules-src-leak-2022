package me.nrules.newclickgui.components.sub;

import me.nrules.clickgui.settings.Setting;
import me.nrules.newclickgui.components.Component;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Slider extends Component {
    private Setting op;
    private boolean hovered;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(Setting option, int x, int y) {
        this.op = option;
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderComponent() {
        Gui.drawRect(x - 110, y + 30, x + 45, y, 0x20000000);
//        RenderUtils.drawRoundedRect2(x - 110, y + 30, x + 45, y, 1, 0x20000000);
        final int drag = (int) (this.op.getValDouble() / this.op.getMax() * 12);
        Gui.drawRect(x - 100, y + 26, x - 12, y + 28, 0xFF111111);
        Gui.drawRect(x - 100, y + 26, x - 100 + (int) renderWidth, y + 28, hovered ? 0xFF888888 : 0xFF999999);
        RenderUtils.drawFilledCircle(x - 100 + (int) renderWidth, y + 27, 2, Color.WHITE);
        Minecraft.getMinecraft().fontRenderer.drawString(this.op.getName().toLowerCase() + ": " + this.op.getValDouble(), x - 100, y + 6, -1);
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButtonD(mouseX, mouseY);

        double diff = Math.min(88, Math.max(0, mouseX - this.x + 100));
        double min = op.getMin();
        double max = op.getMax();

        renderWidth = (88) * (op.getValDouble() - min) / (max - min);

        if (dragging) {
            if (diff == 0) {
                op.setValDouble(op.getMin());
            } else {
                double newValue = roundToPlace(((diff / 88) * (max - min) + min), 2);
                op.setValDouble(newValue);
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
        if (x > this.x - 100 && x < this.x - 12 && y > this.y + 23 && y < this.y + 31) {
            return true;
        }
        return false;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        if (x > this.x - 100 && x < this.x - 12 && y > this.y + 23 && y < this.y + 31) {
            return true;
        }
        return false;
    }
}