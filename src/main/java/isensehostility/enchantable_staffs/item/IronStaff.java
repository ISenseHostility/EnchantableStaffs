package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class IronStaff extends Staff {

    public IronStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(80));
    }

    @Override
    public int getEnchantmentValue() {
        return 14;
    }
}
