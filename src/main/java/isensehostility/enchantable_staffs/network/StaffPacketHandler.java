package isensehostility.enchantable_staffs.network;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.util.ModUtils;
import isensehostility.enchantable_staffs.util.ModifierUtils;
import isensehostility.enchantable_staffs.util.NBTUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
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
                .consumerMainThread(ChargeUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(MaxChargeUpdatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(MaxChargeUpdatePacket::encode)
                .decoder(MaxChargeUpdatePacket::new)
                .consumerMainThread(MaxChargeUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(ElementalEfficiencyUpdatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ElementalEfficiencyUpdatePacket::encode)
                .decoder(ElementalEfficiencyUpdatePacket::new)
                .consumerMainThread(ElementalEfficiencyUpdatePacket::handle)
                .add();
        INSTANCE.messageBuilder(ChargeAddPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ChargeAddPacket::encode)
                .decoder(ChargeAddPacket::new)
                .consumerMainThread(ChargeAddPacket::handle)
                .add();
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleChargeUpdate(ChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        NBTUtils.setCharge(Minecraft.getInstance().player, msg.charge);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleMaxChargeUpdate(MaxChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        NBTUtils.setMaxCharge(Minecraft.getInstance().player, msg.maxCharge);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handleElementalEfficiencyUpdate(ElementalEfficiencyUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        NBTUtils.setElementalEfficiencyById(Minecraft.getInstance().player, msg.elementId);
    }

    public static void handleChargeAdd(ChargeAddPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer sender = ctx.get().getSender();
        Player player = sender.level.getPlayerByUUID(msg.uuid);
        ModifierUtils.activeChargeModifierLogic(msg.stack, player);
    }
}
