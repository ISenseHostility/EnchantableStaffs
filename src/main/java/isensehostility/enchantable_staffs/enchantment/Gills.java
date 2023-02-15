package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.IStaffEnchantmentHolder;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class Gills extends Enchantment implements IStaffEnchantment {
    public Gills() {
        super(Rarity.UNCOMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.WATER};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.gillsExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.gillsChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (player.hasEffect(StaffEffects.GILLS.get())) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
        if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        player.addEffect(new MobEffectInstance(StaffEffects.GILLS.get(), 600));

        ModUtils.spawnParticleCloud(ParticleTypes.BUBBLE, level, new BlockPos(player.getEyePosition()));
        level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 100.0F, 1.0F);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
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
        return stack.getItem() instanceof IStaffEnchantmentHolder && doesExist();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
