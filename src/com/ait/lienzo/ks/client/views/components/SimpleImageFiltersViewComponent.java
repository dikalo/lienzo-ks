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

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.image.PictureFilteredHandler;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.image.filter.ColorLuminosityImageDataFilter;
import com.ait.lienzo.client.core.image.filter.LuminosityGrayScaleImageDataFilter;
import com.ait.lienzo.client.core.image.filter.StackBlurImageDataFilter;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.IColor;
import com.ait.lienzo.shared.core.types.TextAlign;
import com.ait.lienzo.shared.core.types.TextBaseLine;

public class SimpleImageFiltersViewComponent extends AbstractViewComponent
{
    private Picture m_original;
    
    private Picture m_modified;

    public SimpleImageFiltersViewComponent()
    {
        LienzoPanel lienzo = new LienzoPanel();

        final Layer player = new Layer();

        new Picture("blogjet256x256.png").onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_original = picture;

                m_original.setX(200).setY(50);

                player.add(m_original);

                player.batch();
            }
        });
        new Picture("blogjet256x256.png").onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                m_modified = picture;

                m_modified.setX(200).setY(300);

                player.add(m_modified);

                player.batch();
            }
        });
        lienzo.add(player);

        Layer clayer = new Layer();

        clayer.add(doMakeFilterControl("Clear", 100, 100, ColorName.WHITE, new Runnable()
        {
            @Override
            public void run()
            {
                if (null != m_modified)
                {
                    m_modified.clearFilters().reFilter(new PictureFilteredHandler()
                    {
                        @Override
                        public void onPictureFiltered(Picture picture)
                        {
                            player.batch();
                        }
                    });
                }
            }
        }));
        clayer.add(doMakeFilterControl("Grey Scale", 100, 200, ColorName.LIGHTGRAY, new Runnable()
        {
            @Override
            public void run()
            {
                if (null != m_modified)
                {
                    m_modified.setFilters(new LuminosityGrayScaleImageDataFilter()).reFilter(new PictureFilteredHandler()
                    {
                        @Override
                        public void onPictureFiltered(Picture picture)
                        {
                            player.batch();
                        }
                    });
                }
            }
        }));
        clayer.add(doMakeFilterControl("Salmon", 100, 300, ColorName.LIGHTSALMON, new Runnable()
        {
            @Override
            public void run()
            {
                if (null != m_modified)
                {
                    m_modified.setFilters(new ColorLuminosityImageDataFilter(ColorName.LIGHTSALMON)).reFilter(new PictureFilteredHandler()
                    {
                        @Override
                        public void onPictureFiltered(Picture picture)
                        {
                            player.batch();
                        }
                    });
                }
            }
        }));
        clayer.add(doMakeFilterControl("Sky Blue", 100, 400, ColorName.LIGHTSKYBLUE, new Runnable()
        {
            @Override
            public void run()
            {
                if (null != m_modified)
                {
                    m_modified.setFilters(new ColorLuminosityImageDataFilter(ColorName.LIGHTSKYBLUE)).reFilter(new PictureFilteredHandler()
                    {
                        @Override
                        public void onPictureFiltered(Picture picture)
                        {
                            player.batch();
                        }
                    });
                }
            }
        }));
        clayer.add(doMakeFilterControl("Blur", 100, 500, ColorName.ANTIQUEWHITE, new Runnable()
        {
            @Override
            public void run()
            {
                if (null != m_modified)
                {
                    m_modified.setFilters(new StackBlurImageDataFilter(6)).reFilter(new PictureFilteredHandler()
                    {
                        @Override
                        public void onPictureFiltered(Picture picture)
                        {
                            player.batch();
                        }
                    });
                }
            }
        }));
        lienzo.add(clayer);

        lienzo.setBackgroundLayer(new StandardBackgroundLayer());

        add(lienzo);
    }

    private Group doMakeFilterControl(String label, int x, int y, IColor color, final Runnable callback)
    {
        Group control = new Group().setX(x).setY(y);

        control.addNodeMouseClickHandler(new NodeMouseClickHandler()
        {
            @Override
            public void onNodeMouseClick(NodeMouseClickEvent event)
            {
                callback.run();
            }
        });
        Circle circle = new Circle(45).setFillColor(color).setStrokeColor(ColorName.BLACK).setStrokeWidth(3);

        control.add(circle);

        Text text = new Text(label, "Calibri", "bold", 14).setFillColor(ColorName.BLACK).setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);

        control.add(text);

        return control;
    }
}
