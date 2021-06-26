package com.isensehostility.enchantable_staffs.init;

import com.isensehostility.enchantable_staffs.EnchantableStaffs;
import com.isensehostility.enchantable_staffs.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EnchantableStaffs.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }

    // Items
    public static final RegistryObject<GoldStaff> GOLD_STAFF = ITEMS.register("gold_staff", ()-> new GoldStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(120)));
    public static final RegistryObject<IronStaff> IRON_STAFF = ITEMS.register("iron_staff", ()-> new IronStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(80)));
    public static final RegistryObject<ReinforcedGoldStaff> REINFORCED_GOLD_STAFF = ITEMS.register("reinforced_gold_staff", ()-> new ReinforcedGoldStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(180)));
    public static final RegistryObject<ReinforcedIronStaff> REINFORCED_IRON_STAFF = ITEMS.register("reinforced_iron_staff", ()-> new ReinforcedIronStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(140)));
    public static final RegistryObject<NetheriteStaff> NETHERITE_STAFF = ITEMS.register("netherite_staff", ()-> new NetheriteStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(260)));
    public static final RegistryObject<PlatedNetheriteStaff> PLATED_NETHERITE_STAFF = ITEMS.register("plated_netherite_staff", ()-> new PlatedNetheriteStaff(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).defaultMaxDamage(280)));
}
