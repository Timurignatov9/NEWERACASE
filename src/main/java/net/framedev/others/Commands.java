package net.framedev.others;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import net.framedev.Main;
import net.framedev.events.SetCase;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            if (!commandSender.hasPermission("NEWERA.cases")) {
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                return true;
            }
            commandSender.sendMessage(S.s("&7[&6NEWERACASE&7] &7- Кейсы, версия плагина: &6v" + Main.getInstance().getDescription().getVersion()));
            commandSender.sendMessage(S.s("&7Комманды:"));
            commandSender.sendMessage(S.s(" &6/" + s + " setcase &7- Установить позицию для открытия кейсов."));
            commandSender.sendMessage(S.s(" &6/" + s + " givekey [игрок] [кейс] [кол-во] &7- Выдать кейсы игроку"));
            commandSender.sendMessage(S.s(" &6/" + s + " takekey [игрок] [кейс] [кол-во] &7- Забрать кейсы у игрока"));
            commandSender.sendMessage(S.s(" &6/" + s + " setkey [игрок] [кейс] [кол-во] &7- Установить кейсы игроку"));
            commandSender.sendMessage(S.s("&7"));
            commandSender.sendMessage(S.s(" &7Плагин поддерживается и обновляется разработчиками §6none.type.load§f, "));
            commandSender.sendMessage(S.s(" &7если вы заметили недоработку, или есть предложения, то:"));
            commandSender.sendMessage(S.s(" &7Группа  дс: §6https://discord.gg/tjm9Qf4c"));
            commandSender.sendMessage(S.s("&7"));
            return true;
        }
        switch (strings[0]) {
            case "givekey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("NEWERACASES.givekey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.giveKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.give-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "takekey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("NEWERACASES.takekey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                if (!(CasesContainer.takeKey(strings[1], strings[2], Integer.parseInt(strings[3])))) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-take-amount")));
                    return true;
                }
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.take-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setkey" -> {
                if (strings.length != 4) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!commandSender.hasPermission("NEWERACASES.setkey")) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                if (!CasesContainer.isValidateCase(strings[2])) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-contains-case")));
                    return true;
                }
                CasesContainer.setKey(strings[1], strings[2], Integer.parseInt(strings[3]));
                commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.set-key"))
                        .replace("%case%", strings[2].toUpperCase()).replace("%player%", strings[1]).replace("%amount%", strings[3]));
                return true;
            }
            case "setcase" -> {
                if (strings.length != 1) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-argument")));
                    return true;
                }
                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.error-instance")));
                    return true;
                }
                Player player = (Player) commandSender;
                if (!player.hasPermission("NEWERACASES.setcase")) {
                    player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.no-perms")));
                    return true;
                }
                SetCase.setCaseList.add(player);
                player.sendMessage(S.s(Main.getInstance().getConfig().getString("messages.click-set-position")));
                return true;
            }
        }
        return false;
    }
}
