package hypersquare.hypersquare.listener;

import hypersquare.hypersquare.plot.RestrictMovement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        RestrictMovement.movementCheck(player);
    }
}
