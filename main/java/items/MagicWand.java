package items;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import Mod.MagicWandMod;

public class MagicWand extends Item
{
    public static final String NAME = "magic_wand";

    public MagicWand()
    {
        setMaxStackSize(1);
        setUnlocalizedName(NAME);

        setMaxDamage(100);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            MagicWandPlayer p = MagicWandPlayer.get(player);

            boolean completedSpell = false;
            boolean failedSpell = false;

            Block blockType = world.getBlockState(pos).getBlock();

            p.addUsePosition(pos);

            if (p.hasFourUsePositions())
            {
                if (p.isLeftBend(0, 1, 2))
                {
                    if (p.isStraightLine(1, 2, 3))
                    {
                        // 4 3 2
                        // 1
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.dirt, 64), player);
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
                        // 1
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
                            addStackToInventory(new ItemStack(Items.carrot, 64), player);
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
                        // 4
                        // 3 2
                        // 1
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.obsidian, 64), player);
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
                        // 1
                        if (world.provider.getDimensionId() != 0)
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
                                player.setPositionAndUpdate(maxHeightPos.getX() + 0.5D, maxHeightPos.getY(), maxHeightPos.getZ() + 0.5D);
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
                            addStackToInventory(new ItemStack(Blocks.cobblestone, 64), player);
                            msg(player, "Added stack of cobble stone to inventory");
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
                            addStackToInventory(new ItemStack(Items.diamond, 64), player);
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
                            addStackToInventory(new ItemStack(Blocks.glowstone, 64), player);
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

                        msg(player, "inventory size: " + size);
                        
                        int num = 0;
                        for (int i = 0; i < size; i++)
                        {
                            ItemStack stack_ = inv.getStackInSlot(i);
                         
                            if (stack_ != null)
                            {                            
                                if (stack_.isItemEnchantable())
                                {
                                   stack_.addEnchantment(Enchantment.efficiency, 5);
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
                        // 2
                        // 1
                        spawnRabbit(pos.up(), world);
                        msg(player, "Spawned rabbit");
                        completedSpell = true;
                    }
                    else if (p.isLeftBend(0, 1, 3))
                    {
                        // 3
                        // 4 2
                        // 1
                        try
                        {
                            addStackToInventory(new ItemStack(Items.iron_ingot, 64), player);
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
                        // 2
                        // 4 1
                        double midX = pos.getX() + 0.5D;
                        double midZ = pos.getZ() + 0.5D;
                        double y = (double) pos.getY() + 0.5;
                        for (int z = -2; z <= 2; z++)
                        {
                            for (int x = -2; x <= 2; x++)
                            {
                                world.spawnEntityInWorld(new EntityXPOrb(world, midX + x, y, midZ + z, 100));
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
                        // 2
                        // 1
                        spawnSheep(pos.up(), world);
                        msg(player, "Spawned sheep");
                        completedSpell = true;
                    }
                    else if (p.isRightBend(0, 1, 3))
                    {
                        // 3
                        // 2 4
                        // 1
                        try
                        {
                            addStackToInventory(new ItemStack(Items.gold_ingot, 64), player);
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
                        // 3
                        // 2
                        // 1 4
                        try
                        {
                            addStackToInventory(new ItemStack(Items.ender_pearl, 1), player);
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
                            addStackToInventory(new ItemStack(Blocks.planks, 64), player);
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
                        // 2
                        // 1
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.torch, 64), player);
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
                            addStackToInventory(new ItemStack(Items.flint_and_steel, 1), player);
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
                        // 2
                        // 3 1 4
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.quartz_block, 64), player);
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
                        if (world.provider.getDimensionId() != 0)
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
                        // 2
                        // 4 3 1
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.glass, 64), player);
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
                            addStackToInventory(new ItemStack(Blocks.stone, 64), player);
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
                        // 2
                        // 4 1 3
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.quartz_stairs, 64), player);
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
                        
                        if (togglePotionEffect(player, 13, 0))
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
                        
                        if (togglePotionEffect(player, Potion.digSpeed.getId(), 1))
                            msg(player, "Added potion effect of dig speed to player");
                        else
                            msg(player, "Removed potion effect of dig speed to player");
    
                        completedSpell = true;
                    }                    
                    else if (p.isLeftBend(2, 0, 3))
                    {
                        //   2
                        // 1 4
                        //   3
                        
                        try
                        {
                            addStackToInventory(new ItemStack(Blocks.beacon, 1), player);
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
                        if (togglePotionEffect(player, Potion.invisibility.getId(), 0))
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
                            addStackToInventory(new ItemStack(Items.arrow, 64), player);
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
                            addStackToInventory(new ItemStack(Items.bow, 1), player);
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
                    try
                    {
                        addDamageToWand(stack, player, world, pos);
                        playWandSound(world, pos);
                    }
                    catch (WandRenderedUselessException e)
                    {
                        stack.damageItem(1, player);
                        playBreakingWandSound(world, pos);
                    }
                }

                if (completedSpell || failedSpell)
                    p.clearUses();
            }
        }

        return true;
    }

    private void msg(EntityPlayer player, String msg)
    {
        player.addChatComponentMessage(new ChatComponentText(msg));
    }

    private void spawnRabbit(BlockPos pos, World world)
    {
        EntityRabbit rabbit = new EntityRabbit(world);

        float yaw = (float) (Math.random() * 360);

        rabbit.setRabbitType(1);
        rabbit.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, 0.0F);
        world.spawnEntityInWorld(rabbit);
    }

    private void spawnSheep(BlockPos pos, World world)
    {
        EntitySheep sheep = new EntitySheep(world);

        float yaw = (float) (Math.random() * 360);

        sheep.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), yaw, 0.0F);
        world.spawnEntityInWorld(sheep);
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
        int numArmorInventorySlots = inventory.armorInventory.length;

        int numSlots = numMainInventorySlots - numArmorInventorySlots;
        
        for (int i = 0; i < numSlots; i++)
        {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack == null || slotStack.stackSize == 0)
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

    private boolean togglePotionEffect(EntityPlayer player, int id, int effectAmplifier)
    {        
        if (!player.isPotionActive(id))
        {
            PotionEffect eff = new PotionEffect(id, 100000, effectAmplifier);
            player.addPotionEffect(eff);
            return true;
        }
        else
        {
            player.removePotionEffect(id);
            return false;
        }        
    }
    
    private void playWandSound(World world, BlockPos pos)
    {
        world.playSoundEffect(pos.getX(), pos.getY() + 1, pos.getZ(), MagicWandMod.modid + ":" + "magic_wand", 1, 1);
    }

    private void playBreakingWandSound(World world, BlockPos pos)
    {
        world.playSoundEffect(pos.getX(), pos.getY() + 1, pos.getZ(), MagicWandMod.modid + ":" + "magic_wand_break", 1, 1);
    }

    private void addDamageToWand(ItemStack stack, EntityPlayer player, World world, BlockPos pos) throws WandRenderedUselessException
    {
        int damage = this.getDamage(stack);
        damage++;

        setDamage(stack, damage);

        if (damage >= 100)
        {
            throw new WandRenderedUselessException();
        }

    }

    public static void initHandling()
    {
        MinecraftForge.EVENT_BUS.register(new MagicWandPlayer());
    }

    public static class MagicWandPlayer implements IExtendedEntityProperties
    {
        private BlockPos[] lastUses = new BlockPos[4];
        private int numUses = 0;

        private final String NBT_X = "x";
        private final String NBT_Y = "y";
        private final String NBT_Z = "z";
        private final String NBT_NUM = "numUses";
        
        public void clearUses()
        {
            numUses = 0;
        }

        public void addUsePosition(BlockPos pos)
        {
            if (numUses == 4)
            {
                lastUses[0] = lastUses[1];
                lastUses[1] = lastUses[2];
                lastUses[2] = lastUses[3];
                lastUses[3] = pos;
            }
            else
                lastUses[numUses++] = pos;
        }

        public boolean hasFourUsePositions()
        {
            return numUses == 4;
        }

        private boolean isStraightLine(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                if (startX != lastUses[end].getX())
                    return false;

                int step = lastUses[mid].getZ() - startZ;
                if (step != -1 && step != 1)
                    return false;

                return lastUses[end].getZ() - lastUses[mid].getZ() == step;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                if (startZ != lastUses[end].getZ())
                    return false;

                int step = lastUses[mid].getX() - startX;
                if (step != -1 && step != 1)
                    return false;

                return lastUses[end].getX() - lastUses[mid].getX() == step;
            }
            else
                return false;
        }

        private boolean isHalfLeftBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                if (lastUses[end].getZ() == lastUses[mid].getZ() + dz1)
                {
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                if (lastUses[end].getX() == lastUses[mid].getX() + dx1)
                {
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        private boolean isDoubleLeftBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                if (lastUses[end].getZ() == startZ)
                {
                    int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                if (lastUses[end].getX() == startX)
                {
                    int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        private boolean isHalfRightBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                if (lastUses[end].getZ() == lastUses[mid].getZ() + dz1)
                {
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                if (lastUses[end].getX() == lastUses[mid].getX() + dx1)
                {
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        private boolean isDoubleRightBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                if (lastUses[end].getZ() == startZ)
                {
                    int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                if (lastUses[end].getX() == startX)
                {
                    int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        private boolean isRightBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                if (lastUses[mid].getZ() == lastUses[end].getZ())
                {
                    int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 != dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                if (lastUses[mid].getX() == lastUses[end].getX())
                {
                    int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        private boolean isLeftBend(int start, int mid, int end)
        {
            int startX = lastUses[start].getX();
            int startZ = lastUses[start].getZ();

            if (startX == lastUses[mid].getX())
            {
                if (lastUses[mid].getZ() == lastUses[end].getZ())
                {
                    int dz1 = lastUses[mid].getZ() - lastUses[start].getZ();
                    int dx2 = lastUses[end].getX() - lastUses[mid].getX();

                    if ((dz1 != -1 && dz1 != 1) || (dx2 != -1 && dx2 != 1))
                        return false;

                    return dz1 == dx2;
                }
                else
                    return false;
            }
            else if (startZ == lastUses[mid].getZ())
            {
                if (lastUses[mid].getX() == lastUses[end].getX())
                {
                    int dx1 = lastUses[mid].getX() - lastUses[start].getX();
                    int dz2 = lastUses[end].getZ() - lastUses[mid].getZ();

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

        public static void initHandling()
        {
            MinecraftForge.EVENT_BUS.register(new Handler());
        }

        @Override
        public void saveNBTData(NBTTagCompound compound)
        {
            int[] x = new int[4];
            int[] y = new int[4];
            int[] z = new int[4];

            for (int i = 0; i < 4; i++)
            {
                BlockPos pos = lastUses[i];
                
                x[i] = pos != null ? pos.getX() : 0;
                y[i] = pos != null ? pos.getY() : 0;
                z[i] = pos != null ? pos.getZ() : 0;
            }

            NBTTagCompound tag = new NBTTagCompound();

            tag.setIntArray(NBT_X, x);
            tag.setIntArray(NBT_Y, y);
            tag.setIntArray(NBT_Z, z);

            tag.setInteger(NBT_NUM, numUses);

            compound.setTag(MagicWand.NAME, tag);
        }
        
        @Override
        public void loadNBTData(NBTTagCompound compound)
        {
            NBTTagCompound tag = (NBTTagCompound) compound.getTag(MagicWand.NAME);

            int[] x = tag.getIntArray(NBT_X);
            int[] y = tag.getIntArray(NBT_Y);
            int[] z = tag.getIntArray(NBT_Z);

            for (int i = 0; i < 4; i++)
            {
                lastUses[i] = new BlockPos(x[i], y[i], z[i]);
            }

            numUses = tag.getInteger(NBT_NUM);
        }

        @Override
        public void init(Entity entity, World world)
        {
            numUses = 0;
        }

        public static MagicWandPlayer get(EntityPlayer player)
        {
            return (MagicWandPlayer) player.getExtendedProperties(MagicWand.NAME);
        }

        public static class Handler
        {
            @SubscribeEvent
            public void entityConstructing(EntityEvent.EntityConstructing event)
            {
                if (!(event.entity instanceof EntityPlayer) || get((EntityPlayer) event.entity) != null)
                    return;

                event.entity.registerExtendedProperties(MagicWand.NAME, new MagicWandPlayer());
            }
        }
    }

    public class EventSubscriber
    {
        @SubscribeEvent
        public void onEntityConstructing(EntityConstructing event)
        {
            if (event.entity instanceof EntityPlayerMP)
            {
                event.entity.registerExtendedProperties(MagicWand.NAME, new MagicWandPlayer());
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
