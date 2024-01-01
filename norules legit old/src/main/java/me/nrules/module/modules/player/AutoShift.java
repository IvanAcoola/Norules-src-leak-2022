package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.NoSlow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoShift extends Module {
    public AutoShift() {
        super(AutoShift.piska2() + AutoShift.piska3() + AutoShift.piska4() + AutoShift.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Au");
    }

    public static String piska3() {
        return piska2.replace("GHO", "toS");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "hif");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "t");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent e) {
        NoSlow.pressedFalse();
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (mc.player != null && mc.world != null)
            if (packet instanceof CPacketUseEntity) {
                if (((CPacketUseEntity) packet).getAction() == CPacketUseEntity.Action.ATTACK) {
                    NoSlow.pressedTrue();
                }
            }
        return true;
    }
}
