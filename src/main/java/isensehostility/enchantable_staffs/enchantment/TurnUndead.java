package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;

import java.util.List;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class TurnUndead extends Enchantment implements IStaffEnchantment {
    public TurnUndead() {
        super(Rarity.UNCOMMON, StaffCategory.get(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.PURE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.turnUndeadExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.turnUndeadChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        BlockPos pos = rayTrace(level, player, ClipContext.Fluid.NONE, 50).getBlockPos();
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, createBoundingBox(pos, 5));
        boolean success = false;

        for (LivingEntity entity : entities) {
            if (entity.isInvertedHealAndHarm()) {
                entity.hurt(DamageSource.MAGIC, EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.TURN_UNDEAD.get(), stack) * 6.0F);
                spawnParticleCloud(ParticleTypes.CLOUD, entity.getX(), entity.getEyeY(), entity.getZ(), level);
                success = true;
            }
        }

        if (success) {
            if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
                return new InteractionResultHolder<>(InteractionResult.PASS, stack);
            }

            spawnParticleCloud(ParticleTypes.SOUL, player.getX(), player.getEyeY(), player.getZ(), level);
            level.playSound(null, player.eyeBlockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 100.0F, 1.0F);

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
        }

        return new InteractionResultHolder<>(InteractionResult.PASS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
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
