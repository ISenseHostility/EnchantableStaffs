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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class RingOfFire extends Enchantment implements IStaffEnchantment {
    public RingOfFire() {
        super(Rarity.UNCOMMON, StaffCategory.get(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.FIRE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.ringOfFireExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.ringOfFireChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        double x = player.getX();
        double z = player.getZ();

        BlockPos[] positions = new BlockPos[] {
                new BlockPos(x + 4, getHighestBlock(level, x + 4, z + 1).above().getY(), z + 1),
                new BlockPos(x + 4, getHighestBlock(level, x + 4, z - 1).above().getY(), z - 1),
                new BlockPos(x - 4, getHighestBlock(level, x - 4, z + 1).above().getY(), z + 1),
                new BlockPos(x - 4, getHighestBlock(level, x - 4, z - 1).above().getY(), z - 1),
                new BlockPos(x + 4, getHighestBlock(level, x + 4, z).above().getY(), z),
                new BlockPos(x - 4, getHighestBlock(level, x - 4, z).above().getY(), z),
                new BlockPos(x, getHighestBlock(level, x, z - 4).above().getY(), z - 4),
                new BlockPos(x, getHighestBlock(level, x, z + 4).above().getY(), z + 4),
                new BlockPos(x + 1, getHighestBlock(level, x + 1, z + 4).above().getY(), z + 4),
                new BlockPos(x - 1, getHighestBlock(level, x - 1, z + 4).above().getY(), z + 4),
                new BlockPos(x + 1, getHighestBlock(level, x + 1, z - 4).above().getY(), z - 4),
                new BlockPos(x - 1, getHighestBlock(level, x - 1, z - 4).above().getY(), z - 4),
                new BlockPos(x + 3, getHighestBlock(level, x + 3, z + 2).above().getY(), z + 2),
                new BlockPos(x + 3, getHighestBlock(level, x + 3, z + 3).above().getY(), z + 3),
                new BlockPos(x + 2, getHighestBlock(level, x + 2, z + 3).above().getY(), z + 3),
                new BlockPos(x + 3, getHighestBlock(level, x + 3, z - 2).above().getY(), z - 2),
                new BlockPos(x + 3, getHighestBlock(level, x + 3, z - 3).above().getY(), z - 3),
                new BlockPos(x + 2, getHighestBlock(level, x + 2, z - 3).above().getY(), z - 3),
                new BlockPos(x - 2, getHighestBlock(level, x - 2, z - 3).above().getY(), z - 3),
                new BlockPos(x - 3, getHighestBlock(level, x - 3, z - 3).above().getY(), z - 3),
                new BlockPos(x - 3, getHighestBlock(level, x - 3, z - 2).above().getY(), z - 2),
                new BlockPos(x - 2, getHighestBlock(level, x - 2, z + 3).above().getY(), z + 3),
                new BlockPos(x - 3, getHighestBlock(level, x - 3, z + 3).above().getY(), z + 3),
                new BlockPos(x - 3, getHighestBlock(level, x - 3, z + 2).above().getY(), z + 2)
        };

        for (BlockPos pos : positions) {
            level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
        }

        level.playSound(null, player.eyeBlockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 100.0F, 1.0F);
        spawnParticleCloud(ParticleTypes.FLAME, x + 0.5D, player.getEyeY(), z + 0.5D, level);

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
        return stack.getItem() instanceof Staff && doesExist();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
