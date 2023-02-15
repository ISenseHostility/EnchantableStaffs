package isensehostility.enchantable_staffs.item;

import isensehostility.enchantable_staffs.enchantment.Explosion;
import isensehostility.enchantable_staffs.enchantment.IStaffEnchantment;
import isensehostility.enchantable_staffs.enchantment.WeatherAlteration;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.util.ChargeUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
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

public class Scroll extends Item implements IStaffEnchantmentHolder {

    public Scroll() {
        super(new Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(16).rarity(Rarity.UNCOMMON));
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
                    int cost = ChargeUtils.calculateCharge(staffEnchantment, Minecraft.getInstance().player);

                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.charge_cost").append(": " + cost).withStyle(ChatFormatting.AQUA));
                }
            }
        }

        if (Screen.hasControlDown()) {
            for (Enchantment enchantment : enchantments) {
                if (enchantment instanceof Explosion) {
                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.special.explosion").withStyle(ChatFormatting.RED, ChatFormatting.ITALIC));
                } else if (enchantment instanceof WeatherAlteration) {
                    tooltip.add(Component.translatable("tooltip.enchantable_staffs.special.weather_alteration").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
                }
            }
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1 && stack.getAllEnchantments().isEmpty();
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 17;
    }
}
