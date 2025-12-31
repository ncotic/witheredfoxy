package com.abwfl.witheredfoxy;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;

import java.util.Random;
import java.util.function.Supplier;

@Mod(WitheredFoxy.MODID)
public class WitheredFoxy {
    public static final String MODID = "witheredfoxy";

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
    private boolean isPlaying;

    private static Minecraft game = null;
    private static final Random random = new Random();
    private static int tickCounter;

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final Supplier<SoundEvent> JUMPSCARE_SOUND = SOUND_EVENTS.register(
            "jumpscare",
            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath("witheredfoxy", "jumpscare"), 16f)
    );

    public WitheredFoxy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SOUND_EVENTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (isPlaying && event.phase == TickEvent.Phase.END) {
            long time = System.currentTimeMillis();
            if (time - lastFrameTime > 33) {
                if (currentFrame < FRAMES.length - 1) {
                    currentFrame += 1;
                    lastFrameTime = time;
                } else {
                    currentFrame = 0;
                    isPlaying = false;
                }
            }

            Window window = game.getWindow();
            int screenWidth = window.getGuiScaledWidth();
            int screenHeight = window.getGuiScaledHeight();

            Matrix4f oldProjection = RenderSystem.getProjectionMatrix();

            Matrix4f ortho = new Matrix4f().setOrtho2D(
                    0.0f, (float) screenWidth,
                    (float) screenHeight, 0.0f
            );
            RenderSystem.setProjectionMatrix(ortho, VertexSorting.ORTHOGRAPHIC_Z);

            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            RenderSystem.applyModelViewMatrix();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, FRAMES[currentFrame]);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            buffer.vertex(0, 0, 0).uv(0.0f, 0.0f).endVertex();
            buffer.vertex(0, screenHeight, 0).uv(0.0f, 1.0f).endVertex();
            buffer.vertex(screenWidth, screenHeight, 0).uv(1.0f, 1.0f).endVertex();
            buffer.vertex(screenWidth, 0, 0).uv(1.0f, 0.0f).endVertex();
            tesselator.end();

            RenderSystem.disableBlend();

            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.setProjectionMatrix(oldProjection, VertexSorting.ORTHOGRAPHIC_Z);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (game == null) {
            game = Minecraft.getInstance();
        }

        tickCounter++;

        if (tickCounter >= 20) {
            tickCounter = 0;
            chanceCheck();
        }
    }

    public void chanceCheck() {
        if (random.nextInt(10000) == 0) {
            int animatronic = random.nextInt(100);
            game.getSoundManager().play(SimpleSoundInstance.forUI(JUMPSCARE_SOUND.get(), 1f, 1f));
            if (animatronic < 25) {
                FRAMES = MANGLE_FRAMES;
            } else {
                FRAMES = WFOXY_FRAMES;
            }
            isPlaying = true;
        }
    }
}
