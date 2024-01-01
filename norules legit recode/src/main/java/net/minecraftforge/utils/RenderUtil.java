package net.minecraftforge.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtil {

    public static Minecraft mc = Minecraft.getMinecraft();
    private static float defaultSpeed = 0.125f;

    public static float moveUD(float current, float end, float minSpeed) {
        return moveUD(current, end, defaultSpeed, minSpeed);
    }
    public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;

        if (movement > 0) {
            movement = Math.max(minSpeed, movement);
            movement = Math.min(end - current, movement);
        } else if (movement < 0) {
            movement = Math.min(-minSpeed, movement);
            movement = Math.max(end - current, movement);
        }

        return current + movement;
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, int smooth, Color color) {
        Gui.drawRect(((int) left + smooth), (int) top, ((int) right - smooth), (int) bottom, color.getRGB());
        Gui.drawRect((int) left, ((int) top + smooth), (int) right, ((int) bottom - smooth), color.getRGB());
        drawFilledCircle((int) left + smooth, (int) top + smooth, smooth, color);
        drawFilledCircle((int) right - smooth, (int) top + smooth, smooth, color);
        drawFilledCircle((int) right - smooth, (int) bottom - smooth, smooth, color);
        drawFilledCircle((int) left + smooth, (int) bottom - smooth, smooth, color);
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        int sections = 50;
        double dAngle = 6.283185307179586D / sections;
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++) {
            float x = (float) (radius * Math.sin(i * dAngle));
            float y = (float) (radius * Math.cos(i * dAngle));
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static double animate(double target, double current, double speed) {
        boolean larger;
        boolean bl = larger = target > current;
        if (speed < 0.0) speed = 0.0;
        else if (speed > 1.0) speed = 1.0;

        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        return  larger ? (current += factor) : (current -= factor);
    }

    public static double animate(double value, double target) {
        return value + (target * 100 / 100 - value) / 2.0D;
    }

    public static void drawRect(double d0, double d1, double d2, double d3, int i) {
        double d4;

        if (d0 < d2) {
            d4 = d0;
            d0 = d2;
            d2 = d4;
        }

        if (d1 < d3) {
            d4 = d1;
            d1 = d3;
            d3 = d4;
        }

        float f = (float) (i >> 24 & 255) / 255.0F;
        float f1 = (float) (i >> 16 & 255) / 255.0F;
        float f2 = (float) (i >> 8 & 255) / 255.0F;
        float f3 = (float) (i & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f1, f2, f3, f);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(d0, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d3, 0.0D).endVertex();
        worldrenderer.pos(d2, d1, 0.0D).endVertex();
        worldrenderer.pos(d0, d1, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
        sideLength /= 2;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
        if (!filled) {
            GL11.glLineWidth(1);
        }
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);

        for (double i = 0; i <= amountOfSides; i++) {
            double angle = i * (Math.PI * 2) / amountOfSides;
            GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
        }

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableAlpha();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawGradientRect(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, int p_73733_5_, int p_73733_6_) {
        float lvt_7_1_ = (float) (p_73733_5_ >> 24 & 255) / 255.0F;
        float lvt_8_1_ = (float) (p_73733_5_ >> 16 & 255) / 255.0F;
        float lvt_9_1_ = (float) (p_73733_5_ >> 8 & 255) / 255.0F;
        float lvt_10_1_ = (float) (p_73733_5_ & 255) / 255.0F;
        float lvt_11_1_ = (float) (p_73733_6_ >> 24 & 255) / 255.0F;
        float lvt_12_1_ = (float) (p_73733_6_ >> 16 & 255) / 255.0F;
        float lvt_13_1_ = (float) (p_73733_6_ >> 8 & 255) / 255.0F;
        float lvt_14_1_ = (float) (p_73733_6_ & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator lvt_15_1_ = Tessellator.getInstance();
        BufferBuilder lvt_16_1_ = lvt_15_1_.getBuffer();
        lvt_16_1_.begin(7, DefaultVertexFormats.POSITION_COLOR);
        lvt_16_1_.pos((double) p_73733_3_, (double) p_73733_2_, (double) 0).color(155, 155, 155, 128).endVertex();
        lvt_16_1_.pos((double) p_73733_1_, (double) p_73733_2_, (double) 0).color(155, 155, 155, 128).endVertex();
        lvt_16_1_.pos((double) p_73733_1_, (double) p_73733_4_, (double) 0).color(155, 155, 155, 128).endVertex();
        lvt_16_1_.pos((double) p_73733_3_, (double) p_73733_4_, (double) 0).color(155, 155, 155, 128).endVertex();
        lvt_15_1_.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
