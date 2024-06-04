package hypersquare.hypersquare.dev.code.var.action;

import hypersquare.hypersquare.dev.BarrelParameter;
import hypersquare.hypersquare.dev.BarrelTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.code.player.action.PlayerSetAttribute;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.dev.value.type.DecimalNumber;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.action.var.SetVariableItems;
import hypersquare.hypersquare.item.value.DisplayValue;
import hypersquare.hypersquare.menu.barrel.BarrelMenu;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.CodeVariable;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class RandomNumberAction implements Action {
    @Override
    public BarrelParameter[] parameters() {
        return new BarrelParameter[]{
            new BarrelParameter(DisplayValue.VARIABLE, false, false, Component.text("Variable to set"), "variable"),
            new BarrelParameter(DisplayValue.NUMBER, false, false, Component.text("Minimum Number"), "minimum"),
            new BarrelParameter(DisplayValue.NUMBER, false, false, Component.text("Maximum Number"), "maximum"),
            new BarrelParameter(DisplayValue.NUMBER, false, true, Component.text("Generation Seed"), "seed"),
        };
    }

    @Override
    public String getId() {
        return "random_number";
    }

    @Override
    public String getCodeblockId() {
        return "set_variable";
    }

    @Override
    public String getSignName() {
        return "RandomNumber";
    }

    @Override
    public String getName() {
        return "Set to Random Number";
    }

    @Override
    public ActionMenuItem getCategory() {
        return SetVariableItems.NUMERICAL_ACTIONS;
    }

    @Override
    public BarrelTag[] tags() {
        return new BarrelTag[]{
            new BarrelTag("rounding_mode", "Rounding Mode", RoundingModes.WHOLE,
                new BarrelTag.Option(RoundingModes.WHOLE, "Whole number", Material.CLAY),
                new BarrelTag.Option(RoundingModes.DECIMAL, "Decimal number", Material.CLAY_BALL))
        };
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setItemStack(Utilities.getPlayerHead("dice"))
                .setName(Component.text("Set to Random Number").color(NamedTextColor.RED))
                .setDescription(Component.text("Sets a variable to a random"),
                    Component.text("number between two other"),
                    Component.text("numbers."))
                .setParameters(parameters())
                .build();
    }

    @Override
    public BarrelMenu actionMenu(CodeActionData data) {
        return new BarrelMenu(this, 3, data)
                .parameter("variable",11)
                .parameter("minimum", 13)
                .parameter("maximum", 14)
                .parameter("seed", 15)
                .tag("rounding_mode", 26);
    }

    @Override
    public void execute(@NotNull ExecutionContext ctx, @NotNull CodeSelection targetSel) {
        DecimalNumber min = ctx.args().single("minimum");
        DecimalNumber max = ctx.args().single("maximum");
        List<DecimalNumber> seeds = ctx.args().allNonNull("seed");
        float sum = 0;
        RoundingModes roundingMode = ctx.getTag("rounding_mode", RoundingModes::valueOf);
            Random rand = new Random();
            if(!seeds.isEmpty()) rand = new Random(seeds.getFirst().toInt());
            switch(roundingMode) {
                case WHOLE -> {
                    sum = rand.nextInt(max.toInt() - min.toInt() + 1) + min.toInt();
                }
                case DECIMAL -> {
                    sum = rand.nextFloat(max.toFloat() - min.toFloat() + 1) + min.toFloat();
                }
            }
        ctx.args().<CodeVariable>single("variable").set(sum);
    }

    private enum RoundingModes {
        WHOLE,
        DECIMAL
    }
}
