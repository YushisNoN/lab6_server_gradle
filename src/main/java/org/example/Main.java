package org.example;
import utils.files.FileReader;
import utils.kernel.Kernel;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Kernel kernel = new Kernel();
        kernel.setCommands();
        if (args.length == 1) {
            try {
                FileReader.setFileName(args[0]);
            } catch (FileNotFoundException exception) {
                System.out.println(exception.getMessage());
            }
        }
        kernel.runProgram();
    }
}