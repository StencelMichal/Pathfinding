package agh.cs.lab;

import java.util.*;

public class BidirectionalSearch extends PathfindingAlgorithm {

    private final Queue<Vector2d> queue1 = new LinkedList<>();
    private final Queue<Vector2d> queue2 = new LinkedList<>();

    private final Set<Vector2d> visited1 = new HashSet<>();
    private final Set<Vector2d> visited2 = new HashSet<>();

    private final Map<Vector2d,Vector2d> parentsFromTarget = new HashMap<>();

    private Vector2d commonNode;

    private NextTurn nextTurn = NextTurn.NODE_FROM_SOURCE;

    private enum NextTurn {
        NODE_FROM_SOURCE,
        NODE_FROM_TARGET
    }

    public BidirectionalSearch(HashMap<Vector2d, ArrayList<Vector2d>> graph, Vector2d source, Vector2d target) {
        super(graph, source, target);
        queue1.add(source);
        queue2.add(target);
        visited1.add(source);
        visited2.add(target);
    }

    @Override
    boolean Finished() {
        return reachedTarget || (queue1.isEmpty() && queue2.isEmpty());
    }

    @Override
    void nextStep() {
        switch (nextTurn){
            case NODE_FROM_SOURCE -> {
                if(!queue1.isEmpty()) {
                    nextNode(queue1, visited1, visited2, false);
                }
                nextTurn = NextTurn.NODE_FROM_TARGET;
            }
            case NODE_FROM_TARGET -> {
                if(!queue2.isEmpty()) {
                    nextNode(queue2, visited2, visited1, true);
                }
                nextTurn = NextTurn.NODE_FROM_SOURCE;
            }
        }
    }

    private void nextNode(Queue<Vector2d> queue, Set<Vector2d> visitedOwn,
                          Set<Vector2d> visitedOther, boolean backwards) {
        currentNode = queue.remove();
        if(visitedOther.contains(currentNode)){
            reachedTarget = true;
            commonNode = currentNode;
        }
        else{
            for(Vector2d node : graph.get(currentNode)){
                if(!visitedOwn.contains(node)){
                    if(backwards){
                        if(!parentsFromTarget.containsKey(node)){
                            parentsFromTarget.put(node, currentNode);
                        }
                    }
                    else{
                        if(!parents.containsKey(node)){
                            parents.put(node, currentNode);
                        }
                    }
                    queue.add(node);
                    visitedOwn.add(node);
                }
            }
        }
    }

    @Override
    public List<Vector2d> getPath() {
        if(!Finished()){
            throw new IllegalStateException("Cannot create path because algorithm didn't finished its job");
        }
        List<Vector2d> path = pathToNode(source, commonNode, parents);
        List<Vector2d> pathFromTarget = pathToNode(target, commonNode, parentsFromTarget);
        Collections.reverse(pathFromTarget);
        path.addAll(pathFromTarget);

        return path;
    }

    private List<Vector2d> pathToNode(Vector2d from, Vector2d to, Map<Vector2d,Vector2d> parents){
        List<Vector2d> path = new LinkedList<>();
        while(!to.equals(from)){
            path.add(to);
            to = parents.get(to);
        }
        path.add(from);

        Collections.reverse(path);
        return path;
    }
}

