package Mod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import proxies.CommonProxy;

import commands.FreetimeCommand;
import commands.WorkCommand;

@Mod(modid = ExtraCommandsMod.modid, version = ExtraCommandsMod.version, name = "ExtraCommandsMod")
public class ExtraCommandsMod
{
    @SidedProxy(clientSide = "proxies.CommonProxy", serverSide = "proxies.CommonProxy")
    public static CommonProxy proxy;

    public static final String modid = "extracommandsmod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new FreetimeCommand());
        event.registerServerCommand(new WorkCommand());
    }    
}
