package commands;

import java.util.ArrayList;
import java.util.List;

import util.ChatUtil;
import util.InventoryUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class VillagerSpawnerEggsCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "villagereggs";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "villagereggs";
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
             
                int typeId = 120;
                
                try
                {
                    InventoryUtil.addStackToInventory(new ItemStack(Items.spawn_egg, 64, typeId), player);
                    ChatUtil.msg(player, "Added villager spawner eggs!");
                }
                catch(InventoryUtil.InventoryFullException e)
                {
                    ChatUtil.msg(player, "The inventory was full, cannot add villager spawner eggs!");
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
