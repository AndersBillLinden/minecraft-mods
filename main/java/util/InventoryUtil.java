package util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class InventoryUtil
{
    public static void addStackToInventory(ItemStack stack, EntityPlayer player) throws InventoryFullException
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
    
    @SuppressWarnings("serial")
    public static class InventoryFullException extends Exception
    {
    }
}
