package tsuteto.tofu.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

import java.util.List;

public class BiomeIdManager
{
    private List<Integer> assignedIds = Lists.newArrayList();
    private boolean haveIdsAssigned = true;

    private SearchPath[] searchPaths = new SearchPath[]{
            new SearchPath(64, 127),
            new SearchPath(192, 255),
            new SearchPath(63, 0),
            new SearchPath(191, 128)
    };
    
    public boolean isCompleted()
    {
        return haveIdsAssigned;
    }

    public int assignId(String confKey, Configuration conf)
    {
        int id = -1;

        try
        {
            if (conf.hasKey(TcBiomes.CONF_CATEGORY, confKey))
            {
                id = conf.get(TcBiomes.CONF_CATEGORY, confKey, -1).getInt();
                if (id != -1)
                {
                    return id;
                }
            }

            // Find unused id
            if ((id = findUnusedId()) != -1)
            {
                conf.get(TcBiomes.CONF_CATEGORY, confKey, id).set(id);
                return id;
            }

            // Unable to find id
            haveIdsAssigned = false;
            conf.get(TcBiomes.CONF_CATEGORY, confKey, -1).set(-1);
            return -1;
        }
        finally
        {
            if (id != -1) assignedIds.add(id);
        }
    }

    private int findUnusedId()
    {
        for (SearchPath path : searchPaths)
        {
            int id = findUnusedIdRange(path.start, path.end);
            if (id != -1) return id;
        }
        return -1;
    }

    private int findUnusedIdRange(int start, int end)
    {
        boolean ascend = start < end;
        for (int i = start; ascend && i <= end || !ascend && i >= end; i += ascend ? 1 : -1)
        {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[i];
            if (biome == null && !assignedIds.contains(i))
            {
                return i;
            }
        }
        return -1;
    }

    private class SearchPath
    {
        int start;
        int end;

        public SearchPath(int start, int end)
        {
            this.start = start;
            this.end = end;
        }
    }
}
