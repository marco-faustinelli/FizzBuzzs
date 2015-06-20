package com.onebip.fizzbuzz;

// TODO: remove these 90s-style templates

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by giorgio on 07/06/15.
 */
public class Number {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    public String fizzBuzz()
    {
        HashMap<Integer,String> map = new LinkedHashMap<Integer,String>();
        map.put(3, "Fizz");
        map.put(5, "Buzz");
        String result = "";
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (multipleOf(entry.getKey())) {
                result = result + entry.getValue();
            }
        }
        if (result != "") {
            return result;
        } else {
            return toString();
        }
    }

    public String toString() {
        return new Integer(value).toString();
    }

    private boolean multipleOf(int factor) {
        return value % factor == 0;
    }
}
