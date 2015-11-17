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

import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.ks.client.LienzoKS;
import com.ait.lienzo.shared.core.types.ColorName;

public class BluePrintBackgroundLayer extends GridLayer
{
    public BluePrintBackgroundLayer()
    {
        super(20, new Line().setAlpha(0.2).setStrokeWidth(1).setStrokeColor(ColorName.WHITE));

        setTransformable(false).setListening(false);

        getCanvasElement().getStyle().setBackgroundColor(LienzoKS.KSBLUE);
    }
}
