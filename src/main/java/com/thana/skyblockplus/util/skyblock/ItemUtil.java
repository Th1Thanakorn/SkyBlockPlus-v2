package com.thana.skyblockplus.util.skyblock;

import com.thana.skyblockplus.util.skyblock.api.SkyBlockAPIUtil;
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
        return tag.getString("id").orElse("");
    }

    public static ItemRarity getItemRarity(ItemStack itemStack) {
        String itemId = getSkyBlockItemId(itemStack);
        if (itemId == null || itemId.isEmpty()) return ItemRarity.COMMON;

        if (SkyBlockAPIUtil.ITEM_DATA.containsKey(itemId)) {
            return SkyBlockAPIUtil.ITEM_DATA.get(itemId).getRarity();
        }
        else {
            return ItemRarity.COMMON;
        }
    }
}
