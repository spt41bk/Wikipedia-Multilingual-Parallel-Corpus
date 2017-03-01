/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.alignment.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author s1420211
 */
public class Nbest {
    public static void nbest(){
        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{"abc", 0.5f});
        list.add(new Object[]{"de", 0.6f});
        list.add(new Object[]{"de", 0.5f});
//        List<Object[]> list2 = new ArrayList<>(list);
        Collections.sort(list, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                return -Float.compare((float) o1[1], (float) o2[1]);
            }
        });

//        for (Object[] t : list.subList(0, 2)) {
//            System.out.println(t[0] + " " + t[1]);
//        }
        for (Object[] t : list) {
            System.out.println(t[0] + " " + t[1]);
        }
    }
}
