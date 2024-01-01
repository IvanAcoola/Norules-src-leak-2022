package me.nrules.helper;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.module.modules.misc.MClickFriend;
import me.nrules.module.modules.render.NameProtect;
import me.nrules.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FontRendererHook extends FontRenderer {
    public FontRendererHook(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }

    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        if (Main.moduleManager.isModuleEnabled(NameProtect.class) && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {
            String nick = "\u00a77" + "\u00a7o" + "NoRules";
            text = text.replace(Minecraft.getMinecraft().getSession().getUsername(), nick);
        }

        if (Main.moduleManager.isModuleEnabled(MClickFriend.class) && Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null) {

            String nick = "\u00a77" + "\u00a7o" + "NoRules";
            for (EntityPlayer player : Utils.getPlayersList())
                if (FriendManager.isFriend(player.getName())) {
                    text = text.replace(player.getName(), nick);
                }
        }

        return super.drawString(text, x, y, color, dropShadow);
    }
}
