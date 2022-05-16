package isensehostility.enchantable_staffs.ingredient;

import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PotionIngredient extends Ingredient {

    private final Potion potion;
    public PotionIngredient(Potion potion) {
        super(Stream.empty());
        this.potion = potion;
    }

    @Override
    public boolean test(@Nullable ItemStack p_43914_) {
        return p_43914_ != null && p_43914_.hasTag() && p_43914_.getTag().getString("Potion").equals(Registry.POTION.getKey(potion).toString());
    }
}
