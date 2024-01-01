package me.nrules.newclickgui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.Main;
import me.nrules.clickgui.particle.ParticleGenerator;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.render.ClickGUI;
import me.nrules.module.modules.render.HUD;
import me.nrules.newclickgui.components.Component;
import me.nrules.newclickgui.components.sub.Checkbox;
import me.nrules.newclickgui.components.sub.*;
import me.nrules.util.RenderUtils;
import me.nrules.util.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CSGOGui extends GuiScreen {
    public CategoryType categoryType = CategoryType.COMBAT;
    public Category categoryType1 = Category.COMBAT;

    double scaling;
    public Module currentModule = null;
    public boolean listening = false;

    private ParticleGenerator particleSystem = new ParticleGenerator(45);
    public boolean renderCheck = false;
    public static CSGOGui instance;
    private ArrayList<me.nrules.newclickgui.components.Component> subcomponents;
    // UnicodeFontRenderer ufr;
    public int offset;

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x2 * (float) factor), (int) (((float) scale.getScaledHeight() - y22) * (float) factor), (int) ((x22 - x2) * (float) factor), (int) ((y22 - y2) * (float) factor));
    }

    float lastY;
    int index;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if ((this.scaling <= 1))
            this.scaling += 0.1;

        if (scaling > 1)
            scaling = 1f;

        GL11.glPushMatrix();
        GL11.glTranslated(width / 4, 0, 0);
        GL11.glScalef((float) scaling, (float) scaling, (float) scaling);
        GL11.glTranslated(-width / 4, 0, 0);

        if (Main.settingsManager.getSettingByName(Main.moduleManager.getModule(ClickGUI.class), "Particle").getValBoolean()) {
            this.particleSystem.tick(10);
            this.particleSystem.render();
        }

        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 10;
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset > 100) {
                    this.offset = 100;
                }
            } else if (wheel > 0) {
                this.offset -= 10;
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset > 100) {
                    this.offset = 100;
                }
            }
        }
        for (Component comp : this.subcomponents) {
            comp.updateComponent(mouseX, mouseY);
        }
        //this.drawRect(55, 20, 430, 260, 0xFF111111);

        RenderUtils.drawRoundedRect(20, 22, 400, 278, 10, new Color(34, 34, 34, 255));
        RenderUtils.drawRoundedRect(22, 22, 100, 278, 10, new Color(17, 17, 17));

        RenderUtils.drawRoundedRect(207, 28 + offset * 2f, 210, 70 + offset * 2f, 1, Color.WHITE);
        final float hue = (float) Color.WHITE.getRGB();
        //                           colorSaturation  colorBrightness
        Color color = Color.getHSBColor(hue, (float) 250, (float) 1000);
        //  RenderUtils.instance.draw2DImage(new ResourceLocation("textures/sword.png"), 22,  48, 20, 20, Color.WHITE);
//        float targetY = (this.index * 9);
//        float diff = targetY - this.lastY;
//        targetY = this.lastY;
//        this.lastY += diff / 4.0F;
//
        if (this.categoryType == CategoryType.COMBAT) {
//            this.drawRect(22, 22, 24, 48, color.getRGB());
            index = 1;
        }
        if (this.categoryType == CategoryType.MOVEMENT) {
//            this.drawRect(22, 52, 24, 78, color.getRGB());
            index = 4;
        }
        if (this.categoryType == CategoryType.PLAYER) {
//            this.drawRect(22, 112, 24, 138, color.getRGB());
            index = 11;
        }
        if (this.categoryType == CategoryType.GHOST) {
//            this.drawRect(22, 142, 24, 168, color.getRGB());
            index = 14;
        }
        if (this.categoryType == CategoryType.FUN) {
//            this.drawRect(22, 172, 24, 198, color.getRGB());
            index = 18;
        }
        if (this.categoryType == CategoryType.MISC) {
//            this.drawRect(22, 202, 24, 228, color.getRGB());
            index = 21;
        }
        if (this.categoryType == CategoryType.RENDER) {
//            this.drawRect(22, 82, 24, 108, color.getRGB());
            index = 8;
        }
        if (this.categoryType == CategoryType.CONFIG) {
//            this.drawRect(22, 232, 24, 258, color.getRGB());
            index = 24;
        }
//        Gui.drawRect(22, ((12) + (int) targetY) + (int) 1.5, 24, (int) ((9 + 10) + targetY + 20.0F + 2.0F), -1);
        String[] buttons = {"combat", "movement", "player", "ghost", "fun", "misc", "render", "config"};

        int count = 0;

        for (String name : buttons) {
            float x = 33;
            float y = 30 + (count * 30);

            boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(name) - 3 && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
            final float hue2 = (float) Color.WHITE.getRGB();

            if (categoryType == CategoryType.COMBAT) {
                if (name.contains("combat")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("combat")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }

            if (categoryType == CategoryType.MOVEMENT) {
                if (name.contains("movement")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("movement")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }

            if (categoryType == CategoryType.PLAYER) {
                if (name.contains("player")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("player")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }
            if (categoryType == CategoryType.GHOST) {
                if (name.contains("ghost")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("ghost")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }
            if (categoryType == CategoryType.FUN) {
                if (name.contains("fun")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("fun")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }
            if (categoryType == CategoryType.MISC) {
                if (name.contains("misc")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("misc")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }
            if (categoryType == CategoryType.RENDER) {
                if (name.contains("render")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("render")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }
            if (categoryType == CategoryType.CONFIG) {
                if (name.contains("config")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), HUD.astofloc(1000));
                } else if (!name.contains("config")) {
                    mc.fontRenderer.drawStringWithShadow(name, 60 - 29, 30 + (count * 30), -1);
                }
            }


            count++;
        }
        GL11.glPushMatrix();
        this.prepareScissorBox(20, 20, 600, 280);
        GL11.glEnable(3089);

        // if(this.categoryType == CategoryType.COMBAT)
        //"COMBAT", "MOVEMENT", "PLAYER", "GHOST", "FUN", "MISC", "RENDER", "CONFIG"
        if (this.categoryType == CategoryType.COMBAT) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.COMBAT) {
                    // тут был rect2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.COMBAT) {

                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }

        }

        if (this.categoryType == CategoryType.MOVEMENT) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.MOVEMENT) {
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.MOVEMENT) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    //  RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }

                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.PLAYER) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.PLAYER) {
                    // ТУТ БЫЛ RECT2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.PLAYER) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    //RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.GHOST) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.GHOST) {
                    // rect2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.GHOST) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    // RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.FUN) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.FUN) {
                    // RECT 2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.FUN) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    //  RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.MISC) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.MISC) {
                    // RECT 2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.MISC) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    // RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.RENDER) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.RENDER) {
                    // rect 2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.RENDER) {
                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;

                    //RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        if (this.categoryType == CategoryType.CONFIG) {
            int count2 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {
                if (m.getCategory() == Category.CONFIG) {
                    //rect 2
                    RenderUtils.drawRoundedRect2(102, 27 + (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());
                    count2++;
                }
            }
            int count1 = 0;
            for (Module m : Main.moduleManager.getModuleList()) {

                if (m.getCategory() == Category.CONFIG) {

                    float x = 110;
                    float y = 30 + (count1 * 15) - this.offset;
                    boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT;
                    // RenderUtils.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());
                    if (m != this.currentModule) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.darker().getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else if (m.getKey() != Keyboard.KEY_NONE) {
                        mc.fontRenderer.drawString(m.getName().toLowerCase() + ChatFormatting.GRAY + " (" + Keyboard.getKeyName(m.getKey()).toLowerCase() + ")", 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    } else {
                        mc.fontRenderer.drawString(m.getName().toLowerCase(), 110, 29 + (count1 * 15) - this.offset, hovered ? m.isToggled() ? Color.lightGray.getRGB() : Color.lightGray.getRGB() : m.isToggled() ? Color.lightGray.darker().getRGB() : -1);
                    }
                    if (Main.settingsManager.hasSettings(m) && m != this.currentModule) {
                        mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                    } else {
                        if (m.getKey() != Keyboard.KEY_NONE && Main.settingsManager.hasSettings(m)) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        } else if (Main.settingsManager.hasSettings(m) && m.getKey() == 0) {
                            mc.fontRenderer.drawString("...", 190, 29 + (count1 * 15) - this.offset, -1);
                        }
                    }
                    count1++;
                }
            }
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();

        if (!this.subcomponents.isEmpty()) {
            for (me.nrules.newclickgui.components.Component comp : this.subcomponents) {
                comp.renderComponent();
            }
        }

        GL11.glPopMatrix();
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

        for (Component comp : this.subcomponents) {
            comp.mouseClicked(mouseX, mouseY, button);
        }
        if (button == 0) {
            if (this.categoryType == CategoryType.COMBAT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.COMBAT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.MOVEMENT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MOVEMENT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.PLAYER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.PLAYER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.GHOST) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.GHOST) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.FUN) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.FUN) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.MISC) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MISC) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.RENDER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.RENDER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.CONFIG) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.CONFIG) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            m.toggle();
                        }
                        count1++;
                    }
                }
            }
        }

        if (button == 2) {
            if (this.categoryType == CategoryType.COMBAT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.COMBAT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.MOVEMENT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MOVEMENT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.PLAYER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.PLAYER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.GHOST) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.GHOST) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.FUN) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.FUN) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.MISC) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MISC) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.RENDER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.RENDER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.CONFIG) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.CONFIG) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.currentModule = m;
                            this.listening = true;
                        }
                        count1++;
                    }
                }
            }
        }
        // "COMBAT", "MOVEMENT", "PLAYER", "GHOST", "FUN", "MISC", "RENDER", "CONFIG"
        if (button == 1) {
            if (this.categoryType == CategoryType.COMBAT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.COMBAT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));

                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));

                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }

            }
            if (this.categoryType == CategoryType.MOVEMENT) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MOVEMENT) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15) {
                                        count2 = 10;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.PLAYER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.PLAYER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;

                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.GHOST) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.GHOST) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                //TODO test
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new me.nrules.newclickgui.components.sub.Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.FUN) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.FUN) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                //TODO test
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new me.nrules.newclickgui.components.sub.Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.MISC) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.MISC) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                //TODO test
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new me.nrules.newclickgui.components.sub.Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.RENDER) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.RENDER) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                //TODO test
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 15 && !(count >= 17)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 17 && !(count < 17)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new me.nrules.newclickgui.components.sub.Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }
            if (this.categoryType == CategoryType.CONFIG) {
                int count1 = 0;
                for (Module m : Main.moduleManager.getModuleList()) {
                    if (m.getCategory() == Category.CONFIG) {
                        float x = 110;
                        float y = 30 + (count1 * 15) - this.offset;

                        if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(m.getName()) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                            this.subcomponents.clear();
                            if (Main.settingsManager.getSettingsByMod(m) != null) {
                                int count = 0;
                                //TODO test
                                int count2 = 0;
                                for (Setting s : Main.settingsManager.getSettingsByMod(m)) {
                                    if (count >= 16 && !(count >= 18)) {
                                        count2 = 11;
                                        count = 0;
                                    }
                                    if (count >= 18 && !(count < 18)) {
                                        count2 = 20;
                                        count = 1;
                                    }
                                    if (s.isCombo()) {

                                        this.subcomponents.add(new ModeButton(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        //opY += 12;
                                    }
                                    if (s.isSlider()) {
                                        this.subcomponents.add(new Slider(s, 350 + (count2 * 15), 24 + (count * 15)));
                                        //this.subcomponents.add(new Slider(s, this, opY));
                                        count++;
                                        //opY += 12;
                                    }
                                    if (s.isCheck()) {
                                        this.renderCheck = true;
                                        this.subcomponents.add(new Checkbox(s, 350 + (count2 * 15), 30 + (count * 15)));
                                        ///this.subcomponents.add(new Checkbox(s, this, opY));
                                        ///opY += 12;
                                    }
                                    if (s.isHueSlider()) {
                                        this.subcomponents.add(new HueSlider(s, 250, 30 + (count * 15)));
                                    }
                                    if (s.isBrightSlider()) {
                                        this.subcomponents.add(new BrightnessSlider(s, 250, 35 + (count * 15)));
                                    }
                                    if (s.isSaturationSlider()) {
                                        this.subcomponents.add(new SaturationSlider(s, 250, 37 + (count * 15)));
                                        count++;
                                    }
                                    count++;
                                }
                            }
                        }
                        count1++;
                    }
                }
            }

        }
        // АБОБА
        String[] buttons = {"COMBAT", "MOVEMENT", "PLAYER", "GHOST", "FUN", "MISC", "RENDER", "CONFIG"};

        int count = 0;
        for (String name : buttons) {
            float x = 33;
            float y = 30 + (count * 30);

            if (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(name) - 3 && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
                switch (name) {
                    case "COMBAT":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.COMBAT;
                        break;

                    case "MOVEMENT":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.MOVEMENT;
                        break;

                    case "PLAYER":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.PLAYER;
                        break;

                    case "GHOST":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.GHOST;
                        break;

                    case "FUN":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.FUN;
                        break;

                    case "MISC":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.MISC;
                        break;
                    case "RENDER":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.RENDER;
                        break;
                    case "CONFIG":
                        this.subcomponents.clear();
                        this.offset = 0;
                        this.categoryType = CategoryType.CONFIG;
                        break;
                }
            }

            count++;
        }

    }


    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (listening && this.currentModule != null) {
            if (keyCode != Keyboard.KEY_Q) {
//                Utils.printMessage("§9" + this.currentModule.getName() + " §fhas been §7bound §fto §a" + Keyboard.getKeyName(keyCode));
                this.currentModule.setKey(keyCode);
            } else {
//                Utils.printMessage("Unbound '" + currentModule.getName() + "'");
                this.currentModule.setKey(Keyboard.KEY_NONE);
            }
            listening = false;
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (me.nrules.newclickgui.components.Component component : this.subcomponents) {
            component.mouseReleased(mouseX, mouseY, state);
        }
    }


    @Override
    public void initGui() {
        this.subcomponents = new ArrayList<me.nrules.newclickgui.components.Component>();
        this.scaling = 0;
        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed() {
        if (mc.entityRenderer.getShaderGroup() != null) {
            mc.entityRenderer.stopUseShader();
        }
    }


    public enum CategoryType {
        COMBAT,
        MOVEMENT,
        RENDER,
        PLAYER,
        FUN,
        GHOST,
        CONFIG,
        MISC;
    }

}


