package Mod;

import items.IronGolemFarmKit;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

import commands.AddIronGolemFarmCommand;

@Mod(modid = IronGolemFarmMod.modid, version = IronGolemFarmMod.version, name = "IronGolemFarmMod")
public class IronGolemFarmMod
{
    public static Item ironGolemFarmKit;
    
    public static Block blockMobSpawner;

    @SidedProxy(clientSide = "proxies.ClientProxy", serverSide = "proxies.ServerProxy")
    public static CommonProxy proxy;

    public static final String modid = "irongolemfarmmod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerItem(ironGolemFarmKit = new IronGolemFarmKit(), IronGolemFarmKit.NAME);

        GameRegistry.addRecipe(new ItemStack(ironGolemFarmKit), "***", "///", "///", '*', Items.IRON_INGOT, '/', Items.STICK);

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
        event.registerServerCommand(new AddIronGolemFarmCommand());
    }    
}
