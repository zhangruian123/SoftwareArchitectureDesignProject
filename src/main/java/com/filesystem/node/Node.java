package com.filesystem.node;

import com.filesystem.context.SizeContext;

public abstract class Node {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract boolean isDirectory();

    public abstract int getSize(SizeContext context);
}
