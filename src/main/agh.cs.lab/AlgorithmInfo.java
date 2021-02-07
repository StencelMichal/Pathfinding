package agh.cs.lab;

public class AlgorithmInfo {

    Vector2d source;
    Vector2d target;

    Class algorithm;

    public AlgorithmInfo(Vector2d source, Vector2d target, Class algorithm) {
        this.source = source;
        this.target = target;
        this.algorithm = algorithm;
    }

    public Vector2d getSource() {
        return source;
    }

    public Vector2d getTarget() {
        return target;
    }

    public Class getAlgorithm() {
        return algorithm;
    }
}
