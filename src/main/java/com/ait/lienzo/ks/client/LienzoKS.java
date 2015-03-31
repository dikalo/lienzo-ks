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

package com.ait.lienzo.ks.client;

import com.ait.toolkit.sencha.ext.client.core.ExtEntryPoint;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Viewport;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.Window;

public class LienzoKS extends ExtEntryPoint
{
    public static final String KSBLUE = "#0433ff";

    @Override
    public void onLoad()
    {
        Window.setMargin("0px");

        Window.enableScrolling(false);

        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler()
        {
            @Override
            public void onUncaughtException(Throwable e)
            {
                if (GWT.isScript())
                {
                    GWT.log("UNCAUGHT EXCEPTION ", e);
                }
                else
                {
                    GWT.log("UNCAUGHT EXCEPTION ", e);
                }
            }
        });
        Viewport vp = Viewport.get(Layout.BORDER);

        HeaderPanel hp = new HeaderPanel();

        vp.add(hp);

        ContentPanel cp = new ContentPanel();

        vp.add(cp);

        NavigationPanel np = new NavigationPanel();

        vp.add(np);

        cp.run();
    }
}
