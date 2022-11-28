package ch.fhnw.dist.util;

import ch.fhnw.dist.ResourceBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public final class FileUtil {

    private FileUtil() {
    }

    public static File getResourceFile(String filePath) {
        URL url = ResourceBase.class.getResource(filePath);
        if (url == null) {
            return null;
        }

        String absoluteFilePath = url.getPath();
        if (absoluteFilePath == null) {
            return null;
        }

        return new File(absoluteFilePath);
    }

    public static String[] getWords(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .filter(word -> word != null && !word.isEmpty())
                    .toArray(String[]::new);
        } catch (IOException e) {
            System.out.println("Could not read line from file");
        }

        return null;
    }
}