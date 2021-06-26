package com.isensehostility.enchantable_staffs.enchantments;

import com.isensehostility.enchantable_staffs.EnchantableStaffs;
import com.isensehostility.enchantable_staffs.init.EnchantmentRegistry;
import com.isensehostility.enchantable_staffs.items.Staff;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class LightningBolt extends net.minecraft.enchantment.Enchantment {

    public LightningBolt(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, EnchantableStaffs.STAFF, slots);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return 50;
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench)
                && ench != EnchantmentRegistry.LIGHTNING_CIRCLE
                && ench != EnchantmentRegistry.EXPLOSION
                && ench != EnchantmentRegistry.CREATE_WATER
                && ench != EnchantmentRegistry.NECROMANCY
                && ench != EnchantmentRegistry.WARP
                && ench != EnchantmentRegistry.TELEPORT
                && ench != EnchantmentRegistry.WEATHER_ALTERATION
                && ench != EnchantmentRegistry.RING_OF_FIRE
                && ench != EnchantmentRegistry.FIREBALL;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof Staff;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canApply(stack);
    }
}
