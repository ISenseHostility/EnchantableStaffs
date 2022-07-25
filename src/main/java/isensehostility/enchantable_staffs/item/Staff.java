package isensehostility.enchantable_staffs.item;

import isensehostility.enchantable_staffs.StaffUtils;
import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.Explosion;
import isensehostility.enchantable_staffs.enchantment.IStaffEnchantment;
import isensehostility.enchantable_staffs.enchantment.WeatherAlteration;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.enums.EStaffModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class Staff extends Item {
    public static final String TAG_STAFF_MODIFIER = "staff_modifier";

    public Staff(Properties properties) {
        super(properties);
    }

    public EStaffModifiers getModifier(ItemStack stack) {
        return EStaffModifiers.getFromStack(stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ArrayList<EElement> elements = new ArrayList<>();
        Set<Enchantment> enchantments = EnchantmentHelper.getEnchantments(stack).keySet();

        for (Enchantment enchantment : enchantments) {
            if (enchantment instanceof IStaffEnchantment staffEnchantment) {
                elements.addAll(Arrays.stream(staffEnchantment.getElements()).toList());
            }
        }

        if (Screen.hasShiftDown()) {
            for (EElement element : elements) {
                tooltip.add(element.getName().withStyle(element.getColor(), ChatFormatting.BOLD));
            }
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof IStaffEnchantment staffEnchantment) {
                    int cost = StaffUtils.calculateCharge(staffEnchantment, Minecraft.getInstance().player);

                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.charge_cost").append(": " + cost).withStyle(ChatFormatting.AQUA));
                }
            }
        }

        if (Screen.hasControlDown()) {
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof Explosion) {
                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.explosion").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
                } else if (enchantment instanceof WeatherAlteration) {
                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.weather_alteration").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.setDamageValue(stack.getDamageValue() - 1);
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof Staff) {
            for (Enchantment enchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
                if (enchantment instanceof IStaffEnchantment staffEnchantment && StaffUtils.calculateCharge(staffEnchantment, player) <= StaffUtils.getCharge(player)) {
                    InteractionResultHolder resultHolder = staffEnchantment.onUse(stack, level, player);

                    if (resultHolder.getResult() == InteractionResult.SUCCESS) {
                        player.getCooldowns().addCooldown(StaffItems.IRON_STAFF.get(), StaffConfig.staffCooldown.get());
                        player.getCooldowns().addCooldown(StaffItems.REINFORCED_IRON_STAFF.get(), StaffConfig.staffCooldown.get());
                        player.getCooldowns().addCooldown(StaffItems.GOLD_STAFF.get(), StaffConfig.staffCooldown.get());
                        player.getCooldowns().addCooldown(StaffItems.REINFORCED_GOLD_STAFF.get(), StaffConfig.staffCooldown.get());
                        player.getCooldowns().addCooldown(StaffItems.NETHERITE_STAFF.get(), StaffConfig.staffCooldown.get());
                        player.getCooldowns().addCooldown(StaffItems.PLATED_NETHERITE_STAFF.get(), StaffConfig.staffCooldown.get());

                        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
                    }
                }
            }
        }
        return super.use(level, player, hand);
    }


}
