package me.nrules.clickgui.clickgui.component.components;

import me.nrules.Main;
import me.nrules.util.TimerHelper;
import org.lwjgl.opengl.GL11;
import me.nrules.clickgui.clickgui.ClickGui;
import me.nrules.clickgui.clickgui.component.Component;
import me.nrules.clickgui.clickgui.component.Frame;
import me.nrules.clickgui.clickgui.component.components.sub.Checkbox;
import me.nrules.clickgui.clickgui.component.components.sub.Keybind;
import me.nrules.clickgui.clickgui.component.components.sub.ModeButton;
import me.nrules.clickgui.clickgui.component.components.sub.Slider;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Module;
import me.nrules.module.modules.render.HUD;
import me.nrules.util.AnimationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Button extends Component {

    public Module mod;
    public Frame parent;
    public int offset;
    double scaling1 = 25;
    private boolean isHovered;
    private ArrayList<Component> subcomponents;
    public boolean open;
    float a = 0;
    TimerHelper timerHelper = new TimerHelper();
    float textHoverAnimate;
    float currentValueAnimate;
    final int[] count = {1};
    public static FontRenderer font = Minecraft.getMinecraft().fontRenderer;

    public Button(Module mod, Frame parent, int offset) {
        this.mod = mod;
        this.parent = parent;
        this.offset = offset;
        this.subcomponents = new ArrayList<>();
        this.open = false;
        int opY = offset + 12;
        if (Main.settingsManager.getSettingsByMod(mod) != null) {
            for (Setting s : Main.settingsManager.getSettingsByMod(mod)) {
                if (s.isCombo()) {
                    this.subcomponents.add(new ModeButton(s, this, mod, opY));
                    opY += 15;
                }
                if (s.isSlider()) {
                    this.subcomponents.add(new Slider(s, this, opY));
                    opY += 15;
                }
                if (s.isCheck()) {
                    this.subcomponents.add(new Checkbox(s, this, opY));
                    opY += 15;
                }
            }
        }
        this.subcomponents.add(new Keybind(this, opY));
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
        int opY = offset + 12;
        for (Component comp : this.subcomponents) {
            comp.setOff(opY);
            opY += 12;
        }
    }

    @Override
    public void renderComponent() {
        textHoverAnimate = AnimationUtil.animation1(textHoverAnimate, isHovered ? 2.7f : 2, 0.225f, 0.03f);
        Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, (int) (parent.getX() + parent.getWidth()), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isToggled() ? new Color(14, 14, 14).getRGB() : 0xFF111111));
        GL11.glPushMatrix();
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), (parent.getX() + 2), (parent.getY() + offset) + 2, this.mod.isToggled() ? 0xBDBBB6 : -1); //0xCFD1DF - СЕРЫЙ
        count[0]++;
        if (this.subcomponents.size() > 1)
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.open ? "<" : ">", (parent.getX() + parent.getWidth() - 10 + this.textHoverAnimate), (parent.getY() + offset) + 3, -1);
        GL11.glPopMatrix();
        if (this.open) {
            if (!this.subcomponents.isEmpty()) {
                for (Component comp : this.subcomponents) {
                    comp.renderComponent();
                }
                Gui.drawRect(parent.getX() + 2, (parent.getY() + this.offset + 12), parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGui.color);
            }
        }
    }

    @Override
    public int getHeight() {
        if (this.open) {
            return (12 * (this.subcomponents.size() + 1));
        }
        return 12;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.isHovered = isMouseOnButton(mouseX, mouseY);
        if (!this.subcomponents.isEmpty()) {
            for (Component comp : this.subcomponents) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.mod.toggle();
        }
        if (isMouseOnButton(mouseX, mouseY) && button == 1) {
            this.open = !this.open;
            this.parent.refresh();
        }
        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Component comp : this.subcomponents) {
            comp.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        for (Component comp : this.subcomponents) {
            comp.keyTyped(typedChar, key);
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
            return true;
        }
        return false;
    }
}
