package cpurge.main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
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

    static void sendActionBarMessage(Player player, String message)
    {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}"), (byte) 2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

}
