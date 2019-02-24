package cpurge.main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

class Pillar
{

    private static Map<Player, Block> playerBlockMap;
    private static List<Pillar> pillarList;

    static void init()
    {
        playerBlockMap = new HashMap<>();
        pillarList = Collections.synchronizedList(new ArrayList<>());
    }

    static void addPillar(Pillar pillar)
    {
        synchronized (pillarList)
        {
            pillarList.add(pillar);
        }
    }

    static List<Pillar> getPillarList()
    {
        return pillarList;
    }

    static void putBlockMap(Player player, Block block)
    {
        playerBlockMap.put(player, block);
    }

    static Block getBlock(Player player)
    {
        return (playerBlockMap.getOrDefault(player, null));
    }

    private Player owner;
    private List<Block> blockList;
    private Location pillarTop;
    private long timestamp;
    private int height;

    Pillar(Player owner)
    {
        this.owner = owner;
        blockList = new ArrayList<>();
        timestamp = System.currentTimeMillis();
        height = 0;
    }

    boolean isPillarBlock(Block block)
    {
        return blockList.contains(block);
    }

    private List<Block> getBlockList()
    {
        return blockList;
    }

    long getTimestamp()
    {
        return timestamp;
    }

    Location getPillarTop()
    {
        return pillarTop;
    }

    Player getOwner()
    {
        return owner;
    }

    void addBlock(Block... blocks)
    {
        Collections.addAll(blockList, blocks);
        pillarTop = blocks[blocks.length - 1].getLocation().add(0, 1D, 0);
        height += blocks.length;
        timestamp = System.currentTimeMillis();

        if (height == ConfigData.settings_minHeight)
        {
            if (ConfigData.messages_warning.length() != 0) owner.sendMessage(ConfigData.messages_warning);
            if (ConfigData.messages_barWarning.length() != 0) Main.sendActionBarMessage(owner, ConfigData.messages_barWarning);
        }
    }

    void destroy()
    {
        World bWorld = getBlockList().get(0).getWorld();
        for (Block block : getBlockList())
        {
            if (bWorld.getBlockAt(block.getLocation()).getType() == block.getType())
                for (ItemStack itemStack : block.getDrops())
                    bWorld.dropItemNaturally(block.getLocation(), itemStack);
            block.setType(Material.AIR);
        }
        if (ConfigData.messages_postRemoved.length() != 0) owner.sendMessage(ConfigData.messages_postRemoved);
        if (ConfigData.messages_barPostRemoved.length() != 0) Main.sendActionBarMessage(owner, ConfigData.messages_barPostRemoved);
    }

    void save()
    {
        blockList.clear();
        if (ConfigData.messages_postSaved.length() != 0) owner.sendMessage(ConfigData.messages_postSaved);
        if (ConfigData.messages_barPostSaved.length() != 0) Main.sendActionBarMessage(owner, ConfigData.messages_barPostSaved);
    }

    int getHeight()
    {
        return height;
    }
}
