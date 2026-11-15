package com.thana.skyblockplus.keys;

import com.mojang.blaze3d.platform.InputConstants;
import com.thana.skyblockplus.SkyBlockPlus;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;

@EventBusSubscriber(modid = SkyBlockPlus.MODID, value = Dist.CLIENT)
public class KeyMappings {

    public static final KeyMapping.Category CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath("skyblockplus", "general"));

    public static final KeyMapping KEY_HOME = new KeyMapping("key.skyblockplus.home", InputConstants.Type.KEYBOARD, InputConstants.KEY_ADD, CATEGORY);
    public static final KeyMapping KEY_HUB = new KeyMapping("key.skyblockplus.hub", InputConstants.Type.KEYBOARD, InputConstants.KEY_MINUS, CATEGORY);
    public static final KeyMapping KEY_ENDERCHEST = new KeyMapping("key.skyblockplus.enderchest", InputConstants.Type.KEYBOARD, InputConstants.KEY_T, CATEGORY);
    public static final KeyMapping KEY_WARDROBE = new KeyMapping("key.skyblockplus.wardrobe", InputConstants.Type.KEYBOARD, InputConstants.KEY_Y, CATEGORY);
    public static final KeyMapping KEY_PETS = new KeyMapping("key.skyblockplus.pets", InputConstants.Type.KEYBOARD, InputConstants.KEY_U, CATEGORY);

    public static final Lazy<KeyMapping> PRESS_HOME = Lazy.of(()->KEY_HOME);
    public static final Lazy<KeyMapping> PRESS_HUB = Lazy.of(()->KEY_HUB);
    public static final Lazy<KeyMapping> PRESS_ENDERCHEST = Lazy.of(()->KEY_ENDERCHEST);
    public static final Lazy<KeyMapping> PRESS_WARDROBE = Lazy.of(()->KEY_WARDROBE);
    public static final Lazy<KeyMapping> PRESS_PETS = Lazy.of(()->KEY_PETS);

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(PRESS_HOME.get());
        event.register(PRESS_HUB.get());
        event.register(PRESS_ENDERCHEST.get());
        event.register(PRESS_WARDROBE.get());
        event.register(PRESS_PETS.get());
    }
}
