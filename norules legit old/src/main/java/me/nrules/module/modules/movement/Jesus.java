package me.nrules.module.modules.movement;

import me.nrules.Main;
import me.nrules.clickgui.settings.Setting;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.EntityUtils;
import me.nrules.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        Main.settingsManager.rSetting(new Setting("MotionBlock", this, 4, 3, 10, false));
    }

    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        if (EntityUtils.isFluid(-0.1))
            EntityUtils.strafe(Main.settingsManager.getSettingByName("MotionBlock").getValDouble());

        if (EntityUtils.isFluid(0.0000001)) {
            mc.player.fallDistance = 0.0f;
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
            mc.player.motionY = 0.06f;
            mc.player.jumpMovementFactor = 0.01f;
        }
    }

}
