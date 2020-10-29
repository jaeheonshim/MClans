package com.jaeheonshim.simplysurvival.server;

import org.bukkit.ChatColor;

import java.util.Comparator;

public class PlayerFlag {
    private int priority;
    private ChatColor chatColor;
    private String name;

    public static final Comparator<PlayerFlag> byPriority = new Comparator<PlayerFlag>() {
        @Override
        public int compare(PlayerFlag o1, PlayerFlag o2) {
            return o1.getPriority() - o2.getPriority();
        }
    };

    public PlayerFlag(int priority, ChatColor chatColor, String name) {
        this.priority = priority;
        this.chatColor = chatColor;
        this.name = name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
