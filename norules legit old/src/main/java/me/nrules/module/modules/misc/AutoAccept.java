package me.nrules.module.modules.misc;

import me.nrules.FriendManager;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;

public class AutoAccept extends Module {
    public AutoAccept() {
        super(AutoAccept.piska2() + AutoAccept.piska3() + AutoAccept.piska4() + AutoAccept.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Au");
    }

    public static String piska3() {
        return piska2.replace("GHO", "to");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Acce");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "pt");
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (packet instanceof SPacketChat) {
            for (FriendManager.Friend player : FriendManager.friends) {
                if (((SPacketChat) packet).getChatComponent().getUnformattedText().contains(player.getName() + " просит телепортироваться к Вам.")) {
                    if (!((SPacketChat) packet).getChatComponent().getUnformattedText().contains("В бою эта команда недоступна!")) {
                        mc.player.sendChatMessage("/tpaccept");
                    }
                }
            }
        }
        return true;
    }
}
