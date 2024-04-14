package hypersquare.hypersquare.dev.code.player.action;

import hypersquare.hypersquare.item.action.ActionItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum PlayerStatEnum {
    HEAL_PLAYER(new PlayerNumericalStatTemplate(
        new ActionItem()
            .setMaterial(Material.CARROT)
            .setName(Component.text("Heal Player").color(NamedTextColor.RED))
            .setDescription(Component.text("Heals the player the given amount.")),
        "Set Player Health",
        "Health to set to",
        (p, health) -> p.setHealth(p.getHealth() + health)
    )),
    // Have to customize DAMAGE_PLAYER because it can have a source.
    SET_PLAYER_HEALTH(new PlayerNumericalStatTemplate(
        new ActionItem()
            .setMaterial(Material.APPLE)
            .setName(Component.text("Set Current Health").color(NamedTextColor.RED))
            .setDescription(Component.text("Sets the player's current health"),
                Component.text("to the specified number")),
        "Set Player Health",
        "Health to set to",
        Player::setHealth
    )),
    SET_PLAYER_MAX_HEALTH(new PlayerNumericalStatTemplate(
        new ActionItem()
            .setMaterial(Material.GOLDEN_APPLE)
            .setName(Component.text("Set Player Max Health").color(NamedTextColor.RED))
            .setDescription(Component.text("Sets the player's max health"),
                Component.text("to the specified number")),
        "Set Player Max Health",
        "Max health to set to",
        Player::setMaxHealth
    )),
    ;

    final PlayerNumericalStatTemplate instance;

    PlayerStatEnum(PlayerNumericalStatTemplate instance) {
        this.instance = instance;
    }

    public PlayerNumericalStatTemplate getInstance() {
        return instance;
    }
}
