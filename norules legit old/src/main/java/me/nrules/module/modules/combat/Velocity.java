package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class Velocity extends Module {
    public Velocity() {
        super(Velocity.piska2() + Velocity.piska3() + Velocity.piska4() + Velocity.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, false));
        Main.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, false));
        Main.settingsManager.rSetting(new Setting("Custom", this, true));
        Main.settingsManager.rSetting(new Setting("Cancel", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ve");
    }
    public static String piska3() {
        return piska2.replace("GHO", "lo");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "ci");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "ty");
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        if (Main.settingsManager.getSettingByName("Custom").getValBoolean()) {
            if (mc.player == null && mc.world == null)
                return;

            float horizontal = (float) Main.settingsManager.getSettingByName("Horizontal").getValDouble();
            float vertical = (float) Main.settingsManager.getSettingByName("Vertical").getValDouble();

            if (mc.player.hurtTime == mc.player.maxHurtTime && mc.player.maxHurtTime > 0) {
                mc.player.motionX *= horizontal / 100;
                mc.player.motionY *= vertical / 100;
                mc.player.motionZ *= horizontal / 100;
            }
        }

    }


    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (packet instanceof SPacketEntityVelocity && Main.settingsManager.getSettingByName("Cancel").getValBoolean())return mc.player.getEntityId() != ((SPacketEntityVelocity) packet).getEntityID();
        return true;
    }
}