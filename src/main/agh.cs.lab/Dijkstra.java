package agh.cs.lab;

import javafx.util.Pair;

import java.util.*;

public class Dijkstra extends PathfindingAlgorithm {


    private final PriorityQueue<Pair<Vector2d, Integer>> queue
            = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue));

    private final HashMap<Vector2d, Integer> distance = new HashMap<>();

    public Dijkstra(HashMap<Vector2d, ArrayList<Vector2d>> graph, Vector2d source, Vector2d target) {
        super(graph, source, target);
        for (Vector2d node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
        }
        queue.add(new Pair<>(source, 0));
        distance.put(source, 0);
    }

    @Override
    boolean Finished() {
            return reachedTarget || queue.isEmpty();
    }

    @Override
    void nextStep() {
        currentNode = queue.remove().getKey();
        if (currentNode.equals(target)) {
            reachedTarget = true;
        }
        for (Vector2d node : graph.get(currentNode)) {
            if (distance.get(node) > distance.get(currentNode) + 1) {
                distance.put(node, distance.get(currentNode) + 1);
                parents.put(node,currentNode);
                queue.add(new Pair<>(node, distance.get(node)));
            }
        }
    }
}
