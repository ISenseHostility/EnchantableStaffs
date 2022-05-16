package isensehostility.enchantable_staffs.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargeUpdatePacket {
    public int charge;

    public ChargeUpdatePacket(int charge) {
        this.charge = charge;
    }

    public ChargeUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(charge);
    }

    public static void handle(ChargeUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StaffPacketHandler.handleChargeUpdate(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }
}
