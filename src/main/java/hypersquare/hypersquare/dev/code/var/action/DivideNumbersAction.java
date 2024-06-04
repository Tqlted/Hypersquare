package hypersquare.hypersquare.dev.code.var.action;

import hypersquare.hypersquare.dev.BarrelParameter;
import hypersquare.hypersquare.dev.BarrelTag;
import hypersquare.hypersquare.dev.action.Action;
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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DivideNumbersAction implements Action {
    @Override
    public BarrelParameter[] parameters() {
        return new BarrelParameter[]{
            new BarrelParameter(DisplayValue.VARIABLE, false, false, Component.text("Variable to set"), "variable"),
            new BarrelParameter(DisplayValue.NUMBER, true, false, Component.text("Numbers to divide"), "values")
        };
    }

    @Override
    public String getId() {
        return "divide_numbers";
    }

    @Override
    public String getCodeblockId() {
        return "set_variable";
    }

    @Override
    public String getSignName() {
        return "÷";
    }

    @Override
    public String getName() {
        return "Divide Numbers (÷)";
    }

    @Override
    public ActionMenuItem getCategory() {
        return SetVariableItems.NUMERICAL_ACTIONS;
    }

    @Override
    public BarrelTag[] tags() {
        return new BarrelTag[]{};
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.NETHER_BRICKS)
                .setName(Component.text("Divide Numbers (÷)").color(NamedTextColor.RED))
                .setDescription(Component.text("Sets a variable to the quotient"),
                    Component.text("of the given numbers."))
                .setParameters(parameters())
                .build();
    }

    @Override
    public BarrelMenu actionMenu(CodeActionData data) {
        return new BarrelMenu(this, 3, data)
                .parameter("variable",10)
                .parameterRange("values", 12, 16);
    }

    @Override
    public void execute(@NotNull ExecutionContext ctx, @NotNull CodeSelection targetSel) {
        List<DecimalNumber> numbers = ctx.args().allNonNull("values");
        float sum = numbers.getFirst().toFloat();
        if (!numbers.isEmpty()) {
            for (int i = 1; i < numbers.size(); i++) {
                sum /= numbers.get(i).toFloat();
            }
        }
        ctx.args().<CodeVariable>single("variable").set(sum);
    }
}
