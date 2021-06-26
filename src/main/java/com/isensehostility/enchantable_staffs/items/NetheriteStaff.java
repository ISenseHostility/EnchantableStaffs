package com.isensehostility.enchantable_staffs.items;

import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;

import java.util.Set;

public class NetheriteStaff extends Staff{

    public NetheriteStaff(Properties builder) {
        super(builder);
    }

    @Override
    public int getItemEnchantability() {
        return 15;
    }
}
