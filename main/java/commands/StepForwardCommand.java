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

public class StepForwardCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "stepforward";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "stepforward";
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
                BlockPos pos = player.getPosition();
                
                float direction = (360 + player.rotationYaw) % 360;

                boolean south = direction < 90 || direction >= 270;
                boolean north = direction >= 90 && direction < 270;
                
                boolean west = direction < 180;
                boolean east = direction >= 180;
                
                if (north)
                    pos = pos.north();
                else if (south)
                    pos = pos.south();
                
                if (west)
                    pos = pos.west();
                else if (east)
                    pos = pos.east();
                
                
                pos = world.getHeight(pos);
                
                player.setPositionAndUpdate(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                ChatUtil.msg(player, "Took safe step forward");
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
