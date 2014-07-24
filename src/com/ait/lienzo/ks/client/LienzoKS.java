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

package com.ait.lienzo.ks.client;

import com.ait.lienzo.client.core.image.ImageCache;
import com.ait.lienzo.client.core.image.ImageLoader;
import com.ait.toolkit.sencha.ext.client.core.ExtEntryPoint;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Viewport;

public class LienzoKS extends ExtEntryPoint
{
    @Override
    public void onLoad()
    {
        new ImageLoader("crosshatch.png")
        {
            @Override
            public void onLoaded(ImageLoader loader)
            {
                ImageCache.get().save("crosshatch", loader.getJSImage());

                build();
            }

            @Override
            public void onError(ImageLoader loader, String message)
            {
                build();
            }
        };
    }

    private final void build()
    {
        Viewport vp = Viewport.get(Layout.BORDER);

        vp.add(new HeaderPanel());

        vp.add(new ContentPanel());

        vp.add(new NavigationPanel());
    }
}
