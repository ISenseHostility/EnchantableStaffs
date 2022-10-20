package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.NBTUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Warp extends Enchantment implements IStaffEnchantment {
    public Warp() {
        super(Rarity.VERY_RARE, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.ENDER};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.warpExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.warpChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {

        if (Screen.hasShiftDown()) {
            if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
                return new InteractionResultHolder<>(InteractionResult.PASS, stack);
            }

            NBTUtils.setWarpDimension(stack, player);
            NBTUtils.setWarpPosition(stack, player);
            NBTUtils.setWarpOwner(stack, player);

            ModUtils.spawnParticleCloud(ParticleTypes.ENCHANTED_HIT, level, new BlockPos(player.getEyePosition()));
            level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.SHULKER_TELEPORT, SoundSource.PLAYERS, 100.0F, 1.0F);

            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        } else if (
                NBTUtils.getWarpDimension(stack).equals(player.level.dimension().location().toString()) &&
                        NBTUtils.getWarpOwner(stack).equals(player.getStringUUID())
        ) {
            long[] positionArray = NBTUtils.getWarpPosition(stack);
            Vec3 position = new Vec3(positionArray[0] / 1000000.0D, positionArray[1] / 1000000.0D, positionArray[2] / 1000000.0D);

            ModUtils.spawnParticleCloud(ParticleTypes.PORTAL, level, new BlockPos(player.getEyePosition()));

            player.setPos(position);

            ModUtils.spawnParticleCloud(ParticleTypes.PORTAL, level, new BlockPos(player.getEyePosition()));
            level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 100.0F, 1.0F);

            if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
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
