package com.thana.skyblockplus.util;

public class TimeUtils {

    public static String getRelativeTime(long timestamp) {
        long diff = System.currentTimeMillis() - timestamp;

        if (timestamp <= 0 || diff < 0) return "Unknown";
        long seconds = diff / 1000;
        if (seconds < 60) return seconds + " seconds ago";

        long minutes = seconds / 60;
        if (minutes < 60) return minutes + " minutes ago";

        long hours = minutes / 60;
        if (hours < 24) return hours + " hours ago";

        long days = hours / 24;
        return days + " days ago";
    }
}
