package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class WitherSkull extends Enchantment implements IStaffEnchantment {
    public WitherSkull() {
        super(Rarity.RARE, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.UNDEAD};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.witherSkullExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.witherSkullChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        level.levelEvent(player, 1024, new BlockPos(player.getEyePosition()), 0);

        Vec3 direction = getDirection(player);
        Vec3 eyePos = player.getEyePosition();

        net.minecraft.world.entity.projectile.WitherSkull witherskull = new net.minecraft.world.entity.projectile.WitherSkull(level, player, direction.x(), direction.y(), direction.z());
        witherskull.setOwner(player);
        witherskull.setPosRaw(eyePos.x(), eyePos.y(), eyePos.z());

        if (Screen.hasShiftDown()) {
            witherskull.setDangerous(true);
        }

        level.addFreshEntity(witherskull);

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
