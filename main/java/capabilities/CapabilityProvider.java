package capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ISetHomePlayerLocations.class)
    public static final Capability<ISetHomePlayerLocations> LOCATIONS_CAPABILITY = null;
    
    private ISetHomePlayerLocations instance = LOCATIONS_CAPABILITY.getDefaultInstance();
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == LOCATIONS_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == LOCATIONS_CAPABILITY ? LOCATIONS_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return LOCATIONS_CAPABILITY.getStorage().writeNBT(LOCATIONS_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        LOCATIONS_CAPABILITY.getStorage().readNBT(LOCATIONS_CAPABILITY, this.instance, null, nbt);
    }

}
