package net.aurumaker.quickcalculator.gui;

import com.mojang.datafixers.types.Func;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class QuickCalculatorGUI extends LightweightGuiDescription {

    private Text getOutputText(IExpressionParser expressionParser, String expression)
    {
        return Text.literal(expressionParser.getExpressionValue(expression));
    }

    public QuickCalculatorGUI(IExpressionParser expressionParser) {

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(100, 100);
        root.setInsets(Insets.ROOT_PANEL);

        WTextField input = new WTextField();
        root.add(input, 0, 1, 5, 1);

        WLabel output = new WLabel(Text.literal(""), 0x000000);
        output.setHorizontalAlignment(HorizontalAlignment.LEFT);
        root.add(output, 0, 3, 5, 1);

        Consumer<String> onChanged = newValue -> {
            output.setText(getOutputText(expressionParser, input.getText()));
        };

        input.setChangedListener(onChanged);
        input.setText("0");

        root.validate(this);
    }
}