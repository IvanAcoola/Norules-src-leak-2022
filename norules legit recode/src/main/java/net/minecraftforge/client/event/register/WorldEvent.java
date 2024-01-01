package net.minecraftforge.client.event.register;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.managers.FriendManager;

import java.util.Random;

public class WorldEvent extends Module {
    public WorldEvent() {
        super(WorldEvent.piska2() + WorldEvent.piska3() + WorldEvent.piska4() + WorldEvent.piska5(), Category.Combat, "Автоматически бьет при наведении на игрока.", 2);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Trigg");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "er");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "Bo");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "t");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.objectMouseOver == null || mc.player == null || mc.world == null) return;
        for (Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                if (e != mc.player) {
                    if (timerHelper.hasReached(600L + new Random().nextInt(100)) && !FriendManager.isFriend(e.getName()) && mc.objectMouseOver.typeOfHit != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && (e = mc.objectMouseOver.entityHit) instanceof EntityPlayer) {
                        timerHelper.reset();
                        mc.playerController.attackEntity(mc.player, e);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }

}
