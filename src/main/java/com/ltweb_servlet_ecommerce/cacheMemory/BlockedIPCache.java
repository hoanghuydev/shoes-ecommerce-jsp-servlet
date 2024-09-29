package com.ltweb_servlet_ecommerce.cacheMemory;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BlockedIPCache {
    private static BlockedIPCache instance = null;
    private Map<String, Long> blockedIPs = new ConcurrentHashMap<>();
    private static final long BLOCK_DURATION = 5 * 60 * 1000; // 5 minute

    private BlockedIPCache() {

    }

    public static BlockedIPCache getInstance() {
        if (instance == null) {
            synchronized (BlockedIPCache.class) {
                if (instance == null) {
                    instance = new BlockedIPCache();
                }
            }
        }
        return instance;
    }

    public synchronized boolean isBlocked(String ip) {
        Long blockTime = blockedIPs.get(ip);
        if (blockTime != null && System.currentTimeMillis() - blockTime < BLOCK_DURATION) {
            return true;
        } else {
            // Remove IP from blocked list after block duration
            blockedIPs.remove(ip);
            return false;
        }
    }

    public synchronized void blockIP(String ip) {
        blockedIPs.put(ip, System.currentTimeMillis());
    }

    public synchronized Runnable cleanupExpiredBlocks() {
        for (String ip : blockedIPs.keySet()) {
            if (System.currentTimeMillis() - blockedIPs.get(ip) >= BLOCK_DURATION) {
                blockedIPs.remove(ip);
            }
        }
        return null;
    }


    public void printBlockedIPs() {
        blockedIPs.forEach((k, v) -> System.out.printf("ip: %s, blocked time: %s\n", k, new Timestamp(v).toString()));
    }
}
