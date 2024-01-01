package me.nrules.module.modules.player;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoGapple extends Module {
    public AutoGapple() {
        super(AutoGapple.piska2() + AutoGapple.piska3() + AutoGapple.piska4() + AutoGapple.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Healht", this, 14, 0, 20, true));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Aut");
    }

    public static String piska3() {
        return piska2.replace("GHO", "oGa");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "pp");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "le");
    }

    public static int key = mc.gameSettings.keyBindUseItem.getKeyCode();

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        int health = (int) Math.ceil(mc.player.getHealth());

        if (mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemAppleGold) {
            if (health <= Main.settingsManager.getSettingByName("Healht").getValDouble()) {
                KeyBinding.setKeyBindState(key, true);
            }
            if (health >= Main.settingsManager.getSettingByName("Healht").getValDouble()) {
                KeyBinding.setKeyBindState(key, false);
            }
        }

    }

    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(key, false);
        super.onDisable();
    }
}
