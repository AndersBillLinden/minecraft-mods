package commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.ChatUtil;

public class TeleportToRoomBeneathCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "beneath";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "beneath";
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
                BlockPos pos = player.getPosition().down();
             
                while (pos.getY() > 0 && !world.isAirBlock(pos))
                {
                    pos = pos.down();
                }
                
                while (pos.getY() > 0 && world.isAirBlock(pos))
                {
                    pos = pos.down();
                }
                
                pos = pos.up();

                if (pos.getY()> 0 && world.isAirBlock(pos))
                {
                    player.setPositionAndUpdate(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                    ChatUtil.msg(player, "Teleported to room beneath");
                }
                else
                {
                    ChatUtil.msg(player, "Command failed");
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
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
            net.minecraft.util.math.BlockPos targetPos)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
