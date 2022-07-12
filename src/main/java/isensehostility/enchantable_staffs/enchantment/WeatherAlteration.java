package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.enchantment.category.StaffCategory;
import isensehostility.enchantable_staffs.enums.EElement;
import isensehostility.enchantable_staffs.enums.EWeather;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import static isensehostility.enchantable_staffs.StaffUtils.*;

public class WeatherAlteration extends Enchantment implements IStaffEnchantment {
    public WeatherAlteration() {
        super(Rarity.COMMON, StaffCategory.get(), new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public EElement[] getElements() {
        return new EElement[]{EElement.WATER, EElement.LIGHTNING, EElement.FIRE};
    }

    @Override
    public boolean doesExist() {
        return StaffConfig.weatherAlterationExists.get();
    }

    @Override
    public int getChargeCost() {
        return StaffConfig.weatherAlterationChargeCost.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> onUse(ItemStack stack, Level level, Player player) {
        if (invokeStaffCosts(player, stack, getChargeCost(), level)) {
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);
        }

        EWeather weather = getWeather(level);

        if (!level.isClientSide) {
            ServerLevel server = (ServerLevel) level;

            switch (weather) {
                case CLEAR:
                    if (player.getRandom().nextBoolean()) {
                        setWeatherRaining(server);
                        invokeWeatherVisuals(EWeather.RAINING, new BlockPos(player.getEyePosition()), level);
                    } else {
                        setWeatherThundering(server);
                        invokeWeatherVisuals(EWeather.THUNDERING, new BlockPos(player.getEyePosition()), level);
                    }
                    break;
                case RAINING:
                    if (player.getRandom().nextBoolean()) {
                        setWeatherClear(server);
                        invokeWeatherVisuals(EWeather.CLEAR, new BlockPos(player.getEyePosition()), level);
                    } else {
                        setWeatherThundering(server);
                        invokeWeatherVisuals(EWeather.THUNDERING, new BlockPos(player.getEyePosition()), level);
                    }
                    break;
                case THUNDERING:
                    if (player.getRandom().nextBoolean()) {
                        setWeatherClear(server);
                        invokeWeatherVisuals(EWeather.CLEAR, new BlockPos(player.getEyePosition()), level);
                    } else {
                        setWeatherRaining(server);
                        invokeWeatherVisuals(EWeather.RAINING, new BlockPos(player.getEyePosition()), level);
                    }
                    break;
            }
        }

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
