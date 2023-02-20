package isensehostility.enchantable_staffs.event;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.ingredient.PotionIngredient;
import isensehostility.enchantable_staffs.network.StaffPacketHandler;
import isensehostility.enchantable_staffs.potion.StaffPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = EnchantableStaffs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(StaffPacketHandler::initialize);
        if (StaffConfig.chargePotionsExist.get()) {
            event.enqueueWork(() -> {
                PotionBrewing.addMix(Potions.AWKWARD, Items.LAPIS_LAZULI, StaffPotions.CHARGE_ESCALATION_POTION.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_ESCALATION_POTION.get(), Items.REDSTONE, StaffPotions.CHARGE_ESCALATION_POTION_LONG.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_ESCALATION_POTION.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_ESCALATION_POTION_STRONG_1.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_1.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_ESCALATION_POTION_STRONG_2.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_2.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_ESCALATION_POTION_STRONG_3.get());

                PotionBrewing.addMix(StaffPotions.CHARGE_ESCALATION_POTION.get(), Items.FERMENTED_SPIDER_EYE, StaffPotions.CHARGE_BREAKDOWN_POTION.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_BREAKDOWN_POTION.get(), Items.REDSTONE, StaffPotions.CHARGE_BREAKDOWN_POTION_LONG.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_BREAKDOWN_POTION.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_1.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_1.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_2.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_2.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_3.get());

                PotionBrewing.addMix(Potions.AWKWARD, Items.ICE, StaffPotions.CHARGE_INSERTION_POTION.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_INSERTION_POTION.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_INSERTION_POTION_STRONG.get());

                PotionBrewing.addMix(StaffPotions.CHARGE_INSERTION_POTION.get(), Items.FERMENTED_SPIDER_EYE, StaffPotions.CHARGE_SICKNESS_POTION.get());
                PotionBrewing.addMix(StaffPotions.CHARGE_SICKNESS_POTION.get(), Items.GLOWSTONE_DUST, StaffPotions.CHARGE_SICKNESS_POTION_LONG.get());
            });
        }
    }
}
