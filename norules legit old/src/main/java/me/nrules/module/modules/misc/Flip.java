package me.nrules.module.modules.misc;

import me.nrules.module.Category;
import me.nrules.module.Module;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class Flip extends Module {
    public Flip() {
        super("Flip", Category.MISC);
    }


    @SubscribeEvent
    public void onUpdate(TickEvent.ClientTickEvent event) {
        try {
            new EntityRenderer(mc, mc.getResourceManager());
            Method declaredMethod = EntityRenderer.class.getDeclaredMethod("orientCamera", float.class);
            declaredMethod.setAccessible(true);
            MethodHandle mh = MethodHandles.lookup().unreflect(declaredMethod);
            mh.invoke(new EntityRenderer(mc, mc.getResourceManager()), float.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}