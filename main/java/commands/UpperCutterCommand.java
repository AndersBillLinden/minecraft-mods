package commands;

import java.util.ArrayList;
import java.util.List;

import Mod.UpperCutterMod;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import util.ChatUtil;
import util.InventoryUtil;

public class UpperCutterCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "uppercutter";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "uppercutter";
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
             
                try
                {
                    InventoryUtil.addStackToInventory(new ItemStack(UpperCutterMod.upperCutter, 1), player);
                    ChatUtil.msg(player, "Added uppercutter!");                    
                }
                catch(InventoryUtil.InventoryFullException e)
                {
                    ChatUtil.msg(player, "The inventory was full, cannot add uppercutter!");
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
        return new ArrayList<String>();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
