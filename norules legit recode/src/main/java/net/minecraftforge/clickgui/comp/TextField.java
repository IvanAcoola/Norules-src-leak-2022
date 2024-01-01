package net.minecraftforge.clickgui.comp;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.font.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;


public class TextField {

    private FontRenderer font;

    private String fillText = "";

    private String text = "";

    private boolean focused = false;
    private boolean borders = true;

    private int maxLength;

    private int color;

    private double x;
    private double y;
    private double width;
    private double height;

    private boolean obfedText;

    public TextField(FontRenderer font) {
        this.setColor(0xFF0A0A0A);
        this.font = font;
        maxLength = 80;
        obfedText = false;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
//        if (borders)
//            Render2D.drawBorderedRect(x + 1, y + 1, width - 2, height - 2, 1, 0xFFBEBBBB, color);
//        else
//        Gui.drawRect((int) x, (int) y, (int) width, (int) height, color);

        double diff = Math.max(font.getStringWidth(getText()) + 4 - getWidth(), 0);

        String text = obfedText ? getText().replaceAll("(?s).", "*") : getText();
        double diffY = obfedText ? 1.5 : 0;

//        Render2D.startScissor(x, y, width, height);
//        GL11.glPushMatrix();
//        prepareScissorBox((int) getX(), (int) getY(), (int) width, (int) height);
//        GL11.glEnable(3089);

        if (!getText().isEmpty()) {
            font.drawString(text, (int) (getX() + 10 - diff), (int) (getY() + (getHeight() / 2) - (ForgeInternalHandler.SANS_SMALL.getFontHeight() / 2) + diffY + 2),
                    -1);
        }
        if (focused)
            Gui.drawRect((int) (getX() + 10), (int) getY() + 193, (int) (getX() + 14), (int) getY() + 203,
                    new Color(255, 255, 255, System.currentTimeMillis() % 2000 > 1000 ? 255 : 0).getRGB());

//        Render2D.stopScissor();
//        GL11.glDisable(3089);
//        GL11.glPopMatrix();

        if (!focused && text.isEmpty() && !fillText.isEmpty())
            font.drawString(fillText, (int) (getX() + 10), (int) (getY() + (getHeight() / 2) - (ForgeInternalHandler.SANS_SMALL.getFontHeight() / 2) + 2), 0xFFF8F8, 0.9f);
    }

    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY, x, y, width, height) && mouseButton == 0)
            focused = true;
        else
            focused = false;
    }

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseX - width <= x && mouseY >= y && mouseY - height <= y;
    }

    public void keyTyped(Character typedChar, int keyCode) {
        if (focused) {

            if (keyCode == Keyboard.KEY_BACK) {
                if (getText().length() != 0)
                    setText(getText().substring(0, getText().length() - 1));
            } else if (GuiScreen.isKeyComboCtrlV(keyCode))
                setText(getText() + GuiScreen.getClipboardString());
            else if (typedChar != null && text.length() < maxLength
                    && ChatAllowedCharacters.isAllowedCharacter(typedChar))
                setText(getText() + typedChar);
        }
    }

    public void onClose() {
        focused = false;
    }

    public TextField setBorders(boolean borders) {
        this.borders = borders;
        return this;
    }

    public TextField setColor(int color) {
        this.color = color;
        return this;
    }

    public TextField setObfedText(boolean obf) {
        this.obfedText = obf;
        return this;
    }

    public boolean isFocused() {
        return focused;
    }

    /**
     * Getter for text in field
     *
     * @return text in field
     */
    public String getText() {
        return text;
    }

    /**
     * Setter for text in field
     *
     * @param value text
     */
    public void setText(String value) {
        text = value;
    }

    /**
     * Sets the fill text
     *
     * @param value text
     */
    public void setFillText(String value) {
        fillText = value;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getColor() {
        return color;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

}