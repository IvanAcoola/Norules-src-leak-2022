package me.nrules.module.modules.movement;

import me.nrules.module.Category;
import me.nrules.module.Module;
import me.nrules.module.modules.movement.InvMove;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
    public InvMove() {
        super(InvMove.piska2() + InvMove.piska3() + InvMove.piska4() + InvMove.piska5(), Category.MOVEMENT);
    }

    private static final String piska = "GOGA";
    private static final String piska2 = "GHO";
    private static final String piska3 = "UIOL";
    private static final String piska5 = "NGHO";

    public static String piska2() {
        return piska.replace("GOGA", "I");
    }

    public static String piska3() {
        return piska2.replace("GHO", "nv");
    }

    public static String piska4() {
        return piska3.replace("UIOL", "Mo");
    }

    public static String piska5() {
        return piska5.replace("NGHO", "ve");
    }

    @SubscribeEvent
    public void onUpdate(InputUpdateEvent event) {
        if (mc.player == null && mc.world == null && mc.currentScreen == null)
            return;

        if (mc.currentScreen != null) {
            if (!(mc.currentScreen instanceof GuiChat)) {

                if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                    event.getMovementInput().moveForward = 1f;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                    event.getMovementInput().moveForward = -1f;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                    event.getMovementInput().moveStrafe = 1f;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                    event.getMovementInput().moveStrafe = -1f;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    event.getMovementInput().jump = true;
                }

                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    event.getMovementInput().sneak = true;

                    if (Keyboard.isKeyDown(Keyboard.KEY_W))
                        event.getMovementInput().moveForward = 0.25f;

                    if (Keyboard.isKeyDown(Keyboard.KEY_S))
                        event.getMovementInput().moveForward = -0.25f;

                    if (Keyboard.isKeyDown(Keyboard.KEY_D))
                        event.getMovementInput().moveStrafe = -0.25f;

                    if (Keyboard.isKeyDown(Keyboard.KEY_A))
                        event.getMovementInput().moveStrafe = 0.25f;
                }
            }
        }
    }

}