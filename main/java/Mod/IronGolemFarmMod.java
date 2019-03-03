package Mod;

import items.IronGolemFarmKit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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

    @EventHandler
    public void load(FMLPreInitializationEvent event)
    {
        GameRegistry.addShapedRecipe(new ResourceLocation(IronGolemFarmKit.NAME),
                new ResourceLocation(IronGolemFarmMod.modid),
                new ItemStack(ironGolemFarmKit),
                new Object[]
        {
            "***",
            "///",
            "///",
            '*', Items.IRON_INGOT, '/', Items.STICK
        });

        proxy.preInit(event);
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ironGolemFarmKit = new IronGolemFarmKit());
    }    

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(ironGolemFarmKit);
    }
        
    static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }        
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new AddIronGolemFarmCommand());
    }    
}
