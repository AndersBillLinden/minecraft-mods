package commands;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicates;

import Mod.ExtraCommandsMod;
import util.ChatUtil;
import util.InventoryUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class NoMobsCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getCommandName()
    {
        return "nomobs";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "nomobs";
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
            
                int numZombiesRemoved = 0;
                List<EntityZombie> zombies = world.getEntities(EntityZombie.class, Predicates.<EntityZombie>alwaysTrue());
    
                for (EntityZombie z : zombies)
                {
                    z.isDead = true;
                    numZombiesRemoved++;
                }
    
                int numSkeletonsRemoved = 0;
                List<EntitySkeleton> skeletons = world.getEntities(EntitySkeleton.class, Predicates.<EntitySkeleton>alwaysTrue());
                for (EntitySkeleton s : skeletons)
                {
                    s.isDead = true;
                    numSkeletonsRemoved++;
                }
    
                int numSpidersRemoved = 0;
                List<EntitySpider> spiders = world.getEntities(EntitySpider.class, Predicates.<EntitySpider>alwaysTrue());
                for (EntitySpider s : spiders)
                {
                    s.isDead = true;
                    numSpidersRemoved++;
                }
    
                int numCreepersRemoved = 0;
                List<EntityCreeper> creepers = world.getEntities(EntityCreeper.class, Predicates.<EntityCreeper>alwaysTrue());
                for (EntityCreeper c : creepers)
                {
                    c.isDead = true;
                    numCreepersRemoved++;
                }
                
                ChatUtil.msg(player, "Removed zombies: " + numZombiesRemoved +
                        ", skeletons: " + numSkeletonsRemoved +
                        ", spiders: " + numSpidersRemoved +
                        ", creepers: " + numCreepersRemoved);
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
