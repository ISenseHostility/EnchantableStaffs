package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;

public class SummonSkeletonHorse extends Enchantment implements IStaffEnchantment {
    public SummonSkeletonHorse() {
        super(Rarity.COMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.SUMMON, EElement.UNDEAD};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.summonSkeletonHorseExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.summonSkeletonHorseChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        SkeletonHorse horse = new SkeletonHorse(EntityType.SKELETON_HORSE, level);
        horse.setPos(Vec3.atCenterOf(player.getOnPos()));
        horse.tameWithName(player);

        if (horse.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
            horse.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get().insertItem(0, new ItemStack(Items.SADDLE), false);
        }

        level.addFreshEntity(horse);

        ModUtils.spawnParticleCloud(ParticleTypes.SOUL, level, player.getX(), player.getY() + 1.0D, player.getZ());
        level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 100.0F, 1.0F);

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
