package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class WaterJump extends Module {
    public WaterJump() {
        super(WaterJump.piska2() + WaterJump.piska3() + WaterJump.piska4() + WaterJump.piska5(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("WaterJump", this, false));
        Main.settingsManager.rSetting(new Setting("FastUp", this, false));
        Main.settingsManager.rSetting(new Setting("FastDown", this, false));
        Main.settingsManager.rSetting(new Setting("WaterClimb", this, false));
        Main.settingsManager.rSetting(new Setting("MotionY", this, 1, 0, 10, false));
        Main.settingsManager.rSetting(new Setting("JumpVal", this, 0.01, 0.001, 0.09, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Wat");
    }

    public static String piska3() {
        return piska2.replace("GHO", "er");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Feat");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ures");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        // high jump in water, doesnt matrix bypass @nrules
        if (!Main.settingsManager.getSettingByName("FastDown").getValBoolean() && !Main.settingsManager.getSettingByName("FastUp").getValBoolean() && !Main.settingsManager.getSettingByName("WaterJump").getValBoolean() && !Main.settingsManager.getSettingByName("WaterClimb").getValBoolean()) {
            if (mc.player.isInWater()) {
                mc.player.motionY = Main.settingsManager.getSettingByName("MotionY").getValDouble();
            }

        }

        // faster swim in down, matrix bypass @nrules
        if (Main.settingsManager.getSettingByName("FastDown").getValBoolean()) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && mc.player.isInWater()) {
                if (mc.player.ticksExisted % 3 == 0) {
                    mc.player.motionY -= .069f;
                }
            }
        }

        if (Main.settingsManager.getSettingByName("WaterClimb").getValBoolean()) {
            if (mc.player.collidedHorizontally && mc.player.isInWater()) {
                mc.player.motionY = 0.25f;
            }
        }

        // faster swim in up, matrix bypass @nrules
        if (Main.settingsManager.getSettingByName("FastUp").getValBoolean()) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && mc.player.isInWater()) {
                if (mc.player.ticksExisted % 3 == 0) {
                    mc.player.motionY += .02155555f;
                }
            }
        }

        // matrix water jump bypass @nrules
        if (Main.settingsManager.getSettingByName("WaterJump").getValBoolean()) {
            if (mc.player.isInWater() && mc.player.moveForward > 0) {
                float f = Utils.getDirection();

                mc.player.jump();
                mc.player.motionX -= MathHelper.sin(f) * Main.settingsManager.getSettingByName("JumpVal").getValDouble();
                mc.player.motionZ += MathHelper.cos(f) * Main.settingsManager.getSettingByName("JumpVal").getValDouble();

            }
        }
    }

//    @Override
//    public boolean onPacketSent(Packet<?> packet) {
//        if ((!Main.settingsManager.getSettingByName("FastDown").getValBoolean() && !Main.settingsManager.getSettingByName("FastUp").getValBoolean() && !Main.settingsManager.getSettingByName("WaterJump").getValBoolean() && !Main.settingsManager.getSettingByName("WaterClimb").getValBoolean())) {
//            try {
//                if (mc.player.isInWater()) {
//                    Field inWater = Entity.class.getDeclaredField("inWater"); //field_70171_ac
//                    inWater.setAccessible(true);
//                    inWater.setBoolean(mc.player, false);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        return true;
//    }

    @Override
    public void onDisable() {
//        Timer.setTickLength(50F);
        super.onDisable();
    }
}
