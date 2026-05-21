package com.filesystem.node;

import com.filesystem.context.SizeContext;

public class FileNode extends Node {
    private int size;

    public FileNode(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public int getSize(SizeContext context) {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
