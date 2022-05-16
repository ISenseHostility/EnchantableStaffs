package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class PlatedNetheriteStaff extends Staff {

    public PlatedNetheriteStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(280));
    }

    @Override
    public int getEnchantmentValue() {
        return 20;
    }
}
