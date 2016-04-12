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
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;
import util.ChatUtil;

public class GetNumberOfVillagesCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "villages";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "villages";
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

                VillageCollection villages = world.getVillageCollection();
                
                List<Village> villageList = villages.getVillageList();
                
                String msg = "Number of villages: " + villageList.size() + "  ";
                
                int num = 0;
                for (Village v : villageList)
                {
                    int numDoors = v.getNumVillageDoors();
                    int numVillagers = v.getNumVillagers();
                    
                    msg += "" + ++num + ". doors: " + numDoors + ", villagers: " + numVillagers + "  ";
                }
                
                ChatUtil.msg(player, msg);
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
