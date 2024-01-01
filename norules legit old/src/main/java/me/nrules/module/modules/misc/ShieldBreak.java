package me.nrules.module.modules.misc;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.helper.GCDFix;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.RandomUtils;
import me.nrules.util.TimerHelper;
import net.minecraft.client.entity.EntityPlayerSP;
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
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShieldBreak extends Module {
    public ShieldBreak() {
        super(ShieldBreak.piska2() + ShieldBreak.piska3() + ShieldBreak.piska4() + ShieldBreak.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Shi");
    }

    public static String piska3() {
        return piska2.replace("GHO", "eld");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Bre");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ak");
    }

    TimerHelper timerHelper = new TimerHelper();

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        for (Object theObject : mc.world.loadedEntityList) {
            if (theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;
                if (entity instanceof EntityPlayerSP)
                    continue;

                if (entity.getHeldItemOffhand().getItem() instanceof ItemShield && entity.isHandActive() && mc.player.getDistance(entity) < 3.5 && !FriendManager.isFriend(entity.getName())) {
                    this.destroyShield();
                    if (mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                        if (timerHelper.hasReached(300f)) {
                            timerHelper.reset();
                            mc.playerController.attackEntity(mc.player, entity);
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                        }
                    }
                } else if (entity.getHeldItemOffhand().getItem() instanceof ItemShield && !entity.isHandActive()) {
                    getSword();
                }
            }
        }
    }

    @Override
    public synchronized boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayer) {
            if (mc.player != null && mc.world != null) {
                try {
                    Field yaw = CPacketPlayer.class.getDeclaredFields()[3];
                    yaw.setAccessible(true);
                    Field pitch = CPacketPlayer.class.getDeclaredFields()[4];
                    pitch.setAccessible(true);

                    List<EntityPlayer> enemies = mc.world.playerEntities.stream()
                            .filter(entity -> entity != mc.player)
                            .filter(entity -> mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("Range").getValDouble())
                            .filter(entity -> !entity.isDead)
                            .filter(entity -> attackCheck(entity))
                            .sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()))
                            .collect(Collectors.toList());
//
                    for (EntityPlayer entity : enemies) {
                        float[] rotations = getRatations(entity, false);
                        if (mc.player.getDistance(entity) < 3.6 && !entity.isInvisible() && entity.isHandActive()) {
                            yaw.setFloat(packet, rotations[0]);
                            pitch.setFloat(packet, rotations[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return true;
    }


    public static float[] getRatations(Entity e, boolean oldPositionUse) {
//pasted (kolosya)
        double diffX = (oldPositionUse ? e.prevPosX : e.posX) - (oldPositionUse ? mc.player.prevPosX : mc.player.posX);
        double diffZ = (oldPositionUse ? e.prevPosZ : e.posZ) - (oldPositionUse ? mc.player.prevPosZ : mc.player.posZ);
        double diffY;

        if (e instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) e;
            float randomed = RandomUtils.nextFloat((float) (entitylivingbase.posY + entitylivingbase.getEyeHeight() / 1.5F), (float) (entitylivingbase.posY + entitylivingbase.getEyeHeight() - entitylivingbase.getEyeHeight() / 3F));
            diffY = randomed - (mc.player.posY + (double) mc.player.getEyeHeight());
        } else {
            diffY = RandomUtils.nextFloat((float) e.getEntityBoundingBox().minY, (float) e.getEntityBoundingBox().maxY) - (mc.player.posY + (double) mc.player.getEyeHeight());
        }

        double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (((Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f)) + RandomUtils.nextFloat(-2F, 2F);
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / Math.PI)) + RandomUtils.nextFloat(-2F, 2F);

        yaw = (mc.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapDegrees(yaw - mc.player.rotationYaw)));
        pitch = mc.player.rotationPitch + GCDFix.getFixedRotation(MathHelper.wrapDegrees(pitch - mc.player.rotationPitch));
        pitch = MathHelper.clamp(pitch, -90F, 90F);
        return new float[]{yaw, pitch};
    }

    public static boolean attackCheck(Entity entity) {

        if (entity instanceof EntityPlayer && !FriendManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (entity instanceof EntityAnimal
                && entity instanceof IAnimals) {
            if (entity instanceof EntityTameable) {
                return false;
            } else {
                return false;
            }
        }
        if (entity instanceof EntityMob
                || entity instanceof EntityVillager
                || entity instanceof IMob
                || entity instanceof EntityZombie) {
            return false;
        }
        return false;
    }

    public void destroyShield() {
        if (getAxeH() == -1) {
            return;
        }
        mc.player.inventory.currentItem = getAxeH();
    }

    public void getSword() {
        if (getSwordH() == -1) {
            return;
        }
        mc.player.inventory.currentItem = getSwordH();
    }

    public static int getSwordH() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemSword) {
                return i;
            }
        }

        return -1;
    }

    public static int getAxeH() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() instanceof ItemAxe) {
                return i;
            }
        }

        return -1;
    }
}
