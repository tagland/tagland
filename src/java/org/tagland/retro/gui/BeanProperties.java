package org.tagland.retro.gui;

import org.tagland.retro.gui.designers.PropertyRow;
import org.tagland.retro.gui.editors.ObjectPicker;
import us.anarchia.Anarchia;
import us.anarchia.gui.AnarchiaCellEditorMapping;
import us.anarchia.obj.Author;
import us.anarchia.obj.Copyright;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanProperties {
    public static final String IN = "\r\n\t";

    public static boolean DEBUG = false;

    public static void showFields(Object o, boolean showInheritedMethods) throws Exception {
        Class aClass = o.getClass();

        //or do one at a time, by name: Field field = aClass.getField("someField");
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            Object oldvalue = field.get(o);
            Class type = field.getType();
            if (type == int.class) {
                field.set(o, 3);
            } else if (type == java.lang.String.class) {
                field.set(o, "changed");
            }
            Object value = field.get(o);
            if (DEBUG) System.out.println("field " + field.toString()
                    + IN + "name: " + field.getName()
                    + IN + "type: " + field.getType()
                    + IN + "value.before: " + oldvalue
                    + IN + "value.after:" + value);
            //to set: field.set(o, value);
        }
        printGettersSetters(aClass, showInheritedMethods);
    }

    public static List<PropertyRow> fieldsToRows(Object o, boolean showInheritedMethods) throws Exception {
        List<PropertyRow> rows = new ArrayList<PropertyRow>();

        if (o == null){
            System.err.println("ERROR: Object o parameter was null in BeanProperties.fieldsToRows()");
            return rows;
        }
        Class aClass = o.getClass();

        //DO PUBLIC FIELDS:
        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            PropertyRow row = new PropertyRow();
            Object oldvalue = field.get(o);
            Class type = field.getType();
            //if (type == int.class) {
            //    field.set(o, 3);
            //} else if (type == java.lang.String.class) {
            //   field.set(o, "changed");
            //}
            Object value = field.get(o);
            if (DEBUG) System.out.println("field " + field.toString()
                    + IN + "name: " + field.getName()
                    + IN + "type: " + field.getType()
                    + IN + "value.before: " + oldvalue
                    + IN + "value.after:" + value);
            row.target = o;
            row.oldValue = value;
            row.dirty = false;
            row.id = field.getName();
            //row.dataClassname = field.getType().getClass().getName();
            row.dataClass = field.getType();
            row.dataClassname = field.getType().getName();
            row.field = field; //WARNING: this can be a leak, so be sure to clean up.
            rows.add(row);
        }

        //DO PUBLIC GETTERS/SETTERS
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            PropertyRow row = new PropertyRow();
            Class dc = method.getDeclaringClass();
            boolean sameClass = aClass.equals(dc);
            if (showInheritedMethods || sameClass) {
                //    GETTER
                if (isGetter(method)) {
                    row.target = o;
                    row.id = method.getName();
                    row.getMethod = method;
                    row.dataClassname = method.getReturnType().getName();
                    row.dataClass = method.getReturnType();

                    try {
                        Object out = method.invoke(o);
                        if (out != null) {
                            row.oldValue = out;     //setting oldValue because below we will set the dirty == false, since we are not the USER setting this value.
                            row.oldValueStr = out.toString();
                        }
                    } catch (Throwable t) {
                        row.error = t.toString();
                    }
                    row.dirty = false;
                    rows.add(row);
                     if (DEBUG) System.out.println("getter: " + method + " in class: " + dc);
                }
                //    SETTER
                if (isSetter(method) && method.getParameterTypes().length == 1) {
                    row.id = method.getName();
                    row.setMethod = method;
                    Class parameterClass = method.getParameterTypes()[0];
                    if (parameterClass.isArray()){         //   an array of  Enum
                        //parameterClass.isEnum()
                        Class componentType = parameterClass.getComponentType();    //Will be the Enum 's class
                        row.dataClassname = componentType.getName();
                        row.dataClass = Class.forName(row.dataClassname);
                    }  else { //Not an Enum.
                        row.dataClassname = parameterClass.getName();
                        row.dataClass= parameterClass;
                    }
                    rows.add(row);
                    if (DEBUG) System.out.println("setter: " + method + " in class: " + dc);
                }
            }
        }
        return rows;
    }

    public static void printGettersSetters(Class aClass, boolean showInheritedMethods) {
        Method[] methods = aClass.getMethods();

        for (Method method : methods) {
            Class dc = method.getDeclaringClass();
            boolean sameClass = aClass.equals(dc);
            if (showInheritedMethods || sameClass) {
                if (isGetter(method)) {
                    System.out.println("getter: " + method + " in class: " + dc);
                }
                if (isSetter(method)) {
                    System.out.println("setter: " + method + " in class: " + dc);
                }
            }
        }
    }

    public static boolean isGetter(Method method) {
        if (!method.getName().startsWith("get")) return false;
        if (method.getParameterTypes().length != 0) return false;
        if (void.class.equals(method.getReturnType())) return false;
        return true;
    }

    public static boolean isSetter(Method method) {
        if (!method.getName().startsWith("set")) return false;
        if (method.getParameterTypes().length != 1) return false;
        return true;
    }

    public static void printInputForm(Object target, boolean showInherited) throws Exception {
            List<PropertyRow> rows = fieldsToRows(target, showInherited);
            for (PropertyRow row : rows) {
                System.out.println(row.toString());
            }
        }

    //==============================================================================================

    public static void test() throws Exception{
        /*
        these all work:
        BeanProperties.showFields(new AdNasty(), false);
        BeanProperties.showFields(new Copyright(), false);

        BeanProperties.printInputForm(new AdNasty(), false);
        BeanProperties.printInputForm(new Copyright(), false);

        (new ObjectPicker(false)).showPropertyPageForObject(new Copyright());
        (new ObjectPicker(false)).showPropertyPageForObject(new AdNasty());
         */
        Anarchia anarchia = new Anarchia();
        Author author     = new Author();
        Copyright copyright = new Copyright();
        author.setCopyright(copyright);
        anarchia.addAuthor(author);
        //worked:  (new ObjectPicker(true)).showPropertyPageForObject(anarchia, new AnarchiaCellEditorMapping());
         (new ObjectPicker(true)).showPropertyPageForObject(copyright, new AnarchiaCellEditorMapping());
    }

    public static void main(String[] args) throws Exception {
       test();
    }
}
