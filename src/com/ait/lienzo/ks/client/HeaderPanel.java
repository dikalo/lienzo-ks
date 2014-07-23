
package com.ait.lienzo.ks.client;

import com.ait.toolkit.sencha.ext.client.core.SimpleComponent;
import com.ait.toolkit.sencha.ext.client.layout.Align;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.HBoxLayout;
import com.ait.toolkit.sencha.ext.client.ui.Container;

public class HeaderPanel extends Container
{
    public HeaderPanel()
    {
        setRegion(BorderRegion.NORTH);

        HBoxLayout layout = new HBoxLayout();

        layout.setAlign(Align.MIDDLE);

        setLayout(layout);

        setHeight(60);

        SimpleComponent title = new SimpleComponent();

        title.setHtml("Lienzo Kitchen Sink v 1.0.0");

        title.setFlex(1);

        add(title);
    }
}
