package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Step extends Module {
    public Step() {
        super(Step.piska2() + Step.piska3() + Step.piska4() + Step.piska5(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("Vanilla", this, false));
        Main.settingsManager.rSetting(new Setting("AAC", this, false));
        Main.settingsManager.rSetting(new Setting("NCP", this, true));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "S");
    }

    public static String piska3() {
        return piska2.replace("GHO", "t");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "e");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "p");
    }

    private double previousX, previousY, previousZ;
    private double offsetX, offsetY, offsetZ;
    private double frozenX, frozenZ;
    private byte cancelStage;
    private float _prevEntityStep;

    @Override
    public void onEnable() {
        super.onEnable();
        cancelStage = 0;

        if (mc.player != null && mc.player.isRiding()) _prevEntityStep = mc.player.getRidingEntity().stepHeight;
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("AAC").getValBoolean()) {
            boolean b = true;
            if (b) {
                Timer.setTickLength(50f / 0.9f + mc.player.ticksExisted % 4 / 20);
            }

            if (mc.player.collidedHorizontally && mc.player.onGround) {
                double cacheY = mc.player.posY;
                mc.player.isAirBorne = true;
                b = true;
                Timer.setTickLength(50F / 4);
                mc.player.motionY = 0.47f;
            }
        }

        if (Main.settingsManager.getSettingByName("NCP").getValBoolean()) {
            offsetX = 0;
            offsetY = 0;
            offsetZ = 0;

            mc.player.stepHeight = mc.player.onGround && mc.player.collidedHorizontally && cancelStage == 0 && mc.player.posY % 1 == 0 ? 1.1F : 0.5F;

            if (cancelStage == -1) {
                cancelStage = 0;
                return;
            }

            double yDist = mc.player.posY - previousY;
            double hDistSq = (mc.player.posX - previousX) * (mc.player.posX - previousX) + (mc.player.posZ - previousZ) * (mc.player.posZ - previousZ);

            if (yDist > 0.5 && yDist < 1.05 && hDistSq < 1 && cancelStage == 0) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(previousX, previousY + 0.42, previousZ, false));
                offsetX = previousX - mc.player.posX;
                offsetY = 0.755 - yDist;
                offsetZ = previousZ - mc.player.posZ;

                frozenX = previousX;
                frozenZ = previousZ;
                mc.player.stepHeight = 1.05F;
                cancelStage = 1;
            }


            switch (cancelStage) {
                case 1:
                    cancelStage = 2;
                    mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(frozenX - mc.player.posX, 0, frozenZ - mc.player.posZ));
                    break;
                case 2:
                    event.setCanceled(true);
                    cancelStage = -1;
                    break;
            }

            previousX = mc.player.posX;
            previousY = mc.player.posY;
            previousZ = mc.player.posZ;

            if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
                mc.player.posX += offsetX;
                mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(0, offsetY, 0));
                mc.player.posZ += offsetZ;
            } else {
                if (offsetX != 0 || offsetY != 0 || offsetZ != 0) {
                    mc.player.posX -= offsetX;
                    mc.player.setEntityBoundingBox(mc.player.getEntityBoundingBox().offset(0, -offsetY, 0));
                    mc.player.posZ -= offsetZ;
                }
            }
        }
        if (Main.settingsManager.getSettingByName("Vanilla").getValBoolean()) {
            mc.player.stepHeight = 1f;
        }
    }


    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.6f;
        super.onDisable();
    }
}
