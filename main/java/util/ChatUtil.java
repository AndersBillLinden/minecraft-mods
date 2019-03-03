package util;

import java.awt.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ChatUtil
{
    public static void msg(EntityPlayer player, String message)
    {
        player.sendMessage(new TextComponentString(message));
    }
    
    public static void msgAll4(String message)
    {
        //World w = MinecraftServer.class.
                
        //        .getServer().getEntityWorld();
        /*
        java.util.List<EntityPlayer> players = w.playerEntities;
        
        for (EntityPlayer p : players)
        {
            msg(p, message);
        }*/
    }
}
