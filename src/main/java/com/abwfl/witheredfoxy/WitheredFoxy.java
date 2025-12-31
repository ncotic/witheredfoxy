package com.abwfl.witheredfoxy;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Random;
import java.util.function.Supplier;

@Mod(WitheredFoxy.MODID)
public class WitheredFoxy {
    public static final String MODID = "witheredfoxy";

    private static final Identifier[] WFOXY_FRAMES = {
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy0.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy1.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy2.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy3.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy4.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy5.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy6.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy7.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy8.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy9.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy10.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy11.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy12.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/wfoxy13.png")
    };
    private static final Identifier[] MANGLE_FRAMES = {
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle0.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle1.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle2.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle3.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle4.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle5.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle6.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle7.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle8.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle9.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle10.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle11.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle12.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle13.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle14.png"),
            Identifier.fromNamespaceAndPath("witheredfoxy", "textures/gui/mangle15.png")
    };
    public static boolean isPlaying;
    public static Identifier[] FRAMES;

    private static Minecraft game = null;
    private static final Random random = new Random();
    private static int tickCounter;

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);

    public static final Supplier<SoundEvent> JUMPSCARE_SOUND = SOUND_EVENTS.register(
            "jumpscare",
            registryName -> SoundEvent.createFixedRangeEvent(registryName, 16f)
    );

    public WitheredFoxy(IEventBus modEventBus, ModContainer modContainer) {
        SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
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
