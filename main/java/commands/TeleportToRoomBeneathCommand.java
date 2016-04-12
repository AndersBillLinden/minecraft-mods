package commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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
    public String getCommandName()
    {
        return "beneath";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "beneath";
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
                int maxHeight = 0;
                
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
