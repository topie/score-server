package com.orange.score.common.utils;

import java.util.Set;

/**
 * @author: CuiGaoshun
 * @Title: CollectionUtil
 * @Description:
 * @Date: 2019/1/24 0024
 */
public class CollectionUtil {
    public static boolean isHaveUnionBySet(Set set1, Set set2) {
        if (set1 == null || set2 == null) {
            return false;
        }

        int set1Size = set1.size();
        if (set1Size == 0) {
            return false;
        }
        int set2Size = set2.size();
        if (set2Size == 0) {
            return false;
        }

        if (set1 == set2) {
            return true;
        }

        if (set1Size >= set2Size) {
            for (Object o : set2) {
                if (set1.contains(o)) {
                    return true;
                }
            }
        } else {
            for (Object o : set1) {
                if (set2.contains(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer[] StringToIntegerArr(String str) {
        String[] strArray = str.split(",");
        Integer[] integetArray = new Integer[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            integetArray[i] = Integer.parseInt(strArray[i]);
        }
        return integetArray;
    }
}
