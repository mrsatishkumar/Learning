package org.satish;

import java.util.*;

class PathFinder {
    private List<Edge> edges = new ArrayList<>();
    private Set<Character> nodeNames = new HashSet<>();
    private Map<Character, Node> nodes = new HashMap<>();
    private Node endNode;

    public void findPath(Character begin, Character end) {
        List<Character> unvisited = initializeSearch(begin, end);
        for (Character node = begin; node != null && !node.equals(end);
             node = getNext(unvisited)) {
            unvisited.remove(node);
            visit(node);
        }
        setupEndNode(end);
    }

    private List<Character> initializeSearch(Character begin,
                                             Character end) {
        nodeNames.add(begin);
        nodeNames.add(end);
        List<Character> unvisited = new ArrayList<>(nodeNames);
        for (Character node : unvisited)
            nodes.put(node, new Node(Integer.MAX_VALUE));

        nodes.get(begin).length = 0;
        return unvisited;
    }

    private void visit(Character node) {
        List<Edge> neighbors = findEdges(node);
        Node curNode = nodes.get(node);
        for (Edge e : neighbors) {
            Node nbr = nodes.get(e.end);

            int newLength = curNode.length + e.length;
            if (nbr.length > newLength) {
                nbr.length = newLength;
                nbr.path = new ArrayList<Character>();
                nbr.path.addAll(curNode.path);
                nbr.path.add(node);
            }
        }
    }

    private void setupEndNode(Character end) {
        endNode = nodes.get(end);
        if (endNode.length != Integer.MAX_VALUE)
            endNode.path.add(end);
        else
            endNode.length = 0;
    }

    private Character getNext(List<Character> unvisited) {
        Character minNodeName = null;
        int minLength = Integer.MAX_VALUE;

        for (Character name : unvisited) {
            Node candidate = nodes.get(name);
            if (candidate.length < minLength) {
                minLength = candidate.length;
                minNodeName = name;
            }
        }
        return minNodeName;
    }

    private List<Edge> findEdges(Character begin) {
        List<Edge> found = new ArrayList<>();
        for (Edge e : edges) {
            if (e.begin.equals(begin))
                found.add(e);
        }
        return found;
    }

    public int getLength() {
        return endNode.length;
    }

    public List<Character> getPath() {
        return endNode.path;
    }

    public void addEdge(Character start, Character end, int length) {
        edges.add(new Edge(start, end, length));
        nodeNames.add(start);
        nodeNames.add(end);
    }

    private static class Edge {
        public final Character begin;
        public final Character end;
        public final int length;

        public Edge(Character begin, Character end, int length) {
            this.begin = begin;
            this.end = end;
            this.length = length;
        }
    }

    private static class Node {
        public int length;
        public List<Character> path;

        public Node(int l) {
            this.length = l;
            this.path = new ArrayList<>();
        }
    }
}