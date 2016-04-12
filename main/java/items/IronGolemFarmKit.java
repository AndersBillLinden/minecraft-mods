package items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos.MutableBlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class IronGolemFarmKit extends Item
{
    public static final String NAME = "iron_golem_farm_kit";

    private final int heightToFirstFloor = 5;
    public IronGolemFarmKit()
    {
        setMaxStackSize(64);
        setUnlocalizedName(NAME);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {        
        if (!world.isRemote)
        {
            float direction = (360 + player.rotationYaw) % 360;

            boolean south = direction < 90 || direction >= 270;
            boolean north = direction >= 90 && direction < 270;
            
            boolean west = direction < 180;
            boolean east = direction >= 180;

            int northVector = north ? 1 : -1;
            int eastVector = east ? 1 : -1;
            
            IBlockState stoneBrick = Blocks.stonebrick.getDefaultState();
            
            BlockPos corner1 = pos.up();
            BlockPos corner2 = corner1.east(19 * eastVector);
            BlockPos corner3 = corner2.north(19 * northVector);
            BlockPos corner4 = corner3.west(19 * eastVector);
            
            for (int i = 0; i < heightToFirstFloor; i++)
            {
                world.setBlockState(corner1.up(i), stoneBrick); 
                world.setBlockState(corner2.up(i), stoneBrick);
                world.setBlockState(corner3.up(i), stoneBrick); 
                world.setBlockState(corner4.up(i), stoneBrick); 
            }
            
            buildDownwardsToNonAir(world, corner1, stoneBrick);
            buildDownwardsToNonAir(world, corner2, stoneBrick);
            buildDownwardsToNonAir(world, corner3, stoneBrick);
            buildDownwardsToNonAir(world, corner4, stoneBrick);
            
            buildStage(world, corner1.up(heightToFirstFloor - 1), stoneBrick, northVector, eastVector);
            
            world.playSoundAtEntity(player, "random.explode", 1, 1);
            
            stack.stackSize--;
        }
        return true;
    }

    private void buildStage(World world, BlockPos corner1, IBlockState stoneBrick, int northVector, int eastVector)
    {        
        int x = corner1.getX();
        int y = corner1.getY();
        int z = corner1.getZ();
        
        buildFloorWithHole(world, stoneBrick, northVector, eastVector, x, y, z);
        buildWalls(world, stoneBrick, northVector, eastVector, x, y, z);
        buildDoors(world, northVector, eastVector, x, y + 2, z);
        
        buildTriangles(world, stoneBrick, northVector, eastVector, x, y, z);
        
        buildTorches(world, northVector, eastVector, x, y + 4, z);
        
        buildPockets(world, northVector, eastVector, x, y, z);
        
        addWater(world, northVector, eastVector, x, y, z);
        
        addVillagers(world, northVector, eastVector, x, y, z);
    }

    private void addWater(World world, int northVector, int eastVector, int x, int y, int z)
    {
        IBlockState water = Blocks.water.getDefaultState();

        BlockPos p1 = new BlockPos(x + eastVector, y + 2, z - northVector);
        world.setBlockState(p1, water);

        BlockPos p2 = new BlockPos(x + 18 * eastVector, y + 2, z - 18 * northVector);
        world.setBlockState(p2, water);

        BlockPos p3 = new BlockPos(x + 18 * eastVector, y + 2, z - northVector);
        world.setBlockState(p3, water);

        BlockPos p4 = new BlockPos(x + eastVector, y + 2, z - 18 * northVector);
        world.setBlockState(p4, water);
        
        for (int i = 9; i < 11; i++)
        {
            BlockPos p5 = new BlockPos(x + i * eastVector, y + 1, z - northVector);
            world.setBlockState(p5, water);

            BlockPos p6 = new BlockPos(x + eastVector, y + 1, z - i * northVector);
            world.setBlockState(p6, water);

            BlockPos p7 = new BlockPos(x + i * eastVector, y + 1, z - 18 * northVector);
            world.setBlockState(p7, water);

            BlockPos p8 = new BlockPos(x + 18 * eastVector, y + 1, z - i * northVector);
            world.setBlockState(p8, water);            
        }
    }

    private void buildPockets(World world, int northVector, int eastVector, int x, int y, int z)
    {
        IBlockState stoneBrick = Blocks.stonebrick.getDefaultState();
        IBlockState water = Blocks.water.getDefaultState();
        
        for (int yd = 0; yd < 4; yd++)
        {
            for (int ad = 0; ad < 6; ad++)
            {
                for (int bd = 0; bd < 5; bd++)
                {
                    BlockPos p1 = new BlockPos(x + (7 + ad) * eastVector, y + yd, z + (1 + bd) * northVector);
                    BlockPos p2 = new BlockPos(x - (1 + bd) * eastVector, y + yd, z - (7 + ad) * northVector);
                    BlockPos p3 = new BlockPos(x + (7 + ad) * eastVector, y + yd, z - (20 + bd) * northVector);
                    BlockPos p4 = new BlockPos(x + (20 + bd) * eastVector, y + yd, z - (7 + ad) * northVector);
                    
                    if (ad > 1 && ad < 4 && bd > 0 && bd < 3 && yd > 1)
                    {
                        if (yd == 2)
                        {
                            world.setBlockState(p1, water);
                            world.setBlockState(p2, water);
                            world.setBlockState(p3, water);
                            world.setBlockState(p4, water);
                        }
                    }
                    else
                    {
                        world.setBlockState(p1, stoneBrick);
                        world.setBlockState(p2, stoneBrick);
                        world.setBlockState(p3, stoneBrick);
                        world.setBlockState(p4, stoneBrick);
                    }                    
                }
            }
        }
    }

    private void addVillagers(World world, int northVector, int eastVector, int x, int y, int z)
    {
        for (int ad = 0; ad < 2; ad++)
        {
            for (int bd = 0; bd < 2; bd++)
            {
                addVillager(world, x + (9.5f + ad) * eastVector, y, z + (1.5f + bd) * northVector); // L
                addVillager(world, x - (1.5f + ad) * eastVector, y, z - (9.5f + bd)* northVector); // B
                addVillager(world, x + (9.5f + ad) * eastVector, y, z - (21.5f + bd) * northVector); // R
                addVillager(world, x + (21.5f + ad) * eastVector, y, z - (9.5f + bd) * northVector); // T 
            }
        }
    }
    
    private void addVillager(World world, float x, float y, float z)
    {
        EntityVillager v = new EntityVillager(world);
        v.setPosition(x, y + 3.5, z);

        world.spawnEntityInWorld(v);
    }
    
    private void buildTorches(World world, int northVector, int eastVector, int x, int y, int z)
    {
        IBlockState torch = Blocks.torch.getDefaultState();
        IBlockState northFacingTorch
            = torch.withProperty(BlockTorch.FACING, EnumFacing.NORTH);

        IBlockState southFacingTorch
            = torch.withProperty(BlockTorch.FACING, EnumFacing.SOUTH);

        IBlockState eastFacingTorch
            = torch.withProperty(BlockTorch.FACING, EnumFacing.EAST);

        IBlockState westFacingTorch
            = torch.withProperty(BlockTorch.FACING, EnumFacing.WEST);
        
        int zb1 = z - northVector;
        int zb2 = z - 18 * northVector;
        IBlockState torches1 = northVector == 1 ? northFacingTorch : southFacingTorch;
        IBlockState torches2 = northVector == 1 ? southFacingTorch : northFacingTorch;
        for (int xi = 0, xb = x + 2 * eastVector; xi < 16; xi++, xb += eastVector)
        {
            BlockPos p1 = new BlockPos(xb, y, zb1);
            world.setBlockState(p1, torches1);
            
            BlockPos p2 = new BlockPos(xb, y, zb2);
            world.setBlockState(p2, torches2);
        }
    
        int xb1 = x + eastVector;
        int xb2 = x + 18 * eastVector;
        IBlockState torches3 = eastVector == 1 ? eastFacingTorch : westFacingTorch;
        IBlockState torches4 = eastVector == 1 ? westFacingTorch : eastFacingTorch;
        for (int zi = 0, zb = z - 2 * northVector; zi < 16; zi++, zb -= northVector)
        {
            BlockPos p1 = new BlockPos(xb1, y, zb);
            world.setBlockState(p1, torches3);
            
            BlockPos p2 = new BlockPos(xb2, y, zb);
            world.setBlockState(p2, torches4);
        }
    }

    private void buildDoors(World world, int northVector, int eastVector, int x, int y, int z)
    {
        IBlockState door = Blocks.acacia_door.getDefaultState();
        IBlockState northFacingDoor
            = door.withProperty(BlockDoor.FACING, EnumFacing.NORTH);

        IBlockState southFacingDoor
            = door.withProperty(BlockDoor.FACING, EnumFacing.SOUTH);

        IBlockState eastFacingDoor
            = door.withProperty(BlockDoor.FACING, EnumFacing.EAST);

        IBlockState westFacingDoor
            = door.withProperty(BlockDoor.FACING, EnumFacing.WEST);
        
        int zb1 = z;
        int zb2 = z - 19 * northVector;
        
        EnumFacing facing1 = northVector == 1 ? EnumFacing.SOUTH : EnumFacing.NORTH;
        EnumFacing facing2 = northVector == 1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
        for (int xi = 1, xb = x + eastVector; xi < 19; xi++, xb += eastVector)
        {
            if (xi < 7 || (xi > 7 && xi < 12) || xi > 12)
            {
                BlockPos p1 = new BlockPos(xb, y, zb1);
                ItemDoor.placeDoor(world, p1, facing1, Blocks.acacia_door);
                
                BlockPos p2 = new BlockPos(xb, y, zb2);
                ItemDoor.placeDoor(world, p2, facing2, Blocks.acacia_door);
            }
        }
    
        int xb1 = x;
        int xb2 = x + 19 * eastVector;
        EnumFacing facing3 = eastVector == 1 ? EnumFacing.WEST : EnumFacing.EAST;
        EnumFacing facing4 = eastVector == 1 ? EnumFacing.EAST : EnumFacing.WEST;
        for (int zi = 1, zb = z - northVector; zi < 19; zi++, zb -= northVector)
        {
            if (zi < 7 || (zi > 7 && zi < 12) || zi > 12)
            {            
                BlockPos p1 = new BlockPos(xb1, y, zb);
                ItemDoor.placeDoor(world, p1, facing3, Blocks.acacia_door);
                
                BlockPos p2 = new BlockPos(xb2, y, zb);
                ItemDoor.placeDoor(world, p2, facing4, Blocks.acacia_door);
            }
        }
    }
    
    private void buildFloorWithHole(World world, IBlockState block, int northVector, int eastVector, int x, int y, int z)
    {
        for (int zi = 0, zb = z; zi < 20; zi++, zb -= northVector)
        {
            for (int xi = 0, xb = x; xi < 20; xi++, xb += eastVector)
            {
                if (zi < 9 || zi > 10 || xi < 9 || xi > 10)
                {
                    BlockPos p = new BlockPos(xb, y, zb);
                    world.setBlockState(p, block);
                }
            }
        }
    }
        
    private void buildWalls(World world, IBlockState block, int northVector, int eastVector, int x, int y, int z)
    {
        int xMax = x + 19 * eastVector;
        int zMin = z - 19 * northVector;        
        
        for (int yd = 1; yd < 5; yd++)
        {
            for (int zi = 0, z2 = z; zi < 20; zi++, z2 -= northVector)
            {
                if (yd == 1 || yd == 4 || zi == 0 || zi == 7 || zi == 12 || zi == 19)
                {
                    BlockPos p = new BlockPos(x, y + yd, z2);
                    world.setBlockState(p, block);
    
                    BlockPos p2 = new BlockPos(xMax, y + yd, z2);        
                    world.setBlockState(p2, block);
                }
            }

            for (int xi = 1, x2 = x + eastVector; xi < 19; xi++, x2 += eastVector)
            {
                if (yd == 1 || yd == 4 || xi == 7 || xi == 12)
                {
                    BlockPos p = new BlockPos(x2, y + yd, z);
                    world.setBlockState(p, block);  
    
                    BlockPos p2 = new BlockPos(x2, y + yd, zMin);                
                    world.setBlockState(p2, block);                
                }
            }
        }
    }

    private void buildTriangles(World world, IBlockState block, int northVector, int eastVector, int x, int y, int z)
    {
        for (int a = 7; a > 0; a--)
        {
            int z1 = z - northVector * (8 - a);
            int z2 = z - northVector * (11 + a);

            for (int xd = 0; xd < a; xd++)
            {
                BlockPos p = new BlockPos(x + (xd + 1) * eastVector, y + 1, z1);                
                world.setBlockState(p, block);  
    
                BlockPos p2 = new BlockPos(x + (18 - xd) * eastVector, y + 1, z1);        
                world.setBlockState(p2, block);                

                BlockPos p3 = new BlockPos(x + (xd + 1) * eastVector, y + 1, z2);                
                world.setBlockState(p3, block); 
    
                BlockPos p4 = new BlockPos(x + (18 - xd) * eastVector, y + 1, z2);        
                world.setBlockState(p4, block);                
            }
        }
    }
    
    private void buildDownwardsToNonAir(World world, BlockPos p, IBlockState s)
    {
        int x = p.getX();
        int z = p.getZ();
        
        for (int y = p.getY() - 1; y > 0; y--)
        {
            BlockPos p2 = new BlockPos(x, y, z);
            if (!world.isAirBlock(p2) && !world.getBlockState(p2).getBlock().getMaterial().isLiquid())
                break;

            world.setBlockState(p2, s);
        }
    }
}
