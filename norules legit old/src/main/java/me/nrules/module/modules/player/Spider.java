package me.nrules.module.modules.player;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.NoSlow;
import me.nrules.module.modules.movement.Timer;
import me.nrules.util.TimerHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

public class Spider extends Module {
    public Spider() {
        super(Spider.piska2() + Spider.piska3() + Spider.piska4() + Spider.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("JumpWall", this, false));
        Main.settingsManager.rSetting(new Setting("MotionWall", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Sp");
    }

    public static String piska3() {
        return piska2.replace("GHO", "i");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "d");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "er");
    }

    TimerHelper timerHelper = new TimerHelper();

    @Override
    public void onDisable() {
        Timer.setTickLength(50f);
        super.onDisable();
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent e) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.collidedHorizontally && !mc.player.isOnLadder()) {
            if (Main.settingsManager.getSettingByName("JumpWall").getValBoolean()) {
                if (timerHelper.hasReached(429)) {
                    timerHelper.reset();
                    mc.player.jump();
                    mc.player.motionY = 0.43f;
                }
            }
            if (Main.settingsManager.getSettingByName("MotionWall").getValBoolean()) {
                mc.player.jump();
            }
        }
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (mc.player.collidedHorizontally && mc.player != null && mc.world != null) {
                if (Main.settingsManager.getSettingByName("MotionWall").getValBoolean()) {
                    try {
                        Field ground = CPacketPlayer.class.getDeclaredFields()[5];
                        ground.setAccessible(true);
                        ground.setBoolean(packet, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (Main.settingsManager.getSettingByName("SpoofWall").getValBoolean()) {
                    try {
                        Field ground = CPacketPlayer.class.getDeclaredFields()[5];
                        ground.setAccessible(true);
                        ground.setBoolean(packet, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }


    public static void pressedFalse() {
        try {
            Field pressed = KeyBinding.class.getDeclaredFields()[8];
            pressed.setAccessible(true);
            pressed.setBoolean(mc.gameSettings.keyBindJump, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

