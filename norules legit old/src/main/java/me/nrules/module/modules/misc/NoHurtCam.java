package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.misc.NoHurtCam;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoHurtCam extends Module {
    public NoHurtCam() {
        super(NoHurtCam.piska2() + NoHurtCam.piska3() + NoHurtCam.piska4() + NoHurtCam.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "NoH");
    }

    public static String piska3() {
        return piska2.replace("GHO", "urt");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Ca");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "m");
    }

    @SubscribeEvent
    public void onHurtCameraEffect(TickEvent.RenderTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.maxHurtTime = 0;
        mc.player.hurtTime = 0;
    }
}
