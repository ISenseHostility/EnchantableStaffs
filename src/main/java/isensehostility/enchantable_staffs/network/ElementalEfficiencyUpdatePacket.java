package isensehostility.enchantable_staffs.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElementalEfficiencyUpdatePacket {
    public int elementId;

    public ElementalEfficiencyUpdatePacket(int elementId) {
        this.elementId = elementId;
    }

    public ElementalEfficiencyUpdatePacket(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(elementId);
    }

    public static void handle(ElementalEfficiencyUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> StaffPacketHandler.handleElementalEfficiencyUpdate(msg, ctx))
        );
        ctx.get().setPacketHandled(true);
    }
}
