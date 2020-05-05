import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import plugin.FileLoader;

public class Main {
    public static void main(String[] args) {
        List<Class> list = new LinkedList<Class>(); //Buat nampung class yang implement FileLoader
       
        File f = new File("plugin");
        String names[] = f.list(); // List semua file di directory plugin
        names = Arrays.stream(names).filter(name -> (name.contains(".class") && (!name.contains("FileLoader")))).map(name -> name.replace(".class", "")).toArray(String[]::new); // Filter array plugin nya aja + hapus string ".class" - nya 

        try {
            Class[] intef; // Buat ngecek interfacenya di implement atau engga
            for (String name : names) {
                Class c = Class.forName("plugin."+ name); // Masukin classnya satu satu ke c
                intef = c.getInterfaces(); // Ambil semua interfacenya c
                for (Class itf : intef) {
                    if (itf.getName().equals("plugin.FileLoader")) { // Cek si c implement FileLoader atau engga, klo iya masukin ke linked list
                        list.add(c);
                        break;
                    } 
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        for (Class c : list) {
            try {
                @SuppressWarnings("unchecked")
                FileLoader temp = (FileLoader) c.getDeclaredConstructor().newInstance(); //Buat objek dari class yang udah di dapet
                System.out.println(temp.cetak()); //Panggil method cetak tiap classs
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }

        }
    }
}