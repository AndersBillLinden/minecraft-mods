package commands;

import java.util.ArrayList;
import java.util.List;

import capabilities.CapabilityProvider;
import capabilities.ISetHomePlayerLocations;
import capabilities.SetHomePlayerLocations.HomeNotFoundException;
import locations.Home;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.ChatUtil;

public class DelHomeCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "delhome";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "delhome";
    }

    @Override
    public List<String> getAliases()
    {
        return new ArrayList<String>();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();
        
        if (!world.isRemote)
        {
            Entity entity = sender.getCommandSenderEntity();
            
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;
        
                ISetHomePlayerLocations p = player.getCapability(CapabilityProvider.LOCATIONS_CAPABILITY, null);
                
                try
                {
                    if (args.length == 1)
                    {
                        String home = args[0];
                        p.DelHome(home);

                        ChatUtil.msg(player, "Deleting the home " + home);
                    }
                }
                catch (HomeNotFoundException e)
                {
                    ChatUtil.msg(player, "No home with that name!");
                }
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, net.minecraft.util.math.BlockPos targetPos)
    {
        ArrayList<String> suggestions = new ArrayList<String>();
        
        if (args.length > 0)
        {
            String homestart = args[0];
            World world = sender.getEntityWorld();
            if (!world.isRemote)
            {
                Entity entity = sender.getCommandSenderEntity();
                
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer player = (EntityPlayer)entity;
                    ISetHomePlayerLocations p = player.getCapability(CapabilityProvider.LOCATIONS_CAPABILITY, null);
                    
                    for (Home home: p.GetHomes())
                    {
                        if (home.dimension == player.dimension && home.name.startsWith(homestart))
                        {
                            suggestions.add(home.name);
                        }
                    }
                }
            }
        }
        
        return suggestions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
