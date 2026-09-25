package com.example.pvpmod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CombatManager {
    private static final Random RANDOM = new Random();
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
    
    private static long lastAttackTime = 0;
    private static int nextTargetCps = 10;
    private static int tickCounter = 0;

    public static void tick() {
        tickCounter++;
        if (tickCounter >= 20) {
            nextTargetCps = 9 + RANDOM.nextInt(5);
            tickCounter = 0;
        }
    }

    public static boolean canAttack() {
        if (!AdvancedPvPMod.cpsRandomizerEnabled) return true;
        
        long currentTime = System.currentTimeMillis();
        long minInterval = 1000L / nextTargetCps;
        
        if (currentTime - lastAttackTime < minInterval) {
            return false;
        }
        
        lastAttackTime = currentTime;
        return true;
    }

    public static void sendCriticalPackets(MinecraftClient client) {
        if (!AdvancedPvPMod.critPacketEnabled || client.player == null || client.getNetworkHandler() == null) {
            return;
        }

        long randomDelay = 20 + RANDOM.nextInt(31);

        SCHEDULER.schedule(() -> {
            if (client.player != null && client.getNetworkHandler() != null) {
                double x = client.player.getX();
                double y = client.player.getY();
                double z = client.player.getZ();

                client.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y + 0.000001, z, false, client.player.horizontalCollision
                ));

                client.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                        x, y, z, true, client.player.horizontalCollision
                ));
            }
        }, randomDelay, TimeUnit.MILLISECONDS);
    }
                           }
