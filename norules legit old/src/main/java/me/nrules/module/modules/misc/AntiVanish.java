package me.nrules.module.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.Main;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.misc.AntiVanish;
import me.nrules.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AntiVanish extends Module {
    public AntiVanish() {
        super(AntiVanish.piska2() + AntiVanish.piska3() + AntiVanish.piska4() + AntiVanish.piska5(), Category.MISC);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "An");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ti");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Va");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "nish");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        List<EntityPlayer> enemies = mc.world.playerEntities.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= 40)
                .filter(entity -> !entity.isDead)
                .sorted(Comparator.comparingDouble(target -> ((EntityLivingBase) target).getHealth()))
                .collect(Collectors.toList());

        for (EntityPlayer player : enemies) {
            if (player.isInvisible()) {
                Utils.printMessage(ChatFormatting.DARK_GRAY + "[~] " + ChatFormatting.GRAY + player.getName() + "does not invisible!");
                player.setInvisible(false);
            }
        }
    }
}
