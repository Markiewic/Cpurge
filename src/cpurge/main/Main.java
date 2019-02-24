package cpurge.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{

    static Plugin plugin = null;
    static YamlConfiguration config = null;

    public void onEnable()
    {
        plugin = this;
        config = Config.load();
        ConfigData.reload();
        Pillar.init();
        Bukkit.getPluginManager().registerEvents(new PurgeListeners(), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PurgeTimer(), 0L, 100L);
        getCommand("cpg").setExecutor(new CommandsHandler());
    }

    static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
