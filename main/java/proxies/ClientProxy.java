package proxies;

import items.MagicWand;
import items.MagicWand.MagicWandPlayer;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import Mod.MagicWandMod;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
        registerModels();

        MagicWandPlayer.initHandling();
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);
    }

    public void registerModels()
    {
        registerItemModel(MagicWandMod.magicwand, 0, MagicWand.NAME);
    }

    public void registerBlockModelAsItem(final Block block, int meta, final String blockName)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(MagicWandMod.modid + ":" + blockName,
                "inventory"));
    }

    public void registerItemModel(final Item item, int meta, final String itemName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(MagicWandMod.modid + ":" + itemName, "inventory"));
    }
}
