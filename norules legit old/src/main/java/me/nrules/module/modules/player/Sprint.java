package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.player.Sprint;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {
    public Sprint() {
        super(Sprint.piska2() + Sprint.piska3() + Sprint.piska4() + Sprint.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Sp");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ri");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "n");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "t");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.moveForward > 0) {
            mc.player.setSprinting(true);
        }
    }

}
