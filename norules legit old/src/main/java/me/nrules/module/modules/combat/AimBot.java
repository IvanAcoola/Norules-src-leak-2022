package me.nrules.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.AttackManager;
import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.helper.GCDFix;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RandomUtils;
import me.nrules.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AimBot extends Module {

    public AimBot() {
        super(AimBot.piska2() + AimBot.piska3() + AimBot.piska4() + AimBot.piska5(), Category.COMBAT);
        Main.settingsManager.rSetting(new Setting("RangeAim", this, 3.67, 3, 5, false));
        Main.settingsManager.rSetting(new Setting("Player", this, true));
        Main.settingsManager.rSetting(new Setting("Animal", this, false));
        Main.settingsManager.rSetting(new Setting("Mob", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "A");
    }

    public static String piska3() {
        return piska2.replace("GHO", "im");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Bo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "t");
    }

    public Random random = new Random();

    @Override
    public void onDisable() {
        Utils.printMessage(ChatFormatting.DARK_GRAY + "[-]" + ChatFormatting.GRAY + " ServerSide AimBot disabled!");
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Utils.printMessage(ChatFormatting.DARK_GRAY + "[+]" + ChatFormatting.GRAY + " ServerSide AimBot enabled!");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        mc.player.rotationYaw += 0.0002;
    }

    @Override
    public synchronized boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
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
                            .filter(entity -> mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("RangeAim").getValDouble())
                            .filter(entity -> !entity.isDead)
                            .filter(entity -> attackCheck(entity))
                            .sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()))
                            .collect(Collectors.toList());

                    for (Entity target : enemies) {
                        float[] rots = getRatations(target);
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

    private boolean attackCheck(Entity entity) {

        if (Main.settingsManager.getSettingByName("Player").getValBoolean() && entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (Main.settingsManager.getSettingByName("Animal").getValBoolean() && entity instanceof EntityAnimal
                && entity instanceof IAnimals) {
            if (entity instanceof EntityTameable) {
                return false;
            } else {
                return true;
            }
        }
        if (Main.settingsManager.getSettingByName("Mob").getValBoolean() && entity instanceof EntityMob
                || entity instanceof EntityVillager
                || entity instanceof IMob
                || entity instanceof EntityZombie) {
            return true;
        }
        return false;
    }

    public static float[] getRatations(Entity e) {
        double diffX = e.posX - mc.player.posX;
        double diffZ = e.posZ - mc.player.posZ;
        double diffY;

        if (e instanceof EntityLivingBase) {
            diffY = e.posY + e.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight()) - 0.42;
        } else {
            diffY = (e.getEntityBoundingBox().minY + e.getEntityBoundingBox().maxY) / 2.0D - (mc.player.posY + mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + RandomUtils.nextFloat(-2, 2);
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + RandomUtils.nextFloat(-2, 2);
        yaw = mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw));
        pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }

}
