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

import com.ait.lienzo.client.core.image.PictureLoadedHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ImageSelectionMode;
import com.ait.lienzo.shared.core.types.ImageSerializationMode;

public class ImageEventsViewComponent extends AbstractToolBarViewComponent
{
    public ImageEventsViewComponent()
    {
        final Layer layer = new Layer();
        
        new Picture("HTML5_Logo_512.png", ImageSelectionMode.SELECT_NON_TRANSPARENT).onLoaded(new PictureLoadedHandler()
        {
            @Override
            public void onPictureLoaded(final Picture picture)
            {
                picture.setX(100).setY(100).setDraggable(true);
                
                picture.setImageSerializationMode(ImageSerializationMode.DATA_URL);

                layer.add(picture);

                layer.batch();
            }
        });
        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }
}
