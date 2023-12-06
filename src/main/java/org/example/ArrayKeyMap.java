package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArrayKeyMap {

    static class ArrayKey {
        private final Object[] array;

        public ArrayKey(Object[] array) {
            this.array = array;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ArrayKey arrayKey = (ArrayKey) o;
            return Arrays.equals(array, arrayKey.array);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(array);
        }

        // Optional: A method to retrieve the underlying array, if necessary
        public Object[] getArray() {
            return array;
        }
    }

    public static void main(String[] args) {
        Map<ArrayKey, String> map = new HashMap<>();

        // You can now use ArrayKey to store arrays as keys in your map.
        map.put(new ArrayKey(new Object[]{"Key1"}), "Value1");
        map.put(new ArrayKey(new Object[]{"Key2"}), "Value2");
        map.put(new ArrayKey(new Object[]{"Key3"}), "Value3");

        // Retrieve a value using an array
        String value = map.get(new ArrayKey(new Object[]{"Key1"}));
        System.out.println("Value: " + value);
    }
}
