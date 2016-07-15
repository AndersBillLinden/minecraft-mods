package commands;

import java.util.ArrayList;
import java.util.List;

import Mod.IronGolemFarmMod;
import util.ChatUtil;
import util.InventoryUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AddIronGolemFarmCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "addfarm";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "addfarm";
    }

    @Override
    public List<String> getCommandAliases()
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
                    InventoryUtil.addStackToInventory(new ItemStack(IronGolemFarmMod.ironGolemFarmKit, 1), player);
                    ChatUtil.msg(player, "Added iron golem farm!");
                }
                catch(InventoryUtil.InventoryFullException e)
                {
                    ChatUtil.msg(player, "The inventory was full, cannot add iron golem farm!");
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
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			net.minecraft.util.math.BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}    
}
