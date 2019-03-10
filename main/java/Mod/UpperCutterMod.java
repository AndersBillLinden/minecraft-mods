package Mod;

import commands.UpperCutterCommand;
import items.UpperCutter;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = UpperCutterMod.modid)
@Mod(modid = UpperCutterMod.modid, version = UpperCutterMod.version, name = "UpperCutterMod")
public class UpperCutterMod
{
    public static Item upperCutter = new UpperCutter();
    
    public static final String modid = "uppercuttermod";
    public static final String version = "1.0.0";

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(upperCutter);
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        Item item = upperCutter;
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }     

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new UpperCutterCommand());
    }    
}
