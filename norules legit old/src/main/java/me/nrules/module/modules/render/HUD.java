package me.nrules.module.modules.render;

import com.google.common.base.Strings;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HUD extends Module {
    public HUD() {
        super("HUD", Category.RENDER);
    }

    private int y;
    public static FontRenderer fr = mc.fontRenderer;
    public static Main customFontRenderer;

    public static class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module arg0, Module arg1) {
            if (mc.fontRenderer.getStringWidth(arg0.getName()) > mc.fontRenderer.getStringWidth(arg1.getName())) {
                return -1;
            }
            if (mc.fontRenderer.getStringWidth(arg0.getName()) > mc.fontRenderer.getStringWidth(arg1.getName())) {
                return 1;
            }
            return 0;
        }
    }

    /*@SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.POTION_ICONS))
        {
            event.setCanceled(true);
        }
    }*/

    @SubscribeEvent
    public void onUpdate(TickEvent.RenderTickEvent event) {
        Collections.sort(ModuleManager.getModules(), new ModuleComparator());
        ScaledResolution sr = new ScaledResolution(mc);

        if (mc.player == null && mc.world == null)
            return;

        if (mc.currentScreen == null && !Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            GL11.glPushMatrix();
            GL11.glScaled(2.0D, 2.0D, 2.0D);
            GL11.glTranslated(3.0D, 3.0D, 0.0D);
            mc.fontRenderer.drawString("NoRules", 0, 0, new Color(39, 220, 196, 165).getRGB());
            GL11.glTranslated(-0.5D, -0.5D, 0.0D);
            mc.fontRenderer.drawString("NoRules", 0, 0, new Color(12, 245, 255, 165).getRGB());
            GL11.glScaled(1.0D, 1.0D, 1.0D);
            GL11.glPopMatrix();
            mc.fontRenderer.drawString("vk.com/nrclient", 5, 22, -6250336);
            GL11.glTranslated(-0.5D, -0.5D, 0.0D);
            mc.fontRenderer.drawString("vk.com/nrclient", 5, 22, -2039584);
//            drawArrayList();

            ArrayList<String> arrayList = new ArrayList();
            ModuleManager.getModules().stream().forEach(asaIBIYOhmSolWc -> {
                if (asaIBIYOhmSolWc.isToggled()) {
                    arrayList.add(String.valueOf(new StringBuilder().append(asaIBIYOhmSolWc.getName())));
                }
            });
            arrayList.sort((string, string2) -> this.mc.fontRenderer.getStringWidth(string2) - this.mc.fontRenderer.getStringWidth(string));
            long l = 0L;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            ScaledResolution scaledResolution = new ScaledResolution(this.mc);
            for (int i = 0; i < arrayList.size(); ++i) {
                String string5 = arrayList.get(i);
                if (Strings.isNullOrEmpty(string5)) {
                    continue;
                }
                int n8 = this.mc.fontRenderer.FONT_HEIGHT;
                int n9 = this.mc.fontRenderer.getStringWidth(string5);
                int n10 = scaledResolution.getScaledWidth() - 1 - n9;
                int n11 = 1 + (n8 + 2) * i;
                int n12 = this.rainbow(n4 + l * 200000000L, 1.0f);
                if (i == 0) {
                    Gui.drawRect((n10 - 2), (n11 - 1), (n10 + this.mc.fontRenderer.getStringWidth(string5)), n11, n12);
                }
                Gui.drawRect((n10 - 2), n11, (n10 + this.mc.fontRenderer.getStringWidth(string5)), (n11 + 11), 1140850688);
                Gui.drawRect((n10 - 3), (n11 - 1), (n10 - 2), (n11 + 11), n12);
                Gui.drawRect(n5, n7, (n5 + (n6 - this.mc.fontRenderer.getStringWidth(string5))), (n7 + 1), n12);
                n6 = this.mc.fontRenderer.getStringWidth(string5);
                n5 = n10 - 2;
                n7 = n11 + 10;
                Gui.drawRect((n10 + this.mc.fontRenderer.getStringWidth(string5)), (n11 - 1), (n10 + this.mc.fontRenderer.getStringWidth(string5) + 1), (n11 + 11), n12);
                if (i == arrayList.size() - 1) {
                    Gui.drawRect((n10 - 2), (n11 + 10), (n10 + this.mc.fontRenderer.getStringWidth(string5)), (n11 + 11), n12);
                }
                this.mc.fontRenderer.drawString(string5, n10, n11 + 1, n12);
                ++l;
                ++n4;
            }
            arrayList.clear();
        }
    }

    private static void drawArrayList() {
        Collections.sort(Main.moduleManager.modules, new ModuleComparator());
        int count = 0;
        final int[] counter = {1};
        ScaledResolution sr = new ScaledResolution(mc);
        for (Module m : ModuleManager.getModules()) {
            if (m.isToggled()) {
                int offset = (count * (fr.FONT_HEIGHT + 5));
                Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), offset, (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), fr.FONT_HEIGHT, Color.black.getRGB());
                Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 6), offset, sr.getScaledWidth(), 12 + offset + 2, Color.black.getRGB());
                Gui.drawRect(sr.getScaledWidth() - 2, offset, sr.getScaledWidth(), offset + 15, rainbow1(counter[0] * 350));
                int n = 0;
                counter[0]++;
                fr.drawString(m.getName(), (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 4), 4 + offset, -1);
                count++;
            }
        }
    }

           /* if (mode.equalsIgnoreCase("Rainbow")) {
                if (mc.currentScreen == null) {

                    Gui.drawRect(1, 1, 65, 15, Color.BLACK.getRGB());
                    GL11.glPushMatrix();
                    GL11.glScaled(1.5, 1.5, 1.5);
                    final int[] counter = {1};
                    mc.fontRenderer.drawString("N", 2, 2, rainbow1(counter[0] * 2));
                    mc.fontRenderer.drawString("o", 8, 2, -1);
                    mc.fontRenderer.drawString("R", 15, 2, rainbow1(counter[0] * 2));
                    counter[0]++;
                    mc.fontRenderer.drawString("u", 21, 2, -1);
                    mc.fontRenderer.drawString("l", 27, 2, -1);
                    mc.fontRenderer.drawString("e", 30, 2, -1);
                    mc.fontRenderer.drawString("s", 36, 2, -1);
                    GL11.glPopMatrix();

                    ModuleManager.modules.sort(Comparator.comparingInt(m -> fr.getStringWidth(((Module) m).getName())).reversed());

                    ScaledResolution sr1 = new ScaledResolution(mc);

                    int y = 2;
                    for (Module m : ModuleManager.getModules()) {
                        if (!m.isToggled())
                            continue;

                        Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.name) - 5, y, sr.getScaledWidth(), fr.FONT_HEIGHT + y, 0x70000000);
                        fr.drawString(m.getName(), sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 4, y - 1, astofloc((y * 25)), true);


                        y += fr.FONT_HEIGHT;
                    }
                }
            }
        }*/

    private static void renderArrayList(ScaledResolution sr) {
        ModuleManager.getModules().sort(new ModuleComparator());
        int count = 0;

        for (PotionEffect potionEffect : mc.player.getActivePotionEffects()) {
            if (potionEffect.getPotion().isBadEffect()) {
                count = 2 * 2;
            }

            if (potionEffect.getPotion().isBeneficial()) {
                count = 2;
            }
        }


        final int[] counter = {1};
        for (Module m : ModuleManager.getModules()) {
            if (m.isToggled()) {
                int offset = (count * (fr.FONT_HEIGHT + 5));
                Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), offset, (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 10), fr.FONT_HEIGHT, 0x99000000);
                Gui.drawRect((sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 6), offset, sr.getScaledWidth(), 12 + offset + 2, 0x99000000);
                Gui.drawRect(sr.getScaledWidth() - 2, offset, sr.getScaledWidth(), offset + 15, rainbow1(counter[0] * 350));
                int n = 0;
                counter[0]++;
                fr.drawString(m.getName(), (sr.getScaledWidth() - fr.getStringWidth(m.getName()) - 4), 4 + offset, -1);
                count++;
            }
        }
    }

    public static int rainbow(long offset, float v) {
        float hue = (float) (System.nanoTime() + offset) / 5.0E9F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F))), 16);
        Color c = new Color((int) color);
        return (new Color((float) c.getRed() / 255.0F, (float) c.getGreen() / 255.0F, (float) c.getGreen() / 255, (float) c.getAlpha() / 255.0F)).getRGB();
    }

    public static int rainbow1(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360f), 0.5f, 1f).getRGB();
    }

    public static int astofloc(int delay) {
        float speed = 3200.0F;
        float hue = (float) (System.currentTimeMillis() % (int) speed) + (delay / 2);
        while (hue > speed)
            hue -= speed;
        hue /= speed;
        if (hue > 0.5D)
            hue = 0.5F - hue - 0.5F;
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1.0F);
    }


}

