package me.nrules.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.nrules.module.*;
import me.nrules.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiBot extends Module {
    public AntiBot() {
        super(AntiBot.piska2() + AntiBot.piska3() + AntiBot.piska4() + AntiBot.piska5(), Category.COMBAT);
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
        return piska3.replace("UIOL", "Bo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "t");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null)
            return;

        for (EntityPlayer bot : mc.world.playerEntities) {
            if (bot == mc.player || bot.isDead) continue;

            if (bot.isInvisible()) {bot.isDead = true;
                Utils.printMessage(ChatFormatting.GREEN + bot.getName() + ChatFormatting.WHITE + " is removed!");
            }
        }
    }
}