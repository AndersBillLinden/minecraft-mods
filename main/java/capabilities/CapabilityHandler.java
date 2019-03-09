package capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler
{
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        Entity obj = event.getObject();
        
        if (obj instanceof EntityPlayer)
        {
            event.addCapability(new ResourceLocation(CapabilityProvider.LOCATIONS_CAPABILITY.getName()),
                    new CapabilityProvider());
        }        
    }
}
