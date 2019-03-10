package mod;

import commands.ClearHomesCommand;
import commands.DelHomeCommand;
import commands.HomeCommand;
import commands.HomesCommand;
import commands.SetHomeCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import proxies.CommonProxy;

@EventBusSubscriber(modid = SetHomeMod.modid)
@Mod(modid = SetHomeMod.modid, version = SetHomeMod.version, name = SetHomeMod.modid)
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
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new ClearHomesCommand());
        event.registerServerCommand(new DelHomeCommand());
        event.registerServerCommand(new HomeCommand());
        event.registerServerCommand(new HomesCommand());
        event.registerServerCommand(new SetHomeCommand());
    }
}
