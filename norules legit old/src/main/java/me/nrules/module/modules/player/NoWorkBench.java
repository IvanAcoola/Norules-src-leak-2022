package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.player.NoWorkBench;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;

public class NoWorkBench extends Module {
    public NoWorkBench() {
        super(NoWorkBench.piska2() + NoWorkBench.piska3() + NoWorkBench.piska4() + NoWorkBench.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "NoIn");
    }

    public static String piska3() {
        return piska2.replace("GHO", "te");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "ra");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ct");
    }


    @Override
    public boolean onPacketSent(Packet<?> packet) {
        if (packet instanceof CPacketPlayerTryUseItemOnBlock) {
            return mc.playerController.interactWithEntity(mc.player, mc.objectMouseOver.entityHit, mc.objectMouseOver, EnumHand.MAIN_HAND) != EnumActionResult.SUCCESS;
        }
        return true;
    }
}
