package com.openpojo.reflection.filters;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;

/**
 * This class filters out any classes that package name doesn't contain the provide string.
 *
 * @author ohiocowboy
 */
public class FilterPackageName implements PojoClassFilter
{
    private String packageName;

    public FilterPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public boolean include(PojoClass pojoClass)
    {
        Package aPackage = pojoClass.getClazz().getPackage();
        return contains(aPackage.getName(), packageName);
    }

    private static boolean contains(CharSequence seq, CharSequence searchSeq)
    {
        return seq != null && searchSeq != null && indexOf(seq, searchSeq, 0) >= 0;
    }

    private static int indexOf(CharSequence cs, CharSequence searchChar, int start)
    {
        return cs.toString().indexOf(searchChar.toString(), start);
    }
}
