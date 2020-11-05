package com.example.javaio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class mian {

    public static void mian(String[] args) {
        try {
            OutputStream outputStream = new FileOutputStream("./app/text.txt");
            outputStream.write('a');
            outputStream.write('b');
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
