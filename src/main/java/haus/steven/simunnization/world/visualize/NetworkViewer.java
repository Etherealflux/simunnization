package haus.steven.simunnization.world.visualize;

import com.github.ajalt.colormath.ConvertibleColor;
import com.github.ajalt.colormath.LAB;
import com.github.ajalt.colormath.RGB;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import haus.steven.simunnization.actors.Entity;
import haus.steven.simunnization.spreading.Spreadable;
import haus.steven.simunnization.spreading.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class NetworkViewer extends JFrame {
    private static final Logger logger = LogManager.getLogger();

    private final mxGraph graph;
    private final mxGraphComponent graphComponent;

    public NetworkViewer(mxGraph graph) {
        super("Network view");
        this.graph = graph;


        Object parent = graph.getDefaultParent();

        graph.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
        graph.getStylesheet().getDefaultVertexStyle().put(mxConstants.STYLE_NOLABEL, "1");
        graph.getModel().beginUpdate();

        doColors(graph);

        mxIGraphLayout layout = new mxFastOrganicLayout(graph);
        layout.execute(graph.getDefaultParent());

        graphComponent = new mxGraphComponent(graph);
        graphComponent.refresh();
        getContentPane().add(graphComponent);
    }

    private void doColors(mxGraph graph) {
        try {
            graph.clearSelection();
            graph.selectAll();

            Object[] cells = graph.getSelectionCells();

            for (Object c : cells) {
                mxCell cell = (mxCell) c;

                mxGeometry geometry = cell.getGeometry();

                if (cell.isVertex()) {
                    Entity entity = (Entity) cell.getValue();
                    geometry.setHeight(20);
                    geometry.setWidth(20);

                    List<ConvertibleColor> colors = new ArrayList<>();
                    List<Double> coefficients = new ArrayList<>();

                    for (Spreadable spreadable : entity.getSpreadables()) {
                        for (State state : State.values()) {
                            colors.add(spreadable.colorFor(state));
                            coefficients.add((double) entity.count(state, spreadable));
                        }
                    }

                    RGB color;

                    logger.info(colors);

                    if (colors.size() == 0) {
                        color = new RGB(0, 0, 0);
                    } else {
                        color = mixColors(colors, coefficients).toRGB();
                    }


                    cell.setStyle("defaultVertex;fillColor=" + color.toHex(true));
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }

    }

    private LAB mixColors(List<ConvertibleColor> colors, List<Double> coefficients) {
        double A = 0;
        double B = 0;
        double L = 0;

        double sum = 0;

        for (int i = 0; i < colors.size(); i++) {
            LAB color = colors.get(i).toLAB();

            A += color.getA() * coefficients.get(i);
            B += color.getB() * coefficients.get(i);
            L += color.getL() * coefficients.get(i);

            sum += coefficients.get(i);
        }

        A /= sum;
        B /= sum;
        L /= sum;

        L = Math.max(Math.min(L, 100.0), 0.0);

        return new LAB(L, A, B);
    }

    public void update() {
        graph.getModel().beginUpdate();

        doColors(graph);

        graph.refresh();
    }
}
