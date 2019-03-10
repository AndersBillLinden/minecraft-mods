package capabilities;

import java.util.ArrayList;

import com.google.gson.Gson;

import locations.Home;
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
        
        ArrayList<Home> locations = instance.GetHomes();
        
        Gson gson = new Gson();
        
        String str = gson.toJson(locations.toArray(new Home[0]));
        
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
            Home[] locations = str != null ? gson.fromJson(str, Home[].class) : new Home[0];
            
            ArrayList<Home> locations2 = new ArrayList<Home>();
            
            for(Home loc : locations)
            {
                if (loc.name != null)
                    locations2.add(loc);
            }
            
            instance.SetLocations(locations2);
        }
    }    
}
