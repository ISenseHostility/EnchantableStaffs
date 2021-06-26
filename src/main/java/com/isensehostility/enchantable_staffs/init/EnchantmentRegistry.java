package com.isensehostility.enchantable_staffs.init;

import com.isensehostility.enchantable_staffs.EnchantableStaffs;
import com.isensehostility.enchantable_staffs.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = EnchantableStaffs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnchantmentRegistry {
    public static final Enchantment LIGHTNING_BOLT = new LightningBolt(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND);
    public static final Enchantment LIGHTNING_CIRCLE = new LightningCircle(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND);
    public static final Enchantment FIREBALL = new Fireball(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND);
    public static final Enchantment EXPLOSION = new Explosion(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND);
    public static final Enchantment CREATE_WATER = new CreateWater(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND);
    public static final Enchantment NECROMANCY = new Necromancy(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND);
    public static final Enchantment WARP = new Warp(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND);
    public static final Enchantment TELEPORT = new Teleport(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND);
    public static final Enchantment WEATHER_ALTERATION = new WeatherAlteration(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND);
    public static final Enchantment RING_OF_FIRE = new RingOfFire(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND);

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        register(event.getRegistry(), "lightning_bolt", LIGHTNING_BOLT);
        register(event.getRegistry(), "lightning_circle", LIGHTNING_CIRCLE);
        register(event.getRegistry(), "fireball", FIREBALL);
        register(event.getRegistry(), "explosion", EXPLOSION);
        register(event.getRegistry(), "create_water", CREATE_WATER);
        register(event.getRegistry(), "necromancy", NECROMANCY);
        register(event.getRegistry(), "warp", WARP);
        register(event.getRegistry(), "teleport", TELEPORT);
        register(event.getRegistry(), "weather_alteration", WEATHER_ALTERATION);
        register(event.getRegistry(), "ring_of_fire", RING_OF_FIRE);
    }

    private static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistry<T> registry, String name, T object) {
        object.setRegistryName(EnchantableStaffs.locate(name));
        registry.register(object);
    }
}