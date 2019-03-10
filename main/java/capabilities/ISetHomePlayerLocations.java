package capabilities;

import java.util.ArrayList;

import capabilities.SetHomePlayerLocations.HomeNotFoundException;
import capabilities.SetHomePlayerLocations.HomesNotFoundException;
import locations.Home;

public interface ISetHomePlayerLocations
{
    void SetHome(Home location);
    Home GetHome(String name) throws HomeNotFoundException;
    ArrayList<Home> GetHomes();
    int DelHome(String name) throws HomeNotFoundException;
    int ClearHomes() throws HomesNotFoundException;
    void SetLocations(ArrayList<Home> locations);
}
