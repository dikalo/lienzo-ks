/*
   Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.ait.lienzo.ks.client.views.components;

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.wires.WiresLayer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.tooling.nativetools.client.util.Console;

public class WiresSquaresViewComponent extends AbstractViewComponent
{
    public WiresSquaresViewComponent()
    {
        Layer layer = new Layer();

        WiresManager wires_manager = WiresManager.get(layer);

        double w = 100;

        double h = 100;

        WiresShape wiresShape0 = wires_manager.createShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CC0000")));

        wiresShape0.getGroup().setX(400).setY(400).setDraggable(true);

        WiresShape wiresShape1 = wires_manager.createShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#00CC00")));

        wiresShape1.getGroup().setX(400).setY(50).setDraggable(true);

        WiresShape wiresShape2 = wires_manager.createShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#0000CC")));

        wiresShape2.getGroup().setX(750).setY(400).setDraggable(true);

        WiresShape wiresShape3 = wires_manager.createShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CCCC00")));

        wiresShape3.getGroup().setX(400).setY(700).setDraggable(true);

        WiresShape wiresShape4 = wires_manager.createShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CC00CC")));

        wiresShape4.getGroup().setX(50).setY(400).setDraggable(true);

        wires_manager.createMagnets(wiresShape0);

        wires_manager.createMagnets(wiresShape1);

        wires_manager.createMagnets(wiresShape2);

        wires_manager.createMagnets(wiresShape3);

        wires_manager.createMagnets(wiresShape4);

        WiresLayer wiresLayer = wires_manager.getLayer();

        wiresLayer.add(wiresShape0);

        wiresLayer.add(wiresShape1);

        wiresLayer.add(wiresShape2);

        wiresLayer.add(wiresShape3);

        wiresLayer.add(wiresShape4);

        connect(layer, wiresShape1, 4, 5, 6, wiresShape0, 2, 1, 8, wires_manager);

        connect(layer, wiresShape2, 6, 7, 8, wiresShape0, 4, 3, 2, wires_manager);

        connect(layer, wiresShape3, 8, 1, 2, wiresShape0, 6, 5, 4, wires_manager);

        connect(layer, wiresShape4, 2, 3, 4, wiresShape0, 8, 7, 6, wires_manager);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private MultiPath click(final MultiPath path)
    {
        path.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {

            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                Console.get().info("click");
            }
        });
        return path;
    }

    private void connect(Layer layer, WiresShape shape0, int i0_0, int i0_1, int i0_2, WiresShape shape1, int i1_0, int i1_1, int i1_2, WiresManager wires_manager)
    {
        WiresMagnet m0_0 = shape0.getMagnets().getMagnet(i0_0);

        WiresMagnet m0_1 = shape0.getMagnets().getMagnet(i0_1);

        WiresMagnet m0_2 = shape0.getMagnets().getMagnet(i0_2);

        WiresMagnet m1_0 = shape1.getMagnets().getMagnet(i1_0);

        WiresMagnet m1_1 = shape1.getMagnets().getMagnet(i1_1);

        WiresMagnet m1_2 = shape1.getMagnets().getMagnet(i1_2);

        double x0, x1, y0, y1;

        double x0multi = 1;

        double x1multi = 1;

        double y0multi = 1;

        double y1multi = 1;

        x0 = m0_0.getControl().getX();

        y0 = m0_0.getControl().getY();

        x1 = m1_0.getControl().getX();

        y1 = m1_0.getControl().getY();

        if (y0 == y1)
        {
            x0multi = 0.9;

            x1multi = 1.1;
        }
        else
        {
            y0multi = 0.9;

            y1multi = 1.1;
        }
        OrthogonalPolyLine line;

        line = createLine(layer, 0, 0, x0, y0, (x0 + ((x1 - x0) / 2)) * x0multi, (y0 + ((y1 - y0) / 2)) * y0multi, x1, y1);

        wires_manager.createConnector(m0_0, m1_0, line);

        x0 = m0_1.getControl().getX();

        y0 = m0_1.getControl().getY();

        x1 = m1_1.getControl().getX();

        y1 = m1_1.getControl().getY();

        line = createLine(layer, 0, 0, x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);

        wires_manager.createConnector(m0_1, m1_1, line);

        x0 = m0_2.getControl().getX();

        y0 = m0_2.getControl().getY();

        x1 = m1_2.getControl().getX();

        y1 = m1_2.getControl().getY();

        line = createLine(layer, 0, 0, x0, y0, x0 + ((x1 - x0) / 2) * x1multi, y0 + ((y1 - y0) / 2) * y1multi, x1, y1);

        wires_manager.createConnector(m0_2, m1_2, line);
    }

    public static OrthogonalPolyLine createLine(final Layer layer, final double x, final double y, double... points)
    {
        final Point2DArray array = Point2DArray.fromArrayOfDouble(points);

        final OrthogonalPolyLine line = new OrthogonalPolyLine(array);

        line.setCornerRadius(5);

        line.setX(x).setY(y).setStrokeWidth(5).setStrokeColor("#0000CC");

        line.setDraggable(true);

        layer.add(line);

        return line;
    }
}
