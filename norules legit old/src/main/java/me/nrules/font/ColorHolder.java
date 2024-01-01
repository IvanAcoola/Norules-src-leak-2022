package me.nrules.font;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class ColorHolder {

	private int r;
	private int g;
	private int b;
	private int a;

	public final int getR() {
		return this.r;
	}

	public final void setR(int r) {
		this.r = r;
	}

	public final int getG() {
		return this.g;
	}

	public final void setG(int g) {
		this.g = g;
	}

	public final int getB() {
		return this.b;
	}

	public final void setB(int b) {
		this.b = b;
	}

	public final int getA() {
		return this.a;
	}

	public final void setA(int a) {
		this.a = a;
	}

	public final ColorHolder brighter() {
		return new ColorHolder(Math.min(this.r + 10, 255), Math.min(this.g + 10, 255), Math.min(this.b + 10, 255),
				this.a);
	}

	public final ColorHolder darker() {
		return new ColorHolder(Math.max(this.r - 10, 0), Math.max(this.g - 10, 0), Math.max(this.b - 10, 0), this.a);
	}

	public final void setGLColor() {
		GL11.glColor4f(this.r / 255.0f, this.g / 255.0f, this.b / 255.0f, this.a / 255.0f);
	}

	public final void becomeHex(final int hex) {
		this.r = (hex & 0xFF0000) >> 16;
		this.g = (hex & 0xFF00) >> 8;
		this.b = (hex & 0xFF);
		this.a = 255;
	}

	public final ColorHolder fromHex(final int hex) {
		final ColorHolder n = new ColorHolder(0, 0, 0);
		n.becomeHex(hex);
		return n;
	}

	public final int toHex() {
		return 0xFF000000 | (this.r & 0xFF) << 16 | (this.g & 0xFF) << 8 | (this.b & 0xFF);
	}

	public final Color toJavaColour() {
		return new Color(this.r, this.g, this.b, this.a);
	}

	public final ColorHolder clone() {
		return new ColorHolder(this.r, this.g, this.b, this.a);
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		final ColorHolder colorHolder = (ColorHolder) other;
		return this.r == ((ColorHolder) other).r && this.g == ((ColorHolder) other).g
				&& this.b == ((ColorHolder) other).b && this.a == ((ColorHolder) other).a;
	}

	@Override
	public int hashCode() {
		int result = this.r;
		result = 31 * result + this.g;
		result = 31 * result + this.b;
		result = 31 * result + this.a;
		return result;
	}

	public ColorHolder(final int r, final int g, final int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}

	public ColorHolder(final int r, final int g, final int b, final int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public ColorHolder(final Color color) {
		this.r = color.getRed();
		this.g = color.getGreen();
		this.b = color.getBlue();
		this.a = color.getAlpha();
	}

}
