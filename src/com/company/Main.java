package com.company;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("godboots")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Players only BOSS");
                return true;
            }
            Player player = (Player) sender;
            if (player.getInventory().firstEmpty() == -1) {
                Location loc = player.getLocation();
                World world = player.getWorld();
                world.dropItemNaturally(loc, getItem());
                player.sendMessage(ChatColor.GOLD + "da god");
                return true;
            }
            player.getInventory().addItem(getItem());
            player.sendMessage(ChatColor.GOLD + "DA GOD BLESSED U!");

        }
        return false;
    }

    public ItemStack getItem() {
        ItemStack gboots = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta meta = gboots.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "God boots");
        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.ITALIC + "" + ChatColor.GREEN + "Boots for a real one");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LURE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setUnbreakable(true);
        gboots.setItemMeta(meta);

        return gboots;
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        Player player = (Player) event.getPlayer();
        if (player.getInventory().getBoots() != null) {
            if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("God boots")) {
                if (player.getInventory().getBoots().getItemMeta().hasLore()) {
                    if (event.getFrom().getY() < event.getTo().getY() &&
                            player.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                        player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
                if (player.getInventory().getBoots() != null)
                    if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("God boots"))
                        if (player.getInventory().getBoots().getItemMeta().hasLore()) {
                            event.setCancelled(true);
                        }

        }

    }

}

