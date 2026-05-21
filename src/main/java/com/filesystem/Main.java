package com.filesystem;

import com.filesystem.path.InvalidPathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FileSystem fs = new FileSystem();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            processCommand(fs, line);
        }
    }

    private static void processCommand(FileSystem fs, String line) {
        String[] parts = line.split(" ");
        if (parts.length == 0) {
            return;
        }

        String cmd = parts[0];

        try {
            switch (cmd) {
                case "MKDIR":
                    if (parts.length == 2) {
                        fs.mkdir(parts[1]);
                    }
                    break;
                case "TOUCH":
                    if (parts.length == 3) {
                        int size = Integer.parseInt(parts[2]);
                        fs.touch(parts[1], size);
                    }
                    break;
                case "LS":
                    if (parts.length == 2) {
                        List<String> result = fs.ls(parts[1]);
                        for (String name : result) {
                            System.out.println(name);
                        }
                    }
                    break;
                case "INFO":
                    if (parts.length == 2) {
                        System.out.println(fs.info(parts[1]));
                    }
                    break;
                default:
                    // unknown command, ignore
                    break;
            }
        } catch (InvalidPathException | NumberFormatException e) {
            // invalid path or size — silently ignore
        }
    }
}
