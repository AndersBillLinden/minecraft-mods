package commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CreeperRingCommand implements ICommand
{
    @Override
    public int compareTo(ICommand arg0)
    {
        return 0;
    }

    @Override
    public String getName()
    {
        return "creepers";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "creepers";
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
            
                BlockPos pos = player.getPosition();
                
                for (int i = 0; i < 360; i+= 10)
                {
                    double angle = Math.PI * ((double)i) / 180;
                    double x = pos.getX() + 20 * Math.cos(angle);                    
                    double z = pos.getZ() + 20 * Math.sin(angle);
                    
                    BlockPos creeperPos = new BlockPos(x, 0, z);
                    
                    BlockPos highPos = world.getHeight(creeperPos);
                    
                    EntityCreeper creeper = new EntityCreeper(world);
                    creeper.rotationYaw = i;
                    creeper.setPosition(creeperPos.getX(), highPos.getY(), creeperPos.getZ());
                    
                    world.spawnEntity(creeper);
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
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }    
}
