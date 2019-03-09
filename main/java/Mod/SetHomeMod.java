package Mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import proxies.CommonProxy;

import commands.SetHomeCommand;
import commands.HomeCommand;

@Mod.EventBusSubscriber(modid = SetHomeMod.modid)
@Mod(modid = SetHomeMod.modid, version = SetHomeMod.version, name = "sethomemod")
public class SetHomeMod
{
    @SidedProxy(clientSide = "proxies.CommonProxy", serverSide = "proxies.CommonProxy")
    public static CommonProxy proxy;

    public static final String modid = "sethomemod";
    public static final String version = "1.0.0";

    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new SetHomeCommand());
        event.registerServerCommand(new HomeCommand());
    }    
}