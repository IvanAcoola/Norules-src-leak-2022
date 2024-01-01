package me.nrules.module.modules.player;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SafeWalk extends Module {
    public SafeWalk() {
        super(SafeWalk.piska2() + SafeWalk.piska3() + SafeWalk.piska4() + SafeWalk.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Sa");
    }

    public static String piska3() {
        return piska2.replace("GHO", "feW");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "al");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "k");
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.player == null && mc.world == null)
            return;

        BlockPos blockPos = new BlockPos(mc.player).add(0, -1, 0);
        boolean bl = mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            if (!bl) {
                setjump(false);
            }
            setsneak(mc.player.onGround || bl);
            if (!mc.player.onGround) {
                mc.player.motionX = 0.0;
                mc.player.motionZ = 0.0;
            }
        } else {
            setsneak(bl);
        }
    }

    public void setjump(boolean bl) {
        mc.player.movementInput.jump = bl;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), bl);
    }

    public void setsneak(boolean bl) {
        mc.player.movementInput.sneak = bl;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), bl);
    }
}
