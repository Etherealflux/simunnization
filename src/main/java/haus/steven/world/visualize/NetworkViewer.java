package haus.steven.world.visualize;

import com.github.ajalt.colormath.LAB;
import com.github.ajalt.colormath.RGB;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import haus.steven.actors.Entity;
import haus.steven.spreading.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkViewer extends JFrame {
    private static final Logger logger = LogManager.getLogger();

    private final LAB susceptibleColor = new RGB(0, 255, 0).toLAB();
    private final LAB infectedColor = new RGB(255, 0, 0).toLAB();
    private final LAB recoveredColor = new RGB(0, 0, 255).toLAB();

    private final List<LAB> colors = new ArrayList<>(Arrays.asList(susceptibleColor, infectedColor, recoveredColor));

    private final mxGraph graph;
    private final mxGraphComponent graphComponent;

    public NetworkViewer(mxGraph graph)
    {
        super("Network view");
        this.graph = graph;


        Object parent = graph.getDefaultParent();

        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_NOLABEL, "1");
        graph.getModel().beginUpdate();

        try {
            graph.clearSelection();
            graph.selectAll();

            Object[] cells = graph.getSelectionCells();

            for (Object c: cells) {
                mxCell cell = (mxCell) c;

                mxGeometry geometry = cell.getGeometry();

                if (cell.isVertex()) {
                    Entity entity = (Entity) cell.getValue();
                    geometry.setHeight(20);
                    geometry.setWidth(20);

                    double sC = entity.count(State.SUSCEPTIBLE);
                    double iC = entity.count(State.INFECTED);
                    double rC = entity.count(State.RECOVERED);

                    List<Double> coefficients = new ArrayList<>(Arrays.asList(sC, iC, rC));

                    RGB color = mixColors(colors, coefficients).toRGB();

                    cell.setStyle("defaultVertex;fillColor=" + color.toHex(true));
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }

        mxIGraphLayout layout = new mxFastOrganicLayout(graph);
        layout.execute(graph.getDefaultParent());

        graphComponent = new mxGraphComponent(graph);
        graphComponent.refresh();
        getContentPane().add(graphComponent);
    }

    private LAB mixColors(List<LAB> colors, List<Double> coefficients) {
        double A = 0;
        double B = 0;
        double L = 0;

        double sum = 0 ;

        for (int i = 0; i < colors.size(); i++) {
            A += colors.get(i).getA() * coefficients.get(i);
            B += colors.get(i).getB() * coefficients.get(i);
            L += colors.get(i).getL() * coefficients.get(i);
            sum += coefficients.get(i);
        }

        A /= sum;
        B /= sum;
        L /= sum;

        return new LAB(L, A, B);
    }

    public void update() {
        graph.getModel().beginUpdate();

        try {
            graph.clearSelection();
            graph.selectAll();

            Object[] cells = graph.getSelectionCells();

            for (Object c: cells) {
                mxCell cell = (mxCell) c;

                mxGeometry geometry = cell.getGeometry();

                if (cell.isVertex()) {
                    Entity entity = (Entity) cell.getValue();
                    geometry.setHeight(20);
                    geometry.setWidth(20);

                    double sC = entity.count(State.SUSCEPTIBLE);
                    double iC = entity.count(State.INFECTED);
                    double rC = entity.count(State.RECOVERED);

                    List<Double> coefficients = new ArrayList<>(Arrays.asList(sC, iC, rC));

                    RGB color = mixColors(colors, coefficients).toRGB();

                    cell.setStyle("defaultVertex;fillColor=" + color.toHex(true));
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }

        graph.refresh();
    }
}
