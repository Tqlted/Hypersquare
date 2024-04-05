package hypersquare.hypersquare.util;

import hypersquare.hypersquare.Hypersquare;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ValueDisplay {
    public static void spawnValueDisplay(Location location, int timeout, NamedTextColor color) {
        Slime display = location.getWorld().spawn(new Location(location.getWorld(), 0, 300, 0), Slime.class);
        display.setSize(1);
        display.teleport(location.clone().add(0, -0.25, 0));

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = board.registerNewTeam(display.getUniqueId() + "");
        team.displayName(Component.text("displayValues"));
        team.color(color);
        team.addEntry(display.getUniqueId().toString());
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

        display.setAI(false);
        display.setInvisible(true);
        display.setInvulnerable(true);
        display.setGlowing(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                display.remove();
            }
        }.runTaskLater(Hypersquare.instance, 20L * timeout);
    }

    public static void removeValueDisplay(Location location) {
        for (Entity entity : location.getWorld().getNearbyEntities(location, 0.5, 0.5, 0.5)) {
            entity.remove();
        }

    }
}
