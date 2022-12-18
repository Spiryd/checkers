package org.checkers;

public class Translator {
    public int[] translateCoords(String message){
        String[] split = message.split(";");
        int[] coords = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            coords[i] = Integer.parseInt(split[i]);
        }
        return coords;
    }
}
