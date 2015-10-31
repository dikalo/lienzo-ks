/*
   Copyright (c) 2014,2015 Ahome' Innovation Technologies. All rights reserved.

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
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.shape.SimpleArrow;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.shape.wires.Connector;
import com.ait.lienzo.client.core.shape.wires.IMagnets;
import com.ait.lienzo.client.core.shape.wires.Magnet;
import com.ait.lienzo.client.core.shape.wires.WiresLayer;
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

        WiresShape wiresShape0 = wires_manager.createShape(new MultiPath().rect(0, 0, w, h).setStrokeColor("#CC0000"));
        
        wiresShape0.getGroup().setX(400).setY(400).add(new Circle(30).setX(50).setY(50).setDraggable(true));

        WiresShape wiresShape1 = wires_manager.createShape(new MultiPath().rect(0, 0, w, h).setStrokeColor("#00CC00"));
        
        wiresShape1.getGroup().setX(50).setY(50).add(new Star(5, 15, 40).setX(50).setY(55));

        WiresShape wiresShape2 = wires_manager.createShape(new MultiPath().rect(0, 0, 200, 150).setStrokeColor("#0000CC"));
        
        wiresShape2.getGroup().setX(250).setY(100);

        WiresShape wiresShape3 = wires_manager.createShape(new MultiPath("M 0 100 L 65 115 L 65 105 L 120 125 L 120 115 L 200 180 L 140 160 L 140 170 L 85 150 L 85 160 L 0 140 Z").setStrokeColor("#0000CC"));
        
        wiresShape3.getGroup().setX(50).setY(300);

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

        wires_manager.addToIndex(wiresShape0);
        
        wires_manager.addToIndex(wiresShape1);
        
        wires_manager.addToIndex(wiresShape2);
        
        wires_manager.addToIndex(wiresShape3);
        
        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private void connect(Layer layer, IMagnets magnets0, int i0_1, IMagnets magnets1, int i1_1, WiresManager wires_manager)
    {
        Magnet m0_1 = magnets0.getMagnet(i0_1);
        
        Magnet m1_1 = magnets1.getMagnet(i1_1);
        
        double x0 = m0_1.getControl().getX();
        
        double y0 = m0_1.getControl().getY();
        
        double x1 = m1_1.getControl().getX();
        
        double y1 = m1_1.getControl().getY();
        
        OrthogonalPolyLine line = createLine(x0, y0, (x0 + ((x1 - x0) / 2)), (y0 + ((y1 - y0) / 2)), x1, y1);
        
        Connector connector = wires_manager.createConnector(m0_1, m1_1, line, new SimpleArrow(20, 0.75), new SimpleArrow(20, 0.75));
        
        connector.getDecoratableLine().setStrokeWidth(5).setStrokeColor("#0000CC");
    }

    private final OrthogonalPolyLine createLine(final double... points)
    {
        return new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points)).setCornerRadius(5).setDraggable(true);
    }
}
