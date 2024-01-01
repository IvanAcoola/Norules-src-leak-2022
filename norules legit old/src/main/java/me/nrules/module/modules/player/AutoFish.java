package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.player.AutoFish;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;

import java.util.Timer;
import java.util.TimerTask;

public class AutoFish extends Module {
    public AutoFish() {
        super(AutoFish.piska2() + AutoFish.piska3() + AutoFish.piska4() + AutoFish.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Au");
    }

    public static String piska3() {
        return piska2.replace("GHO", "toF");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "is");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "h");
    }

    private transient Timer timer = new Timer();

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        if (packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet1 = (SPacketSoundEffect) packet;
            if (packet1.getCategory() == SoundCategory.NEUTRAL && packet1.getSound() == SoundEvents.ENTITY_BOBBER_SPLASH) {
                final Minecraft mc = Minecraft.getMinecraft();

                if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                    sheduleUse(500);
                    sheduleUse(1000);
                }
            }
        }
        return true;
    }


    private void sheduleUse(int delay) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
            }
        }, delay);
    }
}
