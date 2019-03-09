package proxies;

import capabilities.CapabilityHandler;
import capabilities.ISetHomePlayerLocations;
import capabilities.SetHomePlayerFactory;
import capabilities.SetHomePlayerStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        CapabilityManager.INSTANCE.register(ISetHomePlayerLocations.class, new SetHomePlayerStorage(),
                new SetHomePlayerFactory());

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    }
}
