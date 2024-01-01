package net.minecraftforge.client.event.handler;

import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;

public class EntityJoinWorld extends Module {
    public EntityJoinWorld() {
        super(EntityJoinWorld.piska2() + EntityJoinWorld.piska3() + EntityJoinWorld.piska4() + EntityJoinWorld.piska5(), Category.Movement, "Ускоряет время", 1);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Ti");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "m");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "e");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "r");
    }

    @Override
    public void setup() {
        super.setup();
        this.rSetting(new Setting("Range", this, 0.5, 0.1, 1, false));
        this.rSetting(new Setting("Monster", this, false));

    }
}
