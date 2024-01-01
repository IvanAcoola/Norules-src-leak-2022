package me.nrules.module.modules.player;

import me.nrules.helper.Entity301;
import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.util.Utils;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

public class Freecam extends Module {
    public Freecam() {
        super(Freecam.piska2() + Freecam.piska3() + Freecam.piska4() + Freecam.piska5(), Category.PLAYER);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "Fre");
    }

    public static String piska3() {
        return piska2.replace("GHO", "ec");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "a");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "m");
    }

    public Entity301 entity301 = null;

    @Override
    public void onDisable() {
        if (this.entity301 != null && mc.world != null) {
            mc.player.setPosition(this.entity301.posX, this.entity301.posY, this.entity301.posZ);
            mc.player.rotationPitch = this.entity301.rotationPitch;
            mc.player.rotationYaw = this.entity301.rotationYaw;
            mc.player.rotationYawHead = this.entity301.rotationYawHead;
            mc.world.removeEntity(this.entity301);
            this.entity301 = null;
            mc.player.setGameType(mc.playerController.getCurrentGameType());
            super.onDisable();
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        mc.player.noClip = true;
        mc.player.fallDistance = 0;
        mc.player.onGround = true;

        mc.player.capabilities.isFlying = false;
        mc.player.motionX = 0;
        mc.player.motionY = 0;
        mc.player.motionZ = 0;

        float speed = 0.65f;
        mc.player.jumpMovementFactor = speed;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += speed;
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= speed;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
            float f = Utils.getDirection();
            mc.player.motionX -= (MathHelper.sin(f) * 0.8000F);
            mc.player.motionZ += (MathHelper.cos(f) * 0.8000F);
        }

        if (mc.player.isInWater() && mc.player.isInLava()) {
            float f = Utils.getDirection();
            mc.player.motionX -= (MathHelper.sin(f) * 1.0029F);
            mc.player.motionZ += (MathHelper.cos(f) * 1.0029F);
        }

    }

    @Override
    public void onEnable() {
        if (mc.player != null && mc.world != null) {
            this.entity301 = new Entity301(mc.world, mc.player.getGameProfile());
            this.entity301.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);
            this.entity301.inventory = mc.player.inventory;
            this.entity301.rotationPitch = mc.player.rotationPitch;
            this.entity301.rotationYaw = mc.player.rotationYaw;
            this.entity301.rotationYawHead = mc.player.rotationYawHead;
            mc.world.spawnEntity(this.entity301);
        }
        super.onEnable();
    }


    @Override
    public boolean onPacketSent(Packet<?> packet) {
        boolean skip = true;

        if (packet instanceof CPacketPlayer) {
            skip = false;
        }

        return skip;
    }

    @Override
    public boolean onPacketReceive(Packet<?> packet) {
        boolean skip = true;

        if (packet instanceof SPacketPlayerPosLook) {
            skip = false;
        }

        return skip;
    }


}
