package net.minecraftforge.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class GuiInGameHook extends GuiIngame {

    protected final GuiNewChatHook persistantChatGUI;
    private Minecraft mc;

    public GuiInGameHook(Minecraft p_i46325_1_) {
        super(p_i46325_1_);
        this.mc = p_i46325_1_;
        this.persistantChatGUI = new GuiNewChatHook(p_i46325_1_);

    }

    @Override
    public void renderGameOverlay(float p_175180_1_) {
        super.renderGameOverlay(p_175180_1_);

        this.persistantChatGUI.drawChat(this.updateCounter);
    }

}
