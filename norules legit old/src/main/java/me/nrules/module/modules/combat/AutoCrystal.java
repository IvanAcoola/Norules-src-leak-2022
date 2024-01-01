package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.TimerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoCrystal extends Module {
    public AutoCrystal() {
        super(AutoCrystal.piska2() + AutoCrystal.piska3() + AutoCrystal.piska4() + AutoCrystal.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("RangeCrystal", this, 3.70D, 0, 7, false));
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
        return piska3.replace("UIOL", "Cryst");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "al");
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal) {
                if (mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("RangeCrystal").getValDouble()) {
                    if (timerHelper.hasReached(250)) {
                        timerHelper.reset();
                        mc.playerController.attackEntity(mc.player, entity);
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }
}
