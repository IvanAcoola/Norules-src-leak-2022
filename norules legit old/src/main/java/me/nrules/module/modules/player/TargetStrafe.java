package me.nrules.module.modules.player;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.Timer;
import me.nrules.util.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TargetStrafe extends Module {
    public TargetStrafe() {
        super(TargetStrafe.piska2() + TargetStrafe.piska3() + TargetStrafe.piska4() + TargetStrafe.piska5(), Category.PLAYER);
        Main.settingsManager.rSetting(new Setting("Speed", this, 0.2, 0, 0.5, false));
        Main.settingsManager.rSetting(new Setting("Distance", this, 3, 0.1, 10, false));
        Main.settingsManager.rSetting(new Setting("Range", this, 7, 0.1, 15, false));
        Main.settingsManager.rSetting(new Setting("AutoJump", this, true));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Tar");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ge");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "tStr");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "afe");
    }

    public static EntityLivingBase target;
    public static int direction = -1;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static int index;
    float motion;

//    private void drawCirle(EntityPlayer entity, Color color, float partialTicks) {
//        GL11.glPushMatrix();
//        GL11.glDisable(3553);
//        GL11.glEnable(3042);
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL11.GL_LINE_SMOOTH);
//        final double x = RenderUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks) - mc.getRenderManager().viewerPosX;
//        final double y = RenderUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks) - mc.getRenderManager().viewerPosY;
//        final double z = RenderUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks) - mc.getRenderManager().viewerPosZ;
//        GL11.glLineWidth(4.0f);
//        final ArrayList<Vec3d> posArrayList = new ArrayList<>();
//        for (float rotation = 0; rotation < (3.141592f * 2.0); rotation += 3.141592f * 2.0f / 27f) {
//            final Vec3d pos = new Vec3d(Main.settingsManager.getSettingByName("Distance").getValDouble() * Math.cos(rotation) + x, y, Main.settingsManager.getSettingByName("Distance").getValDouble() * Math.sin(rotation) + z);
//            posArrayList.add(pos);
//        }
//        GL11.glEnable(GL11.GL_LINE_STIPPLE);
//        GL11.glLineStipple(4, (short) 0xAAAA);
//        GL11.glBegin(GL11.GL_LINE_STRIP);
//        {
//            for (Vec3d pos : posArrayList) {
//                if (entity.getDistance(mc.player) <= Main.settingsManager.getSettingByName("Range").getValDouble())
//                    GL11.glColor3d(1, 0, 0);
//                GL11.glVertex3d(pos.x, pos.y, pos.z);
//            }
//        }
//        GL11.glEnd();
//        GL11.glDisable(GL11.GL_LINE_STIPPLE);
//        GL11.glDisable(GL11.GL_LINE_SMOOTH);
//        GL11.glDisable(3042);
//        GL11.glEnable(3553);
//        GL11.glLineWidth(1);
//        GL11.glPopMatrix();
//    }

    public double getMovementSpeed() {
        motion = 2;
        boolean boosted = false;
        double d = motion / 10;

        if (Minecraft.getMinecraft().player.isPotionActive(Objects.requireNonNull(Potion.getPotionById((int) 1))) && !boosted) {
            int n = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect(Objects.requireNonNull(Potion.getPotionById((int) 1)))).getAmplifier();
            d *= 1.0 + 0.2 * (double) (n + 1);
        }
        return d;
    }

    public Entity getTargetEz() {
        if (mc.player == null || mc.player.isDead) {
            return null;
        }
        List list = mc.world.loadedEntityList.stream().filter(entity -> entity != mc.player).filter(entity -> mc.player.getDistance(entity) <= Main.settingsManager.getSettingByName("Range").getValDouble()).filter(entity -> !entity.isDead).filter(this::target).sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth())).collect(Collectors.toList());
        if (list.size() > 0) {
            return (Entity) list.get(0);
        }
        return null;
    }


    public final boolean doStrafeAtSpeed(double d) {
        boolean bl = true;
        Entity entity = this.getTargetEz();
        if (entity != null) {
            if (mc.player.onGround && Main.settingsManager.getSettingByName("AutoJump").getValBoolean()) {
                mc.player.jump();
                if (mc.player.fallDistance > 0.1 && mc.player.fallDistance <= 0.4) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
                } else {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
                }
                Timer.setTickLength(50F / 1.025F);
                mc.player.motionY = 0.42f;
                mc.player.motionY -= 0.009999432423423423423432423424234245453543534;
            } else {
                Timer.setTickLength(50F);
            }
            float[] arrf = RotationUtils.getNeededRotations1((EntityLivingBase) entity);
            if ((double) Minecraft.getMinecraft().player.getDistance(entity) <= Main.settingsManager.getSettingByName("Distance").getValDouble()) {
                if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                    invertStrafe();
                } else {
                    if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                        invertStrafe();
                    }
                }
                setSpeed1((d - 0.29) + Main.settingsManager.getSettingByName("Speed").getValDouble(), arrf[0], direction, 0.0);
            } else {
                setSpeed1((d - 0.29) + Main.settingsManager.getSettingByName("Speed").getValDouble(), arrf[0], direction, 1.0);
            }
        }
        return bl;
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (mc.player.collidedHorizontally) {
            invertStrafe();
        }
        /*        Timer.setTickLength(50f / 0.79f);*/
        mc.player.movementInput.moveForward = 0.0f;
        double d = this.getMovementSpeed();
        doStrafeAtSpeed(d);
    }

    public static void setSpeed1(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0D) {
            if (pseudoStrafe > 0.0D) {
                yaw = pseudoYaw + (float) (pseudoForward > 0.0D ? -45 : 45);
            } else if (pseudoStrafe < 0.0D) {
                yaw = pseudoYaw + (float) (pseudoForward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (pseudoForward > 0.0D) {
                forward = 1.0D;
            } else if (pseudoForward < 0.0D) {
                forward = -1.0D;
            }
        }

        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }

        double mx = Math.cos(Math.toRadians((double) (yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((double) (yaw + 90.0F)));
        mc.player.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.player.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    private boolean target(Entity entity) {
        return this.attackCheckin(entity);
    }

    private void invertStrafe() {
        direction = -direction;
    }

    public boolean attackCheckin(Entity entity) {
        return entity instanceof EntityPlayer && ((EntityPlayer) entity).getHealth() > 0.0f && !FriendManager.isFriend(entity.getName()) && Math.abs(mc.player.rotationYaw - RotationUtils.getNeededRotations1((EntityLivingBase) entity)[0]) % 180.0f < 190.0f && !entity.isInvisible() && !entity.getUniqueID().equals(mc.player.getUniqueID());
    }

    @Override
    public void onDisable() {
                Timer.setTickLength(50f);
        super.onDisable();
    }
}