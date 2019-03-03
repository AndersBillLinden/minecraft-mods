package util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil
{
    public static void msg(EntityPlayer player, String message)
    {
        player.sendMessage(new TextComponentString(message));
    }    
}
