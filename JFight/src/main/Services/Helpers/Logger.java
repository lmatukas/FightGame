package main.Services.Helpers;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Logger {

    private Logger(){}

    public static void error(String message) {
        try (FileWriter fw = new FileWriter(pathFinder("main/External/Logger.txt"), true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date()) + " Error -> " + message);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    private static String pathFinder(String relativePath) {
        String path = new File(Logger.class.getClassLoader().getResource(relativePath).getFile()).getAbsolutePath();
        System.out.println(path);
        String[] absolutePathArr = path.split("\\\\");
        StringBuilder newPath = new StringBuilder();
        boolean targetIsFound = false;
        for (int i = 0; i < absolutePathArr.length; i++) {
            if (absolutePathArr[i].equals("target")) {
                targetIsFound = true;
                newPath.append("src/");
            }
            if (!targetIsFound) {
                newPath.append(absolutePathArr[i]).append("/");
            }
        }
        newPath.append(relativePath);
        System.out.println(newPath.toString());
        return newPath.toString();
    }
}
