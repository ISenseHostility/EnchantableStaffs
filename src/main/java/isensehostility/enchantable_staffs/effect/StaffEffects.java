package isensehostility.enchantable_staffs.effect;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StaffEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EnchantableStaffs.MODID);

    public static void initialize() {
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<MobEffect> GILLS = EFFECTS.register("gills", GillsEffect::new);
    public static final RegistryObject<MobEffect> CRITICAL = EFFECTS.register("critical", CriticalEffect::new);
    public static final RegistryObject<MobEffect> ELEMENTAL_EFFICIENCY = EFFECTS.register("elemental_efficiency", ElementalEfficiencyEffect::new);
    public static final RegistryObject<MobEffect> CHARGE_ESCALATION = EFFECTS.register("charge_escalation", ChargeEscalation::new);
    public static final RegistryObject<MobEffect> CHARGE_BREAKDOWN = EFFECTS.register("charge_breakdown", ChargeBreakdown::new);
    public static final RegistryObject<MobEffect> CHARGE_SICKNESS = EFFECTS.register("charge_sickness", ChargeSickness::new);
    public static final RegistryObject<MobEffect> CHARGE_INSERTION = EFFECTS.register("charge_insertion", ChargeInsertion::new);
}
