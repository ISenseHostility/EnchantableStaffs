package isensehostility.enchantable_staffs.potion;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.effect.StaffEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StaffPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, EnchantableStaffs.MODID);

    public static void initialize() {
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Potion> CHARGE_ESCALATION_POTION = POTIONS.register("charge_escalation", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_ESCALATION.get(), 2400, 0)));
    public static final RegistryObject<Potion> CHARGE_ESCALATION_POTION_LONG = POTIONS.register("charge_escalation_long", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_ESCALATION.get(), 4800, 0)));
    public static final RegistryObject<Potion> CHARGE_ESCALATION_POTION_STRONG_1 = POTIONS.register("charge_escalation_strong_1", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_ESCALATION.get(), 2400, 1)));
    public static final RegistryObject<Potion> CHARGE_ESCALATION_POTION_STRONG_2 = POTIONS.register("charge_escalation_strong_2", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_ESCALATION.get(), 2400, 2)));
    public static final RegistryObject<Potion> CHARGE_ESCALATION_POTION_STRONG_3 = POTIONS.register("charge_escalation_strong_3", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_ESCALATION.get(), 2400, 3)));

    public static final RegistryObject<Potion> CHARGE_BREAKDOWN_POTION = POTIONS.register("charge_breakdown", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 2400, 0)));
    public static final RegistryObject<Potion> CHARGE_BREAKDOWN_POTION_LONG = POTIONS.register("charge_breakdown_long", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 4800, 0)));
    public static final RegistryObject<Potion> CHARGE_BREAKDOWN_POTION_STRONG_1 = POTIONS.register("charge_breakdown_strong_1", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 2400, 1)));
    public static final RegistryObject<Potion> CHARGE_BREAKDOWN_POTION_STRONG_2 = POTIONS.register("charge_breakdown_strong_2", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 2400, 2)));
    public static final RegistryObject<Potion> CHARGE_BREAKDOWN_POTION_STRONG_3 = POTIONS.register("charge_breakdown_strong_3", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_BREAKDOWN.get(), 2400, 3)));

    public static final RegistryObject<Potion> CHARGE_SICKNESS_POTION = POTIONS.register("charge_sickness", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_SICKNESS.get(), 600, 0)));
    public static final RegistryObject<Potion> CHARGE_SICKNESS_POTION_LONG = POTIONS.register("charge_sickness_long", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_SICKNESS.get(), 1200, 0)));

    public static final RegistryObject<Potion> CHARGE_INSERTION_POTION = POTIONS.register("charge_insertion", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_INSERTION.get(), 1, 0)));
    public static final RegistryObject<Potion> CHARGE_INSERTION_POTION_STRONG = POTIONS.register("charge_insertion_strong", () -> new Potion(new MobEffectInstance(StaffEffects.CHARGE_INSERTION.get(), 1, 1)));

}
