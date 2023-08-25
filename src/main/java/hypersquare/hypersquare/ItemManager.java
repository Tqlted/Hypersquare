package hypersquare.hypersquare;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemManager {
    static NamespacedKey key; // Declare the key without initializing it here

    public static void initializeKey(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, "itemId"); // Initialize the key using the passed plugin instance
    }
    public static void registerItems() {
        ItemStack myPlots = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta meta = myPlots.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Click to access your plots");
        meta.setLore(lore);

        meta.setDisplayName(ChatColor.AQUA + "◇" + ChatColor.GREEN + " My Plots " + ChatColor.AQUA + "◇");
        myPlots.setItemMeta(meta);

        addItem("myPlots", myPlots);
    }
    private static HashMap<String, ItemStack> items;

    public ItemManager() {

        items = new HashMap<>();
    }

    public static void addItem(String itemId, ItemStack item) {
        item.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, itemId);
        items.put(itemId, item);
    }

    public static ItemStack getItem(String itemId) {
        return items.get(itemId);
    }

    public static String getItemID(ItemStack item) {
        if (item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Hypersquare.getPlugin(Hypersquare.class), "itemId"), PersistentDataType.STRING);
        }
        return null;
    }

}

