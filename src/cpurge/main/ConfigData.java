package cpurge.main;

import java.util.List;

class ConfigData
{

    static String messages_warning;
    static String messages_barWarning;
    static String messages_postSaved;
    static String messages_barPostSaved;
    static String messages_postRemoved;
    static String messages_barPostRemoved;
    static int settings_minHeight;
    static int settings_removeTimeout;
    static int settings_forgerTimeout;
    static List<String> settings_disableOnWorlds;

    static void reload()
    {
        messages_warning = Main.colorize(Main.config.getString("messages.warning"));
        messages_postSaved = Main.colorize(Main.config.getString("messages.postSaved"));
        messages_postRemoved = Main.colorize(Main.config.getString("messages.postRemoved"));
        settings_minHeight = Main.config.getInt("settings.minHeight");
        settings_removeTimeout = Main.config.getInt("settings.removeTimeout");
        settings_forgerTimeout = Main.config.getInt("settings.forgerTimeout");
        settings_disableOnWorlds = Main.config.getStringList("settings.disableOnWorlds");
    }

}
