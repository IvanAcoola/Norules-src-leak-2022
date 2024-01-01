package me.nrules.module.modules.ghost;


import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.MathUtilsNR;
import me.nrules.util.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FJEJFWEJFJWEJFWEJFJWE extends Module {
    public FJEJFWEJFJWEJFWEJFJWE() {
        super(FJEJFWEJFJWEJFWEJFJWE.piska2() + FJEJFWEJFJWEJFWEJFJWE.piska3() + FJEJFWEJFJWEJFWEJFJWE.piska4() + FJEJFWEJFJWEJFWEJFJWE.piska5(), Category.GHOST);
        Main.settingsManager.rSetting(new Setting("Random", this, 2, 0, 4, false));
        Main.settingsManager.rSetting(new Setting("Speed", this, 3.7, 1.0D, 10, false));
        Main.settingsManager.rSetting(new Setting("Range", this, 3.7, 3.0D, 6, false));
        Main.settingsManager.rSetting(new Setting("Click Aim", this, false));
        Main.settingsManager.rSetting(new Setting("Yaw", this, true));
        Main.settingsManager.rSetting(new Setting("Pitch", this, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Ai");
    }

    public static String piska3() {
        return piska2.replace("GHO", "mAs");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "si");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "st");
    }

    private List<EntityLivingBase> targets = new ArrayList<>();
    private EntityLivingBase target;

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        collectTargets();
        sortTargets();
        target = targets.isEmpty() ? null : targets.get(0);
        float random = Main.settingsManager.getSettingByName("Random").getValDouble() != 0 ? (float) MathUtilsNR.getRandomInRange(-(float) Main.settingsManager.getSettingByName("Random").getValDouble(), (float) Main.settingsManager.getSettingByName("Random").getValDouble()) : 0;
        if (!Main.settingsManager.getSettingByName("Click Aim").getValBoolean() || Mouse.isButtonDown(0))
            if (target != null && mc.currentScreen == null) {
                if (Main.settingsManager.getSettingByName("Yaw").getValBoolean())
                    mc.player.rotationYaw = (RotationUtils.getRotations(target, (float) (Main.settingsManager.getSettingByName("Speed").getValDouble() * 2) + random)[0]) + random;
                if (Main.settingsManager.getSettingByName("Pitch").getValBoolean())
                    mc.player.rotationPitch = (RotationUtils.getRotations(target, (float) (Main.settingsManager.getSettingByName("Speed").getValDouble() * 2) + random)[1]) + random;
            }

    }

    private void sortTargets() {
        targets.sort(Comparator.comparingDouble(RotationUtils::getAngleChange));
    }

    private void collectTargets() {
        targets.clear();
        for (Entity entity : mc.player.getEntityWorld().loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (isValid(entityLivingBase))
                    targets.add(entityLivingBase);
            }
        }
    }

    private boolean isValid(EntityLivingBase entityLivingBase) {
        return entityLivingBase != mc.player && !entityLivingBase.isDead && !(entityLivingBase.getDistance(mc.player) > Main.settingsManager.getSettingByName("Range").getValDouble()) && entityLivingBase.getHealth() != 0 && !entityLivingBase.isInvisible() && !(entityLivingBase instanceof EntityArmorStand);
    }

}

