package Mod;

import commands.GlassUpFillerCommand;
import items.GlassUpFiller;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = GlassUpFillerMod.modid)
@Mod(modid = GlassUpFillerMod.modid, version = GlassUpFillerMod.version)
public class GlassUpFillerMod
{
    public static Item glassUpFiller = new GlassUpFiller();

    public static final String modid = "glassupfillermod";
    public static final String version = "1.0.0";

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(glassUpFiller);
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        Item item = glassUpFiller;
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }    
        
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new GlassUpFillerCommand());
    }    
}
