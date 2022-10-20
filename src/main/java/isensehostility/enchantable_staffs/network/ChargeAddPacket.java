package isensehostility.enchantable_staffs.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChargeAddPacket {
    public UUID uuid;
    public ItemStack stack;

    public ChargeAddPacket(UUID uuid, ItemStack stack) {
        this.uuid = uuid;
        this.stack = stack;
    }

    public ChargeAddPacket(FriendlyByteBuf buffer) {
        this(buffer.readUUID(), buffer.readItem());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeItemStack(stack, false);
    }

    public static void handle(ChargeAddPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
            StaffPacketHandler.handleChargeAdd(msg, ctx)
        );
        ctx.get().setPacketHandled(true);
    }
}
