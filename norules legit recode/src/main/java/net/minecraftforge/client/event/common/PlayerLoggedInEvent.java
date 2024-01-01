package net.minecraftforge.client.event.common;

import net.minecraftforge.ForgeInternalHandler;
import net.minecraftforge.clickgui.setting.Setting;
import net.minecraftforge.client.Category;
import net.minecraftforge.client.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerLoggedInEvent extends Module {
    public PlayerLoggedInEvent() {
        super(PlayerLoggedInEvent.piska2() + PlayerLoggedInEvent.piska3() + PlayerLoggedInEvent.piska4() + PlayerLoggedInEvent.piska5(), Category.Render, "Убирает тряску экрана при получение урона", 2);
    }

    private static final String piska = "FFFFFFFFFFFFFFFFF";
    private static final String piska2 = "AAAAAAAAAAAAAA";
    private static final String piska3 = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVV";
    private static final String piska5 = "ZZZZZZZZZZZZZZZZZZ";

    public static String piska2() {
        return piska.replace("FFFFFFFFFFFFFFFFF", "Hu");
    }

    public static String piska3() {
        return piska2.replace("AAAAAAAAAAAAAA", "rt");
    }

    public static String piska4() {
        return piska3.replace("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV", "C");
    }

    public static String piska5() {
        return piska5.replace("ZZZZZZZZZZZZZZZZZZ", "am");
    }

    @SubscribeEvent
    public void onUpdate(EntityViewRenderEvent.CameraSetup event) {
        if (mc.player == null || mc.world == null) return;

        cameraHurt((float) event.getRenderPartialTicks());
    }

    private void cameraHurt(float partialTicks) {
        if (mc.getRenderViewEntity() instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) mc.getRenderViewEntity();
            float f = (float) entitylivingbase.hurtTime - partialTicks;
            if (entitylivingbase.getHealth() <= 0.0F) {
                float f1 = (float) entitylivingbase.deathTime + partialTicks;
//                GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
            }

            if (f < 0.0F) {
                return;
            }

            f = f / (float) entitylivingbase.maxHurtTime;
            f = MathHelper.sin(f * f * f * f * (float) Math.PI);
            float f2 = entitylivingbase.attackedAtYaw;

            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float) (-f * -ForgeInternalHandler.settingsManager.getSettingByName("Hurt Effect").getValDouble() * 2), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
        }
    }

    @Override
    public void setup() {
        super.setup();
        this.rSetting(new Setting("Hurt Effect", this, 9, 1, 10, false));

    }
}
