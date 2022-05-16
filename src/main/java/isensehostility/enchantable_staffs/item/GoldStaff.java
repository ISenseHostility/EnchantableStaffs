package isensehostility.enchantable_staffs.item;

import net.minecraft.world.item.CreativeModeTab;

public class GoldStaff extends Staff {

    public GoldStaff() {
        super(new Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).defaultDurability(120));
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }
}
