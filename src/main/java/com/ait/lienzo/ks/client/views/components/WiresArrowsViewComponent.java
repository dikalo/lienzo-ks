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

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.SimpleArrow;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.shape.wires.IConnectionAcceptor;
import com.ait.lienzo.client.core.shape.wires.IContainmentAcceptor;
import com.ait.lienzo.client.core.shape.wires.MagnetManager.Magnets;
import com.ait.lienzo.client.core.shape.wires.WiresConnection;
import com.ait.lienzo.client.core.shape.wires.WiresConnector;
import com.ait.lienzo.client.core.shape.wires.WiresContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import com.ait.lienzo.client.core.shape.wires.WiresManager;
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;

public class WiresArrowsViewComponent extends AbstractViewComponent
{
    public WiresArrowsViewComponent()
    {
        Layer layer = new Layer();

        WiresManager wires_manager = WiresManager.get(layer);

        double w = 100;

        double h = 100;

        wires_manager.setConnectionAcceptor(new IConnectionAcceptor()
        {
            @Override
            public boolean headConnectionAllowed(WiresConnection head, WiresShape shape)
            {
                WiresConnection tail = head.getConnector().getTailConnection();

                WiresMagnet m = tail.getMagnet();

                if (m == null)
                {
                    return true;
                }
                return accept(shape.getGroup(), tail.getMagnet().getMagnets().getGroup());
            }

            @Override
            public boolean tailConnectionAllowed(WiresConnection tail, WiresShape shape)
            {
                WiresConnection head = tail.getConnector().getHeadConnection();

                WiresMagnet m = head.getMagnet();

                if (m == null)
                {
                    return true;
                }
                return accept(head.getMagnet().getMagnets().getGroup(), shape.getGroup());
            }

            @Override
            public boolean acceptHead(WiresConnection head, WiresMagnet magnet)
            {
                WiresConnection tail = head.getConnector().getTailConnection();

                WiresMagnet m = tail.getMagnet();

                if (m == null)
                {
                    return true;
                }
                return accept(magnet.getMagnets().getGroup(), tail.getMagnet().getMagnets().getGroup());
            }

            @Override
            public boolean acceptTail(WiresConnection tail, WiresMagnet magnet)
            {
                WiresConnection head = tail.getConnector().getHeadConnection();

                WiresMagnet m = head.getMagnet();

                if (m == null)
                {
                    return true;
                }
                return accept(head.getMagnet().getMagnets().getGroup(), magnet.getMagnets().getGroup());
            }

            private boolean accept(Group head, Group tail)
            {
                return head.getUserData().equals(tail.getUserData());
            }
        });

        // A shape can only contain shapes of different letters for UserData

        wires_manager.setContainmentAcceptor(new IContainmentAcceptor()
        {
            @Override
            public boolean containmentAllowed(WiresContainer parent, WiresShape child)
            {
                return acceptContainment(parent, child);
            }

            @Override
            public boolean acceptContainment(WiresContainer parent, WiresShape child)
            {
                if (parent.getParent() == null)
                {
                    return true;
                }
                return !parent.getContainer().getUserData().equals(child.getGroup().getUserData());
            }
        });
        WiresShape wiresShape0 = wires_manager.createShape(new MultiPath().rect(0, 0, w, h).setStrokeColor("#CC0000"));
        wiresShape0.getGroup().setX(400).setY(400).add(new Circle(30).setX(50).setY(50).setDraggable(true));
        wiresShape0.getGroup().setUserData("A");

        WiresShape wiresShape1 = wires_manager.createShape(new MultiPath().rect(0, 0, w, h).setStrokeColor("#00CC00"));
        wiresShape1.getGroup().setX(50).setY(50).add(new Star(5, 15, 40).setX(50).setY(55));
        wiresShape1.getGroup().setUserData("A");

        WiresShape wiresShape2 = wires_manager.createShape(new MultiPath().rect(0, 0, 300, 200).setStrokeColor("#0000CC"));
        wiresShape2.getGroup().setX(50).setY(100);
        wiresShape2.getGroup().setUserData("B");

        // bolt
        String svg = "M 0 100 L 65 115 L 65 105 L 120 125 L 120 115 L 200 180 L 140 160 L 140 170 L 85 150 L 85 160 L 0 140 Z";
        WiresShape wiresShape3 = wires_manager.createShape(new MultiPath(svg).setStrokeColor("#0000CC"));
        wiresShape3.getGroup().setX(50).setY(300);
        wiresShape3.getGroup().setUserData("B");

        wires_manager.createMagnets(wiresShape0);
        wires_manager.createMagnets(wiresShape1);
        wires_manager.createMagnets(wiresShape2);
        wires_manager.createMagnets(wiresShape3);

        WiresLayer wiresLayer = wires_manager.getLayer();
        wiresLayer.add(wiresShape0);
        wiresLayer.add(wiresShape2);
        wiresLayer.add(wiresShape1);
        wiresLayer.add(wiresShape3);

        connect(layer, wiresShape1.getMagnets(), 3, wiresShape0.getMagnets(), 7, wires_manager);

        wires_manager.registerShape(wiresShape0);
        wires_manager.registerShape(wiresShape1);
        wires_manager.registerShape(wiresShape2);
        wires_manager.registerShape(wiresShape3);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private void connect(Layer layer, Magnets magnets0, int i0_1, Magnets magnets1, int i1_1, WiresManager wires_manager)
    {
        WiresMagnet m0_1 = magnets0.getMagnet(i0_1);

        WiresMagnet m1_1 = magnets1.getMagnet(i1_1);

        double x0 = m0_1.getControl().getX();

        double y0 = m0_1.getControl().getY();

        double x1 = m1_1.getControl().getX();

        double y1 = m1_1.getControl().getY();

        OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);

        WiresConnector connector = wires_manager.createConnector(m0_1, m1_1, line, new SimpleArrow(20, 0.75), new SimpleArrow(20, 0.75));

        connector.getDecoratableLine().setStrokeWidth(5).setStrokeColor("#0000CC");
    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }
}
