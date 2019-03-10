package commands;

import java.util.ArrayList;
import java.util.List;

import capabilities.CapabilityProvider;
import capabilities.ISetHomePlayerLocations;
import locations.Home;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.ChatUtil;

public class HomesCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "homes";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "homes";
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
                
                ArrayList<Home> homes = p.GetHomes();
                
                if (homes.size() == 0)
                {
                    ChatUtil.msg(player, "Empty list of homes");
                }
                else
                {
                    ArrayList<String> homes2 = new ArrayList<String>();
                    for (Home home : homes)
                    {
                        homes2.add(home.name);
                    }
                    
                    ChatUtil.msg(player, "Homes: " + String.join(", ", homes2));
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
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
