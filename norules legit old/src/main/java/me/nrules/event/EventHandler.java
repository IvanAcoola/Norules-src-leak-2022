package me.nrules.event;

import me.nrules.module.Module;
import me.nrules.module.ModuleManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class EventHandler {
    public List<Module> enableModule() {
        return ModuleManager.modules.stream().filter(Module::isToggled).collect(toList());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void logIn(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        new ConnectionHandler(this, (NetHandlerPlayClient) e.getHandler());
    }
}
