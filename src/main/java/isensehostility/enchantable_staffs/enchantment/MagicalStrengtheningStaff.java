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

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class MagicalStrengtheningStaff extends Enchantment implements IStaffEnchantment {
    public MagicalStrengtheningStaff() {
        super(Rarity.RARE, StaffCategory.get(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.NONE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.magicalStrengtheningExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.magicalStrengtheningChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        ItemStack item = player.getItemInHand(InteractionHand.OFF_HAND);

        if (EnchantmentHelper.getEnchantments(item).containsKey(StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get()) || !isMeleeWeapon(item)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        item.enchant(StaffEnchantments.MAGICAL_STRENGTHENING_WEAPON.get(), EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.MAGICAL_STRENGTHENING_STAFF.get(), stack));
        setMagicalStrengtheningTime(player, 600);

        spawnParticleCloud(ParticleTypes.ENCHANT, player.getX(), player.getEyeY(), player.getZ(), level);
        level.playSound(null, player.eyeBlockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 100.0F, 1.0F);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 50;
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
