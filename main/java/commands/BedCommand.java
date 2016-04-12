package commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import util.ChatUtil;

public class BedCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "bed";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "bed";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return new ArrayList<String>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();
        
        if (!world.isRemote)
        {
            Entity entity = sender.getCommandSenderEntity();
            
            if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;
             
                BlockPos bedPos = player.getBedLocation();
                
                if (bedPos != null)
                {
                    player.setPositionAndUpdate(bedPos.getX() + 0.5D, bedPos.getY(), bedPos.getZ() + 0.5D);
                    ChatUtil.msg(player, "Teleported to bed");
                }
                else
                {
                    ChatUtil.msg(player, "There is no bed position");
                }
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
