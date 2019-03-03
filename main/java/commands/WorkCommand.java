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

public class WorkCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "work";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "work";
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
                BlockPos pos = calcHashFromPlayer(world, player);
             
                BlockPos playerCurrentPos = player.getPosition();
                
                player.setPositionAndUpdate(pos.getX() + 0.5D, playerCurrentPos.getY(), pos.getZ() + 0.5D);
                ChatUtil.msg(player, "Teleported to work site");
            }
        }
    }
    
    private BlockPos calcHashFromPlayer(World world, EntityPlayer player)
    {
        String nick = player.getDisplayNameString();
        int strlen = nick.length();
        
        int xhash = 557;
        int zhash = 141;
        for (int i = 0; i < strlen; i++) {
            xhash = xhash * 31 + nick.charAt(i);
            zhash = zhash * 31 + nick.charAt(i);
        }
        
        int x = ((xhash >= 0 ? xhash : -xhash) % 100000) - 50000;
        int z = ((zhash >= 0 ? zhash : -zhash) % 100000) - 50000;
        
        BlockPos pos = new BlockPos(x, 0, z);
                
        return pos;
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
