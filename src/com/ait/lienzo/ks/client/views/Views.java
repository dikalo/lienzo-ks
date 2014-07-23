
package com.ait.lienzo.ks.client.views;

import java.util.HashMap;

import com.ait.lienzo.ks.client.views.components.WelcomeViewComponent;
import com.ait.lienzo.ks.shared.ViewNames;

public final class Views implements ViewNames
{
    private static final Views                  INSTANCE    = new Views();

    private final HashMap<String, IViewFactory> m_factories = new HashMap<String, IViewFactory>();

    public final static Views get()
    {
        return INSTANCE;
    }

    private Views()
    {
        put(WELCOME, new IViewFactory()
        {
            @Override
            public void make(IViewFactoryCallback callback)
            {
                callback.accept(new WelcomeViewComponent());
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
}
