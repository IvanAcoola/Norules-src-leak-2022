package me.nrules.particles;

import me.nrules.clickgui.clickgui.ClickGui;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import javax.vecmath.Vector2f;

public final class ParticleSystem {

    private final int PARTS = 100;
    private final Particle[] particles = new Particle[PARTS];

    private ScaledResolution scaledResolution;

    public ParticleSystem(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
        for (int i = 0; i < PARTS; i++) {
            this.particles[i] = new Particle(new Vector2f((float) (Math.random() * scaledResolution.getScaledWidth()), (float) (Math.random() * scaledResolution.getScaledHeight())));
        }
    }

    public void update() {
        for (int i = 0; i < PARTS; i++) {
            final Particle particle = this.particles[i];
            if (this.scaledResolution != null) {
                final boolean isOffScreenX = particle.getPos().x > this.scaledResolution.getScaledWidth() || particle.getPos().x < 0;
                final boolean isOffScreenY = particle.getPos().y > this.scaledResolution.getScaledHeight() || particle.getPos().y < 0;
                if (isOffScreenX || isOffScreenY) {
                    particle.respawn(this.scaledResolution);
                }
            }
            particle.update();
        }
    }

    public void render(int mouseX, int mouseY) {
        final boolean ClickGui = Minecraft.getMinecraft().currentScreen instanceof ClickGui && Minecraft.getMinecraft().player != null;

        for (int i = 0; i < PARTS; i++) {
            final Particle particle = this.particles[i];

            if (ClickGui) {
                for (int j = 1; j < PARTS; j++) {
                    if (i != j) {
                        final Particle otherParticle = this.particles[j];
                        final Vector2f diffPos = new Vector2f(particle.getPos());
                        diffPos.sub(otherParticle.getPos());
                        final float diff = diffPos.length();
                        final int distance = 80 / (scaledResolution.getScaleFactor() <= 1 ? 3 : scaledResolution.getScaleFactor());
                        if (diff < distance) {
                            final int lineAlpha = (int) map(diff, distance, 0, 0, 127);
                            if (lineAlpha > 8) {
                                RenderUtils.drawLine(particle.getPos().x + particle.getSize() / 2.0f, particle.getPos().y + particle.getSize() / 2.0f, otherParticle.getPos().x + otherParticle.getSize() / 2.0f, otherParticle.getPos().y + otherParticle.getSize() / 2.0f, 1.0f, changeAlpha(0xFF9900EE, lineAlpha));
                            }
                        }
                    }
                }
            }

            particle.render(mouseX, mouseY);
        }
    }

    public static int changeAlpha(int origColor, int userInputedAlpha) {
        origColor = origColor & 0x00FFFFFF;
        return (userInputedAlpha << 24) | origColor;
    }

    public static double map(double value, double a, double b, double c, double d) {
        // first map value from (a..b) to (0..1)
        value = (value - a) / (b - a);
        // then map it from (0..1) to (c..d) and return it
        return c + value * (d - c);
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public void setScaledResolution(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }
}
