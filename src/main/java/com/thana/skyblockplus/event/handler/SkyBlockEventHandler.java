package com.thana.skyblockplus.event.handler;

import com.thana.skyblockplus.SkyBlockPlus;
import com.thana.skyblockplus.util.skyblock.api.SkyBlockAPIUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = SkyBlockPlus.MODID, value = Dist.CLIENT)
public class SkyBlockEventHandler {

    private static long lastBazaarRefresh = -1L;

    public static boolean isSkyBlock;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Objective objective = mc.level. getScoreboard().getDisplayObjective(DisplaySlot.SIDEBAR);
        if (objective == null) return;
        String title = ChatFormatting.stripFormatting(objective.getDisplayName().getString());
        isSkyBlock = title.contains("SKYBLOCK");

        long now = System.currentTimeMillis();
        if (isSkyBlock && now-lastBazaarRefresh >= 120_000L) {
            lastBazaarRefresh = now;
            SkyBlockAPIUtil.getBazaarData();
        }
        if (SkyBlockAPIUtil.ITEM_DATA.isEmpty()) {
            SkyBlockAPIUtil.getItemData();
        }
    }
}
