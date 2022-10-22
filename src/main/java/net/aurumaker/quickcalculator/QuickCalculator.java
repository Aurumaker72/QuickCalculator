package net.aurumaker.quickcalculator;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.aurumaker.quickcalculator.gui.IExpressionParser;
import net.aurumaker.quickcalculator.gui.QuickCalculatorGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mariuszgromada.math.mxparser.*;

public class QuickCalculator implements ClientModInitializer {

	private static KeyBinding showCalculatorKeyBinding;

	@Override
	public void onInitializeClient() {
		showCalculatorKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.quickcalculator.showcalculator",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_N,
				"category.quickcalculator.calculator"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (showCalculatorKeyBinding.wasPressed()) {

				MinecraftClient.getInstance().setScreen(new CottonClientScreen(new QuickCalculatorGUI(
						new IExpressionParser() {
							@Override
							public String getExpressionValue(String inputExpression) {
								var result = String.valueOf(new Expression(inputExpression).calculate());
								// there's a better way to do this, but intellisense isnt cooperating with the eval library so i dont
								// know what type it returns lol
								if (result.equalsIgnoreCase("nan")){
									result = Text.translatable("key.quickcalculator.unevaluatable").toString();
								}
								return result;
							}
						}
				)));

			}
		});
	}
}
