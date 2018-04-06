package com.cloud.support;

import org.apache.commons.lang.StringUtils;

import java.util.Comparator;

/**
 * @author summer
 */
public class NumberStringComparator implements Comparator<String> {
    private int strlen;

    public NumberStringComparator(int strlen) {
        this.strlen = strlen;
    }

    @Override
    public int compare(String str1, String str2) {
        str1 = StringUtils.leftPad(str1, strlen, '0');
        str2 = StringUtils.leftPad(str2, strlen, '0');
        return str1.compareTo(str2);
    }
}
