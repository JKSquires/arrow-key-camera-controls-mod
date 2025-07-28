package com.cameramod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class CameramodClient implements ClientModInitializer {
	private static final KeyBinding keyRight = new KeyBinding("Camera Right", InputUtil.Type.KEYSYM, 262, "key.categories.movement");
	private static final KeyBinding keyLeft = new KeyBinding("Camera Left", InputUtil.Type.KEYSYM, 263, "key.categories.movement");
	private static final KeyBinding keyUp = new KeyBinding("Camera Up", InputUtil.Type.KEYSYM, 265, "key.categories.movement");
	private static final KeyBinding keyDown = new KeyBinding("Camera Down", InputUtil.Type.KEYSYM, 264, "key.categories.movement");

	private float cameraSpeed = 2.0F;

	@Override
	public void onInitializeClient() {
		KeyBindingHelper.registerKeyBinding(keyRight);
		KeyBindingHelper.registerKeyBinding(keyLeft);
		KeyBindingHelper.registerKeyBinding(keyUp);
		KeyBindingHelper.registerKeyBinding(keyDown);

		MinecraftClient client = MinecraftClient.getInstance();

		HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
			handleCameraInput(client, tickDelta);
		});
	}

	private void handleCameraInput(MinecraftClient client, float tickDelta) {
		if (client.player != null && client.inGameHud != null && client.currentScreen == null) {
			float yaw = client.player.getYaw();
			float pitch = client.player.getPitch();

			if (keyRight.isPressed()) {
				yaw += cameraSpeed;
			} else if (keyLeft.isPressed()) {
				yaw -= cameraSpeed;
			}

			if (keyUp.isPressed() && pitch > -90.0F) {
				pitch -= cameraSpeed;
			} else if (keyDown.isPressed() && pitch < 90.0F) {
				pitch += cameraSpeed;
			}

			client.player.setYaw(yaw);
			client.player.setPitch(pitch);
		}
	}
}
