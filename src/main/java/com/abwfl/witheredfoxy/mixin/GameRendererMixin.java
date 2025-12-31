package com.abwfl.witheredfoxy.mixin;

import com.abwfl.witheredfoxy.WitheredFoxy;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique
    private int witheredFoxy$currentFrame = 0;
    @Unique
    private long witheredFoxy$lastFrameTime = 0;

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/render/GuiRenderer;render(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"))
    public void onRenderEnd(CallbackInfo ci, @Local GuiGraphics guigraphics) {
        if (WitheredFoxy.isPlaying) {
            System.out.println("RUN");
            long time = System.currentTimeMillis();
            if (time - witheredFoxy$lastFrameTime > 33) {
                if (witheredFoxy$currentFrame < WitheredFoxy.FRAMES.length - 1) {
                    witheredFoxy$currentFrame += 1;
                    witheredFoxy$lastFrameTime = time;
                } else {
                    witheredFoxy$currentFrame = 0;
                    WitheredFoxy.isPlaying = false;
                }
            }

            Window window = this.minecraft.getWindow();
            int screenWidth = window.getGuiScaledWidth();
            int screenHeight = window.getGuiScaledHeight();

            guigraphics.blit(
                    WitheredFoxy.FRAMES[witheredFoxy$currentFrame],
                    0, 0,
                    screenWidth, screenHeight,
                    0, 1,
                    0, 1);
        }
    }
}

