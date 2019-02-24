package cpurge.main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Iterator;

public class PurgeListeners implements Listener
{

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.getPlayer().hasPermission("cpurge.bypass") || ConfigData.settings_disableOnWorlds.contains(event.getBlockPlaced().getWorld().getName())) return;
        Location newBlockLocation = event.getBlockPlaced().getLocation();
        Block oldBlock = Pillar.getBlock(event.getPlayer());
        Pillar.putBlockMap(event.getPlayer(), event.getBlock());

        for (Pillar pillar : Pillar.getPillarList())
            if (pillar.getPillarTop().getBlockX() == newBlockLocation.getBlockX() &&
                    pillar.getPillarTop().getBlockY() == newBlockLocation.getBlockY() &&
                    pillar.getPillarTop().getBlockZ() == newBlockLocation.getBlockZ() &&
                    pillar.getPillarTop().getWorld() == newBlockLocation.getWorld())
            {
                pillar.addBlock(event.getBlockPlaced());
                return;
            }

        if (oldBlock != null)
        {
            Location oldBlockLocation = oldBlock.getLocation();
            if (oldBlockLocation.getBlockX() == newBlockLocation.getBlockX() &&
                    oldBlockLocation.getBlockZ() == newBlockLocation.getBlockZ() &&
                    oldBlockLocation.getBlockY() == newBlockLocation.getBlockY() - 1)
            {
                Pillar pillar = new Pillar(event.getPlayer());
                pillar.addBlock(oldBlock, event.getBlock());
                Pillar.addPillar(pillar);
            }
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && Pillar.getBlock(event.getPlayer()) != null)
            if (event.getPlayer().getItemInHand().getType() == Material.AIR || event.getPlayer().getItemInHand().getType() == Pillar.getBlock(event.getPlayer()).getType())
            synchronized (Pillar.getPillarList())
            {
                Iterator it = Pillar.getPillarList().iterator();
                while (it.hasNext())
                {
                    Pillar pillar = (Pillar) it.next();
                    if (pillar.isPillarBlock(event.getClickedBlock()) && pillar.getOwner() == event.getPlayer() && pillar.getHeight() >= ConfigData.settings_minHeight)
                    {
                        pillar.save();
                        it.remove();
                    }
                }
            }
    }

}
