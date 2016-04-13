package items;

import java.lang.reflect.Field;
import java.util.Set;

import util.ChatUtil;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Mod.GlassUpFillerMod;

public class GlassUpFiller extends Item
{
    public static final String NAME = "glassupfiller";
    
    public GlassUpFiller()
    {
        setMaxStackSize(1);
        setUnlocalizedName(NAME);
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
            BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        BlockPos top = world.getHeight(pos);
        
        int x = pos.getX();
        int z = pos.getZ();
        int minY = pos.getY();
        int maxY = top.getY();
        
        if (minY < player.getPosition().getY())
        {
            ChatUtil.msg(player, "Cant use this tool beneath or at the same level as yourself!");
        }
        else
        {        
            IBlockState glass = Blocks.glass.getDefaultState();
            
            for (int yi = minY; yi < maxY; yi++)
            {
                world.setBlockState(new BlockPos(x, yi, z), glass);
            }
            
            world.playSoundAtEntity(player, "random.glass", 1, 1);
        }
        return true;
    }
}
