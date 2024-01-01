package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.player.SpeedMine;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class SpeedMine extends Module {
    public SpeedMine() {
        super(SpeedMine.piska2() + SpeedMine.piska3() + SpeedMine.piska4() + SpeedMine.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Spe");
    }

    public static String piska3() {
        return piska2.replace("GHO", "edM");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "in");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "e");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

            mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 102, 1));
    }

    @Override
    public void onDisable() {
        mc.player.removePotionEffect(Potion.getPotionById(3));
        super.onDisable();
    }
}
