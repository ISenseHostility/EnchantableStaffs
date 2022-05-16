package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class ReinforcedIronStaff extends Staff {
    public ReinforcedIronStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(140));
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }
}
