package sounds;

import Mod.MagicWandMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = MagicWandMod.MODID)
public class Sounds
{
    public static SoundEvent magicWand;
    public static SoundEvent magicWandBreak;
    
    @SubscribeEvent
    public static void registerSounds(Register<SoundEvent> event)
    {
       IForgeRegistry<SoundEvent> registry = event.getRegistry();
        
       ResourceLocation loc1 = new ResourceLocation(MagicWandMod.MODID, "magic_wand");
       magicWand = new SoundEvent(loc1);
       magicWand.setRegistryName(loc1);

       ResourceLocation loc2 = new ResourceLocation(MagicWandMod.MODID, "magic_wand_break");
       magicWandBreak = new SoundEvent(loc2);
       magicWandBreak.setRegistryName(loc2);

       registry.registerAll(magicWand, magicWandBreak);
    }
}
