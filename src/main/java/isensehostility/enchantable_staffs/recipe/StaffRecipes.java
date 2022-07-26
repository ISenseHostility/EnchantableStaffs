package isensehostility.enchantable_staffs.recipe;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StaffRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EnchantableStaffs.MODID);

    public static void initialize() {
        RECIPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<RecipeSerializer<ModifierRecipe>> MODIFY_STAFF = RECIPES.register("modify_staff", ModifierRecipe.Serializer::new);
}
