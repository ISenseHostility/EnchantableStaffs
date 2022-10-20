package isensehostility.enchantable_staffs.util;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enums.EStaffModifiers;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModifierUtils {
    public static void passiveChargeModifierLogic(ItemStack stack, LivingEntity entity) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);

            if (modifier == EStaffModifiers.COLD_AFFINITY && ModUtils.isInTemperatureRange(entity, -10.0F, 0.3F)) {
                NBTUtils.addCharge(entity);
            }
            if (modifier == EStaffModifiers.HEAT_AFFINITY && ModUtils.isInTemperatureRange(entity, 0.95F, 10.0F)) {
                NBTUtils.addCharge(entity);
            }
        }
    }

    public static void activeChargeModifierLogic(ItemStack stack, LivingEntity entity) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);
            ItemStack offhand = entity.getOffhandItem();

            if (modifier == EStaffModifiers.GLUTTONY && offhand.isEdible() && NBTUtils.getCharge(entity) < NBTUtils.getMaxCharge(entity)) {
                int foodValue = offhand.getFoodProperties(entity).getNutrition();
                if (foodValue != 0) {
                    NBTUtils.addCharge(entity, (int) Math.ceil(foodValue / 4));
                    offhand.shrink(1);
                }
            }
            if (modifier == EStaffModifiers.GREED && isGreedCompatible(offhand) && NBTUtils.getCharge(entity) < NBTUtils.getMaxCharge(entity)) {
                NBTUtils.addCharge(entity, getGreedValue(offhand));
                offhand.shrink(1);
            }
        }
    }

    public static void envyLogic(ItemStack stack, LivingEntity entity, LivingEntity target) {
        if (stack.getItem() instanceof Staff && Staff.hasModifier(stack)) {
            EStaffModifiers modifier = Staff.getModifier(stack);

            if (modifier == EStaffModifiers.ENVY && NBTUtils.getCharge(entity) < NBTUtils.getMaxCharge(entity)) {
                NBTUtils.addCharge(entity, (int) Math.ceil(target.getMaxHealth()));
            }
        }
    }

    public static boolean isGreedCompatible(ItemStack stack) {
        Item item = stack.getItem();

        return item == Items.COAL ||
                item == Items.CHARCOAL ||
                item == Items.RAW_COPPER ||
                item == Items.COPPER_INGOT ||
                item == Items.RAW_IRON ||
                item == Items.IRON_INGOT ||
                item == Items.REDSTONE ||
                item == Items.LAPIS_LAZULI ||
                item == Items.RAW_GOLD ||
                item == Items.GOLD_INGOT ||
                item == Items.EMERALD ||
                item == Items.AMETHYST_SHARD ||
                item == Items.QUARTZ ||
                item == Items.DIAMOND ||
                item == Items.NETHERITE_SCRAP ||
                item == Items.NETHERITE_INGOT;
    }

    public static int getGreedValue(ItemStack stack) {
        Item item = stack.getItem();

        if (item == Items.COAL || item == Items.CHARCOAL) {
            return StaffConfig.greedValueCoal.get();
        }
        if (item == Items.RAW_COPPER || item == Items.COPPER_INGOT) {
            return StaffConfig.greedValueCopper.get();
        }
        if (item == Items.RAW_IRON || item == Items.IRON_INGOT ) {
            return StaffConfig.greedValueIron.get();
        }
        if (item == Items.REDSTONE) {
            return StaffConfig.greedValueRedstone.get();
        }
        if (item == Items.LAPIS_LAZULI) {
            return StaffConfig.greedValueLapis.get();
        }
        if (item == Items.RAW_GOLD || item == Items.GOLD_INGOT) {
            return StaffConfig.greedValueGold.get();
        }
        if (item == Items.EMERALD) {
            return StaffConfig.greedValueEmerald.get();
        }
        if (item == Items.AMETHYST_SHARD) {
            return StaffConfig.greedValueAmethyst.get();
        }
        if (item == Items.QUARTZ) {
            return StaffConfig.greedValueNetherQuartz.get();
        }
        if (item == Items.DIAMOND) {
            return StaffConfig.greedValueDiamond.get();
        }
        if (item == Items.NETHERITE_SCRAP) {
            return StaffConfig.greedValueScrap.get();
        }
        if (item == Items.NETHERITE_INGOT) {
            return StaffConfig.greedValueNetherite.get();
        }

        return 0;
    }
}
