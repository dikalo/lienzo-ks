
package com.ait.lienzo.ks.client;

import com.ait.toolkit.sencha.ext.client.core.ExtEntryPoint;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Viewport;

public class LienzoKS extends ExtEntryPoint
{
    @Override
    public void onLoad()
    {
        Viewport vp = Viewport.get(Layout.BORDER);
        
        vp.add(new HeaderPanel());
        
        vp.add(new ContentPanel());
        
        vp.add(new NavigationPanel());
    }
}
