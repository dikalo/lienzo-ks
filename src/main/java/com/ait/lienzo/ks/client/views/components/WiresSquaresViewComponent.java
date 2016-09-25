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
import com.ait.lienzo.client.core.shape.*;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.tooling.nativetools.client.util.Console;

import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.*;

public class WiresSquaresViewComponent extends AbstractViewComponent
{
    public WiresSquaresViewComponent()
    {
        Layer layer = new Layer();

        WiresManager wires_manager = WiresManager.get(layer);

        double w = 100;

        double h = 100;

        double radius = 25;

        WiresShape wiresShape0 =
                new WiresShape( click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CC0000")) )
                        .setX(400)
                        .setY(200)
                        .setDraggable(true)
                        .addChild(new Circle(radius).setFillColor( "#CC0000" ), CENTER);

        wires_manager.register( wiresShape0 );
        wires_manager.getMagnetManager().createMagnets(wiresShape0);

        WiresShape wiresShape1 =
                new WiresShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#00CC00")))
                        .setX(400)
                        .setY(50)
                        .setDraggable(true)
                        .addChild(new Circle(radius).setFillColor( "#00CC00" ), TOP);

        wires_manager.register( wiresShape1 );
        wires_manager.getMagnetManager().createMagnets(wiresShape1);

        WiresShape wiresShape2 =
                new WiresShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#0000CC")))
                        .setX(750)
                        .setY(200)
                        .setDraggable(true)
                        .addChild(new Circle(radius).setFillColor( "#0000CC" ), RIGHT);

        wires_manager.register( wiresShape2 );
        wires_manager.getMagnetManager().createMagnets(wiresShape2);

        WiresShape wiresShape3 =
                new WiresShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CCCC00")))
                        .setX(400)
                        .setY(400)
                        .setDraggable(true)
                        .addChild(new Circle(radius).setFillColor( "#CCCC00" ), BOTTOM);

        wires_manager.register( wiresShape3 );
        wires_manager.getMagnetManager().createMagnets(wiresShape3);

        WiresShape wiresShape4 =
                new WiresShape(click(new MultiPath().rect(0, 0, w, h).setStrokeWidth(5).setStrokeColor("#CC00CC")))
                        .setX(50)
                        .setY(200)
                        .setDraggable(true)
                        .addChild(new Circle(radius).setFillColor( "#CC00CC" ), LEFT);

        wires_manager.register( wiresShape4 );
        wires_manager.getMagnetManager().createMagnets(wiresShape4);

        connect(layer, wiresShape1.getMagnets(), 4, wiresShape0.getMagnets(), 2, "#00CC00", wires_manager);
        connect(layer, wiresShape1.getMagnets(), 5, wiresShape0.getMagnets(), 1, "#00CC00", wires_manager);
        connect(layer, wiresShape1.getMagnets(), 6, wiresShape0.getMagnets(), 8, "#00CC00", wires_manager);

        connect(layer, wiresShape2.getMagnets(), 6, wiresShape0.getMagnets(), 4, "#0000CC", wires_manager);
        connect(layer, wiresShape2.getMagnets(), 7, wiresShape0.getMagnets(), 3, "#0000CC", wires_manager);
        connect(layer, wiresShape2.getMagnets(), 8, wiresShape0.getMagnets(), 2, "#0000CC", wires_manager);

        connect(layer, wiresShape3.getMagnets(), 8, wiresShape0.getMagnets(), 6, "#CCCC00", wires_manager);
        connect(layer, wiresShape3.getMagnets(), 1, wiresShape0.getMagnets(), 5, "#CCCC00", wires_manager);
        connect(layer, wiresShape3.getMagnets(), 2, wiresShape0.getMagnets(), 4, "#CCCC00", wires_manager);

        connect(layer, wiresShape4.getMagnets(), 2, wiresShape0.getMagnets(), 8, "#CC00CC", wires_manager);
        connect(layer, wiresShape4.getMagnets(), 3, wiresShape0.getMagnets(), 7, "#CC00CC", wires_manager);
        connect(layer, wiresShape4.getMagnets(), 4, wiresShape0.getMagnets(), 6, "#CC00CC", wires_manager);

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

    private void connect( Layer layer, MagnetManager.Magnets magnets0, int i0_1,
                          MagnetManager.Magnets magnets1, int i1_1,
                          String color, WiresManager wiresManager)
    {
        WiresMagnet m0_1 = (WiresMagnet) magnets0.getMagnet(i0_1);
        WiresMagnet m1_1 = (WiresMagnet) magnets1.getMagnet(i1_1);

        double x0, x1, y0, y1;

        MultiPath head = new MultiPath();
        head.M(15, 20);
        head.L(0, 20);
        head.L(15 / 2, 0);
        head.Z();

        MultiPath tail = new MultiPath();
        tail.M(15, 20);
        tail.L(0, 20);
        tail.L(15 / 2, 0);
        tail.Z();

        OrthogonalPolyLine line;
        x0 = m0_1.getControl().getX();
        y0 = m0_1.getControl().getY();
        x1 = m1_1.getControl().getX();
        y1 = m1_1.getControl().getY();
        line = createLine(layer, 0, 0, x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        line.setHeadOffset(head.getBoundingBox().getHeight());
        line.setTailOffset(tail.getBoundingBox().getHeight());

        WiresConnector connector = new WiresConnector(m0_1, m1_1, line, new MultiPathDecorator(head), new MultiPathDecorator(tail));
        wiresManager.register( connector );

        head.setStrokeWidth(5).setStrokeColor(color);
        tail.setStrokeWidth(5).setStrokeColor(color);
        line.setStrokeWidth(5).setStrokeColor(color);
    }

    private final OrthogonalPolyLine createLine(Layer layer, double x, double y, final double... points)
    {
        return new OrthogonalPolyLine( Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }


}
