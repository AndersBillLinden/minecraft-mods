package items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpperCutter extends Item
{
    public static final String NAME = "uppercutter";
    
    public UpperCutter()
    {
        setMaxStackSize(1);
        setRegistryName(NAME);
        setUnlocalizedName(NAME);
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ)
    {
        int x = pos.getX();
        int z = pos.getZ();
        int minY = pos.getY();
        
        for (int yi = minY; yi < 256; yi++)
        {
            world.setBlockToAir(new BlockPos(x, yi, z));
        }
        
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }
}
