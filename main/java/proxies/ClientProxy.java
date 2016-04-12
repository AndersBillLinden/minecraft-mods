package proxies;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import Mod.ExtraCommandsMod;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);
    }

    public void registerBlockModelAsItem(final Block block, int meta, final String blockName)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(ExtraCommandsMod.modid + ":" + blockName,
                "inventory"));
    }

    public void registerItemModel(final Item item, int meta, final String itemName)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(ExtraCommandsMod.modid + ":" + itemName, "inventory"));
    }
}
