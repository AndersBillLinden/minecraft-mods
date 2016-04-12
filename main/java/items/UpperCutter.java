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
import Mod.UpperCutterMod;

public class UpperCutter extends Item
{
    public static final String NAME = "uppercutter";
    
    public UpperCutter()
    {
        setMaxStackSize(1);
        setUnlocalizedName(NAME);
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
            BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        //BlockPos top = world.getHeight(pos);
        
        int x = pos.getX();
        int z = pos.getZ();
        int minY = pos.getY();
        //aint maxY = top.getY();
        
        for (int yi = minY; yi < 256; yi++)
        {
            world.setBlockToAir(new BlockPos(x, yi, z));
        }
        
        world.playSoundAtEntity(player, "dig.stone", 1, 1);
        
        return true;
    }
}
