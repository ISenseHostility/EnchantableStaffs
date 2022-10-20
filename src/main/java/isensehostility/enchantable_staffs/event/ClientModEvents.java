package isensehostility.enchantable_staffs.event;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.item.Staff;
import isensehostility.enchantable_staffs.item.StaffItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EnchantableStaffs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(StaffItems.IRON_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
            ItemProperties.register(StaffItems.GOLD_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
            ItemProperties.register(StaffItems.NETHERITE_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
            ItemProperties.register(StaffItems.REINFORCED_IRON_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
            ItemProperties.register(StaffItems.REINFORCED_GOLD_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
            ItemProperties.register(StaffItems.PLATED_NETHERITE_STAFF.get(),
                    new ResourceLocation(EnchantableStaffs.MODID, "modifier"), (stack, level, entity, number) -> {
                        if (Staff.hasModifier(stack)) {
                            return Staff.getModifier(stack).getId();
                        } else {
                            return 0;
                        }
                    });
        });
    }
}
