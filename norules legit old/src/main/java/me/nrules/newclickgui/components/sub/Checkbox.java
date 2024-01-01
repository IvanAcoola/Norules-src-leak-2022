package me.nrules.newclickgui.components.sub;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.newclickgui.components.Component;
import me.nrules.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class Checkbox extends Component {

    private boolean hovered;
    private Setting op;
    float textHoverAnimate;
    private int x;
    private int y;

    public int offset;
    public int offset1;

    public Checkbox(Setting option, int x, int y) {
        offset = 0;
        this.op = option;
        this.x = x;
        this.y = y;

    }

    @Override
    public void renderComponent() {
        Gui.drawRect(x - 110, y + 10, x + 45, y - 5, 0x20000000);
        if (op.getName().contains("Diamonds") || op.getName().contains("Shulker") || op.getName().contains("Spawner") || op.getName().contains("Chest") || op.getName().contains("EChest") || op.getName().contains("Gold") || op.getName().contains("Iron") || op.getName().contains("Emerald")) {
            Minecraft.getMinecraft().fontRenderer.drawString(this.op.getName().toLowerCase().replace(op.getName().toLowerCase(), ""), x - 100, y - offset, -1);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawString(this.op.getName().toLowerCase(), x - 100, y - offset, -1);
        }


        if (op.getValBoolean()) {
            RenderUtils.drawRoundedRect(x + 10, y - offset + 1, x + 26, y + 5, 1, new Color(73, 181, 83));
            RenderUtils.drawFilledCircle((int) (x + 10 + (int) offset + 16), y - offset + 3, 3, Color.WHITE);
        } else {
            RenderUtils.drawRoundedRect(x + 10, y - offset + 1, x + 26, y + 5, 1, new Color(98, 98, 98));
            RenderUtils.drawFilledCircle((int) (x + 10 + (int) offset + 2), y - offset + 3, 2, Color.WHITE);
        }

        if (op.getName().contains("Shulker")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.PURPLE_SHULKER_BOX, 1));
        }
        if (op.getName().contains("Spawner")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.MOB_SPAWNER, 1));
        }
        if (op.getName().contains("Chest")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.CHEST, 1));
        }
        if (op.getName().contains("EChest")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.ENDER_CHEST, 1));
        }

        if (op.getName().contains("Diamonds")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.DIAMOND_ORE, 1));
        }
        if (op.getName().contains("Gold")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.GOLD_ORE, 1));
        }
        if (op.getName().contains("Iron")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.IRON_ORE, 1));
        }
        if (op.getName().contains("Emerald")) {
            RenderUtils.drawItem(x - 103, y - offset - 5, 1, Minecraft.getMinecraft().player, new ItemStack(Blocks.EMERALD_ORE, 1));
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && button == 0) {
            this.op.setValBoolean(!op.getValBoolean());
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButton(mouseX, mouseY);
    }

    public boolean isMouseOnButton(int x, int y) {
        if (x > this.x + 10 && x < this.x + 30 && y > this.y && y < this.y + 12) {
            return true;
        }
        return false;
    }

}