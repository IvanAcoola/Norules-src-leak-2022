package me.nrules.helper;

import me.nrules.util.ReflectUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ProfilerHook extends Profiler {

    private Minecraft mc = Minecraft.getMinecraft();
    private String lastSection = "";
    private boolean fontRenderererHooked = false;

    public void startSection(String name) {
        if (name.equals("gui")) {
            if (!this.fontRenderererHooked) {
                this.fontRenderererHooked = true;

                try {
                    Field fontRendererField = ReflectUtils.getField(Minecraft.class, "fontRenderer", "k");
                    Class fontRendererClazz = FontRenderer.class;
                    FontRendererHook fontRendererHook = new FontRendererHook(this.mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.mc.getTextureManager(), false);
                    Field[] var5 = fontRendererClazz.getFields();
                    int var6 = var5.length;

                    int var7;
                    Field f;
                    Field modifiersField;
                    for (var7 = 0; var7 < var6; ++var7) {
                        f = var5[var7];
                        f.setAccessible(true);
                        if (Modifier.isFinal(f.getModifiers())) {
                            modifiersField = ReflectUtils.getField(Field.class, "modifiers");
                            modifiersField.setInt(f, f.getModifiers() & -17);
                        }
                    }

                    var5 = fontRendererClazz.getDeclaredFields();
                    var6 = var5.length;

                    for (var7 = 0; var7 < var6; ++var7) {
                        f = var5[var7];
                        f.setAccessible(true);
                        if (Modifier.isFinal(f.getModifiers())) {
                            modifiersField = ReflectUtils.getField(Field.class, "modifiers");
                            modifiersField.setInt(f, f.getModifiers() & -17);
                        }

                        f.set(fontRendererHook, f.get(this.mc.fontRenderer));
                    }
                    this.mc.fontRenderer = fontRendererHook;

                } catch (Exception var11) {
                    var11.printStackTrace();
                }
            }

            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        }
    }

}
