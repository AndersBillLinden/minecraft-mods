package capabilities;

import java.util.concurrent.Callable;

public class SetHomePlayerFactory implements Callable<ISetHomePlayerLocations>
{
    @Override
    public ISetHomePlayerLocations call() throws Exception
    {
        return new SetHomePlayerLocations();
    }
}    
