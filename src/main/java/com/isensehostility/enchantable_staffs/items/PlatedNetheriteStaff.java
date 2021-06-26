package com.isensehostility.enchantable_staffs.items;

public class PlatedNetheriteStaff extends Staff{

    public PlatedNetheriteStaff(Properties builder) {
        super(builder);
    }

    @Override
    public int getItemEnchantability() {
        return 20;
    }
}
