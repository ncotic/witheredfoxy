package com.abwfl.witheredfoxy;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.Random;

public class Witheredfoxy implements ModInitializer {
    public static final String MOD_ID = "witheredfoxy";

    private static final Identifier[] WFOXY_FRAMES = {
            new Identifier("witheredfoxy", "textures/gui/wfoxy0.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy1.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy2.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy3.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy4.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy5.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy6.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy7.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy8.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy9.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy10.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy11.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy12.png"),
            new Identifier("witheredfoxy", "textures/gui/wfoxy13.png")
    };
    private static final Identifier[] MANGLE_FRAMES = {
            new Identifier("witheredfoxy", "textures/gui/mangle0.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle1.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle2.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle3.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle4.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle5.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle6.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle7.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle8.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle9.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle10.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle11.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle12.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle13.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle14.png"),
            new Identifier("witheredfoxy", "textures/gui/mangle15.png")
    };
    public static boolean isPlaying;
    public static Identifier[] FRAMES;

    private static MinecraftClient game = null;
    private static final Random random = new Random();
    private static int tickCounter;

    private static SoundEvent JUMPSCARE_EVENT = SoundEvent.of(new Identifier(MOD_ID, "jumpscare"));

    @Override
    public void onInitialize() {
        Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "jumpscare"),
                JUMPSCARE_EVENT);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (game == null) {
                game = MinecraftClient.getInstance();
            }

            tickCounter++;

            if (tickCounter >= 20) {
                tickCounter = 0;
                chanceCheck();
            }
        });
    }

    public void chanceCheck() {
        if (random.nextInt(10000) == 0) {
            int animatronic = random.nextInt(100);
            game.getSoundManager().play(PositionedSoundInstance.master(JUMPSCARE_EVENT, 1.0F));
            if (animatronic < 25) {
                FRAMES = MANGLE_FRAMES;
            } else {
                FRAMES = WFOXY_FRAMES;
            }
            isPlaying = true;
        }
    }
}
