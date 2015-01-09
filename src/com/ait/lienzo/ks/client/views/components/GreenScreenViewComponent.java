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

import com.ait.lienzo.client.core.config.LienzoCore;
import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.image.filter.ColorDeltaAlphaImageDataFilter;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Movie;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class GreenScreenViewComponent extends AbstractToolBarViewComponent
{
    private final Movie    m_movie;

    private final KSButton m_play = new KSButton("Play");

    public GreenScreenViewComponent()
    {
        m_play.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (m_movie.isPaused())
                {
                    m_movie.play();

                    m_movie.setLoop(true);

                    m_play.setText("Pause");
                }
                else
                {
                    m_movie.pause();

                    m_play.setText("Play");
                }
            }
        });
        m_play.setWidth(90);

        getToolBarContainer().add(m_play);

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
        show.setWidth(90);

        getToolBarContainer().add(show);

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

        getLienzoPanel().add(layer, movie);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    @Override
    public boolean suspend()
    {
        if (super.suspend())
        {
            if (false == m_movie.isPaused())
            {
                m_movie.pause();

                m_play.setText("Play");
            }
            return true;
        }
        return false;
    }
}
