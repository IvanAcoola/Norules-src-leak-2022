package net.minecraftforge.hooks;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.client.event.common.MissingMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class FontRendererHook extends FontRenderer {

    public FontRendererHook(GameSettings p_i1035_1_, ResourceLocation p_i1035_2_, TextureManager p_i1035_3_, boolean p_i1035_4_) {
        super(p_i1035_1_, p_i1035_2_, p_i1035_3_, p_i1035_4_);
    }

    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        if (ForgeInternalHandler.moduleManager.getModule(MissingMappings.class).isToggled() && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            String nick = "NRulesLegit";
            text = text.replace(Minecraft.getMinecraft().getSession().getUsername(), nick);
        }

//        if (Main.moduleManager.isModuleEnabled(MClickFriend.class) && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
//
//            String nick = "\u00a77" + "\u00a7o" + "NoRules";
//            for (EntityPlayer player : Utils.getPlayersList())
//                if (FriendManager.isFriend(player.getName())) {
//                    text = text.replace(player.getName(), nick);
//                }
//        }

        return super.drawString(text, x, y, color, dropShadow);
    }

}
