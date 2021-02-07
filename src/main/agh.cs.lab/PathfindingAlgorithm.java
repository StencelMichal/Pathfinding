package agh.cs.lab;


import java.util.*;

public abstract class PathfindingAlgorithm {

    protected final Map<Vector2d,Vector2d> parents= new HashMap<>();
    protected final HashMap<Vector2d, ArrayList<Vector2d>> graph;

    protected final Vector2d target;
    protected final Vector2d source;
    protected Vector2d currentNode;

    protected boolean reachedTarget = false;


    public PathfindingAlgorithm(HashMap<Vector2d, ArrayList<Vector2d>> graph, Vector2d source, Vector2d target) {
        this.graph = graph;
        this.currentNode = source;
        this.target = target;
        this.source = source;
    }

    abstract boolean Finished();

    public List<Vector2d> getPath(){
        if(!Finished()){
            throw new IllegalStateException("Cannot create path because algorithm didn't finished its job");
        }
        List<Vector2d> path = new LinkedList<>();
        Vector2d current = this.target;
        while(!current.equals(source)){
            path.add(current);
            current = parents.get(current);
        }
        path.add(source);
        Collections.reverse(path);

        return path;
    }

    public boolean isReachedTarget() {
        return reachedTarget;
    }

    public Vector2d getCurrentNode() {
        return currentNode;
    }

    abstract void nextStep();


}
