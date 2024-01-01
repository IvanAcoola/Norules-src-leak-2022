package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class Fly extends Module {
    public Fly() {
        super(Fly.piska2() + Fly.piska3() + Fly.piska4(), Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("FlySpeed", this, 2.0, 0.1, 3, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "F");
    }

    public static String piska3() {
        return piska2.replace("GHO", "l");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "y");
    }


    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (!mc.player.onGround) {
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) Main.settingsManager.getSettingByName("FlySpeed").getValDouble());

            mc.player.motionX = 0.0;
            mc.player.motionY = -0.01;
            mc.player.motionZ = 0.0;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += 0.2f;
        }

//        if (mc.player.moveForward == 0) {
//            Main.moduleManager.getModule(Fly.class).setToggled(false);
//        }
    }


    public void onFlySpeed() {
        try {
            Field flySpeed = PlayerCapabilities.class.getDeclaredField("field_149116_e");
            flySpeed.setAccessible(true);
            flySpeed.setFloat(mc.player.capabilities, 0.77f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void onDisable() {
        mc.player.capabilities.isFlying = false;
        super.onDisable();
    }
}
