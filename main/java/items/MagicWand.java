package items;

import java.util.concurrent.Callable;

import capabilities.CapabilityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import sounds.Sounds;

public class MagicWand extends Item
{
    public static final String NAME = "magic_wand";

    public MagicWand()
    {
        setMaxStackSize(1);
        setRegistryName(NAME);
        setUnlocalizedName(NAME);
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxDamage(20);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            ItemStack stack = player.getHeldItem(hand);            
            IMagicWandPlayer p = player.getCapability(CapabilityProvider.WAND_CAPABILITY, null);
            
            boolean completedSpell = false;
            boolean failedSpell = false;

            p.addUsePosition(pos);

            if (p.hasFourUsePositions())
            {
                if (p.isLeftBend(0, 1, 2))
                {
                    if (p.isStraightLine(1, 2, 3))
                    {
                        // 4 3 2
                        //     1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.DIRT, 64), player);
                            msg(player, "Added stack of dirt to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isRightBend(1, 2, 3))
                    {
                        // 4
                        // 3 2
                        //   1

                        toggleRain(world);
                        msg(player, "Toggled rain");
                        completedSpell = true;
                    }
                    else if (p.isLeftBend(1, 2, 3))
                    {
                        // 3 2
                        // 4 1

                        try
                        {
                            addStackToInventory(new ItemStack(Items.CARROT, 64), player);
                            msg(player, "Added stack of carrots to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isStraightLine(0, 1, 3))
                    {
                        //   4
                        // 3 2
                        //   1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.OBSIDIAN, 64), player);
                            msg(player, "Added stack of obsidian to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isRightBend(0, 1, 3))
                    {
                        // 3 2 4
                        //   1

                        if (world.provider.getDimension() != 0)
                        {
                            msg(player, "Spell must be performed in overworld!");
                            failedSpell = true;
                        }
                        else
                        {
                            int maxHeight = 0;
                            BlockPos maxHeightPos = null;

                            for (int z = -3; z <= 3; z++)
                            {
                                for (int x = -3; x <= 3; x++)
                                {
                                    BlockPos pos_ = world.getHeight(pos.add(x, 0, z));
                                    int height = pos_.getY();
                                    if (height > maxHeight)
                                    {
                                        maxHeightPos = pos_;
                                        maxHeight = height;
                                    }
                                }
                            }

                            if (maxHeightPos != null)
                            {
                                player.setPositionAndUpdate(maxHeightPos.getX() + 0.5D, maxHeightPos.getY(),
                                        maxHeightPos.getZ() + 0.5D);
                                msg(player, "Changing to highest location in 7x7 area");
                                pos = maxHeightPos;
                                completedSpell = true;
                            }
                            else
                            {
                                msg(player, "Could not find high point");
                                failedSpell = true;
                            }
                        }
                    }
                }
                else if (p.isRightBend(0, 1, 2))
                {
                    if (p.isStraightLine(1, 2, 3))
                    {
                        // 2 3 4
                        // 1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.COBBLESTONE, 64), player);
                            msg(player, "Added stack of cobblestone to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(1, 2, 3))
                    {
                        //   4
                        // 2 3
                        // 1

                        add12Hours(world);
                        msg(player, "Added 12 hours");
                        completedSpell = true;
                    }
                    else if (p.isRightBend(1, 2, 3))
                    {
                        // 2 3
                        // 1 4
                        try
                        {
                            addStackToInventory(new ItemStack(Items.DIAMOND, 64), player);
                            msg(player, "Added stack of diamonds to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isStraightLine(0, 1, 3))
                    {
                        // 4
                        // 2 3
                        // 1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.GLOWSTONE, 64), player);
                            msg(player, "Added stack of glowstone to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(0, 1, 3))
                    {
                        // 4 2 3
                        //   1

                        InventoryPlayer inv = player.inventory;

                        int size = inv.getSizeInventory();

                        int num = 0;
                        for (int i = 0; i < size; i++)
                        {
                            ItemStack stack_ = inv.getStackInSlot(i);

                            if (stack_ != null)
                            {
                                if (stack_.isItemEnchantable())
                                {
                                    stack_.addEnchantment(Enchantments.EFFICIENCY, 5);
                                    num++;
                                }
                            }
                        }

                        if (num > 0)
                        {
                            player.inventoryContainer.detectAndSendChanges();
                            msg(player, "Speed enchanted " + num + " items in inventory");
                            completedSpell = true;
                        }
                        else
                        {
                            msg(player, "No enchantable items in inventory");
                            failedSpell = true;
                        }
                    }
                }
                else if (p.isHalfLeftBend(0, 1, 2))
                {
                    if (p.isHalfRightBend(0, 1, 3))
                    {
                        // 3 4
                        //  2
                        //  1
                        spawnRabbit(pos.up(), world);
                        msg(player, "Spawned rabbit");
                        completedSpell = true;
                    }
                    else if (p.isLeftBend(0, 1, 3))
                    {
                        // 3
                        // 4 2
                        //   1

                        try
                        {
                            addStackToInventory(new ItemStack(Items.IRON_INGOT, 64), player);
                            msg(player, "Added stack of iron ingots to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isDoubleLeftBend(0, 1, 3))
                    {
                        // 3
                        //   2
                        // 4 1

                        double midX = pos.getX() + 0.5D;
                        double midZ = pos.getZ() + 0.5D;
                        double y = (double) pos.getY() + 0.5;
                        for (int z = -2; z <= 2; z++)
                        {
                            for (int x = -2; x <= 2; x++)
                            {
                                world.spawnEntity(new EntityXPOrb(world, midX + x, y, midZ + z, 100));
                            }

                        }

                        msg(player, "Spawned experience orbs");
                        completedSpell = true;
                    }
                }
                else if (p.isHalfRightBend(0, 1, 2))
                {
                    if (p.isHalfLeftBend(0, 1, 3))
                    {
                        // 4 3
                        //  2
                        //  1

                        spawnSheep(pos.up(), world);
                        msg(player, "Spawned sheep");
                        completedSpell = true;
                    }
                    else if (p.isRightBend(0, 1, 3))
                    {
                        //    3
                        //  2 4
                        //  1

                        try
                        {
                            addStackToInventory(new ItemStack(Items.GOLD_INGOT, 64), player);
                            msg(player, "Added stack of gold ingots to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isDoubleRightBend(0, 1, 3))
                    {
                        //   3
                        // 2
                        // 1 4

                        try
                        {
                            addStackToInventory(new ItemStack(Items.ENDER_PEARL, 1), player);
                            msg(player, "Added ender pearl to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }

                }
                else if (p.isStraightLine(0, 1, 2))
                {
                    if (p.isStraightLine(1, 2, 3))
                    {
                        // 4
                        // 3
                        // 2
                        // 1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.PLANKS, 64), player);
                            msg(player, "Added stack of oak planks to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(1, 2, 3))
                    {
                        // 4 3
                        //   2
                        //   1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.TORCH, 64), player);
                            msg(player, "Added stack of torches to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isRightBend(1, 2, 3))
                    {
                        // 3 4
                        // 2
                        // 1

                        float health = player.getHealth();
                        if (health < 20f)
                        {
                            player.heal(20);
                            msg(player, "Healing");
                            completedSpell = true;
                        }
                        else
                        {
                            msg(player, "Already at full health!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isRightBend(0, 1, 3))
                    {
                        // 3
                        // 2 4
                        // 1

                        try
                        {
                            addStackToInventory(new ItemStack(Items.FLINT_AND_STEEL, 1), player);
                            msg(player, "Added flint and steel to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                }
                else if (p.isDoubleLeftBend(0, 1, 2))
                {
                    if (p.isDoubleRightBend(0, 1, 3))
                    {
                        //   2
                        // 3 1 4

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.QUARTZ_BLOCK, 64), player);
                            msg(player, "Added stack of quartz blocks to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(0, 1, 3))
                    {
                        // 4 2
                        // 3 1
                        
                        if (world.provider.getDimension() != 0)
                        {
                            msg(player, "Spell must be performed in overworld!");
                            failedSpell = true;
                        }
                        else
                        {
                            BlockPos bedPos = player.getBedLocation();

                            if (bedPos != null)
                            {
                                player.setPositionAndUpdate(bedPos.getX() + 0.5D, bedPos.getY(), bedPos.getZ() + 0.5D);
                                msg(player, "Teleported to bed");
                                pos = bedPos;
                                completedSpell = true;
                            }
                            else
                            {
                                msg(player, "There is no bed position");
                                failedSpell = true;
                            }
                        }
                    }
                    else if (p.isStraightLine(0, 2, 3))
                    {
                        //     2
                        // 4 3 1

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.GLASS, 64), player);
                            msg(player, "Added stack of glass to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                }
                else if (p.isDoubleRightBend(0, 1, 2))
                {
                    // 2 4
                    // 1 3

                    if (p.isRightBend(0, 1, 3))
                    {
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.STONE, 64), player);
                            msg(player, "Added stack of stone to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isDoubleLeftBend(0, 1, 3))
                    {
                        //   2
                        // 4 1 3

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.QUARTZ_STAIRS, 64), player);
                            msg(player, "Added stack of quartz stairs to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isStraightLine(0, 2, 3))
                    {
                        // 2
                        // 1 3 4

                        if (togglePotionEffect(player, MobEffects.WATER_BREATHING, 1))
                            msg(player, "Added potion effect of underwater breathing to player");
                        else
                            msg(player, "Removed potion effect of underwater breathing from player");

                        completedSpell = true;
                    }
                }
                else if (p.isStraightLine(1, 0, 2))
                {
                    if (p.isRightBend(2, 0, 3))
                    {
                        // 2
                        // 1 4
                        // 3

                        if (togglePotionEffect(player, Potion.getPotionById(3), 1))
                            msg(player, "Added potion effect of haste (dig speed) to player");
                        else
                            msg(player, "Removed potion effect of haste (dig speed) to player");

                        completedSpell = true;
                    }
                    else if (p.isLeftBend(2, 0, 3))
                    {
                        //   2
                        // 4 1 
                        //   3

                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.BEACON, 1), player);
                            msg(player, "Added beacon to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(3, 2, 0))
                    {
                        //   2
                        //   1
                        // 4 3

                        /* invisibility */
                        if (togglePotionEffect(player, MobEffects.INVISIBILITY, 0))
                            msg(player, "Added potion effect of invisibility to player");
                        else
                            msg(player, "Removed potion effect of invisibility to player");

                        completedSpell = true;
                    }
                    else if (p.isRightBend(3, 2, 0))
                    {
                        // 2
                        // 1
                        // 3 4

                        try
                        {
                            addStackToInventory(new ItemStack(Items.ARROW, 64), player);
                            msg(player, "Added stack of arrows to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                    else if (p.isLeftBend(0, 1, 3))
                    {
                        // 4 2
                        //   1
                        //   3

                        try
                        {
                            addStackToInventory(new ItemStack(Items.BOW, 1), player);
                            msg(player, "Added bow to inventory");
                            completedSpell = true;
                        }
                        catch (InventoryFullException e)
                        {
                            msg(player, "Inventory was already full!");
                            failedSpell = true;
                        }
                    }
                }

                if (completedSpell)
                {
                    stack.damageItem(1, player);
                    
                    if (stack.getItemDamage() <= stack.getMaxDamage())
                        playWandSound(player, world, pos);
                }

                if (completedSpell || failedSpell)
                    p.clearUses();
            }
        }

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    private void msg(EntityPlayer player, String msg)
    {
        player.sendMessage(new TextComponentString(msg));
    }

    private void spawnRabbit(BlockPos pos, World world)
    {
        EntityRabbit rabbit = new EntityRabbit(world);

        float yaw = (float) (Math.random() * 360);

        rabbit.setRabbitType(1);
        rabbit.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, 0.0F);
        world.spawnEntity(rabbit);
    }

    private void spawnSheep(BlockPos pos, World world)
    {
        EntitySheep sheep = new EntitySheep(world);

        float yaw = (float) (Math.random() * 360);

        sheep.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, 0.0F);
        world.spawnEntity(sheep);
    }

    private void add12Hours(World world)
    {
        long time = world.getWorldTime();

        world.setWorldTime((time + 12000L) % 24000L);
    }

    private void toggleRain(World world)
    {
        WorldInfo worldInfo = world.getWorldInfo();

        worldInfo.setRaining(!worldInfo.isRaining());
    }

    private void addStackToInventory(ItemStack stack, EntityPlayer player) throws InventoryFullException
    {
        boolean foundSlot = false;
        InventoryPlayer inventory = player.inventory;

        int numMainInventorySlots = inventory.getSizeInventory();
        int numArmorInventorySlots = inventory.armorInventory.size();

        int numSlots = numMainInventorySlots - numArmorInventorySlots;
        
        for (int i = 0; i < numSlots; i++)
        {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack == null || slotStack.getCount() == 0)
            {
                inventory.setInventorySlotContents(i, stack);
                player.inventoryContainer.detectAndSendChanges();
                foundSlot = true;
                break;
            }
        }

        if (!foundSlot)
        {
            throw new InventoryFullException();
        }
    }

    private boolean togglePotionEffect(EntityPlayer player, Potion potion, int effectAmplifier)
    {
        if (!player.isPotionActive(potion))
        {
            PotionEffect eff = new PotionEffect(potion, 100000, effectAmplifier);
            player.addPotionEffect(eff);
            return true;
        }
        else
        {
            player.removePotionEffect(potion);
            return false;
        }
    }

    private void playWandSound(EntityPlayer player, World world, BlockPos pos)
    {
        world.playSound(null, pos, Sounds.magicWand, SoundCategory.MASTER, 1, 1);
    }
    
    public static interface IMagicWandPlayer
    {
        int getNumUses();
        void setNumUses(int value);
        int[] getLastUsesX();
        int[] getLastUsesZ();
        void setLastUsesX(int[] value);
        void setLastUsesZ(int[] value);
        void clearUses();
        void addUsePosition(BlockPos pos);
        boolean hasFourUsePositions();
        boolean isStraightLine(int start, int mid, int end);
        boolean isHalfLeftBend(int start, int mid, int end);
        boolean isDoubleLeftBend(int start, int mid, int end);
        boolean isHalfRightBend(int start, int mid, int end);
        boolean isDoubleRightBend(int start, int mid, int end);
        boolean isLeftBend(int start, int mid, int end);
        boolean isRightBend(int start, int mid, int end);
    }

    public static class MagicWandPlayer implements IMagicWandPlayer
    {
        private int[] lastUsesX = new int[4];
        private int[] lastUsesZ = new int[4];
        
        private int numUses = 0;

    	public int getNumUses() { return numUses; }
    	public void setNumUses(int value) {	numUses = value; }
    	public int[] getLastUsesX()	{ return lastUsesX; }
    	public int[] getLastUsesZ() { return lastUsesZ; }
    	public void setLastUsesX(int[] value) {	lastUsesX = value; }
    	public void setLastUsesZ(int[] value) { lastUsesZ = value; }

        public void clearUses()
        {
            numUses = 0;
        }

        public void addUsePosition(BlockPos pos)
        {
            if (numUses == 4)
            {
                lastUsesX[0] = lastUsesX[1];
                lastUsesX[1] = lastUsesX[2];
                lastUsesX[2] = lastUsesX[3];
                lastUsesX[3] = pos.getX();

                lastUsesZ[0] = lastUsesZ[1];
                lastUsesZ[1] = lastUsesZ[2];
                lastUsesZ[2] = lastUsesZ[3];
                lastUsesZ[3] = pos.getZ();
            }
            else
            {
                lastUsesX[numUses] = pos.getX();
                lastUsesZ[numUses] = pos.getZ();
                
                numUses++;
            }
        }

        public boolean hasFourUsePositions()
        {
            return numUses == 4;
        }

        public boolean isStraightLine(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                if (startX != lastUsesX[end])
                    return false;

                int step = lastUsesZ[mid] - startZ;
                if (step != -1 && step != 1)
                    return false;

                return lastUsesZ[end] - lastUsesZ[mid] == step;
            }
            else if (startZ == lastUsesZ[mid])
            {
                if (startZ != lastUsesZ[end])
                    return false;

                int step = lastUsesX[mid] - startX;
                if (step != -1 && step != 1)
                    return false;

                return lastUsesX[end] - lastUsesX[mid] == step;
            }
            else
                return false;
        }

        public boolean isHalfLeftBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                if (lastUsesZ[end] == lastUsesZ[mid] + dz1)
                {
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                int dx1 = lastUsesX[mid] - lastUsesX[start];
                if (lastUsesX[end] == lastUsesX[mid] + dx1)
                {
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 != dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }

        public boolean isDoubleLeftBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                if (lastUsesZ[end] == startZ)
                {
                    int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                if (lastUsesX[end] == startX)
                {
                    int dx1 = lastUsesX[mid] - lastUsesX[start];
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 != dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }

        public boolean isHalfRightBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                if (lastUsesZ[end] == lastUsesZ[mid] + dz1)
                {
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                int dx1 = lastUsesX[mid] - lastUsesX[start];
                if (lastUsesX[end] == lastUsesX[mid] + dx1)
                {
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 == dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }

        public boolean isDoubleRightBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                if (lastUsesZ[end] == startZ)
                {
                    int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                if (lastUsesX[end] == startX)
                {
                    int dx1 = lastUsesX[mid] - lastUsesX[start];
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 == dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }

        public boolean isRightBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                if (lastUsesZ[mid] == lastUsesZ[end])
                {
                    int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                if (lastUsesX[mid] == lastUsesX[end])
                {
                    int dx1 = lastUsesX[mid] - lastUsesX[start];
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 == dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }

        public boolean isLeftBend(int start, int mid, int end)
        {
            int startX = lastUsesX[start];
            int startZ = lastUsesZ[start];

            if (startX == lastUsesX[mid])
            {
                if (lastUsesZ[mid] == lastUsesZ[end])
                {
                    int dz1 = lastUsesZ[mid] - lastUsesZ[start];
                    int dx2 = lastUsesX[end] - lastUsesX[mid];

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUsesZ[mid])
            {
                if (lastUsesX[mid] == lastUsesX[end])
                {
                    int dx1 = lastUsesX[mid] - lastUsesX[start];
                    int dz2 = lastUsesZ[end] - lastUsesZ[mid];

                    if ((dx1 != -1 && dx1 != 1) || (dz2 != -1 && dz2 != 1))
                        return false;

                    return dx1 != dz2;
                }
                else
                    return false;
            }
            else
                return false;
        }
    }

    public static class MagicWandPlayerStorage implements IStorage<IMagicWandPlayer>
    {
        @Override
        public NBTBase writeNBT(Capability<IMagicWandPlayer> capability, IMagicWandPlayer instance, EnumFacing side)
        {
            NBTTagCompound tag = new NBTTagCompound();
            int numUses = instance.getNumUses();
            
            tag.setInteger("numuses", numUses);
                
            tag.setIntArray("lastx", instance.getLastUsesX());
            tag.setIntArray("lastz", instance.getLastUsesZ());
            
            return tag;
        }

        @Override
        public void readNBT(Capability<IMagicWandPlayer> capability, IMagicWandPlayer instance, EnumFacing side,
                NBTBase nbt)
        {
            if (nbt instanceof NBTTagCompound)
            {
                NBTTagCompound tag = (NBTTagCompound) nbt;
                
                int numUses = tag.getInteger("numuses");
                
                BlockPos[] lastUses = new BlockPos[4];
                
                instance.setNumUses(numUses);
                
                instance.setLastUsesX(tag.getIntArray("lastx"));
                instance.setLastUsesZ(tag.getIntArray("lastz"));
            }
        }

        public static class Factory implements Callable<IMagicWandPlayer>
        {
            @Override
            public IMagicWandPlayer call() throws Exception
            {
                return new MagicWandPlayer();
            }
        }
    }

    class InventoryFullException extends Exception
    {
    }

    class WandRenderedUselessException extends Exception
    {
    }
}
