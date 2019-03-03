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
        player.addChatComponentMessage(new ChatComponentText(message));
    }    
}
