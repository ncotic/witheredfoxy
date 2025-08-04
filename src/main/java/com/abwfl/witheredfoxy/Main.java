package com.abwfl.witheredfoxy;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;

import java.util.Random;

@Mod(value = Main.MODID, dist = Dist.CLIENT)
public class Main {
    public static final String MODID = "witheredfoxy";
    private static Minecraft game = null;
    private static Random random = new Random();

    private static int tickCounter;

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);

    public static final Holder<SoundEvent> JUMPSCARE_SOUND = SOUND_EVENTS.register(
            "jumpscare",
            registryName -> SoundEvent.createFixedRangeEvent(registryName, 16f)
    );

    public Main(IEventBus modEventBus) {
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

    public static void chanceCheck() {
        if (random.nextInt(10000) == 0) {
            int animatronic = random.nextInt(100);
            game.getSoundManager().play(SimpleSoundInstance.forUI(JUMPSCARE_SOUND.value(), 1f, 1f));
            if (animatronic < 25) {
                game.setScreen(new JumpscareScreen(1));
            } else {
                game.setScreen(new JumpscareScreen(0));
            }
        }
    }
}
