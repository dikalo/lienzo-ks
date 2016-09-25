/*
   Copyright (c) 2014,2015,2016 Ahome' Innovation Technologies. All rights reserved.

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

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

public class WiresDockingViewComponent extends AbstractToolBarViewComponent
{
    private final static KSSimple m_label = new KSSimple("&nbsp;&nbsp;White-smoke shape can contain both red and blue shapes.&nbsp;&nbsp;" +
            "Only blue shape can be docked to while-somke shape..&nbsp;&nbsp;", 1);

    public WiresDockingViewComponent()
    {
        Layer layer = new Layer();

        getToolBarContainer().add(m_label);

        final WiresManager wires_manager = WiresManager.get(layer);

        wires_manager.setContainmentAcceptor(IContainmentAcceptor.ALL);

        wires_manager.setDockingAcceptor(new IDockingAcceptor() {
            @Override
            public boolean dockingAllowed(WiresContainer parent, WiresShape child) {
                return acceptDocking( parent, child );
            }

            @Override
            public boolean acceptDocking(WiresContainer parent, WiresShape child) {
                return parent.getContainer().getUserData().equals("parent")
                        && child.getContainer().getUserData().equals( "dock" );
            }

            @Override
            public int getHotspotSize() {
                return IDockingAcceptor.HOTSPOT_SIZE;
            }

        });

        MultiPath parentMultiPath = new MultiPath().rect(0, 0, 400, 400)
                .setStrokeColor("#000000")
                .setFillColor( ColorName.WHITESMOKE);
        final WiresShape parentShape = new WiresShape(parentMultiPath);
        parentShape.getContainer().setUserData( "parent" );
        wires_manager.register( parentShape );
        parentShape.setDraggable(true).setX(500).setY(200);
        wires_manager.getMagnetManager().createMagnets(parentShape);

        MultiPath childMultiPath = new MultiPath().rect(0, 0, 100, 100)
                .setStrokeColor( ColorName.RED )
                .setFillColor( ColorName.RED );
        final WiresShape childShape = new WiresShape(childMultiPath);
        childShape.getContainer().setUserData( "child" );
        wires_manager.register( childShape );
        childShape.setDraggable(true).setX(50).setY(200);
        wires_manager.getMagnetManager().createMagnets(childShape);

        MultiPath dockMultiPath = new MultiPath().rect(0, 0, 100, 100)
                .setStrokeColor( ColorName.BLUE )
                .setFillColor( ColorName.BLUE );
        final WiresShape dockShape = new WiresShape(dockMultiPath);
        dockShape.getContainer().setUserData( "dock" );
        wires_manager.register( dockShape );
        dockShape.setDraggable(true).setX(50).setY(400);
        wires_manager.getMagnetManager().createMagnets(dockShape);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

}
