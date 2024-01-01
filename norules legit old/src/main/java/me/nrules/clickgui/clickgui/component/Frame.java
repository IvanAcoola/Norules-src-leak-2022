package me.nrules.clickgui.clickgui.component;

import me.nrules.Main;
import me.nrules.clickgui.clickgui.component.components.Button;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RenderUtils;
import me.nrules.util.TimerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

//Your Imports

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	public double myHeight;
	private int x;
	private int barHeight;
	public float lmao;
	private boolean isDragging;
	public int dragX;
	final int[] count = {1};
	float a = 0;
	float textHoverAnimate;
	float currentValueAnimate;
	int ms = 25;
	double scaling;
	private int height;
	public AnimateEnumFormat state;
	public int dragY;
	public static FontRenderer font = Minecraft.getMinecraft().fontRenderer;
	TimerHelper timerHelper = new TimerHelper();

	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		
		for(Module mod : Main.moduleManager.getModulesInCategory(category)) {
			Button modButton = new Button(mod, this, tY);
			this.components.add(modButton);
			tY += 12;
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, Color.BLACK.getRGB());
		GL11.glPushMatrix();
		font.drawString(this.category.name(), (this.x + 12), (this.y) + 2, -1);
		font.drawString(this.open ? "<" : ">", (this.x + this.width - 10) + 3, (int)(this.y + 2.5f), -1);
		GL11.glPopMatrix();
		if(this.open) {
			if (!this.components.isEmpty()) {
				Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), rainbow1(count[0] * 89));
				Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, rainbow1(count[0] * 89));
				Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), rainbow1(count[0] * 89));
				count[0]++;
				for (Component component : components)
				{
					component.renderComponent();
				}
			}
		}
	}

	private void handleScissorBox() {
		int height = this.height;
		switch (this.state) {
			case EXPANDING:
				if (this.myHeight < (height + 2)) {
					this.myHeight = RenderUtils.animate((height + 2), this.myHeight, 0.5D);
					break;
				}
				if (this.myHeight < (height + 2))
					break;
				this.state = AnimateEnumFormat.STATIC;
				break;
			case RETRACTING:
				if (this.myHeight > 0.0D) {
					this.myHeight = RenderUtils.animate(0.0D, this.myHeight, 0.5D);
					break;
				}
				if (this.myHeight > 0.0D)
					break;
				this.state = AnimateEnumFormat.STATIC;
				break;
			case STATIC:
				if (this.myHeight > 0.0D && this.myHeight != (height + 2))
					this.myHeight = RenderUtils.animate((height + 2), this.myHeight, 0.5D);
				this.myHeight = clamp(this.myHeight, (height + 2));
				break;
		}
	}

	public enum AnimateEnumFormat {
		RETRACTING, EXPANDING, STATIC;
	}

	private double clamp(double a, double max) {
		if (a < 0.0D)
			return 0.0D;
		if (a > max)
			return max;
		return a;
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}

	public static int rainbow(int delay) {
		float speed = 3200.0F;
		float hue = (float)(System.currentTimeMillis() % (int)speed) + (delay / 2);
		while (hue > speed)
			hue -= speed;
		hue /= speed;
		if (hue > 0.5D)
			hue = 0.5F - hue - 0.5F;
		hue += 0.5F;
		return Color.HSBtoRGB(hue, 0.5F, 1.0F);
	}

	public static int rainbow1(int delay)
	{
		double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 80);
		rainbowState %= 360;
		return Color.getHSBColor((float) (rainbowState / 360f), 0.5f, 1f).getRGB();
	}
	
}
