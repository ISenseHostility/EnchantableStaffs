package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class Cleansing extends Enchantment implements IStaffEnchantment {
    public Cleansing() {
        super(Rarity.VERY_RARE, StaffCategory.get(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.PURE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.cleansingExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.cleansingChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (getCharge(player) < getChargeCost()) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        ItemStack item = player.getItemInHand(InteractionHand.OFF_HAND);
        Map<Enchantment, Integer> oldEnchantments = EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> newEnchantments = new HashMap<>();
        boolean success = false;

        for (Map.Entry<Enchantment, Integer> entry : oldEnchantments.entrySet()) {
            if (!entry.getKey().isCurse()) {
                newEnchantments.put(entry.getKey(), entry.getValue());
            } else {
                success = true;
            }
        }

        if (success) {
            EnchantmentHelper.setEnchantments(newEnchantments, item);
            spawnParticleCloud(ParticleTypes.END_ROD, player.getX(), player.getEyeY(), player.getZ(), level);
            level.playSound(null, player.eyeBlockPosition(), SoundEvents.CAT_AMBIENT, SoundSource.PLAYERS, 100.0F, 1.0F);

            if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
                return new InteractionResultHolder<>(InteractionResult.PASS, stack);
            }

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        } else {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinCost(int level) {
        return 10;
    }

    @Override
    public int getMaxCost(int level) {
        return 50;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return super.checkCompatibility(enchantment)
                && enchantment == Enchantments.MENDING
                && enchantment == Enchantments.UNBREAKING;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof Staff && doesExist();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
