package agh.cs.lab;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    MutableInt timeGap = new MutableInt(1000);

    public void start(Stage stage) throws Exception{
        Visualization visualization = new Visualization(stage, timeGap);
        new Thread(() -> {
            while (true) {
                while (!visualization.readyToVisualize()) {
                    Thread.onSpinWait();
                }

                AlgorithmInfo algorithmInfo = visualization.getAlgorithmInfo();

                PathfindingAlgorithm algorithm = null;
                Class<?> clazz = algorithmInfo.getAlgorithm();
                Constructor<?> ctor = null;
                try {
                    ctor = clazz.getConstructor(HashMap.class, Vector2d.class, Vector2d.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    assert ctor != null;
                    algorithm = (PathfindingAlgorithm) ctor.newInstance(new Object[]{visualization.constructGraph(),
                            algorithmInfo.getSource(), algorithmInfo.getTarget()});
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                while (!algorithm.Finished()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(timeGap.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    visualization.update(algorithm.getCurrentNode());
                    algorithm.nextStep();
                }

                if (algorithm.isReachedTarget()) {
                    List<Vector2d> path = algorithm.getPath();
                    try {
                        visualization.visualizePath(path);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Platform.runLater(visualization::noConnectionInfo);
                }
            visualization.waitForNextVisualization();
            }
        }).start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
