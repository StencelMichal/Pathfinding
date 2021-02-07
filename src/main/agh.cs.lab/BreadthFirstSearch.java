package agh.cs.lab;

import java.util.*;

public class BreadthFirstSearch extends PathfindingAlgorithm {

    private final Queue<Vector2d> queue = new LinkedList<>();

    private final Set<Vector2d> visited = new HashSet<>();


    public BreadthFirstSearch(HashMap<Vector2d, ArrayList<Vector2d>> graph, Vector2d source, Vector2d target) {
        super(graph, source, target);
        queue.add(source);
        visited.add(source);

    }

    @Override
    public boolean Finished() {
        return reachedTarget || queue.isEmpty();
    }



    @Override
    public void nextStep() {
        currentNode = queue.remove();
        if(currentNode.equals(target)){
            reachedTarget = true;
        }
        if(graph == null){
            System.out.println("graph jest nullem");
        }
        if(currentNode == null){
            System.out.println("current jest nullem");
        }
        if(graph.get(currentNode) == null){
            System.out.println("zaweira nulla");
        }
        for (Vector2d node : graph.get(currentNode)) {
            if (!visited.contains(node)){
                visited.add(node);
                parents.put(node, currentNode);
                queue.add(node);
            }
        }

    }
}
