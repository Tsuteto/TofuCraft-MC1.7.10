package tsuteto.tofu.util;

import java.util.ArrayList;
import java.util.Collection;

public class ListUtils
{
    public static <E> ArrayList<E> forEach(Collection<E> list, Applier<E> app)
    {
        ArrayList<E> newList = new ArrayList<E>();
        for (E element : list)
        {
            newList.add(app.apply(element));
        }
        return newList;
    }

    public static interface Applier<E>
    {
        public E apply(E element);
    }
}
