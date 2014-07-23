
package com.ait.lienzo.ks.client;

import com.ait.lienzo.ks.client.views.IViewComponent;
import com.ait.lienzo.ks.client.views.IViewFactoryCallback;
import com.ait.lienzo.ks.client.views.Views;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.ui.Panel;

public class ContentPanel extends Panel
{
    public ContentPanel()
    {
        setAutoScroll(true);

        setTitle("&nbsp;");

        setRegion(BorderRegion.CENTER);
        
        Views.get().make("WELCOME", new IViewFactoryCallback()
        {
            @Override
            public void accept(IViewComponent component)
            {
                add(component.asViewComponent());
                
                component.activate();
            }
        });
    }
}
