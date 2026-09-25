package com.example.pvpmod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class PvPConfigScreen extends Screen {

    public PvPConfigScreen() {
        super(Text.literal("Advanced PvP Settings"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2 - 100;
        int centerY = this.height / 2 - 50;

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Crit Packets: " + (AdvancedPvPMod.critPacketEnabled ? "ON" : "OFF")),
                button -> {
                    AdvancedPvPMod.critPacketEnabled = !AdvancedPvPMod.critPacketEnabled;
                    button.setMessage(Text.literal("Crit Packets: " + (AdvancedPvPMod.critPacketEnabled ? "ON" : "OFF")));
                })
                .dimensions(centerX, centerY, 200, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Smooth Rotations: " + (AdvancedPvPMod.smoothRotationsEnabled ? "ON" : "OFF")),
                button -> {
                    AdvancedPvPMod.smoothRotationsEnabled = !AdvancedPvPMod.smoothRotationsEnabled;
                    button.setMessage(Text.literal("Smooth Rotations: " + (AdvancedPvPMod.smoothRotationsEnabled ? "ON" : "OFF")));
                })
                .dimensions(centerX, centerY + 24, 200, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("CPS Randomizer: " + (AdvancedPvPMod.cpsRandomizerEnabled ? "ON" : "OFF")),
                button -> {
                    AdvancedPvPMod.cpsRandomizerEnabled = !AdvancedPvPMod.cpsRandomizerEnabled;
                    button.setMessage(Text.literal("CPS Randomizer: " + (AdvancedPvPMod.cpsRandomizerEnabled ? "ON" : "OFF")));
                })
                .dimensions(centerX, centerY + 48, 200, 20)
                .build());

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Yopish"),
                button -> this.close())
                .dimensions(centerX, centerY + 78, 200, 20)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 75, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
                              }
