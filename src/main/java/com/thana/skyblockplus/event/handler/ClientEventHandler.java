package com.thana.skyblockplus.event.handler;

import com.thana.skyblockplus.SkyBlockPlus;
import com.thana.skyblockplus.keys.KeyMappings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = SkyBlockPlus.MODID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onKeyDown(ClientTickEvent.Post event) {
        if (SkyBlockEventHandler.isSkyBlock) {
            while (KeyMappings.PRESS_HOME.get().consumeClick()) {
                Minecraft.getInstance().player.connection.sendChat("/warp home");
            }
            while (KeyMappings.PRESS_HUB.get().consumeClick()) {
                Minecraft.getInstance().player.connection.sendChat("/warp hub");
            }
            while (KeyMappings.PRESS_ENDERCHEST.get().consumeClick()) {
                Minecraft.getInstance().player.connection.sendChat("/echest");
            }
            while (KeyMappings.PRESS_PETS.get().consumeClick()) {
                Minecraft.getInstance().player.connection.sendChat("/pets");
            }
            while (KeyMappings.PRESS_WARDROBE.get().consumeClick()) {
                Minecraft.getInstance().player.connection.sendChat("/wardrobe");
            }
        }
    }
}
