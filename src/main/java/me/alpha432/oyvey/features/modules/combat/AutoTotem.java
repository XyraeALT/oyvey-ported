package client.module.combat;

import client.module.Category;
import client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private int healthThreshold = 10; // half hearts

    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;
        if (mc.currentScreen != null) return; // don't interfere with GUIs

        PlayerInventory inv = mc.player.getInventory();

        // Check if offhand already has totem
        ItemStack offhand = inv.offHand.get(0);
        if (offhand.getItem() == Items.TOTEM_OF_UNDYING) return;

        // Check health
        float health = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        if (health > healthThreshold) return;

        // Find totem in hotbar or inventory
        int totemSlot = -1;
        for (int i = 0; i < inv.size(); i++) {
            if (inv.getStack(i).getItem() == Items.TOTEM_OF_UNDYING) {
                totemSlot = i;
                break;
            }
        }

        if (totemSlot == -1) return;

        // Swap totem to offhand
        // If in hotbar (0-8), use swap key (F) simulation, else move to hotbar first
        if (totemSlot < 9) {
            // Swap hotbar slot with offhand (slot 40)
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, 
                totemSlot < 9 ? 36 + totemSlot : totemSlot, 40, SlotActionType.SWAP, mc.player);
        } else {
            // Move totem from inventory to first empty hotbar slot
            int emptyHotbar = -1;
            for (int i = 0; i < 9; i++) {
                if (inv.getStack(i).isEmpty()) {
                    emptyHotbar = i;
                    break;
                }
            }
            if (emptyHotbar != -1) {
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId,
                    totemSlot, emptyHotbar, SlotActionType.QUICK_MOVE, mc.player);
            }
        }
    }
}
