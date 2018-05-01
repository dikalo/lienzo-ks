/*
 * Copyright (c) 2018 Ahome' Innovation Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ait.lienzo.ks.client.views.components;

import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.AlignAndDistribute;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

public class AlignDistributeViewComponent extends AbstractViewComponent
{
    public AlignDistributeViewComponent()
    {
        final Layer layer = new Layer();

        final Rectangle rect1 = new Rectangle(100, 100);
        rect1.setDraggable(true);
        rect1.setX(100);
        rect1.setY(300);
        rect1.setStrokeWidth(2);
        rect1.setFillColor("#CC0000");
        rect1.setFillAlpha(0.75);
        rect1.setStrokeColor(ColorName.BLACK);
        layer.add(rect1);

        final Circle circ1 = new Circle(50);
        circ1.setDraggable(true);
        circ1.setX(320);
        circ1.setY(325);
        circ1.setStrokeWidth(2);
        circ1.setFillColor("#00CC00");
        circ1.setFillAlpha(0.75);
        circ1.setStrokeColor(ColorName.BLACK);
        layer.add(circ1);

        final Rectangle rect3 = new Rectangle(100, 100);
        rect3.setDraggable(true);
        rect3.setX(500);
        rect3.setY(250);
        rect3.setStrokeWidth(2);
        rect3.setFillColor("#AACC00");
        rect3.setFillAlpha(0.75);
        rect3.setStrokeColor(ColorName.BLACK);
        layer.add(rect3);

        final Rectangle rect4 = new Rectangle(300, 150);
        rect4.setCornerRadius(8);
        rect4.setDraggable(true);
        rect4.setX(50);
        rect4.setY(50);
        rect4.setStrokeWidth(2);
        rect4.setFillColor("#55CCAA");
        rect4.setFillAlpha(0.75);
        rect4.setStrokeColor(ColorName.BLACK);
        layer.add(rect4);

        final Text text1 = new Text("Align");
        text1.setDraggable(true);
        text1.setX(500);
        text1.setY(500);
        text1.setFontSize(96);
        text1.setStrokeWidth(2);
        text1.setFillColor(ColorName.HOTPINK);
        text1.setFontStyle("bold");
        text1.setFillAlpha(0.75);
        text1.setStrokeColor(ColorName.BLACK);
        layer.add(text1);

        final Star star1 = new Star(5, 50, 100);
        star1.setDraggable(true);
        star1.setX(250);
        star1.setY(550);
        star1.setStrokeWidth(2);
        star1.setFillColor(ColorName.DARKORCHID);
        star1.setFillAlpha(0.75);
        star1.setStrokeColor(ColorName.BLACK);
        layer.add(star1);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());

        final AlignAndDistribute index = new AlignAndDistribute(layer);
        index.setStrokeWidth(2);
        index.setStrokeColor(ColorName.DARKBLUE.getValue());
        index.addShape(rect1);
        index.addShape(circ1);
        index.addShape(rect3);
        index.addShape(rect4);
        index.addShape(text1);
        index.addShape(star1);
    }
}
