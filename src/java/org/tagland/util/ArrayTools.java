package org.tagland.util;

/**
 * User: laramie
 * $LastChangedRevision:  $
 * $LastChangedDate:  $
 */
public class ArrayTools {
    // Remember, to create arrays from a Class,  you can call:
    //      Object foo = Array.newInstance(parameterClass, 0);

    public static int getDimension(Object array) {
        int dim = 0;
        Class cls = array.getClass();
        while (cls.isArray()) {
            dim++;
            cls = cls.getComponentType();
        }
        return dim;
    }
}
