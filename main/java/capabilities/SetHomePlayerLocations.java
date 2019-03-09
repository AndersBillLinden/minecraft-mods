package capabilities;

import java.util.ArrayList;

import locations.Location;

public class SetHomePlayerLocations implements ISetHomePlayerLocations
{
    private ArrayList<Location> locations = new ArrayList<Location>();
    
    public void SetLocation(String name, Location location)
    {
        for (Location loc : locations)
        {
            if (loc.name.equals(name))
            {
                loc.X = location.X;
                loc.Y = location.Y;
                loc.Z = location.Z;
                loc.yaw = location.yaw;
                loc.yawhead = location.yawhead;
                loc.pitch = location.pitch;
                loc.flying = location.flying;
                
                return;
            }
        }
        
        locations.add(location);
    }
    
    public Location GetLocation(String name) throws LocationNotFoundException
    {
        for (Location loc : locations)
        {
            if (loc.name.equals(name))
            {
                return loc;
            }
        }
        
        throw new LocationNotFoundException();
    }
        
    public ArrayList<Location> GetLocations()
    {
        return locations;
    }
    
    public void SetLocations(ArrayList<Location> locations)
    {
        this.locations = locations;
    }
    
    @SuppressWarnings("serial")
    public class LocationNotFoundException extends Exception
    {
    }    
}
