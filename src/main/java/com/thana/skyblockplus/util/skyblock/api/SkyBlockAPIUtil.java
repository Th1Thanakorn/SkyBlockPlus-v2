package com.thana.skyblockplus.util.skyblock.api;

import com.google.gson.Gson;
import com.thana.skyblockplus.util.ThreadUtil;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SkyBlockAPIUtil {

    private static final Gson GSON = new Gson();
    public static volatile long bzLastUpdated = -1L;
    public static volatile Map<String, BazaarAPI.Data> BAZAAR_DATA = new HashMap<>();
    public static volatile Map<String, ItemAPI.Item> ITEM_DATA = new HashMap<>();

    public static final String BAZAAR_URL = "https://api.hypixel.net/skyblock/bazaar";
    public static final String ITEM_URL = "https://api.hypixel.net/resources/skyblock/items";

    public static void getBazaarData() {
        ThreadUtil.runAsync(() -> {
            try {
                Map<String, BazaarAPI.Data> updated = new HashMap<>();
                URL url = URI.create(BAZAAR_URL).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(10000);

                try (InputStream input = connection.getInputStream()) {
                    BazaarAPI bazaar = GSON.fromJson(IOUtils.toString(input, StandardCharsets.UTF_8), BazaarAPI.class);
                    for (var entry : bazaar.getProducts().entrySet()) {
                        BazaarAPI.Product product = entry.getValue();
                        BazaarAPI.Status quickStatus = product.getQuickStatus();
                        BazaarAPI.Summary[] buy = product.getBuySummary();
                        BazaarAPI.Summary[] sell = product.getSellSummary();

                        double buyPrice = buy.length == 0 ? quickStatus.getBuyPrice() : buy[0].getPricePerUnit();
                        double sellPrice = sell.length == 0 ? quickStatus.getSellPrice() : sell[0].getPricePerUnit();
                        updated.put(entry.getKey(), new BazaarAPI.Data(buyPrice, sellPrice));
                    }

                    bzLastUpdated = bazaar.getLastUpdated();
                    BAZAAR_DATA = Map.copyOf(updated);
                }
                finally {
                    connection.disconnect();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void getItemData() {
        ThreadUtil.runAsync(() -> {
            try {
                Map<String, ItemAPI.Item> updated = new HashMap<>();
                URL url = URI.create(ITEM_URL).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(10000);

                try (InputStream input = connection.getInputStream()) {
                    ItemAPI itemAPI = GSON.fromJson(IOUtils.toString(input, StandardCharsets.UTF_8), ItemAPI.class);
                    for (ItemAPI.Item item : itemAPI.getItems()) {
                        updated.put(item.getId(), item);
                    }
                    ITEM_DATA = Map.copyOf(updated);
                }
                finally {
                    connection.disconnect();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
