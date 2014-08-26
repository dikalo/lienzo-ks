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

package com.ait.lienzo.ks.client.views;

import java.util.HashMap;

import com.ait.lienzo.ks.client.views.components.GreenScreenViewComponent;
import com.ait.lienzo.ks.client.views.components.MovieViewComponent;
import com.ait.lienzo.ks.client.views.components.SimpleImageFiltersViewComponent;
import com.ait.lienzo.ks.client.views.components.PieChartViewComponent;
import com.ait.lienzo.ks.client.views.components.SpritesViewComponent;
import com.ait.lienzo.ks.client.views.components.TigerViewComponent;
import com.ait.lienzo.ks.client.views.components.WelcomeViewComponent;
import com.ait.lienzo.ks.shared.KSViewNames;

public final class ViewFactoryInstance implements KSViewNames
{
    private static final ViewFactoryInstance    INSTANCE    = new ViewFactoryInstance();

    private final HashMap<String, IViewFactory> m_factories = new HashMap<String, IViewFactory>();

    public final static ViewFactoryInstance get()
    {
        return INSTANCE;
    }

    private ViewFactoryInstance()
    {
        put(WELCOME, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new WelcomeViewComponent());
            }
        });
        put(PIE_CHART, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new PieChartViewComponent());
            }
        });
        put(SIMPLE_IMAGE_FILTERS, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new SimpleImageFiltersViewComponent());
            }
        });
        put(TIGER, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new TigerViewComponent());
            }
        });
        put(SPRITES, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new SpritesViewComponent());
            }
        });
        put(MOVIE, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new MovieViewComponent());
            }
        });
        put(GREEN_SCREEN, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new GreenScreenViewComponent());
            }
        });
    }

    private final void put(String link, IViewFactory fact)
    {
        m_factories.put(link, fact);
    }

    public final void make(String link, IViewFactoryCallback callback)
    {
        IViewFactory factory = m_factories.get(link);

        if (null != factory)
        {
            factory.make(callback);
        }
        else
        {
            callback.accept(new WelcomeViewComponent());
        }
    }

    public final boolean isDefined(String link)
    {
        return m_factories.containsKey(link);
    }
}
