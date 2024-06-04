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
import hypersquare.hypersquare.util.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class RemainderAction implements Action {
    @Override
    public BarrelParameter[] parameters() {
        return new BarrelParameter[]{
            new BarrelParameter(DisplayValue.VARIABLE, false, false, Component.text("Variable to set"), "variable"),
            new BarrelParameter(DisplayValue.NUMBER, false, false, Component.text("Dividend"), "dividend"),
            new BarrelParameter(DisplayValue.NUMBER, false, false, Component.text("Divisor"), "divisor"),
        };
    }

    @Override
    public String getId() {
        return "remainder";
    }

    @Override
    public String getCodeblockId() {
        return "set_variable";
    }

    @Override
    public String getSignName() {
        return "%";
    }

    @Override
    public String getName() {
        return "Set to Remainder (%)";
    }

    @Override
    public ActionMenuItem getCategory() {
        return SetVariableItems.NUMERICAL_ACTIONS;
    }

    @Override
    public BarrelTag[] tags() {
        return new BarrelTag[]{
                new BarrelTag("remainder_mode", "Remainder Mode", RemainderModes.REMAINDER,
                        new BarrelTag.Option(RemainderModes.REMAINDER, "Remainder", Material.PURPLE_DYE),
                        new BarrelTag.Option(RemainderModes.MODULO, "Modulo", Material.PINK_DYE))
        };
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
                .setMaterial(Material.NETHER_WART)
                .setName(Component.text("Set to Remainder (%)").color(NamedTextColor.RED))
                .setDescription(Component.text("Sets a variable to the remainder"),
                    Component.text("after dividing two numbers with"),
                    Component.text("a whole quotient."))
                .setParameters(parameters())
                .build();
    }

    @Override
    public BarrelMenu actionMenu(CodeActionData data) {
        return new BarrelMenu(this, 3, data)
                .parameter("variable",12)
                .parameter("dividend", 13)
                .parameter("divisor", 14)
                .tag("remainder_mode", 26);
    }

    @Override
    public void execute(@NotNull ExecutionContext ctx, @NotNull CodeSelection targetSel) {
        DecimalNumber dividend = ctx.args().single("dividend");
        DecimalNumber divisor = ctx.args().single("divisor");
        float sum = 0;
        RemainderModes remainderMode = ctx.getTag("remainder_mode", RemainderModes::valueOf);
            switch(remainderMode) {
                case REMAINDER -> {
                    sum = dividend.toFloat()%divisor.toFloat();
                }
                case MODULO -> {
                    sum = Math.floorMod(dividend.toInt(), divisor.toInt());
                }
            }
        ctx.args().<CodeVariable>single("variable").set(sum);
    }

    private enum RemainderModes {
        REMAINDER,
        MODULO
    }
}
