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

public class TeleportToHighPointCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "higher";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "higher";
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
                BlockPos maxHeightPos = null;
                
                EntityPlayer player = (EntityPlayer)entity;
                BlockPos pos = player.getPosition();
             
                for (int z = -3; z <= 3; z++)
                {
                    for (int x = -3; x <= 3; x++)
                    {
                        BlockPos pos_ = world.getHeight(pos.add(x, 0, z));
                        int height = pos_.getY();
                        if (height > maxHeight)
                        {
                            maxHeightPos = pos_;
                            maxHeight = height;
                        }
                    }
                }

                if (maxHeightPos != null)
                {
                    player.setPositionAndUpdate(maxHeightPos.getX() + 0.5D, maxHeightPos.getY(), maxHeightPos.getZ() + 0.5D);
                    ChatUtil.msg(player, "Teleported to highest location in 7x7 area");
                }
                else
                {
                    ChatUtil.msg(player, "Could not find high point");
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
