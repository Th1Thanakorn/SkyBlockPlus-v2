package com.thana.skyblockplus.util.skyblock;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class ItemUtil {

    public static String getSkyBlockItemId(ItemStack itemStack) {
        if (itemStack.isEmpty()) return "";

        CustomData data = itemStack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return "";
        CompoundTag tag = data.copyTag();
        if (!tag.contains("ExtraAttributes")) return null;

        return tag.getCompound("ExtraAttributes").get().getString("id").orElse("");
    }
}
