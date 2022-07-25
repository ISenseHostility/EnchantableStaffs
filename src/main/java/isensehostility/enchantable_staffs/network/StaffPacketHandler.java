package isensehostility.enchantable_staffs.network;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.StaffUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class StaffPacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(EnchantableStaffs.MODID,"main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void initialize() {
        int id = 0;
        INSTANCE.messageBuilder(ChargeUpdatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ChargeUpdatePacket::encode)
                .decoder(ChargeUpdatePacket::new)
                .consumer(ChargeUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(MaxChargeUpdatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(MaxChargeUpdatePacket::encode)
                .decoder(MaxChargeUpdatePacket::new)
                .consumer(MaxChargeUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(ElementalEfficiencyUpdatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ElementalEfficiencyUpdatePacket::encode)
                .decoder(ElementalEfficiencyUpdatePacket::new)
                .consumer(ElementalEfficiencyUpdatePacket::handle)
                .add();
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleChargeUpdate(ChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        StaffUtils.setCharge(Minecraft.getInstance().player, msg.charge);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleMaxChargeUpdate(MaxChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        StaffUtils.setMaxCharge(Minecraft.getInstance().player, msg.maxCharge);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleElementalEfficiencyUpdate(ElementalEfficiencyUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        StaffUtils.setElementalEfficiencyById(Minecraft.getInstance().player, msg.elementId);
    }
}
