package me.nrules.module.modules.player;

import me.nrules.module.*;
import me.nrules.util.BlockUtil;
import me.nrules.util.PlayerControllerUtil;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FastBreak extends Module {
    public FastBreak() {
        super("FastBreak", Category.PLAYER);
    }

    public void onUpdate() {
        if(mc.player != null && mc.world != null) PlayerControllerUtil.setBlockHitDelay(0);
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        float progress = PlayerControllerUtil.getCurBlockDamageMP() + BlockUtil.getHardness(event.getPos());	
    	if(progress >= 1) return;
    	mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), mc.objectMouseOver.sideHit));
    }
}
