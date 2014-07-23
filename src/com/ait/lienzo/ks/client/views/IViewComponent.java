
package com.ait.lienzo.ks.client.views;

import com.ait.toolkit.sencha.ext.client.core.Component;

public interface IViewComponent
{
    public Component asViewComponent();
        
    public boolean isActive();

    public boolean activate();

    public boolean suspend();

    public String getSourceURL();
}
