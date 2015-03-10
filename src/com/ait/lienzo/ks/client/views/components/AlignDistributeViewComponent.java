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
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.wires.AlignAndDistribute;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;

public class AlignDistributeViewComponent extends AbstractViewComponent
{
    public AlignDistributeViewComponent()
    {
        Layer layer = new Layer();

        AlignAndDistribute index = new AlignAndDistribute(layer);
        
        index.setStrokeWidth(2);

        Rectangle rect1 = new Rectangle(100, 100);
        rect1.setDraggable(true);
        rect1.setX(100);
        rect1.setY(300);
        rect1.setStrokeWidth(3);
        rect1.setFillColor("#CC0000");
        rect1.setStrokeColor("#CC0000");
        layer.add(rect1);

        Circle circ1 = new Circle(50);
        circ1.setDraggable(true);
        circ1.setX(320);
        circ1.setY(325);
        circ1.setStrokeWidth(3);
        circ1.setFillColor("#00CC00");
        circ1.setStrokeColor("#00CC00");
        layer.add(circ1);

        Rectangle rect3 = new Rectangle(100, 100);
        rect3.setDraggable(true);
        rect3.setX(500);
        rect3.setY(250);
        rect3.setStrokeWidth(3);
        rect3.setFillColor("#AACC00");
        rect3.setStrokeColor("#AACC00");
        layer.add(rect3);

        Rectangle rect4 = new Rectangle(300, 150);
        rect4.setDraggable(true);
        rect4.setX(50);
        rect4.setY(50);
        rect4.setStrokeWidth(3);
        rect4.setFillColor("#55CCAA");
        rect4.setStrokeColor("#55CCAA");
        layer.add(rect4);

        index.addShape(rect1);
        index.addShape(circ1);
        index.addShape(rect3);
        index.addShape(rect4);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}
