package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.IStaffEnchantmentHolder;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class Explosion extends Enchantment implements IStaffEnchantment {
    public Explosion() {
        super(Rarity.COMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.NONE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.explosionExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.explosionChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        BlockHitResult rayTrace = ModUtils.rayTrace(level, player, ClipContext.Fluid.NONE, 100);
        Direction direction = rayTrace.getDirection();
        BlockPos posCollide = rayTrace.getBlockPos();
        BlockPos pos = posCollide.relative(direction);

        if (ModUtils.posIsAir(level, posCollide)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
        if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        if (!level.isClientSide) {
            level.explode(player, pos.getX(), pos.getY(), pos.getZ(), EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.EXPLOSION.get(), stack) + 0.5F, BlockInteraction.BREAK);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 20 * (level - 1);
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
        return stack.getItem() instanceof IStaffEnchantmentHolder && doesExist();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
