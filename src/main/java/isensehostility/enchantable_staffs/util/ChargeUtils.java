package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.IStaffEnchantment;
import isensehostility.enchantable_staffs.enums.EChargeTextColors;
import isensehostility.enchantable_staffs.enums.EElement;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;

public class ChargeUtils {
    public static boolean chargeIsBetween(LivingEntity entity, int min, int max) {
        if (NBTUtils.getCharge(entity) == NBTUtils.getMaxCharge(entity)) {
            return false;
        }
        return min <= NBTUtils.getCharge(entity) && NBTUtils.getCharge(entity) <= max;
    }

    public static ChatFormatting assignChargeColor(Player player) {
        ChatFormatting color = switch (
                chargeIsBetween(player, 0, NBTUtils.getMaxCharge(player) / 3) ?
                        EChargeTextColors.RED :

                        chargeIsBetween(player, NBTUtils.getMaxCharge(player) / 3, NBTUtils.getMaxCharge(player) / 3 * 2) ?
                                EChargeTextColors.YELLOW :

                                chargeIsBetween(player, NBTUtils.getMaxCharge(player) / 3 * 2, NBTUtils.getMaxCharge(player)) ?
                                        EChargeTextColors.GREEN :

                                        EChargeTextColors.AQUA) {
            case RED -> ChatFormatting.RED;
            case YELLOW -> ChatFormatting.YELLOW;
            case GREEN -> ChatFormatting.GREEN;
            default -> ChatFormatting.AQUA;
        };

        return color;
    }

    public static int calculateCharge(IStaffEnchantment staffEnchantment, Player player) {
        int cost = staffEnchantment.getChargeCost();
        int efficiencyLevel;

        if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()) && Arrays.stream(staffEnchantment.getElements()).toList().contains(EElement.getById(NBTUtils.getElementalEfficiency(player)))) {
            efficiencyLevel = player.getEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get()).getAmplifier() + 1;
            int reducedCost = (cost / 100) * (100 - (efficiencyLevel * 15));

            if (efficiencyLevel != 0) {
                cost = reducedCost;
            }
        }

        return cost;
    }

}
