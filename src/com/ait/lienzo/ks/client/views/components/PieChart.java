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

import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Slice;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;

public class PieChart extends Group
{
    public PieChart(double radius, double... values)
    {
        if ((null == values) || (values.length < 1))
        {
            return;
        }
        double sofar = 0;
        
        double total = 0;

        for (int i = 0; i < values.length; i++)
        {
            total += values[i];
        }
        Group slices = new Group();
        
        Group labels = new Group();

        for (int i = 0; i < values.length; i++)
        {
            double value = values[i] / total;

            PieSlice slice = new PieSlice(radius, sofar, value);
            
            slice.setFillColor(Color.getRandomHexColor()).setStrokeColor(ColorName.BLACK).setStrokeWidth(3);
            
            slices.add(slice);

            double s_ang = Math.PI * (2.0 * sofar);
            
            double e_ang = Math.PI * (2.0 * (sofar + value));
            
            double n_ang = (s_ang + e_ang) / 2.0;

            if (n_ang > (Math.PI * 2.0))
            {
                n_ang = n_ang - (Math.PI * 2.0);
            }
            else if (n_ang < 0)
            {
                n_ang = n_ang + (Math.PI * 2.0);
            }
            double lx = Math.sin(n_ang) * (radius + 25);
            
            double ly = 0 - Math.cos(n_ang) * (radius + 25);

            TextAlign align;

            if (n_ang <= (Math.PI * 0.5))
            {
                lx += 2;
                
                align = TextAlign.LEFT;
            }
            else if ((n_ang > (Math.PI * 0.5)) && (n_ang <= Math.PI))
            {
                lx += 2;
                
                align = TextAlign.LEFT;
            }
            else if ((n_ang > Math.PI) && (n_ang <= (Math.PI * 1.5)))
            {
                lx -= 2;
                
                align = TextAlign.RIGHT;
            }
            else
            {
                lx -= 2;
                
                align = TextAlign.RIGHT;
            }
            Text text = new Text(getLabel(value * 100), "Calibri", 12).setStrokeColor(ColorName.BLACK).setStrokeWidth(0.75).setX(lx).setY(ly).setTextAlign(align).setTextBaseLine(TextBaseLine.MIDDLE);

            Line line = new Line((Math.sin(n_ang) * radius), 0 - (Math.cos(n_ang) * radius), (Math.sin(n_ang) * (radius + 25)), 0 - (Math.cos(n_ang) * (radius + 25))).setStrokeColor(ColorName.BLACK).setStrokeWidth(1);

            labels.add(text);
            
            labels.add(line);
            
            sofar += value;
        }
        add(slices);
        
        add(labels);
    }

    private final native String getLabel(double perc)
    /*-{
		var numb = perc;
		
		return numb.toFixed(2) + "%";
    }-*/;

    private static class PieSlice extends Slice
    {
        public PieSlice(double radius, double sofar, double value)
        {
            super(radius, Math.PI * (-0.5 + 2 * sofar), Math.PI * (-0.5 + 2 * (sofar + value)), false);
        }
    }
}