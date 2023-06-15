package org.hnxxxy.rg1b.utils.fileutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
    public static String fileRead(String fileName){
        BufferedReader is = null;
        StringBuilder txt = new StringBuilder();
            try {
            is = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = is.readLine()) != null){
                txt.append(line);
                txt.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return txt.toString();
    }
}
