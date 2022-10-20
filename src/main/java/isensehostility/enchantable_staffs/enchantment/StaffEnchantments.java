package isensehostility.enchantable_staffs.enchantment;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StaffEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EnchantableStaffs.MODID);

    public static void initialize() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Enchantment> TELEPORT = ENCHANTMENTS.register("teleport", Teleport::new);
    public static final RegistryObject<Enchantment> CREATE_WATER = ENCHANTMENTS.register("create_water", CreateWater::new);
    public static final RegistryObject<Enchantment> CREATE_LAVA = ENCHANTMENTS.register("create_lava", CreateLava::new);
    public static final RegistryObject<Enchantment> LIGHTNING_BOLT = ENCHANTMENTS.register("lightning_bolt", LightningBolt::new);
    public static final RegistryObject<Enchantment> FIREBALL = ENCHANTMENTS.register("fireball", Fireball::new);
    public static final RegistryObject<Enchantment> DRACONIC_FIREBALL = ENCHANTMENTS.register("draconic_fireball", DraconicFireball::new);
    public static final RegistryObject<Enchantment> FLAME_BREATH = ENCHANTMENTS.register("flame_breath", FlameBreath::new);
    public static final RegistryObject<Enchantment> FREEZE_BREATH = ENCHANTMENTS.register("freeze_breath", FreezeBreath::new);
    public static final RegistryObject<Enchantment> DRAGON_BREATH = ENCHANTMENTS.register("dragon_breath", DragonBreath::new);
    public static final RegistryObject<Enchantment> AREA_HEAL = ENCHANTMENTS.register("area_heal", AreaHeal::new);
    public static final RegistryObject<Enchantment> CLEANSING = ENCHANTMENTS.register("cleansing", Cleansing::new);
    public static final RegistryObject<Enchantment> EXPLOSION = ENCHANTMENTS.register("explosion", Explosion::new);
    public static final RegistryObject<Enchantment> CURE_ILLNESS = ENCHANTMENTS.register("cure_illness", CureIllness::new);
    public static final RegistryObject<Enchantment> CRITICAL = ENCHANTMENTS.register("critical", Critical::new);
    public static final RegistryObject<Enchantment> WEATHER_ALTERATION = ENCHANTMENTS.register("weather_alteration", WeatherAlteration::new);
    public static final RegistryObject<Enchantment> LIGHTNING_CIRCLE = ENCHANTMENTS.register("lightning_circle", LightningCircle::new);
    public static final RegistryObject<Enchantment> RING_OF_FIRE = ENCHANTMENTS.register("ring_of_fire", RingOfFire::new);
    public static final RegistryObject<Enchantment> HEAL = ENCHANTMENTS.register("heal", Heal::new);
    public static final RegistryObject<Enchantment> GREATER_HEAL = ENCHANTMENTS.register("greater_heal", GreaterHeal::new);
    public static final RegistryObject<Enchantment> GLOW = ENCHANTMENTS.register("glow", Glow::new);
    public static final RegistryObject<Enchantment> TURN_UNDEAD = ENCHANTMENTS.register("turn_undead", TurnUndead::new);
    public static final RegistryObject<Enchantment> GILLS = ENCHANTMENTS.register("gills", Gills::new);
    public static final RegistryObject<Enchantment> WARP = ENCHANTMENTS.register("warp", Warp::new);
    public static final RegistryObject<Enchantment> SUMMON_SKELETON_HORSE = ENCHANTMENTS.register("summon_skeleton_horse", SummonSkeletonHorse::new);
    public static final RegistryObject<Enchantment> NECROMANCY = ENCHANTMENTS.register("necromancy", Necromancy::new);
    public static final RegistryObject<Enchantment> MAGICAL_STRENGTHENING_STAFF = ENCHANTMENTS.register("magical_strengthening_staff", MagicalStrengtheningStaff::new);
    public static final RegistryObject<Enchantment> MAGICAL_STRENGTHENING_WEAPON = ENCHANTMENTS.register("magical_strengthening_weapon", MagicalStrengtheningWeapon::new);
    public static final RegistryObject<Enchantment> SPECTRAL_WINGS = ENCHANTMENTS.register("spectral_wings", SpectralWings::new);
    public static final RegistryObject<Enchantment> ELEMENTAL_EFFICIENCY = ENCHANTMENTS.register("elemental_efficiency", ElementalEfficiency::new);
    public static final RegistryObject<Enchantment> WITHER_SKULL = ENCHANTMENTS.register("wither_skull", WitherSkull::new);
    public static final RegistryObject<Enchantment> HEALING_RAY = ENCHANTMENTS.register("healing_ray", HealingRay::new);
    public static final RegistryObject<Enchantment> GARDEN_GROWTH = ENCHANTMENTS.register("garden_growth", GardenGrowth::new);
    public static final RegistryObject<Enchantment> BITE = ENCHANTMENTS.register("bite", Bite::new);
    public static final RegistryObject<Enchantment> RING_OF_FANGS = ENCHANTMENTS.register("ring_of_fangs", RingOfFangs::new);
    public static final RegistryObject<Enchantment> SUMMON_VEX = ENCHANTMENTS.register("summon_vex", SummonVex::new);
    public static final RegistryObject<Enchantment> EVOKER_FANGS = ENCHANTMENTS.register("evoker_fangs", EvokerFangs::new);
    public static final RegistryObject<Enchantment> CONVERSION = ENCHANTMENTS.register("conversion", Conversion::new);
}
