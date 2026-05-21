package com.filesystem.path;

import java.util.ArrayList;
import java.util.List;

public class PathUtil {

    private PathUtil() {}

    /**
     * Validates and splits an absolute path into segments.
     * Returns an empty list for root "/".
     *
     * @throws InvalidPathException if path is invalid
     */
    public static List<String> parse(String path) {
        if (path == null || path.isEmpty()) {
            throw new InvalidPathException();
        }
        if (!path.startsWith("/")) {
            throw new InvalidPathException();
        }
        if (path.contains("//")) {
            throw new InvalidPathException();
        }
        if (path.length() > 1 && path.endsWith("/")) {
            throw new InvalidPathException();
        }

        List<String> segments = new ArrayList<>();
        if (path.equals("/")) {
            return segments;
        }

        String[] parts = path.substring(1).split("/");
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".") || part.equals("..")) {
                throw new InvalidPathException();
            }
            segments.add(part);
        }
        return segments;
    }
}
