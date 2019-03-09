package capabilities;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import com.google.gson.Gson;

import locations.Location;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class SetHomePlayerStorage implements IStorage<ISetHomePlayerLocations>
{

    @Override
    public NBTBase writeNBT(Capability<ISetHomePlayerLocations> capability, ISetHomePlayerLocations instance,
            EnumFacing side)
    {
        NBTTagCompound tag = new NBTTagCompound();
        
        ArrayList<Location> locations = instance.GetLocations();
        
        Gson gson = new Gson();
        
        String str = gson.toJson(locations.toArray(new Location[0]));
        
        tag.setString("locations", str);
        
        return tag;
    }

    @Override
    public void readNBT(Capability<ISetHomePlayerLocations> capability, ISetHomePlayerLocations instance,
            EnumFacing side, NBTBase nbt)
    {
        if (nbt instanceof NBTTagCompound)
        {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            
            String str = tag.getString("locations");
            
            Gson gson = new Gson();
            Location[] locations = gson.fromJson(str, Location[].class);
            
            ArrayList<Location> locations2 = new ArrayList<Location>();
            
            for(Location loc : locations)
            {
                locations2.add(loc);
            }
            
            instance.SetLocations(locations2);
        }
    }    
}
