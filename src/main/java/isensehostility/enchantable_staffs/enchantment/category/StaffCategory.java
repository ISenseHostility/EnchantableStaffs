package isensehostility.enchantable_staffs.enchantment.category;

import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StaffCategory {
    private static final EnchantmentCategory STAFF = EnchantmentCategory.create("staff", Staff.class::isInstance);

    public static EnchantmentCategory get() {
        return STAFF;
    }
}
