package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class ReinforcedGoldStaff extends Staff {

    public ReinforcedGoldStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(180));
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }
}
