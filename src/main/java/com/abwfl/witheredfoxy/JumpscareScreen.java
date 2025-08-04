package com.abwfl.witheredfoxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class JumpscareScreen extends Screen {
    private static final ResourceLocation[] WFOXY_FRAMES = {
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy0.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy1.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy2.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy3.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy4.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy5.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy6.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy7.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy8.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy9.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy10.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy11.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy12.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy13.png")
    };
    private static final ResourceLocation[] MANGLE_FRAMES = {
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle0.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle1.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle2.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle3.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle4.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle5.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle6.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle7.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle8.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle9.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle10.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle11.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle12.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle13.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle14.png"),
            ResourceLocation.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle15.png")
    };
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private ResourceLocation[] FRAMES;

    public JumpscareScreen(int animatronic) {
        super(Component.literal("Jumpscare"));
        if (animatronic == 1) {
            FRAMES = MANGLE_FRAMES;
        } else {
            FRAMES = WFOXY_FRAMES;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        long time = System.currentTimeMillis();
        if (time - lastFrameTime > 33) {
            if (currentFrame < FRAMES.length - 1) {
                currentFrame += 1;
                lastFrameTime = time;
            } else {
                super.onClose();
                super.removed();
            }
        }

        guiGraphics.blit(
                FRAMES[currentFrame],
                0, 0,
                width, height,
                0, 1,
                0, 1
        );
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
