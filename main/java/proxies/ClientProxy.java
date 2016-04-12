package proxies;

import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
        registerModels();
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);
    }

    public void registerModels()
    {
    }

    public void registerBlockModelAsItem(final Block block, int meta, final String blockName)
    {
    }

    public void registerItemModel(final Item item, int meta, final String itemName)
    {
    }
}
