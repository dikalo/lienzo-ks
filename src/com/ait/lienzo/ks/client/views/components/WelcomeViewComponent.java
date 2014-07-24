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

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.image.ImageCache;
import com.ait.lienzo.client.core.image.JSImage;
import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.PatternGradient;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.FillRepeat;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;

public class WelcomeViewComponent extends AbstractViewComponent
{
    private Text m_text = new Text("Lienzo 2.0").setStrokeWidth(3).setFontSize(3 * 48).setFontStyle("bold").setStrokeColor(ColorName.WHITE).setX(600).setY(400).setScale(1, 1).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);

    public WelcomeViewComponent()
    {
        LienzoPanel lienzo = new LienzoPanel();

        Layer layer = new Layer();

        JSImage crosshatch = ImageCache.get().find("crosshatch");

        if (null != crosshatch)
        {
            m_text.setFillGradient(new PatternGradient(crosshatch, FillRepeat.REPEAT)).setFillAlpha(0.50);
        }
        else
        {
            m_text.setFillColor(ColorName.WHITE.getColor().setA(0.20));
        }
        layer.add(m_text);

        lienzo.add(layer);

        lienzo.setBackgroundColor("#0433ff");

        lienzo.setBackgroundLayer(new GridLayer(20, new Line().setAlpha(0.2).setStrokeWidth(1).setStrokeColor(ColorName.WHITE)).setTransformable(false));

        add(lienzo);
    }

    @Override
    public boolean activate()
    {
        m_text.getLayer().setListening(false);

        m_text.animate(AnimationTweener.LINEAR, AnimationProperties.create(AnimationProperty.Properties.SCALE(-1, 1)), 1000, new AnimationCallback()
        {
            @Override
            public void onClose(IAnimation animation, IAnimationHandle handle)
            {
                m_text.animate(AnimationTweener.LINEAR, AnimationProperties.create(AnimationProperty.Properties.SCALE(1, 1)), 1000, new AnimationCallback()
                {
                    @Override
                    public void onClose(IAnimation animation, IAnimationHandle handle)
                    {
                        m_text.getLayer().setListening(true);

                        m_text.getLayer().draw();
                    }
                });
            }
        });
        return true;
    }
}
