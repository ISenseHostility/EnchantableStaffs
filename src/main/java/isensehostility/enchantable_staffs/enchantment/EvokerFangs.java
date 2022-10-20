package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.NBTUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class EvokerFangs extends Enchantment implements IStaffEnchantment {
    public EvokerFangs() {
        super(Rarity.UNCOMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.SUMMON};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.evokerFangsExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.evokerFangsChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (NBTUtils.getCharge(player) < getChargeCost()) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        boolean success = false;

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, ModUtils.createBoundingBox(player.blockPosition(), 10));
        LivingEntity target = level.getNearestEntity(entities, TargetingConditions.forCombat(), player, player.getX(), player.getY(), player.getZ());

        if (target != null) {
            performSpellCasting(player, target);
            success = true;
        }

        if (success) {
            ModUtils.spawnParticleCloud(ParticleTypes.CAMPFIRE_COSY_SMOKE, level, new BlockPos(player.getEyePosition()));
            level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 100.0F, 1.0F);

            if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
                return new InteractionResultHolder<>(InteractionResult.PASS, stack);
            }

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

    public void performSpellCasting(LivingEntity attacker, LivingEntity target) {
        double d0 = Math.min(target.getY(), attacker.getY());
        double d1 = Math.max(target.getY(), attacker.getY()) + 1.0D;
        float f = (float) Mth.atan2(target.getZ() - attacker.getZ(), target.getX() - attacker.getX());
        if (attacker.distanceToSqr(target) < 9.0D) {
            for(int i = 0; i < 5; ++i) {
                float f1 = f + (float)i * (float)Math.PI * 0.4F;
                this.createSpellEntity(attacker.getX() + (double)Mth.cos(f1) * 1.5D, attacker.getZ() + (double)Mth.sin(f1) * 1.5D, d0, d1, f1, 0, attacker);
            }

            for(int k = 0; k < 8; ++k) {
                float f2 = f + (float)k * (float)Math.PI * 2.0F / 8.0F + 1.2566371F;
                this.createSpellEntity(attacker.getX() + (double)Mth.cos(f2) * 2.5D, attacker.getZ() + (double)Mth.sin(f2) * 2.5D, d0, d1, f2, 3, attacker);
            }
        } else {
            for(int l = 0; l < 16; ++l) {
                double d2 = 1.25D * (double)(l + 1);
                int j = 1 * l;
                this.createSpellEntity(attacker.getX() + (double)Mth.cos(f) * d2, attacker.getZ() + (double)Mth.sin(f) * d2, d0, d1, f, j, attacker);
            }
        }

    }

    public void createSpellEntity(double p_32673_, double p_32674_, double p_32675_, double p_32676_, float p_32677_, int p_32678_, LivingEntity attacker) {
        BlockPos blockpos = new BlockPos(p_32673_, p_32676_, p_32674_);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = attacker.level.getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(attacker.level, blockpos1, Direction.UP)) {
                if (!attacker.level.isEmptyBlock(blockpos)) {
                    BlockState blockstate1 = attacker.level.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(attacker.level, blockpos);
                    if (!voxelshape.isEmpty()) {
                        d0 = voxelshape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockpos = blockpos.below();
        } while(blockpos.getY() >= Mth.floor(p_32675_) - 1);

        if (flag) {
            attacker.level.addFreshEntity(new net.minecraft.world.entity.projectile.EvokerFangs(attacker.level, p_32673_, (double)blockpos.getY() + d0, p_32674_, p_32677_, p_32678_, attacker));
        }

    }
}
