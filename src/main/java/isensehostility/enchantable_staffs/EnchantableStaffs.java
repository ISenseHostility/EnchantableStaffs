package isensehostility.enchantable_staffs;

import isensehostility.enchantable_staffs.config.StaffConfig;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import isensehostility.enchantable_staffs.enchantment.StaffEnchantments;
import isensehostility.enchantable_staffs.item.StaffItems;
import isensehostility.enchantable_staffs.potion.StaffPotions;
import isensehostility.enchantable_staffs.recipe.StaffRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.Random;

@Mod("enchantable_staffs")
public class EnchantableStaffs {

    public static final String MODID = "enchantable_staffs";
    public static final Random RANDOM = new Random();

    public EnchantableStaffs() {
        StaffConfig.loadConfig(StaffConfig.config, FMLPaths.CONFIGDIR.get().resolve(MODID + "-config.toml").toString());
        StaffItems.initialize();
        StaffEffects.initialize();
        StaffEnchantments.initialize();
        StaffPotions.initialize();
        StaffRecipes.initialize();
    }
}
