package net.minecraftforge.client.event.register;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterItemHandler extends Module {
    public RegisterItemHandler() {
        super(RegisterItemHandler.piska2() + RegisterItemHandler.piska3() + RegisterItemHandler.piska4() + RegisterItemHandler.piska5(), Category.Combat, "Увеличивает дистанцию удара", 2);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Re");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "ac");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "h");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null || mc.world == null) return;

        mc.player.jumpMovementFactor = (float) ForgeInternalHandler.getSettingsManager().getSettingByName("Range").getValDouble();
    }

    @Override
    public void setup() {
        super.setup();
        this.rSetting(new Setting("Range", this, 0.5, 0.1, 1, false));
        this.rSetting(new Setting("Value", this, 0.5, 1, 10, true));
        this.rSetting(new Setting("Percent", this, 3, 3, 5, true));
        this.rSetting(new Setting("Player", this, false));
        this.rSetting(new Setting("Monster", this, false));

    }
}
