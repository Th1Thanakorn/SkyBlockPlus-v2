package com.thana.skyblockplus.util.skyblock.api;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BazaarAPIUtil {

    private static final Gson GSON = new Gson();
    public static volatile long lastUpdated = -1L;
    public static volatile Map<String, BazaarAPI.Data> BAZAAR_DATA = new HashMap<>();

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final AtomicBoolean refreshing = new AtomicBoolean(false);

    public static void getBazaarData() {
        if (!refreshing.compareAndSet(false, true)) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                Map<String, BazaarAPI.Data> updated = new HashMap<>();
                URL url = URI.create("https://api.hypixel.net/skyblock/bazaar").toURL();
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

                    lastUpdated = bazaar.getLastUpdated();
                    BAZAAR_DATA = Map.copyOf(updated);
                }
                finally {
                    connection.disconnect();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            finally {
                refreshing.set(false);
            }
        }, EXECUTOR);
    }
}
