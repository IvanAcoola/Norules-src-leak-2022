package me.nrules.module.modules.combat;

import me.nrules.FriendManager;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class TriggerBot extends Module {
    public TriggerBot() {
        super(TriggerBot.piska2() + TriggerBot.piska3() + TriggerBot.piska4() + TriggerBot.piska5(), Category.GHOST);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Trig");
    }
    public static String piska3() {
        return piska2.replace("GHO", "ger");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "Bo");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "t");
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.objectMouseOver == null || mc.player == null || mc.world == null) return;
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                if (e != mc.player) {
                    if (mc.player.getDistance(e) <= 4f && timerHelper.hasReached(600L + new Random().nextInt(100)) && !FriendManager.isFriend(e.getName()) && mc.objectMouseOver.typeOfHit != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && (e = mc.objectMouseOver.entityHit) instanceof EntityPlayer) {
                        timerHelper.reset();
                        mc.playerController.attackEntity(mc.player, e);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }
}
