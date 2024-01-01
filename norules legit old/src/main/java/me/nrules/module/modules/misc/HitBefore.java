package me.nrules.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.AttackManager;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.text.TextComponentString;

public class HitBefore extends Module {
    public HitBefore() {
        super(HitBefore.piska2() + HitBefore.piska3() + HitBefore.piska4() + HitBefore.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Hi");
    }

    public static String piska3() {
        return piska2.replace("GHO", "tBef");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "or");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "e");
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketUseEntity) {
            if (((CPacketUseEntity) packet).getAction() == CPacketUseEntity.Action.ATTACK) {
                EntityPlayer player = (EntityPlayer) Minecraft.getMinecraft().objectMouseOver.entityHit;
                String name = player.getName();
                if (AttackManager.isFriend(name)) {
                    Utils.printMessage(ChatFormatting.DARK_GRAY + "[-] " + ChatFormatting.GRAY + "Killaura" + "\u00a7a" + " DOESNT" + "\u00a77" + " target:" + "\u00a7a " + player.getName());
                    AttackManager.deleteFriend(name);
                    Main.moduleManager.getModule(HitBefore.class).setToggled(false);
                } else {
                    Utils.printMessage(ChatFormatting.DARK_GRAY + "[+] " +" Killaura target:" + "\u00a7c " + player.getName());
                    AttackManager.addFriend(name, name);
                    Main.moduleManager.getModule(HitBefore.class).setToggled(false);
                }
                return true;
            }
        }
        return true;
    }
}