package com.isensehostility.enchantable_staffs.enchantments;

import com.isensehostility.enchantable_staffs.EnchantableStaffs;
import com.isensehostility.enchantable_staffs.init.EnchantmentRegistry;
import com.isensehostility.enchantable_staffs.items.Staff;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class Fireball extends net.minecraft.enchantment.Enchantment {

    public Fireball(Enchantment.Rarity rarityIn, EquipmentSlotType... slots) {
        super(rarityIn, EnchantableStaffs.STAFF, slots);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel)
    {
        return 10 + 10 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 50;
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
                && ench != EnchantmentRegistry.LIGHTNING_BOLT
                && ench != EnchantmentRegistry.EXPLOSION
                && ench != EnchantmentRegistry.CREATE_WATER
                && ench != EnchantmentRegistry.NECROMANCY
                && ench != EnchantmentRegistry.WARP
                && ench != EnchantmentRegistry.TELEPORT
                && ench != EnchantmentRegistry.WEATHER_ALTERATION
                && ench != EnchantmentRegistry.RING_OF_FIRE
                && ench != EnchantmentRegistry.LIGHTNING_CIRCLE;
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

