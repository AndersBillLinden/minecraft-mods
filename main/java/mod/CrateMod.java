package mod;

import block.BlockCrate;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

@EventBusSubscriber(modid = CrateMod.MODID)
@Mod(modid = CrateMod.MODID, version = CrateMod.VERSION)
public class CrateMod
{
    public static final String MODID = "cratemod";
    public static final String VERSION = "1.0";

    public static Block blockCrate;
    public static Item itemCrate;
    
    @SidedProxy(clientSide = "proxies.CommonProxy", serverSide = "proxies.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        blockCrate = new BlockCrate().setRegistryName("crate").setUnlocalizedName("crate");
        itemCrate = new ItemBlock(blockCrate).setRegistryName("crate").setUnlocalizedName("crate");
        
        proxy.preInit(event);
    }    
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        GameRegistry.addShapedRecipe(new ResourceLocation(BlockCrate.NAME),
            new ResourceLocation(MODID), new ItemStack(blockCrate), new Object[]
            {
                    "###",
                    "###",
                    "###",
                    '#', Blocks.PLANKS                               
            });
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {        
        event.getRegistry().registerAll(itemCrate);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(blockCrate);
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        registerModel(itemCrate);
    }
        
    static void registerModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }    
}
