package com.jaeheonshim.simplysurvival.mclans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClansGuideBook {
    private static final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
    private static boolean init;

    public static void initBook(JavaPlugin plugin) {
        BookMeta bm = ((BookMeta) book.getItemMeta());
        bm.setAuthor("jaeheonshim");
        bm.setTitle("Clans Guide");

        List<String> allPages = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(plugin.getResource("clanbook.txt")))) {
            String str;
            while((str = reader.readLine()) != null) {
                allPages.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allPages = allPages.stream().map(e -> {
            e = e.replace("{", "&1&o");
            e = e.replace("}", "&r&0");
            e = e.replace("\\n", "\n");

            return ChatColor.translateAlternateColorCodes('&', e);
        }).collect(Collectors.toList());

        bm.setPages(allPages);

        book.setItemMeta(bm);
    }

    public static ItemStack getBook() {
        return book;
    }

    public static void main(String[] args) {
        ClansGuideBook.getBook();
    }
}
