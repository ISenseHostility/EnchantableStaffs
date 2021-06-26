package com.isensehostility.enchantable_staffs.items;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;

import java.util.Set;

public class ReinforcedIronStaff extends Staff{

    public ReinforcedIronStaff(Properties builder) {
        super(builder);
    }

    @Override
    public int getItemEnchantability() {
        return 12;
    }
}
