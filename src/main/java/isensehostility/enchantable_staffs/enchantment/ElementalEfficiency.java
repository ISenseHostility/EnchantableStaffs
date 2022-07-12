package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class ElementalEfficiency extends Enchantment implements IStaffEnchantment {
    public ElementalEfficiency() {
        super(Rarity.RARE, StaffCategory.getInstance(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.NONE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.elementalEfficiencyExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.elementalEfficiencyChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (player.hasEffect(StaffEffects.ELEMENTAL_EFFICIENCY.get())) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        player.addEffect(new MobEffectInstance(StaffEffects.ELEMENTAL_EFFICIENCY.get(), 1200, EnchantmentHelper.getItemEnchantmentLevel(StaffEnchantments.ELEMENTAL_EFFICIENCY.get(), stack) - 1));

        List<EElement> elementList = StaffConfig.elementsAllowedEfficiency.get();
        EElement selectedElement = elementList.get(getRandom().nextInt(elementList.size()));
        setElementalEfficiency(player, selectedElement);

        player.sendSystemMessage(Component.translatable("message.efficient_element").append(selectedElement.getName().withStyle(selectedElement.getColor())));

        spawnParticleCloud(ParticleTypes.END_ROD, player.getX(), player.getEyeY(), player.getZ(), level);
        level.playSound(null, new BlockPos(player.getEyePosition()), SoundEvents.AXE_WAX_OFF, SoundSource.PLAYERS, 100.0F, 1.0F);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 2;
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
