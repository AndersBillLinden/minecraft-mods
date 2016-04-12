package Mod;

import items.UpperCutter;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

import commands.UpperCutterCommand;

@Mod(modid = UpperCutterMod.modid, version = UpperCutterMod.version, name = "UpperCutterMod")
public class UpperCutterMod
{
    public static Item upperCutter;
    
    @SidedProxy(clientSide = "proxies.ClientProxy", serverSide = "proxies.ServerProxy")
    public static CommonProxy proxy;

    public static final String modid = "uppercuttermod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerItem(upperCutter = new UpperCutter(), UpperCutter.NAME);

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
        event.registerServerCommand(new UpperCutterCommand());
    }    
}
