package cpurge.main;

import java.util.Iterator;

public class PurgeTimer implements Runnable
{

    @Override
    public void run()
    {
        Iterator it;
        synchronized (Pillar.getPillarList())
        {
            it = Pillar.getPillarList().iterator();
            while (it.hasNext())
            {
                Pillar pillar = (Pillar) it.next();
                if (pillar.getHeight() >= ConfigData.settings_minHeight &&
                        System.currentTimeMillis() - pillar.getTimestamp() >= ConfigData.settings_removeTimeout * 1000)
                {
                    pillar.destroy();
                    it.remove();
                } else if (pillar.getHeight() < ConfigData.settings_minHeight &&
                        System.currentTimeMillis() - pillar.getTimestamp() > ConfigData.settings_forgerTimeout * 1000)
                {
                    it.remove();
                }
            }
        }
    }
}
