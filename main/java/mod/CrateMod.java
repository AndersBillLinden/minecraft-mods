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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = CrateMod.MODID)
@Mod(modid = CrateMod.MODID, version = CrateMod.VERSION)
public class CrateMod
{
    public static final String MODID = "cratemod";
    public static final String VERSION = "1.0";
    
    public static Item itemCrate = null;
    private static Item getItemCrate()
    {
        if (itemCrate == null)
        {
            itemCrate = new ItemBlock(getBlockCrate());
            itemCrate.setRegistryName(MODID, BlockCrate.NAME).setUnlocalizedName("crate");
            itemCrate.setMaxStackSize(64);
        }
        
        return itemCrate;
    }
    
    public static Block blockCrate = null;
    private static Block getBlockCrate()
    {
        if (blockCrate == null)
        {
            blockCrate = new BlockCrate();
            blockCrate.setRegistryName(MODID, BlockCrate.NAME);
        }
        
        return blockCrate;
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
        event.getRegistry().register(getItemCrate());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().register(getBlockCrate());
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        Item item = getItemCrate();
        
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
        
    }
}
