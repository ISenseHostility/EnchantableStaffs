package com.isensehostility.enchantable_staffs;

import com.isensehostility.enchantable_staffs.init.ItemRegistry;
import com.isensehostility.enchantable_staffs.items.Staff;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("enchantable_staffs")
public class EnchantableStaffs
{
    public static final String MODID = "enchantable_staffs";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final EnchantmentType STAFF = EnchantmentType.create( "staff", (Item item )->item instanceof Staff);

    public static ResourceLocation locate(String name) {
        return new ResourceLocation(MODID, name);
    }

    public EnchantableStaffs() {
        ItemRegistry.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
