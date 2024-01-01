package me.nrules.module.modules.combat;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FEFWEFWEFWEFWE extends Module {
    public FEFWEFWEFWEFWE() {
        super(FEFWEFWEFWEFWE.piska2() + FEFWEFWEFWEFWE.piska3() + FEFWEFWEFWEFWE.piska4() + FEFWEFWEFWEFWE.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("Motion", this, false));
        Main.settingsManager.rSetting(new Setting("Packet", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Cri");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ti");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ca");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ls");
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.CameraSetup event) {
        if (mc.player == null || mc.world == null) return;
        if (Main.settingsManager.getSettingByName("Motion").getValBoolean()) if (mc.player.fallDistance > 0.7) mc.player.motionY = 0;
    }

    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (Main.settingsManager.getSettingByName("Packet").getValBoolean()) {
            if (packet instanceof CPacketUseEntity && ((CPacketUseEntity) packet).getAction() == CPacketUseEntity.Action.ATTACK) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + .1625, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 4.0E-6, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.0E-6, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer(false));
            }
        }
        return true;
    }
}
