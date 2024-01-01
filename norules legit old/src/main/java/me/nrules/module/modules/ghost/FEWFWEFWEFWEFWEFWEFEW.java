package me.nrules.module.modules.ghost;

import me.nrules.FriendManager;
import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.EntitySize;
import me.nrules.util.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FEWFWEFWEFWEFWEFWEFEW extends Module {
    public FEWFWEFWEFWEFWEFWEFEW() {
        super(FEWFWEFWEFWEFWEFWEFEW.piska2() + FEWFWEFWEFWEFWEFWEFEW.piska3() + FEWFWEFWEFWEFWEFWEFEW.piska4() + FEWFWEFWEFWEFWEFWEFEW.piska5(), Category.GHOST);
        Main.settingsManager.rSetting(new Setting("Widht", this, 1, 0.1, 5, false));
        Main.settingsManager.rSetting(new Setting("Heihgt", this, 1, 0.1, 5, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Hi");
    }

    public static String piska3() {
        return piska2.replace("GHO", "t");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Bo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "x");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        for (Entity entity : mc.world.playerEntities) {
            if (!check((EntityLivingBase) entity))
                continue;

            if (entity != null && !FriendManager.isFriend(entity.getName())) {
                float width = (float) Main.settingsManager.getSettingByName("Widht").getValDouble();
                float height = (float) Main.settingsManager.getSettingByName("Heihgt").getValDouble();

                setEntityBoundingBoxSize(entity, width, (float) (height * 1.3));
            }
        }
    }

    public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
        EntitySize size = Utils.getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d0 = (double) (width) / 2.0D;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d0, entity.posY, entity.posZ - d0, entity.posX + d0, entity.posY + (double) height, entity.posZ + d0));
    }

    @Override
    public void onDisable() {
        for (EntityPlayer player : Utils.getPlayersList()) {
            Utils.setEntityBoundingBoxSize(player);
        }
        super.onDisable();
    }

    public boolean check(EntityLivingBase entity) {
        if (entity instanceof EntityPlayerSP || entity == mc.player || entity.isDead) {
            return false;
        }
        return true;
    }


}
