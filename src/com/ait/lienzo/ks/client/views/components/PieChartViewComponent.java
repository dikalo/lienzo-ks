/*
   Copyright (c) 2014 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;

public class PieChartViewComponent extends AbstractViewComponent
{
    public PieChartViewComponent()
    {
        LienzoPanel lienzo = new LienzoPanel();

        Layer layer = new Layer();

        PieChart chart = new PieChart(125, 50, 80, 22, 70, 30);

        chart.setDraggable(true);

        chart.setX(400);

        chart.setY(400);

        layer.add(chart);

        lienzo.add(layer);

        lienzo.setBackgroundLayer(new StandardBackgroundLayer());

        add(lienzo);
    }
}
