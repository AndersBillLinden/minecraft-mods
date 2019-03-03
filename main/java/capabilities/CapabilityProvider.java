package capabilities;

import items.MagicWand.IMagicWandPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IMagicWandPlayer.class)
    public static final Capability<IMagicWandPlayer> WAND_CAPABILITY = null;
    
    private IMagicWandPlayer instance = WAND_CAPABILITY.getDefaultInstance();
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == WAND_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == WAND_CAPABILITY ? WAND_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return WAND_CAPABILITY.getStorage().writeNBT(WAND_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        WAND_CAPABILITY.getStorage().readNBT(WAND_CAPABILITY, this.instance, null, nbt);
    }
}
