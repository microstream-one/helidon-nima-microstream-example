package io.exampel.helidon.microstream;

import java.util.HashMap;

public class DataRoot {
    HashMap<String, String> dataMap;

    DataRoot() {
        dataMap = new HashMap<>();
    }

    public HashMap<String, String> dataMap1() {
        return dataMap;
    }

}
