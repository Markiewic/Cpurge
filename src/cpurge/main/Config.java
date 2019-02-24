package cpurge.main;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Config {

    static YamlConfiguration load()
    {
        try
        {
            File config = new File(Main.plugin.getDataFolder(), "config" + ".yml");
            Main.plugin.getDataFolder().mkdirs();
            InputStream input;
            FileOutputStream e;
            byte[] buff;
            int n;
            if (!config.exists())
            {
                input = Main.class.getResourceAsStream("/" + "config" + ".yml");
                try
                {
                    e = new FileOutputStream(config);
                    buff = new byte[65536];
                    while ((n = input.read(buff)) > 0)
                    {
                        e.write(buff, 0, n);
                        e.flush();
                    }
                    e.close();
                    if (System.getProperty("os.name").startsWith("Windows"))
                    {
                        Path p = Paths.get(config.toURI());
                        ByteBuffer bb = ByteBuffer.wrap(Files.readAllBytes(p));
                        CharBuffer cb = Charset.forName("UTF-8").decode(bb);
                        bb = Charset.forName("windows-1251").encode(cb);
                        Files.write(p, bb.array());
                    }
                } catch (Exception ex)
                {
                    return null;
                }
            }
            try
            {
                return YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(config), "UTF-8"));
            } catch (UnsupportedEncodingException | FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}