package lpl.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class TreePrint {
    
    /**
     * Path for the html template (relative to the location of the class).
     */
    private static final String HTML_TEMPLATE_PATH = "tree-template.html";
    
    /**
     * Generate html to render an object as a tree. Private fields are ignored.
     * @param fileName the generated html is written to this file
     * @param tree the object to be rendered
     * @throws IOException 
     */
    public static void makeHtml(String fileName, Object tree) throws IOException {
        makeHtml(false, fileName, tree);
    }
    
    /**
     * Generate html to render an object as a tree.
     * @param includePrivateFields if this is set to true, private fields will
     * be included as children in the tree, otherwise they will be ignored
     * @param fileName the generated html is written to this file
     * @param tree the object to be rendered
     * @throws IOException 
     */
    public static void makeHtml(boolean includePrivateFields, String fileName, Object tree) throws IOException {
        String json = makeJson(includePrivateFields, tree);
        (new TreePrint()).writeJson(json, fileName);
    }
    
    private static String makeJson(boolean includePrivateFields, Object tree) {
        if (tree == null) return treeify("null");
        Class clazz = tree.getClass();
        String className = clazz.getName();
        //if (isBasic(className)) {
        if (tree instanceof String) {
            return treeify("\"" + tree + "\"");
        } else if (isBasic(className) || clazz.isPrimitive() || clazz.isEnum()) {
            return treeify(tree.toString());
        } else if (tree instanceof List) {
            List list = (List)tree;
            String[] children = new String[list.size()];
            for (int i = 0; i < children.length; ++i) {
                children[i] = makeJson(includePrivateFields, list.get(i));
            }
            return treeify("List", children);
        } else if (clazz.isArray()) {
            int length = Array.getLength(tree);
            String[] children = new String[length];
            for (int i = 0; i < children.length; ++i) {
                children[i] = makeJson(includePrivateFields, Array.get(tree, i));
            }
            return treeify("Array", children);
        } else {
            Field[] fieldList = clazz.getDeclaredFields();
            List<Field> included = new ArrayList<>();
            for (Field field: fieldList) {
                if (includePrivateFields || !Modifier.isPrivate(field.getModifiers())) included.add(field);
            }
            String[] children = new String[included.size()];
            for (int i = 0; i < children.length; ++i) {
                Field field = included.get(i);
                try {
                    field.setAccessible(true);
                    children[i] = makeJson(includePrivateFields, field.get(tree));
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    children[i] = treeify("UnableToAcccessField");
                }
            }
            return treeify(className, children);
        }
    }
    
    private static boolean isBasic(String className) {
        switch (className) {
            case "java.lang.String":
            case "java.lang.StringBuffer":
            case "java.lang.StringBuilder":
            case "java.lang.Integer":
            case "java.lang.Double":
            case "java.lang.Boolean":
            case "java.lang.Long":
            case "java.lang.Character":
            case "java.lang.Short":
            case "java.lang.Byte":
            case "java.lang.Float":
                return true;
            default:
                return false;
        }
    }
    
    private void writeJson(String json, String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(HTML_TEMPLATE_PATH)));
                Writer writer = new FileWriter(fileName)
                ) {
            while(true) {
                String line = reader.readLine();
                if (line == null) break;
                if ("%JSON".equals(line)) {
                    line = json;
                } else {
                    line = line.replaceAll("%NAME", "Generic Tree");
                }
                writer.write(line + "\n");
            }
            writer.flush();
        }
    }

    private static String treeify(String parent, String... children) {
        String t = "text: { name: \"" + parent.replaceAll("\\\"", Matcher.quoteReplacement("\\\"")) + "\" }";
        if (children.length > 0) {
            t = t + ", children: [";
            t = t + "{ " + children[0] + " }";
            for (int i = 1; i < children.length; ++i) {
                t = t + "," + "{ " + children[i] + " }";
            }
            t = t + "]";
        }
        return t;
    }
    
    public static void main(String[] args) throws IOException {
        // see what the internal structure of a HashMap looks like!
        java.util.Map<String, int[]> map = new java.util.HashMap<>();
        map.put("key1", new int[] {99,100});
        map.put("key2", new int[] {2,3,5,7,11});
        makeHtml("temp.html", map);
    }

}
