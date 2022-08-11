package isensehostility.enchantable_staffs.recipe;

import com.google.gson.JsonObject;
import isensehostility.enchantable_staffs.enums.EStaffModifiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class ModifierRecipe extends UpgradeRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;
    private final ResourceLocation id;

    public ModifierRecipe(ResourceLocation location, Ingredient base, Ingredient addition, ItemStack result) {
        super(location, base, addition, result);
        this.id = location;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public ItemStack assemble(Container container) {
        ItemStack itemstack = container.getItem(0).copy();
        CompoundTag compoundtag = container.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        EStaffModifiers.setToStack(itemstack, EStaffModifiers.getByMaterial(container.getItem(1).getItem()));

        return itemstack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return StaffRecipes.MODIFY_STAFF.get();
    }

    public static class Serializer implements RecipeSerializer<ModifierRecipe> {
        public ModifierRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "staff"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "modifier"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new ModifierRecipe(location, base, addition, itemstack);
        }

        public ModifierRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buf) {
            Ingredient base = Ingredient.fromNetwork(buf);
            Ingredient addition = Ingredient.fromNetwork(buf);
            ItemStack itemstack = buf.readItem();
            return new ModifierRecipe(location, base, addition, itemstack);
        }

        public void toNetwork(FriendlyByteBuf buf, ModifierRecipe recipe) {
            recipe.base.toNetwork(buf);
            recipe.addition.toNetwork(buf);
            buf.writeItem(recipe.result);
        }
    }
}
