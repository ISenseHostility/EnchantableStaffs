package isensehostility.enchantable_staffs.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import isensehostility.enchantable_staffs.enums.EElement;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;
import java.util.List;

public class StaffConfig {
    public static ForgeConfigSpec.IntValue teleportChargeCost;
    public static ForgeConfigSpec.IntValue createWaterChargeCost;
    public static ForgeConfigSpec.IntValue createLavaChargeCost;
    public static ForgeConfigSpec.IntValue lightningBoltChargeCost;
    public static ForgeConfigSpec.IntValue fireballChargeCost;
    public static ForgeConfigSpec.IntValue draconicFireballChargeCost;
    public static ForgeConfigSpec.IntValue dragonBreathChargeCost;
    public static ForgeConfigSpec.IntValue flameBreathChargeCost;
    public static ForgeConfigSpec.IntValue freezeBreathChargeCost;
    public static ForgeConfigSpec.IntValue areaHealChargeCost;
    public static ForgeConfigSpec.IntValue cleansingChargeCost;
    public static ForgeConfigSpec.IntValue criticalChargeCost;
    public static ForgeConfigSpec.IntValue cureIllnessChargeCost;
    public static ForgeConfigSpec.IntValue explosionChargeCost;
    public static ForgeConfigSpec.IntValue weatherAlterationChargeCost;
    public static ForgeConfigSpec.IntValue lightningCircleChargeCost;
    public static ForgeConfigSpec.IntValue ringOfFireChargeCost;
    public static ForgeConfigSpec.IntValue healChargeCost;
    public static ForgeConfigSpec.IntValue greaterHealChargeCost;
    public static ForgeConfigSpec.IntValue glowChargeCost;
    public static ForgeConfigSpec.IntValue turnUndeadChargeCost;
    public static ForgeConfigSpec.IntValue gillsChargeCost;
    public static ForgeConfigSpec.IntValue warpChargeCost;
    public static ForgeConfigSpec.IntValue summonSkeletonHorseChargeCost;
    public static ForgeConfigSpec.IntValue necromancyChargeCost;
    public static ForgeConfigSpec.IntValue magicalStrengtheningChargeCost;
    public static ForgeConfigSpec.IntValue spectralWingsChargeCost;
    public static ForgeConfigSpec.IntValue elementalEfficiencyChargeCost;
    public static ForgeConfigSpec.IntValue witherSkullChargeCost;
    public static ForgeConfigSpec.IntValue healingRayChargeCost;
    public static ForgeConfigSpec.IntValue gardenGrowthChargeCost;
    public static ForgeConfigSpec.IntValue biteChargeCost;
    public static ForgeConfigSpec.IntValue ringOfFangsChargeCost;

    public static ForgeConfigSpec.BooleanValue teleportExists;
    public static ForgeConfigSpec.BooleanValue createWaterExists;
    public static ForgeConfigSpec.BooleanValue createLavaExists;
    public static ForgeConfigSpec.BooleanValue lightningBoltExists;
    public static ForgeConfigSpec.BooleanValue fireballExists;
    public static ForgeConfigSpec.BooleanValue draconicFireballExists;
    public static ForgeConfigSpec.BooleanValue dragonBreathExists;
    public static ForgeConfigSpec.BooleanValue flameBreathExists;
    public static ForgeConfigSpec.BooleanValue freezeBreathExists;
    public static ForgeConfigSpec.BooleanValue areaHealExists;
    public static ForgeConfigSpec.BooleanValue cleansingExists;
    public static ForgeConfigSpec.BooleanValue criticalExists;
    public static ForgeConfigSpec.BooleanValue cureIllnessExists;
    public static ForgeConfigSpec.BooleanValue explosionExists;
    public static ForgeConfigSpec.BooleanValue weatherAlterationExists;
    public static ForgeConfigSpec.BooleanValue lightningCircleExists;
    public static ForgeConfigSpec.BooleanValue ringOfFireExists;
    public static ForgeConfigSpec.BooleanValue healExists;
    public static ForgeConfigSpec.BooleanValue greaterHealExists;
    public static ForgeConfigSpec.BooleanValue glowExists;
    public static ForgeConfigSpec.BooleanValue turnUndeadExists;
    public static ForgeConfigSpec.BooleanValue gillsExists;
    public static ForgeConfigSpec.BooleanValue warpExists;
    public static ForgeConfigSpec.BooleanValue summonSkeletonHorseExists;
    public static ForgeConfigSpec.BooleanValue necromancyExists;
    public static ForgeConfigSpec.BooleanValue magicalStrengtheningExists;
    public static ForgeConfigSpec.BooleanValue spectralWingsExists;
    public static ForgeConfigSpec.BooleanValue elementalEfficiencyExists;
    public static ForgeConfigSpec.BooleanValue witherSkullExists;
    public static ForgeConfigSpec.BooleanValue healingRayExists;
    public static ForgeConfigSpec.BooleanValue gardenGrowthExists;
    public static ForgeConfigSpec.BooleanValue biteExists;
    public static ForgeConfigSpec.BooleanValue ringOfFangsExists;

    public static ForgeConfigSpec.IntValue chargeMaxStarting;
    public static ForgeConfigSpec.BooleanValue chargePotionsExist;

    public static ForgeConfigSpec.DoubleValue staffKnockback;
    public static ForgeConfigSpec.IntValue staffCooldown;

    public static ForgeConfigSpec.ConfigValue<List<EElement>> elementsAllowedEfficiency;

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec config;

    static {
        StaffConfig.initialize(builder);
        config = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void initialize(ForgeConfigSpec.Builder builder) {
        teleportChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Teleport enchantment.\n" +
                        "[default=200]")
                .defineInRange("chargeCosts.teleport", 200, 0, Integer.MAX_VALUE);
        teleportExists = builder
                .comment("When set to false the Teleport enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.teleport", true);

        createWaterChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Create Water enchantment.\n" +
                        "[default=60]")
                .defineInRange("chargeCosts.createWater", 60, 0, Integer.MAX_VALUE);
        createWaterExists = builder
                .comment("When set to false the Create Water enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.createWater", true);

        createLavaChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Create Lava enchantment.\n" +
                        "[default=80]")
                .defineInRange("chargeCosts.createLava", 80, 0, Integer.MAX_VALUE);
        createLavaExists = builder
                .comment("When set to false the Create Lava enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.createLava", true);

        lightningBoltChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Fireball enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.lightningBolt", 100, 0, Integer.MAX_VALUE);
        lightningBoltExists = builder
                .comment("When set to false the Fireball enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.lightningBolt", true);

        fireballChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Fireball enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.fireball", 100, 0, Integer.MAX_VALUE);
        fireballExists = builder
                .comment("When set to false the Fireball enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.fireball", true);

        draconicFireballChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Draconic Fireball enchantment.\n" +
                        "[default=120]")
                .defineInRange("chargeCosts.draconicFireball", 120, 0, Integer.MAX_VALUE);
        draconicFireballExists = builder
                .comment("When set to false the Draconic Fireball enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.draconicFireball", true);

        dragonBreathChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Dragon Breath enchantment.\n" +
                        "[default=110]")
                .defineInRange("chargeCosts.dragonBreath", 110, 0, Integer.MAX_VALUE);
        dragonBreathExists = builder
                .comment("When set to false the Dragon Breath enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.dragonBreath", true);

        flameBreathChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Flame Breath enchantment.\n" +
                        "[default=90]")
                .defineInRange("chargeCosts.flameBreath", 90, 0, Integer.MAX_VALUE);
        flameBreathExists = builder
                .comment("When set to false the Flame Breath enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.flameBreath", true);

        freezeBreathChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Freeze Breath enchantment.\n" +
                        "[default=90]")
                .defineInRange("chargeCosts.freezeBreath", 90, 0, Integer.MAX_VALUE);
        freezeBreathExists = builder
                .comment("When set to false the Freeze Breath enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.freezeBreath", true);

        areaHealChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Area Heal enchantment.\n" +
                        "[default=300]")
                .defineInRange("chargeCosts.areaHeal", 300, 0, Integer.MAX_VALUE);
        areaHealExists = builder
                .comment("When set to false the Area Heal enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.areaHeal", true);

        cleansingChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Cleansing enchantment.\n" +
                        "[default=400]")
                .defineInRange("chargeCosts.cleansing", 400, 0, Integer.MAX_VALUE);
        cleansingExists = builder
                .comment("When set to false the Cleansing enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.cleansing", true);

        criticalChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Critical enchantment.\n" +
                        "[default=900]")
                .defineInRange("chargeCosts.critical", 900, 0, Integer.MAX_VALUE);
        criticalExists = builder
                .comment("When set to false the Critical enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.critical", true);

        cureIllnessChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Cure Illness enchantment.\n" +
                        "[default=300]")
                .defineInRange("chargeCosts.cureIllness", 300, 0, Integer.MAX_VALUE);
        cureIllnessExists = builder
                .comment("When set to false the Cure Illness enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.cureIllness", true);

        explosionChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Explosion enchantment.\n" +
                        "[default=120]")
                .defineInRange("chargeCosts.explosion", 120, 0, Integer.MAX_VALUE);
        explosionExists = builder
                .comment("When set to false the Explosion enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.explosion", true);

        weatherAlterationChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Weather Alteration enchantment.\n" +
                        "[default=200]")
                .defineInRange("chargeCosts.weatherAlteration", 200, 0, Integer.MAX_VALUE);
        weatherAlterationExists = builder
                .comment("When set to false the Weather Alteration enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.weatherAlteration", true);

        lightningCircleChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Lightning Circle enchantment.\n" +
                        "[default=200]")
                .defineInRange("chargeCosts.lightningCircle", 200, 0, Integer.MAX_VALUE);
        lightningCircleExists = builder
                .comment("When set to false the Lightning Circle enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.lightningCircle", true);

        ringOfFireChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Ring of Fire enchantment.\n" +
                        "[default=150]")
                .defineInRange("chargeCosts.ringOfFire", 150, 0, Integer.MAX_VALUE);
        ringOfFireExists = builder
                .comment("When set to false the Ring of Fire enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.ringOfFire", true);

        healChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Heal enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.heal", 100, 0, Integer.MAX_VALUE);
        healExists = builder
                .comment("When set to false the Heal enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.heal", true);

        greaterHealChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Greater Heal enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.greaterHeal", 100, 0, Integer.MAX_VALUE);
        greaterHealExists = builder
                .comment("When set to false the Greater Heal enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.greaterHeal", true);

        glowChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Glow enchantment.\n" +
                        "[default=80]")
                .defineInRange("chargeCosts.glow", 80, 0, Integer.MAX_VALUE);
        glowExists = builder
                .comment("When set to false the Glow enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.glow", true);

        turnUndeadChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Turn Undead enchantment.\n" +
                        "[default=120]")
                .defineInRange("chargeCosts.turnUndead", 120, 0, Integer.MAX_VALUE);
        turnUndeadExists = builder
                .comment("When set to false the Turn Undead enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.turnUndead", true);

        gillsChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Gills enchantment.\n" +
                        "[default=600]")
                .defineInRange("chargeCosts.gills", 600, 0, Integer.MAX_VALUE);
        gillsExists = builder
                .comment("When set to false the Gills enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.gills", true);

        warpChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Warp enchantment.\n" +
                        "[default=300]")
                .defineInRange("chargeCosts.warp", 300, 0, Integer.MAX_VALUE);
        warpExists = builder
                .comment("When set to false the Warp enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.warp", true);

        summonSkeletonHorseChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Summon Skeleton Horse enchantment.\n" +
                        "[default=80]")
                .defineInRange("chargeCosts.summonSkeletonHorse", 80, 0, Integer.MAX_VALUE);
        summonSkeletonHorseExists = builder
                .comment("When set to false the Summon Skeleton Horse enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.summonSkeletonHorse", true);

        necromancyChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Necromancy enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.necromancy", 100, 0, Integer.MAX_VALUE);
        necromancyExists = builder
                .comment("When set to false the Necromancy enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.necromancy", true);

        magicalStrengtheningChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Magical Strengthening enchantment.\n" +
                        "[default=900]")
                .defineInRange("chargeCosts.magicalStrengthening", 900, 0, Integer.MAX_VALUE);
        magicalStrengtheningExists = builder
                .comment("When set to false the Magical Strengthening enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.magicalStrengthening", true);

        spectralWingsChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Spectral Wings enchantment.\n" +
                        "[default=1000]")
                .defineInRange("chargeCosts.spectralWings", 1000, 0, Integer.MAX_VALUE);
        spectralWingsExists = builder
                .comment("When set to false the Spectral Wings enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.spectralWings", true);

        elementalEfficiencyChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Elemental Efficiency enchantment.\n" +
                        "[default=900]")
                .defineInRange("chargeCosts.elementalEfficiency", 900, 0, Integer.MAX_VALUE);
        elementalEfficiencyExists = builder
                .comment("When set to false the Elemental Efficiency enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.elementalEfficiency", true);

        witherSkullChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Wither Skull enchantment.\n" +
                        "[default=150]")
                .defineInRange("chargeCosts.witherSkull", 150, 0, Integer.MAX_VALUE);
        witherSkullExists = builder
                .comment("When set to false the Wither Skull enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.witherSkull", true);

        healingRayChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Healing Ray enchantment.\n" +
                        "[default=120]")
                .defineInRange("chargeCosts.healingRay", 120, 0, Integer.MAX_VALUE);
        healingRayExists = builder
                .comment("When set to false the Healing Ray enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.healingRay", true);

        gardenGrowthChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Garden Growth enchantment.\n" +
                        "[default=60]")
                .defineInRange("chargeCosts.gardenGrowth", 60, 0, Integer.MAX_VALUE);
        gardenGrowthExists = builder
                .comment("When set to false the Garden Growth enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.gardenGrowth", true);

        biteChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Bite enchantment.\n" +
                        "[default=100]")
                .defineInRange("chargeCosts.bite", 100, 0, Integer.MAX_VALUE);
        biteExists = builder
                .comment("When set to false the Bite enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.bite", true);

        ringOfFangsChargeCost = builder
                .comment("Cooldown time in ticks (1 second = 20 ticks) for the Ring of Fangs enchantment.\n" +
                        "[default=200]")
                .defineInRange("chargeCosts.bite", 200, 0, Integer.MAX_VALUE);
        ringOfFangsExists = builder
                .comment("When set to false the Ring of Fangs enchantment will not be in the game.\n" +
                        "[default=true]")
                .define("exists.ringOfFangs", true);

        chargeMaxStarting = builder
                .comment("Maximum amount of charge you spawn with.\n" +
                        "[default=200]")
                .defineInRange("charge.maxStarting", 200, 0, Integer.MAX_VALUE);
        chargePotionsExist = builder
                .comment("Determines if you can create potions that affect your charge.")
                .define("charge.chargePotions", true);

        staffKnockback = builder
                .comment("""
                        Determines how far entities are knocked back after a melee staff hit.
                        Setting this to 1.0 disables knockback increase.
                        [default=2.0]""")
                .defineInRange("staff.knockback", 2.0, 1.0, Double.MAX_VALUE);
        staffCooldown = builder
                .comment("""
                        Determines how long of a cooldown is needed between firing spells.
                        Setting this to 0 disables cooldowns.
                        [default=30]""")
                .defineInRange("staff.cooldown", 30, 0, Integer.MAX_VALUE);

        elementsAllowedEfficiency = builder
                .comment("""
                        Determines which elements have a chance to cost less charge when under the Elemental Efficiency effect.
                        [default="NATURE", "FIRE", "ENDER", "WATER", "ICE", "LIGHTNING", "PURE", "UNDEAD", "SUMMON"]""")
                .define("miscellaneous.elementsAllowedEfficiency", EElement.NONE.getEfficiencyAllowedDefault());
    }
}
