package haus.steven.world.visualize;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;

public class NetworkViewer extends JFrame {
    public NetworkViewer(mxGraph graph)
    {
        super("Hello, World!");

        Object parent = graph.getDefaultParent();

        mxIGraphLayout layout = new mxFastOrganicLayout(graph);
        layout.execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}
