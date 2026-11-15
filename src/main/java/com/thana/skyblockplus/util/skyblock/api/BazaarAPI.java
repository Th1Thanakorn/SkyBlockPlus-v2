package com.thana.skyblockplus.util.skyblock.api;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BazaarAPI {

    private final long lastUpdated;
    private final Map<String, Product> products;

    public BazaarAPI(long lastUpdated, Map<String, Product> products) {
        this.lastUpdated = lastUpdated;
        this.products = products;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public static class Product {
        @SerializedName("product_id")
        private final String productId;
        @SerializedName("sell_summary")
        private final Summary[] sellSummary;
        @SerializedName("buy_summary")
        private final Summary[] buySummary;
        @SerializedName("quick_status")
        private final Status quickStatus;

        public Product(String productId, Summary[] sellSummary, Summary[] buySummary, Status quickStatus) {
            this.productId = productId;
            this.sellSummary = sellSummary;
            this.buySummary = buySummary;
            this.quickStatus = quickStatus;
        }

        public Status getQuickStatus() {
            return quickStatus;
        }

        public String getProductId() {
            return productId;
        }

        public Summary[] getSellSummary() {
            return sellSummary;
        }

        public Summary[] getBuySummary() {
            return buySummary;
        }
    }

    public static class Status {
        private final double sellPrice;
        private final double buyPrice;

        public Status(double sellPrice, double buyPrice) {
            this.sellPrice = sellPrice;
            this.buyPrice = buyPrice;
        }

        public double getSellPrice() {
            return sellPrice;
        }

        public double getBuyPrice() {
            return buyPrice;
        }
    }

    public static class Summary {
        private final int amount;
        private final double pricePerUnit;
        private final int orders;

        public Summary(int amount, double pricePerUnit, int orders) {
            this.amount = amount;
            this.pricePerUnit = pricePerUnit;
            this.orders = orders;
        }

        public int getAmount() {
            return amount;
        }

        public double getPricePerUnit() {
            return pricePerUnit;
        }

        public int getOrders() {
            return orders;
        }
    }

    public record Data(double buyPrice, double sellPrice) {
    }
}
