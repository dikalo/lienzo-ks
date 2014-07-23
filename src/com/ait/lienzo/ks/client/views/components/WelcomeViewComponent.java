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

import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

public class WelcomeViewComponent extends AbstractViewComponent
{
    private Text m_text = new Text("Hello Lienzo 2.0").setStrokeWidth(1).setFontSize(48).setStrokeColor(ColorName.WHITE).setX(200).setY(400).setScale(3).setFillColor(ColorName.WHITE.getColor().setA(0.20)).setDraggable(true);

    public WelcomeViewComponent()
    {
        LienzoPanel lienzo = new LienzoPanel();

        Layer layer = new Layer();

        layer.add(m_text);

        lienzo.add(layer);

        lienzo.setBackgroundColor("#0433ff");

        lienzo.setBackgroundLayer(new GridLayer(20, new Line().setAlpha(0.2).setStrokeWidth(1).setStrokeColor(ColorName.WHITE)).setTransformable(false));

        add(lienzo);
    }
}
