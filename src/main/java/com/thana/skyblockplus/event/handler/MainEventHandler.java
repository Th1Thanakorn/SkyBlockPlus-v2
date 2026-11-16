package com.thana.skyblockplus.event.handler;

import com.mojang.blaze3d.platform.InputConstants;
import com.thana.skyblockplus.SkyBlockPlus;
import com.thana.skyblockplus.gui.widget.button.ItemButton;
import com.thana.skyblockplus.util.TimeUtils;
import com.thana.skyblockplus.util.skyblock.ItemUtil;
import com.thana.skyblockplus.util.skyblock.api.BazaarAPI;
import com.thana.skyblockplus.util.skyblock.api.SkyBlockAPIUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.text.DecimalFormat;

@EventBusSubscriber(modid = SkyBlockPlus.MODID, value = Dist.CLIENT)
public class MainEventHandler {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("#,##0.##");

    @SubscribeEvent
    public static void onInventoryLoad(ScreenEvent.Init.Post event) {
        if (SkyBlockEventHandler.isSkyBlock && event.getScreen() instanceof InventoryScreen screen) {
            for (GuiEventListener listener : event.getListenersList()) {
                if (listener instanceof RecipeBookTabButton) {
                    event.removeListener(listener);
                }
            }

            int left = screen.getLeftPos();
            int top = screen.getTopPos();
            ItemStack wardRobeItem = new ItemStack(Items.LEATHER_CHESTPLATE);
            wardRobeItem.set(DataComponents.DYED_COLOR, new DyedItemColor(8339378));

            event.addListener(new ItemButton(left-22, top, new ItemStack(Items.CRAFTING_TABLE), (_) -> Minecraft.getInstance().player.connection.sendChat("/craft")));
            event.addListener(new ItemButton(left-22, top+20, new ItemStack(Items.ENDER_CHEST), (_) -> Minecraft.getInstance().player.connection.sendChat("/echest")));
            event.addListener(new ItemButton(left-22, top+40, new ItemStack(Items.BONE), Component.literal("Pets"), (_) -> Minecraft.getInstance().player.connection.sendChat("/pets")));
            event.addListener(new ItemButton(left-22, top+60, wardRobeItem, Component.literal("Wardrobe"), (_) -> Minecraft.getInstance().player.connection.sendChat("/wardrobe")));
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (!SkyBlockEventHandler.isSkyBlock) return;
        int at = event.getToolTip().size();
        at--;
        if (Minecraft.getInstance().options.advancedItemTooltips) {
            at -= 2;
            if(event.getItemStack().isDamaged()) {
                at--;
            }
        }

        ItemStack stack = event.getItemStack();
//        if (InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)) {
//            event.getToolTip().add(at++, Component.literal(ItemUtil.getSkyBlockItemId(stack)));
//        }

        if (stack.isEmpty()) return;
        String itemId = ItemUtil.getSkyBlockItemId(stack);
        if (itemId == null || itemId.isEmpty()) return;
        BazaarAPI.Data data = SkyBlockAPIUtil.BAZAAR_DATA.get(itemId);
        if (data == null) return;

        if (InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)) {
            double buyStack = 64 * data.buyPrice();
            double sellStack = 64 * data.sellPrice();
            double buyCurrent = stack.getCount() * data.buyPrice();
            double sellCurrent = stack.getCount() * data.sellPrice();

            event.getToolTip().add(at++, Component.literal(ChatFormatting.GRAY + "Buy/Sell (Stack): ").append(Component.literal(PRICE_FORMAT.format(buyStack)).withStyle(ChatFormatting.GOLD)).append(Component.literal("/").withStyle(ChatFormatting.YELLOW)).append(Component.literal(PRICE_FORMAT.format(sellStack)).withStyle(ChatFormatting.GOLD)).append(Component.literal(" coins").withStyle(ChatFormatting.GRAY)));
            if (stack.getCount() > 1 && stack.getCount() < 64) {
                event.getToolTip().add(at++, Component.literal(ChatFormatting.GRAY + "Buy/Sell (Current): ").append(Component.literal(PRICE_FORMAT.format(buyCurrent)).withStyle(ChatFormatting.GOLD)).append(Component.literal("/").withStyle(ChatFormatting.YELLOW).append(Component.literal(PRICE_FORMAT.format(sellCurrent)).withStyle(ChatFormatting.GOLD)).append(Component.literal(" coins").withStyle(ChatFormatting.GRAY))));
            }
            event.getToolTip().add(at++, Component.literal(ChatFormatting.GRAY + "Buy/Sell (One): ").append(Component.literal(PRICE_FORMAT.format(data.buyPrice())).withStyle(ChatFormatting.GOLD)).append(Component.literal("/").withStyle(ChatFormatting.YELLOW)).append(Component.literal(PRICE_FORMAT.format(data.sellPrice())).withStyle(ChatFormatting.GOLD)).append(Component.literal(" coins").withStyle(ChatFormatting.GRAY)));
            event.getToolTip().add(at, Component.literal(ChatFormatting.GRAY +"Last Updated: " + ChatFormatting.WHITE + TimeUtils.getRelativeTime(SkyBlockAPIUtil.bzLastUpdated)));
        }
        else {
            event.getToolTip().add(at, Component.literal(ChatFormatting.GRAY +"Press <SHIFT> to view Bazaar Buy/Sell"));
        }
    }
}
