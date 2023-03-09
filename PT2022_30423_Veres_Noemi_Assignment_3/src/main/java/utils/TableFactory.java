package utils;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class TableFactory {
    /** receives an object and generates the header of the table
     * by extracting through reflection the object properties */
    private static <E> String[] retrieveColumns(E object){
        int N = object.getClass().getDeclaredFields().length;
        String[] columnMatrix = new String[N];
        int i = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            columnMatrix[i] = field.getName();
            i++;
        }
        return columnMatrix;
    }
    /** receives a list of objects and populates the table with the values of the
     elements from the list */
    private static <E> String[][] retrieveData(ArrayList<E> objects, int length) throws IllegalAccessException {
        String[][] data = new String[objects.size()][length];
        int i = 0;
        for (E object : objects){
            int j = 0;
            for (Field field : object.getClass().getDeclaredFields()){
                field.setAccessible(true);
                data[i][j] = field.get(object).toString();
                j++;
                field.setAccessible(false);
            }
            i++;
        }
        return data;
    }
    /** creates a table out of the objects*/
    public static <E> DefaultTableModel  createTable(ArrayList<E> objects) throws IllegalAccessException {
        if(objects.size() == 0){
            return new DefaultTableModel();
        } else {
            String[] columns = retrieveColumns(objects.get(0));
            String[][] data = retrieveData(objects,columns.length);
            return new DefaultTableModel(data,columns);
        }
    }
}
