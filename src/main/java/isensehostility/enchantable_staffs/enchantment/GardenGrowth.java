package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.item.StaffItems;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.NBTUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;

public class GardenGrowth extends Enchantment implements IStaffEnchantment {
    public GardenGrowth() {
        super(Rarity.COMMON, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.NATURE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.gardenGrowthExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.gardenGrowthChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (NBTUtils.getCharge(player) < getChargeCost()) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        UseOnContext ctx = new UseOnContext(level, player, player.getUsedItemHand(), stack, ModUtils.rayTrace(level, player, ClipContext.Fluid.NONE, player.getReachDistance()));

        if (StaffItems.STAFF_BONEMEAL.get().useOn(ctx) == InteractionResult.CONSUME) {
            if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
                return new InteractionResultHolder<>(InteractionResult.PASS, stack);
            }

            ModUtils.spawnParticleCloud(ParticleTypes.HAPPY_VILLAGER, level, ctx.getClickedPos().getX(), ctx.getClickedPos().getY() + 1.0D, ctx.getClickedPos().getZ());

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
}
