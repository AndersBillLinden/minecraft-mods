package Mod;

import items.MagicWand;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

@Mod.EventBusSubscriber(modid = MagicWandMod.MODID)
@Mod(modid = MagicWandMod.MODID, version = MagicWandMod.VERSION)
public class MagicWandMod
{
    public static final String MODID = "magicwandmod";
    public static final String VERSION = "1.0";

    public static Item magicwand = new MagicWand();
                
    @SidedProxy(clientSide = "proxies.CommonProxy", serverSide = "proxies.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preinit(FMLInitializationEvent event)
    {
        proxy.preinit();
    }    
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        GameRegistry.addShapedRecipe(new ResourceLocation(MagicWand.NAME),
                new ResourceLocation(MODID), new ItemStack(magicwand), new Object[]
                {
                        "  *",
                        " / ",
                        "/  ",
                        '*', Items.REDSTONE, '/', Items.STICK                                
                });
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(magicwand);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(magicwand);
    }
        
    static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }    
}
