package isensehostility.enchantable_staffs.event;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.ingredient.PotionIngredient;
import isensehostility.enchantable_staffs.network.StaffPacketHandler;
import isensehostility.enchantable_staffs.potion.StaffPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(Potions.AWKWARD), Ingredient.of(new ItemStack(Items.LAPIS_LAZULI)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_ESCALATION_POTION.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION.get()), Ingredient.of(new ItemStack(Items.REDSTONE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_ESCALATION_POTION_LONG.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_ESCALATION_POTION_STRONG_1.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_1.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_ESCALATION_POTION_STRONG_2.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_2.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_ESCALATION_POTION_STRONG_3.get()));

                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_BREAKDOWN_POTION.get()), Ingredient.of(new ItemStack(Items.REDSTONE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_LONG.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_BREAKDOWN_POTION.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_1.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_1.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_2.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_2.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_3.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_LONG.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_LONG.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_1.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_1.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_2.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_2.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_ESCALATION_POTION_STRONG_3.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_BREAKDOWN_POTION_STRONG_3.get()));

                BrewingRecipeRegistry.addRecipe(new PotionIngredient(Potions.AWKWARD), Ingredient.of(new ItemStack(Items.ICE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_INSERTION_POTION.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_INSERTION_POTION.get()), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_INSERTION_POTION_STRONG.get()));

                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_INSERTION_POTION.get()), Ingredient.of(new ItemStack(Items.FERMENTED_SPIDER_EYE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_SICKNESS_POTION.get()));
                BrewingRecipeRegistry.addRecipe(new PotionIngredient(StaffPotions.CHARGE_SICKNESS_POTION.get()), Ingredient.of(new ItemStack(Items.REDSTONE)), PotionUtils.setPotion(new ItemStack(Items.POTION), StaffPotions.CHARGE_SICKNESS_POTION_LONG.get()));
            });
        }
    }
}
