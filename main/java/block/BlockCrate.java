package block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCrate extends Block
{
    public static final String NAME = "crate";
    
    public BlockCrate()
    {
        super(Material.GROUND);
        this.setUnlocalizedName(NAME);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return true;
    }    
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
