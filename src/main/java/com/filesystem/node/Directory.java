package com.filesystem.node;

import com.filesystem.context.SizeContext;

import java.util.Collection;
import java.util.TreeMap;

public class Directory extends Node {
    private final TreeMap<String, Node> children = new TreeMap<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public int getSize(SizeContext context) {
        int total = 0;
        for (Node child : children.values()) {
            total += child.getSize(context);
        }
        return total;
    }

    public void addChild(Node node) {
        children.put(node.getName(), node);
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public Collection<Node> getChildren() {
        return children.values();
    }
}
