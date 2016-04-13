package Mod;

import items.GlassUpFiller;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

import commands.GlassUpFillerCommand;

@Mod(modid = GlassUpFillerMod.modid, version = GlassUpFillerMod.version, name = "GlassUpFillerMod")
public class GlassUpFillerMod
{
    public static Item glassUpFiller;
    
    public static Block blockMobSpawner;

    @SidedProxy(clientSide = "proxies.ClientProxy", serverSide = "proxies.ServerProxy")
    public static CommonProxy proxy;

    public static final String modid = "glassupfillermod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerItem(glassUpFiller = new GlassUpFiller(), GlassUpFiller.NAME);

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
        event.registerServerCommand(new GlassUpFillerCommand());
    }    
}
