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
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.user.client.Window;

public class RectangleClickViewComponent extends AbstractToolBarViewComponent
{
    public RectangleClickViewComponent()
    {
        final Layer layer = new Layer();

        int newXPosition = 0, positionX = 10;

        int newYPosition = 0, positionY = 10;

        int c = 0;
        
        for (int i = 1; i <= 50; i++)
        {
            for (int j = 1; j <= 50; j++)
            {
                final Rectangle rectangle = new Rectangle(12.5, 12.5);

                rectangle.setX(positionX + newXPosition).setY(positionY + newYPosition);

                final String bgcolor = Color.getRandomHexColor();

                c++;
                
                rectangle.setFillColor(bgcolor);

                try
                {
                layer.add(rectangle);
                }
                catch(Exception e)
                {
                    Window.alert("OOPS " + c + " " + e.getMessage());
                    
                    return;
                }
                rectangle.addNodeMouseClickHandler(new NodeMouseClickHandler()
                {
                    @Override
                    public void onNodeMouseClick(NodeMouseClickEvent event)
                    {
                        getLienzoPanel().setBackgroundColor(bgcolor);
                    }
                });
                newXPosition += 15;
            }
            newYPosition += 15;

            newXPosition = 0;
        }
        getLienzoPanel().setBackgroundColor(ColorName.WHITE);

        getLienzoPanel().add(layer);

        getWorkingContainer().add(getLienzoPanel());
    }
}
