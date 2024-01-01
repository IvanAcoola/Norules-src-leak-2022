package me.nrules.module.modules.fun;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MathUtilsNR;
import me.nrules.util.TimerHelper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;

public class AntiAim extends Module {
    public AntiAim() {
        super(AntiAim.piska2() + AntiAim.piska3() + AntiAim.piska4() + AntiAim.piska5(), Category.FUN);
        Main.settingsManager.rSetting(new Setting("Visual", this, true));
        Main.settingsManager.rSetting(new Setting("Client", this, false));
        Main.settingsManager.rSetting(new Setting("Server", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "An");
    }
    public static String piska3() {
        return piska2.replace("GHO", "ti");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "Ai");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "m");
    }

    TimerHelper timerHelper = new TimerHelper();
    private float KeepYaw;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.rotationYaw += 0.0001;

        if (Main.settingsManager.getSettingByName("Visual").getValBoolean()) {
            if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
                float sens = getSensitivityMultiplier();
                float yaw = ((this.KeepYaw + 7f) + MathUtilsNR.getRandomInRange(0.10000000149011612f, 5.0f));
                yaw = Math.round(yaw / sens) * sens;
                mc.player.renderYawOffset = yaw;
                mc.player.rotationYawHead = yaw;
                this.KeepYaw = yaw;
            }
        }

        if (Main.settingsManager.getSettingByName("Client").getValBoolean()) {
            float sens = getSensitivityMultiplier();
            if (timerHelper.hasReached(100)) {
                timerHelper.reset();
                float yaw = (float) ((this.KeepYaw + 7.7f) + MathUtilsNR.getRandomInRange(1.1f, 5.3f));
                yaw = Math.round(yaw / sens) * sens;
                float pitch = 20f;
                mc.player.rotationYaw = yaw;
                mc.player.rotationPitch = pitch;
                this.KeepYaw = yaw;
            }
        }
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (Main.settingsManager.getSettingByName("Server").getValBoolean() && mc.player != null && mc.world != null) {
                float sens = getSensitivityMultiplier();
                if (timerHelper.hasReached(20)) {
                    timerHelper.reset();
                    float yaw = (float) ((this.KeepYaw + 7.7f) + MathUtilsNR.getRandomInRange(1.1f, 5.3f));
                    yaw = Math.round(yaw / sens) * sens;
                    float pitch = 20f;
                    try {
                        Field yawPlayer = CPacketPlayer.class.getDeclaredFields()[3];
                        yawPlayer.setAccessible(true);
                        Field pitchPlayer = CPacketPlayer.class.getDeclaredFields()[4];
                        pitchPlayer.setAccessible(true);

                        yawPlayer.setFloat(packet, yaw);
                        pitchPlayer.setFloat(packet, pitch);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        return true;
    }

    public static float getSensitivityMultiplier() {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return f * f * f * 8.0F * 0.15F;
    }


}
