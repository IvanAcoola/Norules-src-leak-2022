package me.nrules.clickgui.particle;

import org.lwjgl.opengl.Display;

import javax.vecmath.Vector2f;
import java.util.Random;

public class Particle {
    private static final Random random = new Random();
    private Vector2f velocity;
    private Vector2f pos;
    private float size;
    private float alpha;

    public Particle(Vector2f velocity, float x, float y, float size) {
        this.velocity = velocity;
        this.pos = new Vector2f(x, y);
        this.size = size;
    }

    public static Particle generateParticle() {
        Vector2f velocity = new Vector2f((float) (Math.random() * 2.0D - 1.0D), (float) (Math.random() * 2.0D - 1.0D));
        float x = (float) random.nextInt(Display.getWidth());
        float y = (float) random.nextInt(Display.getHeight());
        float size = (float) (Math.random() * 4.0D) + 1.0F;
        return new Particle(velocity, x, y, size);
    }

    public float getAlpha() {
        return this.alpha;
    }

    public Vector2f getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public float getX() {
        return this.pos.getX();
    }

    public void setX(float x) {
        this.pos.setX(x);
    }

    public float getY() {
        return this.pos.getY();
    }

    public void setY(float y) {
        this.pos.setY(y);
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void tick(int delta, float speed) {
        Vector2f var10000 = this.pos;
        var10000.x += this.velocity.getX() * (float) delta * speed;
        var10000 = this.pos;
        var10000.y += this.velocity.getY() * (float) delta * speed;
        if (this.alpha < 255.0F) {
            this.alpha += 0.05F * (float) delta;
        }

        if (this.pos.getX() > (float) Display.getWidth()) {
            this.pos.setX(0.0F);
        }

        if (this.pos.getX() < 0.0F) {
            this.pos.setX((float) Display.getWidth());
        }

        if (this.pos.getY() > (float) Display.getHeight()) {
            this.pos.setY(0.0F);
        }

        if (this.pos.getY() < 0.0F) {
            this.pos.setY((float) Display.getHeight());
        }

    }

    public float getDistanceTo(Particle particle1) {
        return this.getDistanceTo(particle1.getX(), particle1.getY());
    }

    public float getDistanceTo(float x, float y) {
        return (float) this.distance(this.getX(), this.getY(), x, y);
    }

    public double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((double) ((x - x1) * (x - x1) + (y - y1) * (y - y1)));
    }
}