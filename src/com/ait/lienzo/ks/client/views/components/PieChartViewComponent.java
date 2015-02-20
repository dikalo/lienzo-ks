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

import com.ait.lienzo.charts.client.core.model.PieChartData;
import com.ait.lienzo.charts.client.core.pie.PieChart;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragMode;

public class PieChartViewComponent extends AbstractViewComponent
{
    public PieChartViewComponent()
    {
        Layer layer = new Layer();

        PieChartData data = new PieChartData();

        data.add("Shoes", 50, ColorName.DEEPPINK);

        data.add("Shirts", 80, ColorName.YELLOW);

        data.add("Socks", 22, ColorName.SALMON);

        data.add("Jeans", 70, ColorName.CORNFLOWERBLUE);

        data.add("Ties", 30, ColorName.AQUA);

        PieChart chart = new PieChart(125, data);

        chart.setDraggable(true).setDragMode(DragMode.SAME_LAYER).setX(320).setY(220);
        
        layer.add(chart);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}
