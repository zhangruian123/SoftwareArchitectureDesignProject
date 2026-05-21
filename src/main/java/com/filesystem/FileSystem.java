package com.filesystem;

import com.filesystem.context.SizeContext;
import com.filesystem.node.Directory;
import com.filesystem.node.FileNode;
import com.filesystem.node.Node;
import com.filesystem.path.InvalidPathException;
import com.filesystem.path.PathUtil;

import java.util.ArrayList;
import java.util.List;

public class FileSystem {
    private final Directory root;
    private final SizeContext sizeContext;

    public FileSystem() {
        root = new Directory("/");
        sizeContext = new SizeContext();
    }

    public void mkdir(String path) {
        List<String> segments;
        try {
            segments = PathUtil.parse(path);
        } catch (InvalidPathException e) {
            return;
        }
        if (segments.isEmpty()) {
            return; // root already exists
        }

        String name = segments.get(segments.size() - 1);
        List<String> parentSegments = segments.subList(0, segments.size() - 1);

        Node parent = navigate(parentSegments);
        if (parent == null || !parent.isDirectory()) {
            return;
        }

        Directory dir = (Directory) parent;
        Node existing = dir.getChild(name);
        if (existing != null && existing.isDirectory()) {
            return; // already a directory, keep as-is (no error)
        }

        dir.addChild(new Directory(name));
    }

    public void touch(String path, int size) {
        List<String> segments;
        try {
            segments = PathUtil.parse(path);
        } catch (InvalidPathException e) {
            return;
        }
        if (segments.isEmpty()) {
            return; // cannot touch root
        }

        String name = segments.get(segments.size() - 1);
        List<String> parentSegments = segments.subList(0, segments.size() - 1);

        Node parent = navigate(parentSegments);
        if (parent == null || !parent.isDirectory()) {
            return;
        }

        Directory dir = (Directory) parent;
        Node existing = dir.getChild(name);
        if (existing != null && !existing.isDirectory()) {
            ((FileNode) existing).setSize(size);
            return; // overwrite existing file
        }

        dir.addChild(new FileNode(name, size));
    }

    public List<String> ls(String path) {
        List<String> segments = PathUtil.parse(path);
        Node target = navigate(segments);

        List<String> result = new ArrayList<>();
        if (target.isDirectory()) {
            Directory dir = (Directory) target;
            for (Node child : dir.getChildren()) {
                result.add(child.getName());
            }
        } else {
            result.add(target.getName());
        }
        return result;
    }

    public int info(String path) {
        List<String> segments = PathUtil.parse(path);
        Node target = navigate(segments);
        return target.getSize(sizeContext);
    }

    private Node navigate(List<String> segments) {
        Node current = root;
        for (String segment : segments) {
            if (!current.isDirectory()) {
                return null;
            }
            current = ((Directory) current).getChild(segment);
            if (current == null) {
                return null;
            }
        }
        return current;
    }
}
