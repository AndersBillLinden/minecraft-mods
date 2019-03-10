package capabilities;

import java.util.ArrayList;

import locations.Home;

public class SetHomePlayerLocations implements ISetHomePlayerLocations
{
    private ArrayList<Home> homes = new ArrayList<Home>();
    
    public void SetHome(Home location)
    {
        for (Home loc : homes)
        {
            if (loc.name.equals(location.name))
            {
                loc.X = location.X;
                loc.Y = location.Y;
                loc.Z = location.Z;
                loc.dimension = location.dimension;
                loc.yaw = location.yaw;
                loc.yawhead = location.yawhead;
                loc.pitch = location.pitch;
                loc.flying = location.flying;
                
                return;
            }
        }
        
        homes.add(location);
    }
    
    public Home GetHome(String name) throws HomeNotFoundException
    {
        for (Home loc : homes)
        {
            if (loc.name.equals(name))
            {
                return loc;
            }
        }
        
        throw new HomeNotFoundException();
    }
     
    public int DelHome(String name) throws HomeNotFoundException
    {
        int num = 0;
        ArrayList<Home> result = new ArrayList<Home>();
        
        for (Home home : homes)
        {
            if (!home.name.equals(name))
                result.add(home);
            else
                num++;
        }
        
        if (num == 0)
            throw new HomeNotFoundException();
        
        homes = result;
        
        return num;
    }
        
    public ArrayList<Home> GetHomes()
    {
        return homes;
    }

    public int ClearHomes() throws HomesNotFoundException
    {
        int num = homes.size(); 
        if (num == 0)
            throw new HomesNotFoundException();
        
        homes = new ArrayList<Home>();
        
        return num;
    }
    
    public void SetLocations(ArrayList<Home> locations)
    {
        this.homes = locations;
    }
    
    @SuppressWarnings("serial")
    public class HomeNotFoundException extends Exception
    {
    }
    
    @SuppressWarnings("serial")
    public class HomesNotFoundException extends Exception
    {
    }        
}
