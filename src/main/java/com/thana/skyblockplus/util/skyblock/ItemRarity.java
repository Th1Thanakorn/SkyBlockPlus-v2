package com.thana.skyblockplus.util.skyblock;

public enum ItemRarity {
    COMMON(16777215),
    UNCOMMON(5635925),
    RARE(4561919),
    EPIC(10696174),
    LEGENDARY(16748544),
    MYTHIC(16733695),
    DIVINE(5636095),
    SPECIAL(16733525),
    VERY_SPECIAL(16733525),
    ULTIMATE(13709864);

    private final int color;

    ItemRarity(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public static ItemRarity fromName(String name) {
        for (ItemRarity rarity : values()) {
            if (rarity.name().equals(name)) {
                return rarity;
            }
        }
        return ItemRarity.COMMON;
    }
}
