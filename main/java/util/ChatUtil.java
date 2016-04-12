package util;

import java.awt.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ChatUtil
{
    public static void msg(EntityPlayer player, String message)
    {
        player.addChatMessage(new ChatComponentText(message));
    }
    
    public static void msgAll(String message)
    {
        World w = MinecraftServer.getServer().getEntityWorld();
        
        java.util.List<EntityPlayer> players = w.playerEntities;
        
        for (EntityPlayer p : players)
        {
            msg(p, message);
        }
    }
}
