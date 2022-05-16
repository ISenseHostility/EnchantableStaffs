package isensehostility.enchantable_staffs.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaxChargeUpdatePacket {
    public int maxCharge;

    public MaxChargeUpdatePacket(int maxCharge) {
        this.maxCharge = maxCharge;
    }

    public MaxChargeUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(maxCharge);
    }

    public static void handle(MaxChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StaffPacketHandler.handleMaxChargeUpdate(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }
}
