package capabilities;

import java.util.ArrayList;

import capabilities.SetHomePlayerLocations.LocationNotFoundException;
import locations.Location;

public interface ISetHomePlayerLocations
{
    void SetLocation(String name, Location location);
    Location GetLocation(String name) throws LocationNotFoundException;
    ArrayList<Location> GetLocations();
    void SetLocations(ArrayList<Location> locations);
}
