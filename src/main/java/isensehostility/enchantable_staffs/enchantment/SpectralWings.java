package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.util.NBTUtils;
import isensehostility.enchantable_staffs.util.StaffUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class SpectralWings extends Enchantment implements IStaffEnchantment {
    public SpectralWings() {
        super(Rarity.VERY_RARE, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.ENDER};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.spectralWingsExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.spectralWingsChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (StaffUtils.invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        NBTUtils.setChestplateData(player);

        ItemStack wings = new ItemStack(Items.ELYTRA);
        wings.enchant(Enchantments.BINDING_CURSE, 1);
        wings.enchant(Enchantments.VANISHING_CURSE, 1);
        wings.enchant(Enchantments.UNBREAKING, 3);
        NBTUtils.setIsSpectralWings(wings, true);

        NBTUtils.setSpectralWingsTime(player, 800);
        player.setItemSlot(EquipmentSlot.CHEST, wings);

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
