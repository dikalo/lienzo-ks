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

import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.image.filter.ColorDeltaAlphaImageDataFilter;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Movie;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSToolBar;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;

public class GreenScreenViewComponent extends AbstractViewComponent
{
    private Movie             m_movie;

    private final LienzoPanel m_lienzo = new LienzoPanel();

    public GreenScreenViewComponent()
    {
        KSContainer main = new KSContainer(Layout.BORDER);

        KSToolBar tool = new KSToolBar();

        tool.setRegion(BorderRegion.NORTH);

        tool.setHeight(30);

        final KSButton play = new KSButton("Play");

        play.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_movie.isPaused())
                {
                    m_movie.play();

                    m_movie.setLoop(true);

                    play.setText("Pause");
                }
                else
                {
                    m_movie.pause();

                    play.setText("Play");
                }
            }
        });
        play.setWidth(80);

        tool.add(play);

        final KSButton show = new KSButton("Show");

        show.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_movie.getFilters().size() > 0)
                {
                    m_movie.clearFilters();

                    show.setText("Show");
                }
                else
                {
                    m_movie.setFilters(new ColorDeltaAlphaImageDataFilter(75, 210, 0, 64));

                    show.setText("Hide");
                }
            }
        });
        show.setWidth(80);

        tool.add(show);

        main.add(tool);

        final Layer layer = new Layer();

        new Picture("office.jpg", ImageSelectionMode.SELECT_BOUNDS).onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(Picture picture)
            {
                picture.setX(10).setY(10);

                layer.add(picture);

                layer.batch();
            }
        });
        m_movie = new Movie(LienzoCore.get().isFirefox() ? "doctor.WebM" : "doctor.mp4").setWidth(856).setHeight(480).setX(10).setY(10).setListening(false).setLoop(true);

        Layer movie = new Layer();

        movie.add(m_movie);

        m_lienzo.add(layer);

        m_lienzo.add(movie);

        m_lienzo.setBackgroundLayer(new StandardBackgroundLayer());

        KSContainer work = new KSContainer();

        work.setRegion(BorderRegion.CENTER);

        work.add(m_lienzo);

        main.add(work);

        add(main);
    }

    @Override
    public LienzoPanel getLienzoPanel()
    {
        return m_lienzo;
    }
}
