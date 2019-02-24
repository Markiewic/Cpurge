package cpurge.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsHandler implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(!sender.hasPermission("cpurge.admin"))
        {
            sender.sendMessage(Main.colorize("&eCpurge &8/ &7You don't have permissions"));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
        {

            Main.config = Config.load();
            ConfigData.reload();

            sender.sendMessage(Main.colorize("&eCpurge &8/ &7Reload complete"));

        } else
        {

            sender.sendMessage(Main.colorize("&eCpurge &8/ &7Version 0.9, Author Schirbak"));
            sender.sendMessage(Main.colorize("&e&6/cpurge reload &8- &7Reload plugin"));

        }

        return true;
    }
}
