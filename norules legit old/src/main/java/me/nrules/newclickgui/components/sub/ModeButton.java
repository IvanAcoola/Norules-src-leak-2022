package me.nrules.newclickgui.components.sub;


import me.nrules.clickgui.settings.Setting;
import me.nrules.newclickgui.components.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ModeButton extends Component {

    private boolean hovered;
    private Setting set;
    private int x;
    private int y;

    private int modeIndex;

    public ModeButton(Setting set, int x, int y) {
        this.set = set;
        this.x = x;
        this.y = y;
        this.modeIndex = 0;
    }


    @Override
    public void renderComponent() {
        Gui.drawRect(x - 110, y + 10, x + 45, y - 5, 0x20000000);
        Minecraft.getMinecraft().fontRenderer.drawString(set.getName().toLowerCase(), x - 100, y, -1);
        Minecraft.getMinecraft().fontRenderer.drawString(set.getValString().toLowerCase(), x + 15, y, Color.WHITE.getRGB());
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            int maxIndex = set.getOptions().size();

            if (modeIndex > maxIndex - 2)
                modeIndex = 0;
            else
                modeIndex++;

            set.setValString(set.getOptions().get(modeIndex));
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x + 5 && x < this.x + 60 && y > this.y && y < this.y + 12) {
            return true;
        }
        return false;
    }
}