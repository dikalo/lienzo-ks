
package com.ait.lienzo.ks.client.views.components;

import com.ait.lienzo.client.core.mediator.EventFilter;
import com.ait.lienzo.client.core.mediator.MouseWheelZoomMediator;
import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.SVGPath;
import com.ait.lienzo.client.core.types.Transform;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.lienzo.shared.core.types.DragMode;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class SVGBoundsViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton m_unzoom = new KSButton("Unzoom");

    private final KSButton m_render = new KSButton("Render");

    private final KSSimple m_zoomlb = new KSSimple("&nbsp;&nbsp;Shift+Mouse Wheel to Zoom", 1);

    public SVGBoundsViewComponent()
    {
        final Layer layer = new Layer();

        final Group paths = new Group().setX(200).setY(200).setDraggable(true).setDragMode(DragMode.SAME_LAYER);

        m_unzoom.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                paths.setX(200).setY(200);

                getLienzoPanel().setTransform(new Transform()).draw();
            }
        });
        m_unzoom.setWidth(90);

        getToolBarContainer().add(m_unzoom);

        m_render.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                layer.setListening(false);

                long beg = System.currentTimeMillis();

                layer.draw();

                m_render.setText("Render " + (System.currentTimeMillis() - beg) + "ms");

                layer.setListening(true);

                layer.draw();
            }
        });
        m_render.setWidth(90);

        getToolBarContainer().add(m_render);

        getToolBarContainer().add(m_zoomlb);

        //String pathdef = "M 488 348 A 268 80 0 1 1   -48,348 A 268 80 0 1 1   488 348z";  // CASE 1A
        //String pathdef = "M 488 348 A 268 80 0 1 1 -48,348 A 268 80 0 1 1 488 348z";      // CASE 1B

        String pathdef = "M10,15c10,10,10,0,40,0c30,0,30,10,40,0q-10,30-40,30q-30,0-40-30"; // CASE 2A
        //String pathdef = "M10,15c10,10,10,0,40,0c30,0,30,10,40,0q-10,30-40,30q-30,0-40-30 z"; // CASE 2B

        SVGPath path = new SVGPath(pathdef);
        
        path.setStrokeColor(ColorName.BLACK);
        
        path.setFillColor(ColorName.RED);
        
        paths.add(path);

        layer.add(paths);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());

        getLienzoPanel().getMediators().push(new MouseWheelZoomMediator(EventFilter.SHIFT));
    }

    @Override
    public GridLayer getBackgroundLayer()
    {
        return new BluePrintBackgroundLayer();
    }
}
