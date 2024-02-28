package hypersquare.hypersquare.dev.code.dev;

import hypersquare.hypersquare.dev.ActionTag;
import hypersquare.hypersquare.dev.action.Action;
import hypersquare.hypersquare.dev.codefile.data.CodeActionData;
import hypersquare.hypersquare.item.action.ActionItem;
import hypersquare.hypersquare.item.action.ActionMenuItem;
import hypersquare.hypersquare.item.action.SpecialActionType;
import hypersquare.hypersquare.menu.action.ActionMenu;
import hypersquare.hypersquare.play.CodeSelection;
import hypersquare.hypersquare.play.error.HSException;
import hypersquare.hypersquare.play.execution.ExecutionContext;
import hypersquare.hypersquare.util.color.Colors;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PrintStackTraceAction implements Action {
    @Override
    public ActionParameter[] parameters() {
        return new ActionParameter[]{};
    }

    @Override
    public ActionTag[] tags() {
        return new ActionTag[]{};
    }

    @Override
    public void execute(ExecutionContext ctx, CodeSelection targetSel) {
        List<Component> stack = HSException.getStackTrace(new RuntimeException("<PrintStackTraceAction>"));
        for (Player player : targetSel.players()) {
            for (Component line : stack) {
                player.sendMessage(line);
            }
        }
    }

    @Override
    public String getId() {
        return "print_stack_trace";
    }

    @Override
    public String getCodeblockId() {
        return "dev";
    }

    @Override
    public String getSignName() {
        return "PrintStackTrace";
    }

    @Override
    public String getName() {
        return "Print Stack Trace";
    }

    @Override
    public ActionMenuItem getCategory() {
        return null;
    }

    @Override
    public ItemStack item() {
        return new ActionItem()
            .setName(Component.text(getName()).color(Colors.ORANGE))
            .setMaterial(Material.ORANGE_CANDLE)
            .setDescription(
                Component.text("Prints the current stack trace"),
                Component.text("to the selected players.")
            )
            .setSpecialActionType(SpecialActionType.DEV)
            .setEnchanted(true)
            .build();
    }

    @Override
    public ActionMenu actionMenu(CodeActionData data) {
        return new ActionMenu(this, 3, data);
    }
}
