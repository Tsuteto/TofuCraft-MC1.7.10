package tsuteto.tofu.params;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityInfo
{
    private static final EntityInfo instance = new EntityInfo();
    private final Map<Integer, EntityParams> entityInfo = new HashMap<Integer, EntityParams>();

    public static EntityInfo instance()
    {
        return instance;
    }

    public void set(int entityId, DataType key, Object value)
    {
        if (!entityInfo.containsKey(entityId))
        {
            entityInfo.put(entityId, new EntityParams());
        }
        entityInfo.get(entityId).set(key, value);
    }

    private void addPlayer(int entityId)
    {
        entityInfo.put(entityId, new EntityParams());
    }

    public <T> T get(int entityId, DataType key)
    {
        EntityParams params = entityInfo.get(entityId);
        if (params != null)
        {
            return params.get(key);
        }
        else
        {
            return null;
        }
    }

    public int getInt(int entityId, DataType key)
    {
        return this.<Integer>get(entityId, key);
    }

    public String getString(int entityId, DataType key)
    {
        return this.<String>get(entityId, key);
    }

    public boolean doesDataExist(int entityId, DataType key)
    {
        return entityInfo.containsKey(entityId) && entityInfo.get(entityId).containsKey(key);
    }
    
    public Set<Integer> getEntitySet()
    {
        return entityInfo.keySet();
    }

    public void remove(int entityId, DataType key)
    {
        EntityParams params = entityInfo.get(entityId);
        params.remove(key);
        if (params.size() == 0)
        {
            entityInfo.remove(entityId);
        }
    }

    class EntityParams
    {
        Map<DataType, Object> params = new EnumMap<DataType, Object>(DataType.class);

        public void set(DataType key, Object value)
        {
            params.put(key, value);
        }

        public <T> T get(DataType key)
        {
            return (T)params.get(key);
        }

        public boolean containsKey(DataType key)
        {
            return params.containsKey(key);
        }
        
        public void remove(DataType key)
        {
            params.remove(key);
        }
        
        public int size()
        {
            return params.size();
        }
    }
}
