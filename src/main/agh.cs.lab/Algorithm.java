package agh.cs.lab;

public enum Algorithm {
    DIJKSTRA("Dijkstra", Dijkstra.class),
    BFS("Breadth First Search", BreadthFirstSearch.class),
    BIDIRECTIONAL_SEARCH("Bidirectional Search", BidirectionalSearch.class);

    private final Class algorithmsClass;
    private final String name;

    Algorithm(String name, Class className){
        this.algorithmsClass = className;
        this.name = name;
    }

    public Class getAlgorithmsClass() {
        return algorithmsClass;
    }


    @Override
    public String toString() {
        return name;
    }
}
