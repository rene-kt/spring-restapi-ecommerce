package com.rene.ecommerce;

import java.util.List;

public class Utils {
    public static boolean isInOrder(List<Integer> list, boolean ascending) {
        if (ascending) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i) < list.get(i + 1)) {
                    return false;
                }
            }
        }
        return true;
    }

}
