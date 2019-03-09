package commands;

import java.util.ArrayList;
import java.util.List;

import capabilities.CapabilityProvider;
import capabilities.ISetHomePlayerLocations;
import capabilities.SetHomePlayerLocations.LocationNotFoundException;
import locations.Location;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.ChatUtil;

public class HomeCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "home";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "home <name of home>";
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
                        Location loc = p.GetLocation(home);
                        
                        player.setPositionAndRotation(loc.X, loc.Y, loc.Z, loc.yaw, loc.pitch);
                        player.setRotationYawHead(loc.yawhead);
                    }
                }
                catch (LocationNotFoundException e)
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
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
