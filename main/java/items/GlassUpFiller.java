package items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.ChatUtil;

public class GlassUpFiller extends Item
{
    public static final String NAME = "glassupfiller";
    
    public GlassUpFiller()
    {
        setMaxStackSize(1);
        setRegistryName(NAME);
        setUnlocalizedName(NAME);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ)
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
            IBlockState glass = Blocks.GLASS.getDefaultState();
            
            for (int yi = minY; yi < maxY; yi++)
            {
                world.setBlockState(new BlockPos(x, yi, z), glass);
            }
            
            //world.playSoundAtEntity(player, "random.glass", 1, 1);
        }
        
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
}
