package me.nrules.module.modules.ghost;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Reach extends Module {
    public Reach() {
        super(Reach.piska2() + Reach.piska3() + Reach.piska4() + Reach.piska5(), Category.GHOST);
        Main.settingsManager.rSetting(new Setting("CombatRange", this, 3.67, 3, 5, false));
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Re");
    }
    public static String piska3() {
        return piska2.replace("GHO", "a");
    }
    public static String piska4() {
        return piska3.replace("UIOL", "c");
    }
    public static String piska5() {
        return piska5.replace("NGHO", "h");
    }

    @SubscribeEvent
    public void onUpdate(MouseEvent event) {

        if (mc.player == null && mc.world == null)
            return;

        final Object[] objects = EntityUtils.getEntityCustom(mc.player.rotationPitch, mc.player.rotationYaw, Main.settingsManager.getSettingByName("CombatRange").getValDouble(), 0, 0.0F);
        if (objects == null) {
            return;
        }
        mc.objectMouseOver = new RayTraceResult((Entity) objects[0], (Vec3d) objects[1]);
        mc.pointedEntity = (Entity) objects[0];
    }


}
