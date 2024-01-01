package me.nrules.clickgui.clickgui;

import me.nrules.Main;
import me.nrules.clickgui.clickgui.component.Component;
import me.nrules.clickgui.clickgui.component.Frame;
import me.nrules.clickgui.particle.ParticleGenerator;
import me.nrules.module.Category;
import me.nrules.module.modules.render.ClickGUI;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class ClickGui extends GuiScreen {

    public static ArrayList<Frame> frames;
    public static int color = -1;
    double scaling;

    int ms;
    Random random = new Random();
    TimerHelper timerHelper;
    public static FontRenderer font = Minecraft.getMinecraft().fontRenderer;
    public float lmao;
    private ParticleGenerator particleSystem = new ParticleGenerator(45);

    public ClickGui() {
        this.frames = new ArrayList<Frame>();
        int frameX = 5;
        for (Category category : Category.values()) {
            Frame frame = new Frame(category);
            frame.setX(frameX);
            frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
    }

    public ParticleGenerator getParticleSystem() {
        return this.particleSystem;
    }

    @Override
    public void initGui() {
        scaling = 0;
        lmao = 0.0F;
        if (OpenGlHelper.shadersSupported) {
            Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        final ScaledResolution res = new ScaledResolution(mc);

        for (Frame frame : frames) {
            if ((this.scaling <= 1))
                this.scaling += 0.014;

            GL11.glPushMatrix();
            if (scaling < 255.0F) {

                scaling += 0.35F;
            } else {

                scaling = 255.0F;
            }
            if (this.lmao < 1.0F) {
                GL11.glScaled(1.0D, this.lmao, 1.0D);
                this.lmao += 0.035F;
            } else {
                GL11.glScaled(1.0D, 1.0D, 1.0D);
            }

            if (scaling > 255.0F) {
                scaling = 255.0F;
            }

            frame.renderFrame(font);
            frame.updatePosition(mouseX, mouseY);
            GL11.glPopMatrix();
            if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ClickGUI.class), "Particle").getValBoolean()) {
                this.particleSystem.tick(4);
                this.particleSystem.render();
            }
            for (Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }

        }
    }

    public static int rainbow1(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360f), 0.5f, 1f).getRGB();
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (Frame frame : frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    for (Component component : frame.getComponents()) {
                        component.mouseClicked(mouseX, mouseY, mouseButton);
                    }
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (Frame frame : frames) {
            if (frame.isOpen() && keyCode != 1) {
                if (!frame.getComponents().isEmpty()) {
                    for (Component component : frame.getComponents()) {
                        component.keyTyped(typedChar, keyCode);
                    }
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.setDrag(false);
        }
        for (Frame frame : frames) {
            if (frame.isOpen()) {
                if (!frame.getComponents().isEmpty()) {
                    for (Component component : frame.getComponents()) {
                        component.mouseReleased(mouseX, mouseY, state);
                    }
                }
            }
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.stopUseShader();
        }
        super.onGuiClosed();

    }

    @Override
    public boolean doesGuiPauseGame() {
        return !Main.moduleManager.getModule("InvMove").isToggled();
    }
}
