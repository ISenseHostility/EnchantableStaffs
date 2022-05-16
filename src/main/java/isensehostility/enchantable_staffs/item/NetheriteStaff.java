package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class NetheriteStaff extends Staff {

    public NetheriteStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(260));
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }
}
