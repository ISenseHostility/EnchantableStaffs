package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class StaffUtils {
    public static void reduceDurability(ItemStack stack) {
        int unbreakingLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);
        if (unbreakingLevel > 0) {
            if (EnchantableStaffs.RANDOM.nextInt(100) + 1 > 100 / (unbreakingLevel + 1)) {
                return;
            }
        }
        stack.setDamageValue(stack.getDamageValue() + 1);
    }

    public static boolean invokeStaffCosts(Player player, ItemStack stack, int cost, Level level) {
        if (!level.isClientSide) {
            if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get())) {
                int reducedCost = (cost / 100) * (100 - ((player.getEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()).getAmplifier() + 1) * 15));

                return applyCost(player, stack, reducedCost);
            } else {
                return applyCost(player, stack, cost);
            }
        }

        return true;
    }

    public static boolean applyCost(Player player, ItemStack stack, int cost) {
        if (NBTUtils.getCharge(player) >= cost) {
            NBTUtils.reduceCharge(player, cost);
            reduceDurability(stack);

            return false;
        }

        return true;
    }

    public static boolean isHoldingStaff(LivingEntity entity) {
        return entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Staff || entity.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Staff;
    }

}
