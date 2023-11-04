package net.framedev.api;

import net.framedev.others.S;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class Actions {
    public static void use(List<String> list, Player player) {
        if (list.isEmpty()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            String[] split = list.get(i).split(" ");

            String action = split[0];
            String command = list.get(i).replace(action + " ", "");

            switch (action.toUpperCase()) {

                case "[MESSAGE]" -> player.sendMessage(S.s(command));

                case "[SOUND]" -> player.playSound(player.getLocation(), Sound.valueOf(command), 500.0f, 1.0f);


                case "[TITLE]" -> {
                    if (command.contains(";")) {
                        String[] title = command.split(";");
                        player.sendTitle(S.s(title[0]), S.s(title[1]));
                        break;
                    }
                    player.sendTitle(S.s(command), "");}

                case "[ACTIONBAR]" -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(S.s(command)));

                case "[COMMAND]" -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));

                case "[BROADCAST]" -> Bukkit.broadcastMessage(S.s(command.replace("%player%", player.getName())));
            }
        }
    }
}
