package me.nrules.module.modules.misc;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class MClickFriend extends Module {
    public MClickFriend() {
        super(MClickFriend.piska2() + MClickFriend.piska3() + MClickFriend.piska4() + MClickFriend.piska5(), Category.MISC);
        Main.settingsManager.rSetting(new Setting("NoFriendHit", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "MCli");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ckF");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "rien");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "d");
    }

    @SubscribeEvent
    public void onUpdate(MouseEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (Mouse.isButtonDown(2) && mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().objectMouseOver.entityHit;
            String name = player.getName();
            if (FriendManager.isFriend(name)) {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("\u00a77" + player.getName() + "\u00a74 " + "Enemy!"));
                FriendManager.deleteFriend(name);
            } else {
                mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("\u00a77" + player.getName() + "\u00a7a " + "Friend!"));
                FriendManager.addFriend(name, name);
            }
        }
    }
}

