package Mod;

import items.MagicWand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxies.CommonProxy;

@Mod(modid = MagicWandMod.modid, version = MagicWandMod.version, name = "MagicWandMod")
public class MagicWandMod
{
    public static Item magicwand;
    public static Item cheese;

    @SidedProxy(clientSide = "proxies.ClientProxy", serverSide = "proxies.ServerProxy")
    public static CommonProxy proxy;

    public static final String modid = "magicwandmod";
    public static final String version = "1.0.0";

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        GameRegistry.registerItem(magicwand = new MagicWand(), MagicWand.NAME);

        GameRegistry.addRecipe(new ItemStack(magicwand), "  *", " / ", "/  ", '*', Items.redstone, '/', Items.stick);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
}
