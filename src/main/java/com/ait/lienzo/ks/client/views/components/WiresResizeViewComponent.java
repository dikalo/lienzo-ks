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

import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.*;
import com.ait.lienzo.client.core.shape.wires.event.*;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;

import static com.ait.lienzo.client.core.shape.wires.LayoutContainer.Layout.*;

public class WiresResizeViewComponent extends AbstractToolBarViewComponent
{
    private static final double SIZE = 100;
    private final static KSSimple m_label = new KSSimple("&nbsp;&nbsp;Shift+Click to Resize&nbsp;&nbsp;Click to " +
            "hide the resize points&nbsp;&nbsp;", 1);

    private final KSButton m_left = new KSButton("Left");
    private final KSButton m_right = new KSButton("Right");
    private final KSButton m_center = new KSButton("Center");
    private final KSButton m_top = new KSButton("Top");
    private final KSButton m_bottom = new KSButton("Bottom");

    private WiresShape wires_shape;
    private WiresManager wires_manager;
    private Text text;

    public WiresResizeViewComponent()
    {
        Layer layer = new Layer();

        m_left.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick( com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
            if ( null != wires_shape ) {
                wires_manager.deregister( wires_shape );
                wires_shape = left();
            }
            }
        });
        m_left.setWidth(90);
        getToolBarContainer().add(m_left);

        m_right.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick( com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
                if ( null != wires_shape ) {
                    wires_manager.deregister( wires_shape );
                    wires_shape = right();
                }
            }
        });
        m_right.setWidth(90);
        getToolBarContainer().add(m_right);

        m_center.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick( com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
                if ( null != wires_shape ) {
                    wires_manager.deregister( wires_shape );
                    wires_shape = center();
                }
            }
        });
        m_center.setWidth(90);
        getToolBarContainer().add(m_center);

        m_top.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick( com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
                if ( null != wires_shape ) {
                    wires_manager.deregister( wires_shape );
                    wires_shape = top();
                }
            }
        });
        m_top.setWidth(90);
        getToolBarContainer().add(m_top);

        m_bottom.addClickHandler(new com.ait.toolkit.sencha.ext.client.events.button.ClickHandler()
        {
            @Override
            public void onClick( com.ait.toolkit.sencha.ext.client.events.button.ClickEvent event)
            {
                if ( null != wires_shape ) {
                    wires_manager.deregister( wires_shape );
                    wires_shape = bottom();
                }
            }
        });
        m_bottom.setWidth(90);
        getToolBarContainer().add(m_bottom);

        getToolBarContainer().add(m_label);

        wires_manager = WiresManager.get(layer);

        text = new Text( "[]" )
                .setFontFamily( "Verdana" )
                .setFontSize( 12 )
                .setStrokeWidth( 1 )
                .setStrokeColor( ColorName.WHITE );

        wires_shape = center();

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());
    }

    private WiresShape create( String color, double size, LayoutContainer.Layout layout ) {
        text.setText( "[" + (int) SIZE +", " + (int) SIZE + "]" );
        final MultiPath path = new MultiPath().rect(0, 0, size, size)
                .setStrokeWidth(1)
                .setStrokeColor( color )
                .setFillColor( ColorName.LIGHTGREY );
        final WiresShape wiresShape0 =
                new WiresShape( path )
                        .setX(400)
                        .setY(200)
                        .setDraggable(true)
                        .addChild( new Circle( size / 4 ).setFillColor( color ), layout )
                        .addChild( text, CENTER );

        wires_manager.register( wiresShape0 );
        wires_manager.getMagnetManager().createMagnets(wiresShape0);
        addResizeHandlers( wiresShape0 );
        return wiresShape0;
    }

    private WiresShape left() {
        return create( "#CC00CC", SIZE, LEFT );
    }

    private WiresShape right() {
        return create( "#0000CC", SIZE, RIGHT );
    }

    private WiresShape center() {
        return create( "#CC0000", SIZE, CENTER );
    }

    private WiresShape top() {
        return create( "#00CC00", SIZE, TOP );
    }

    private WiresShape bottom() {
        return create( "#CCCC00", SIZE, BOTTOM );
    }

    private void addResizeHandlers( final WiresShape shape ) {
        shape
                .setResizable( true )
                .getPath()
                .addNodeMouseClickHandler( new NodeMouseClickHandler() {
                    @Override
                    public void onNodeMouseClick( NodeMouseClickEvent event ) {
                        final IControlHandleList controlHandles = shape.loadControls( IControlHandle.ControlHandleStandardType.RESIZE );
                        if ( null != controlHandles ) {
                            if ( event.isShiftKeyDown() ) {
                                controlHandles.show();
                            } else {
                                controlHandles.hide();
                            }

                        }
                    }
                } );
        shape.addWiresResizeStartHandler( new WiresResizeStartHandler() {
            @Override
            public void onShapeResizeStart( final WiresResizeStartEvent event ) {
                onShapeResize( event.getWidth(), event.getHeight() );
            }
        } );

        shape.addWiresResizeStepHandler( new WiresResizeStepHandler() {
            @Override
            public void onShapeResizeStep( final WiresResizeStepEvent event ) {
                onShapeResize( event.getWidth(), event.getHeight() );
            }
        } );

        shape.addWiresResizeEndHandler( new WiresResizeEndHandler() {
            @Override
            public void onShapeResizeEnd( final WiresResizeEndEvent event ) {
                onShapeResize( event.getWidth(), event.getHeight() );
            }
        } );
    }

    private void onShapeResize( final double width, final double height ) {
        final String t = "[" + width + ", " + height + "]";
        text.setText( t );
    }

}
