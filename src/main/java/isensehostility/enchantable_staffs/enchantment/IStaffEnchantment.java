package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.enums.EElement;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IStaffEnchantment {
    EElement[] getElements();

    boolean doesExist();

    int getChargeCost();

    InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player);
}
