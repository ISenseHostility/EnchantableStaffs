package isensehostility.enchantable_staffs.item;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StaffItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnchantableStaffs.MODID);

    public static void initialize() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> IRON_STAFF = ITEMS.register("iron_staff", IronStaff::new);
    public static final RegistryObject<Item> REINFORCED_IRON_STAFF = ITEMS.register("reinforced_iron_staff", ReinforcedIronStaff::new);
    public static final RegistryObject<Item> GOLD_STAFF = ITEMS.register("gold_staff", GoldStaff::new);
    public static final RegistryObject<Item> REINFORCED_GOLD_STAFF = ITEMS.register("reinforced_gold_staff", ReinforcedGoldStaff::new);
    public static final RegistryObject<Item> NETHERITE_STAFF = ITEMS.register("netherite_staff", NetheriteStaff::new);
    public static final RegistryObject<Item> PLATED_NETHERITE_STAFF = ITEMS.register("plated_netherite_staff", PlatedNetheriteStaff::new);
    public static final RegistryObject<Item> STAFF_BONEMEAL = ITEMS.register("staff_bonemeal", StaffBoneMeal::new);
}
