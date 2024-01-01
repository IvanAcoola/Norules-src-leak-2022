package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AutoClicker extends Module {
    public AutoClicker() {
        super(AutoClicker.piska2() + AutoClicker.piska3() + AutoClicker.piska4() + AutoClicker.piska5(), Category.GHOST);
        Main.settingsManager.rSetting(new Setting("1.8", this, true));
        Main.settingsManager.rSetting(new Setting("1.9+", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Au");
    }

    public static String piska3() {
        return piska2.replace("GHO", "to");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Cli");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "cker");
    }

    TimerHelper timerHelper = new TimerHelper();

    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    private double min = 10;
    private double max = 20;
    TimerHelper time = new TimerHelper();
    Random rand = new Random();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("1.8").getValBoolean()) {
            speed = 1.0 / ThreadLocalRandom.current().nextDouble(min - 0.2, max);
            holdLength = speed / ThreadLocalRandom.current().nextDouble(min, max);
            if (Mouse.isButtonDown(0)) {
                if (System.currentTimeMillis() - lastClick > speed * 1000) {
                    lastClick = System.currentTimeMillis();
                    if (hold < lastClick) {
                        hold = lastClick;
                    }
                    int key = mc.gameSettings.keyBindAttack.getKeyCode();
                    KeyBinding.setKeyBindState(key, true);
                    KeyBinding.onTick(key);
                } else if (System.currentTimeMillis() - hold > holdLength * 1000) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                }
            }
            if (min >= max) {
                max = min + 1;
            }

        }

        if (Main.settingsManager.getSettingByName("1.9+").getValBoolean()) {
            if (Mouse.isButtonDown(0) || mc.player.isHandActive()) {
                if (timerHelper.hasReached(800)) {
                    timerHelper.reset();
                    int key = mc.gameSettings.keyBindAttack.getKeyCode();
                    KeyBinding.setKeyBindState(key, true);
                    KeyBinding.onTick(key);
                } else if (timerHelper.hasReached(725)) {
                    timerHelper.reset();
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                }
            }
        }
    }
}
