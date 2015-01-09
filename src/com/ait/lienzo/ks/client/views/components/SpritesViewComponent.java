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

import java.util.ArrayList;

import com.ait.lienzo.client.core.image.SpriteLoadedHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Sprite;
import com.ait.lienzo.client.core.types.BoundingBox;
import com.ait.lienzo.client.core.types.SpriteBehaviorMap;
import com.ait.lienzo.ks.client.views.AbstractViewComponent;
import com.ait.lienzo.shared.core.types.DragMode;

public class SpritesViewComponent extends AbstractViewComponent
{
    private final ArrayList<Sprite> m_splist = new ArrayList<Sprite>();

    public SpritesViewComponent()
    {
        Layer layer = new Layer();

        addSprite(50, 50, layer);

        addSprite(100, 200, layer);

        addSprite(200, 50, layer);

        addSprite(300, 300, layer);

        addSprite(400, 100, layer);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private final void addSprite(int x, int y, final Layer layer)
    {
        ArrayList<BoundingBox> frames = new ArrayList<BoundingBox>();

        for (int i = 0; i < 10; i++)
        {
            frames.add(new BoundingBox(i * 50, 0, (i * 50) + 50, 50));
        }
        double tickssec = 10; // ticks per second

        String behavior = "spincoin";

        Sprite sprite = new Sprite("coinsprite.png", tickssec, new SpriteBehaviorMap(behavior, frames), behavior).setDraggable(true).setDragMode(DragMode.SAME_LAYER).setX(x).setY(y).onLoaded(new SpriteLoadedHandler()
        {
            @Override
            public void onSpriteLoaded(Sprite sprite)
            {
                layer.add(sprite);

                if (isActive())
                {
                    sprite.play();
                }
            }
        });
        m_splist.add(sprite);
    }

    @Override
    public boolean activate()
    {
        if (super.activate())
        {
            for (Sprite sprite : m_splist)
            {
                if ((null != sprite.getLayer()) && (sprite.isLoaded()) && (false == sprite.isPlaying()))
                {
                    sprite.play();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean suspend()
    {
        if (super.suspend())
        {
            for (Sprite sprite : m_splist)
            {
                if ((null != sprite.getLayer()) && (sprite.isLoaded()) && (sprite.isPlaying()))
                {
                    sprite.pause();
                }
            }
            return true;
        }
        return false;
    }
}
