package me.nrules.module.modules.combat;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BowAimBot extends Module {
    public BowAimBot() {
        super("BowAimBot", Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("BowRange", this, 15.67, 3, 70, true));
        Main.settingsManager.rSetting(new Setting("+Predict", this, 0, 0, 1, false));
        Main.settingsManager.rSetting(new Setting("-Predict", this, 0, 0, 1, false));
        Main.settingsManager.rSetting(new Setting("Silent", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Bow");
    }

    public static String piska3() {
        return piska2.replace("GHO", "Ai");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "m");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "");
    }

    @Override
    public synchronized boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer && Main.settingsManager.getSettingByName("Silent").getValBoolean() && Mouse.isButtonDown(1)) {
            if (mc.player != null && mc.world != null) {
                try {
                    Field yaw = CPacketPlayer.class.getDeclaredFields()[3];
                    Field pitch = CPacketPlayer.class.getDeclaredFields()[4];
                    Field rotating = CPacketPlayer.class.getDeclaredFields()[7];

                    yaw.setAccessible(true);
                    pitch.setAccessible(true);
                    rotating.setAccessible(true);
                    rotating.setBoolean(packet, true);

                    List<Entity> enemies = mc.world.loadedEntityList.stream()
                            .filter(entity -> entity != mc.player)
                            .filter(entity -> mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("BowRange").getValDouble())
                            .filter(entity -> !entity.isDead)
                            .filter(entity -> !FriendManager.isFriend(entity.getName()))
                            .sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()))
                            .collect(Collectors.toList());

                    for (Entity target : enemies) {
                        float[] rots = getPlayerRotations(target);
                        mc.player.rotationYawHead = rots[0];
                        mc.player.renderYawOffset = rots[0];

                        yaw.setFloat(packet, rots[0]);
                        pitch.setFloat(packet, rots[1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        mc.player.rotationYaw += 0.0002f;

        if (!Main.settingsManager.getSettingByName("Silent").getValBoolean()) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (Mouse.isButtonDown(1)) {
                    if (entity != null && !FriendManager.isFriend(entity.getName()) && !entity.isDead && entity instanceof EntityPlayer && mc.player.canEntityBeSeen(entity) && entity != mc.player && mc.player.getDistance(entity) < Main.settingsManager.getSettingByName("BowRange").getValDouble()) {
                        float[] rots = getPlayerRotations(entity);
                        mc.player.rotationYaw = rots[0];
                        mc.player.rotationPitch = rots[1];
                    }
                }
            }
        }
    }

    private float[] getPlayerRotations(Entity entity) {
        double xDelta = entity.posX - entity.lastTickPosX;
        double zDelta = entity.posZ - entity.lastTickPosZ;
        double d = mc.player.getDistance(entity);
        d -= d % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        boolean sprint = entity.isSprinting();
        xMulti = d / 0.8 * xDelta * (sprint ? 1.25 + Main.settingsManager.getSettingByName("+Predict").getValDouble() - Main.settingsManager.getSettingByName("-Predict").getValDouble() : 1.0 + Main.settingsManager.getSettingByName("+Predict").getValDouble() - Main.settingsManager.getSettingByName("-Predict").getValDouble());
        zMulti = d / 0.8 * zDelta * (sprint ? 1.25 + Main.settingsManager.getSettingByName("+Predict").getValDouble() - Main.settingsManager.getSettingByName("-Predict").getValDouble() : 1.0 + Main.settingsManager.getSettingByName("+Predict").getValDouble() - Main.settingsManager.getSettingByName("-Predict").getValDouble());
        double x = entity.posX + xMulti - mc.player.posX;
        double z = entity.posZ + zMulti - mc.player.posZ;
        double y = mc.player.posY + mc.player.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        double dist = mc.player.getDistance(entity);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float) Math.toDegrees(Math.atan2(y, dist));
        return new float[]{yaw, pitch};
    }
}