package com.example.pvpmod;

import net.minecraft.client.network.ClientPlayerEntity;
import java.util.Random;

public class RotationManager {
    private static final Random RANDOM = new Random();
    private static float targetYaw = 0.0f;
    private static float targetPitch = 0.0f;

    public static void smoothLookAt(ClientPlayerEntity player, double targetX, double targetY, double targetZ) {
        if (!AdvancedPvPMod.smoothRotationsEnabled) return;

        double diffX = targetX - player.getX();
        double diffY = targetY - (player.getY() + player.getEyeHeight());
        double diffZ = targetZ - player.getZ();

        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        
        float calculatedYaw = (float) (Math.atan2(diffZ, diffX) * (180.0 / Math.PI)) - 90.0f;
        float calculatedPitch = (float) (-(Math.atan2(diffY, dist) * (180.0 / Math.PI)));

        float yawOffset = (float) (RANDOM.nextGaussian() * 1.5);
        float pitchOffset = (float) (RANDOM.nextGaussian() * 1.0);

        targetYaw = player.getYaw() + wrapAngle(calculatedYaw - player.getYaw() + yawOffset);
        targetPitch = Math.max(-90.0f, Math.min(90.0f, player.getPitch() + (calculatedPitch - player.getPitch()) + pitchOffset));

        float currentYaw = player.getYaw();
        float yawDiff = wrapAngle(targetYaw - currentYaw);
        if (Math.abs(yawDiff) <= 60.0f) {
            player.setYaw(currentYaw + yawDiff * 0.3f);
            player.setPitch(player.getPitch() + (targetPitch - player.getPitch()) * 0.3f);
        }
    }

    private static float wrapAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) angle -= 360.0f;
        if (angle < -180.0f) angle += 360.0f;
        return angle;
    }
  }
