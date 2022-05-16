package isensehostility.enchantable_staffs.event;

import isensehostility.enchantable_staffs.EnchantableStaffs;
import isensehostility.enchantable_staffs.item.Staff;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static isensehostility.enchantable_staffs.StaffUtils.*;

@Mod.EventBusSubscriber(modid = EnchantableStaffs.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvents {

    @SubscribeEvent
    public static void onOverlaysRendered(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof Staff || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof Staff) {
                String maxCharge = String.valueOf(getMaxCharge(player));
                String currentCharge = String.valueOf(getCharge(player));
                String chargeText = assignColor(player) + "Charge: " + currentCharge + "/" + maxCharge;

                player.displayClientMessage(new TextComponent(chargeText), true);
            }
        }
    }
}
