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

import static com.ait.lienzo.client.core.animation.AnimationProperties.toPropertyList;
import static com.ait.lienzo.client.core.animation.AnimationProperty.Properties.SCALE;
import static com.ait.lienzo.client.core.animation.AnimationTweener.LINEAR;

import com.ait.lienzo.client.core.animation.AnimationCallback;
import com.ait.lienzo.client.core.animation.IAnimation;
import com.ait.lienzo.client.core.animation.IAnimationHandle;
import com.ait.lienzo.client.core.image.ImageLoader;
import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Line;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.PatternGradient;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.LienzoKS;
import com.ait.lienzo.ks.client.style.KSStyle;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.FillRepeat;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.dom.client.ImageElement;

public class WelcomeViewComponent extends AbstractViewComponent
{
    private final Text        m_text   = getText("Lienzo 2.0");

    private final LienzoPanel m_lienzo = new LienzoPanel();

    public WelcomeViewComponent()
    {
        final Layer layer = new Layer();

        new ImageLoader(KSStyle.get().crosshatch())
        {
            @Override
            public void onLoad(ImageElement image)
            {
                m_text.setFillGradient(new PatternGradient(image, FillRepeat.REPEAT)).setFillAlpha(0.50);

                layer.batch();
            }

            @Override
            public void onError(String message)
            {

            }
        };
        m_text.setFillColor(ColorName.WHITE.getColor().setA(0.20));

        layer.add(m_text);

        m_lienzo.add(layer);

        m_lienzo.setBackgroundColor(LienzoKS.KSBLUE);

        m_lienzo.setBackgroundLayer(new GridLayer(20, new Line().setAlpha(0.2).setStrokeWidth(1).setStrokeColor(ColorName.WHITE)).setTransformable(false));

        add(m_lienzo);
    }

    private final static Text getText(String label)
    {
        return new Text(label).setStrokeWidth(3).setFontSize(144).setFontStyle("bold").setStrokeColor(ColorName.WHITE).setX(600).setY(200).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE).setDraggable(true);
    }

    @Override
    public boolean activate()
    {
        m_text.getLayer().setListening(false);

        m_text.animate(LINEAR, toPropertyList(SCALE(-1, 1)), 500, new AnimationCallback()
        {
            @Override
            public void onClose(IAnimation animation, IAnimationHandle handle)
            {
                m_text.animate(LINEAR, toPropertyList(SCALE(1, 1)), 500, new AnimationCallback()
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

    @Override
    public LienzoPanel getLienzoPanel()
    {
        return m_lienzo;
    }
}
