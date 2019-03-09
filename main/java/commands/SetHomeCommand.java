package commands;

import java.util.ArrayList;
import java.util.List;

import capabilities.CapabilityProvider;
import capabilities.ISetHomePlayerLocations;
import locations.Location;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SetHomeCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "sethome";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "sethome <home>";
    }

    @Override
    public List<String> getAliases()
    {
        return new ArrayList<String>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
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
                
                if (args.length == 1)
                {
                    Vec3d position = player.getPositionVector();
                            
                    Location loc = new Location();
                    loc.X = position.x;
                    loc.Y = position.y;
                    loc.Z = position.z;
                    
                    loc.yaw = player.rotationYaw;
                    loc.pitch = player.rotationPitch;
                    loc.yawhead = player.rotationYawHead;
                    
                    String home = args[0];                    
                    p.SetLocation(home, loc);                    
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
}
