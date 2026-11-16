package com.thana.skyblockplus.util.skyblock.api;

import com.thana.skyblockplus.util.skyblock.ItemRarity;

public class ItemAPI {

    private final long lastUpdated;
    private final Item[] items;

    public ItemAPI(long lastUpdated, Item[] items) {
        this.lastUpdated = lastUpdated;
        this.items = items;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Item[] getItems() {
        return items;
    }

    public static class Item {
        private final String id;
        private final String tier;

        public Item(String id, String tier) {
            this.id = id;
            this.tier = tier;
        }

        public String getId() {
            return id;
        }

        public ItemRarity getRarity() {
            return ItemRarity.fromName(this.tier);
        }
    }
}
