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
import net.minecraft.village.VillageDoorInfo;
import net.minecraft.world.World;
import util.ChatUtil;

public class DestroyVillageDoorsCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "novillagedoors";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "novillagedoors";
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
                                
                int num = 0;
                for (Village v : villageList)
                {
                    List<VillageDoorInfo> infos = v.getVillageDoorInfoList();
                    
                    for (VillageDoorInfo info : infos)
                    {
                        BlockPos pos = info.getDoorBlockPos();
                        world.setBlockToAir(pos);
                        num++;
                    }
                }
                
                ChatUtil.msg(player, "Destroyed " + num + " village doors");
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
