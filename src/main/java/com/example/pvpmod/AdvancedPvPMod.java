package com.example.pvpmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AdvancedPvPMod implements ClientModInitializer {
    public static boolean critPacketEnabled = true;
    public static boolean smoothRotationsEnabled = true;
    public static boolean cpsRandomizerEnabled = true;
    
    private static KeyBinding toggleMenuKey;
    private static KeyBinding toggleModKey;

    @Override
    public void onInitializeClient() {
        toggleMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.advancedpvp.menu",
                InputUtil.Type.KEY_SYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.advancedpvp"
        ));

        toggleModKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.advancedpvp.toggle",
                InputUtil.Type.KEY_SYM,
                GLFW.GLFW_KEY_R,
                "category.advancedpvp"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleMenuKey.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new PvPConfigScreen());
                }
            }
            
            if (toggleModKey.wasPressed()) {
                critPacketEnabled = !critPacketEnabled;
                smoothRotationsEnabled = !smoothRotationsEnabled;
                cpsRandomizerEnabled = !cpsRandomizerEnabled;
            }

            if (cpsRandomizerEnabled) {
                CombatManager.tick();
            }
        });
    }
}
