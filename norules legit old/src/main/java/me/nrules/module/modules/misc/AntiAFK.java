package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiAFK extends Module {
    public AntiAFK() {
        super(AntiAFK.piska2() + AntiAFK.piska3() + AntiAFK.piska4() + AntiAFK.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ant");
    }

    public static String piska3() {
        return piska2.replace("GHO", "i");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "AF");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "K");
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (timerHelper.hasReached(3250)) {
            timerHelper.reset();
            mc.player.rotationYaw += 90;
            mc.player.jump();
        }

//        mc.player.inventory.changeCurrentItem(9);

    }
}
