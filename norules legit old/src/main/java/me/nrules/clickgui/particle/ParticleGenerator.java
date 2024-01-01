package me.nrules.clickgui.particle;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleGenerator {
    private static final float SPEED = 0.1F;
    private List particleList = new ArrayList();
    private int dist = 40;

    public ParticleGenerator(int initAmount) {
        this.addParticles(initAmount);
    }

    public void tick(int delta) {
        if (Mouse.isButtonDown(0)) {
            this.addParticles(0);
        }

        Iterator var2 = this.particleList.iterator();

        while(var2.hasNext()) {
            Particle particle = (Particle)var2.next();
            particle.tick(delta, 0.1F);
        }

    }

    public void render() {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        Iterator var1 = this.particleList.iterator();

        label44:
        while(var1.hasNext()) {
            Particle particle = (Particle)var1.next();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, particle.getAlpha() / 255.0F);
            GL11.glPointSize(particle.getSize());
            GL11.glBegin(0);
            GL11.glVertex2f(particle.getX(), particle.getY());
            GL11.glEnd();
            int i = Mouse.getEventX() * Minecraft.getMinecraft().currentScreen.width / Minecraft.getMinecraft().displayWidth;
            int j = Minecraft.getMinecraft().currentScreen.height - Mouse.getEventY() * Minecraft.getMinecraft().currentScreen.height / Minecraft.getMinecraft().displayHeight - 1;
            float nearestDistance = 0.0F;
            Particle nearestParticle = null;
            Iterator var7 = this.particleList.iterator();

            while(true) {
                Particle particle1;
                float distance;
                do {
                    do {
                        do {
                            if (!var7.hasNext()) {
                                if (nearestParticle != null) {
                                    float alpha = Math.min(1.0F, Math.min(1.0F, 1.0F - nearestDistance / (float)this.dist));
                                    this.drawLine(particle.getX(), particle.getY(), nearestParticle.getX(), nearestParticle.getY(), 1.0F, 1.0F, 1.0F, alpha);
                                }
                                continue label44;
                            }

                            particle1 = (Particle)var7.next();
                            distance = particle.getDistanceTo(particle1);
                        } while(distance > (float)this.dist);
                    } while(this.distance((float)i, (float)j, particle.getX(), particle.getY()) > (double)this.dist && this.distance((float)i, (float)j, particle1.getX(), particle1.getY()) > (double)this.dist);
                } while(nearestDistance > 0.0F && distance > nearestDistance);

                nearestDistance = distance;
                nearestParticle = particle1;
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private void drawLine(float x, float y, float x1, float y1, float r, float g, float b, float alpha) {
        GL11.glColor4f(r, g, b, alpha);
        GL11.glLineWidth(0.1F);
        GL11.glBegin(1);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x1, y1);
        GL11.glEnd();
    }

    public double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((double)((x - x1) * (x - x1) + (y - y1) * (y - y1)));
    }

    public void addParticles(int amount) {
        for(int i = 0; i < amount; ++i) {
            this.particleList.add(Particle.generateParticle());
        }

    }
}