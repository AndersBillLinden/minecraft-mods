package proxies;

import capabilities.CapabilityHandler;
import items.MagicWand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import sounds.Sounds;

@EventBusSubscriber
public class CommonProxy
{   
    public void preinit()
    {
        CapabilityManager.INSTANCE.register(MagicWand.IMagicWandPlayer.class, new MagicWand.MagicWandPlayerStorage(),
                new MagicWand.MagicWandPlayerStorage.Factory());
        
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new Sounds());
    }
    
    public void init()
    {
    }
}
