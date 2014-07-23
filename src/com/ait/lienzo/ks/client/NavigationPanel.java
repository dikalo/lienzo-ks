
package com.ait.lienzo.ks.client;

import com.ait.toolkit.data.client.BaseTreeModel;
import com.ait.toolkit.sencha.ext.client.events.view.ItemClickEvent;
import com.ait.toolkit.sencha.ext.client.events.view.ItemClickHandler;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.Layout;
import com.ait.toolkit.sencha.ext.client.ui.Panel;
import com.ait.toolkit.sencha.ext.client.ui.TreePanel;
import com.google.gwt.user.client.History;

public class NavigationPanel extends Panel
{
    public NavigationPanel()
    {
        setTitle("Examples");

        setLayout(Layout.FIT);

        setRegion(BorderRegion.WEST);

        setWidth(250);

        setMinWidth(100);

        setHeight(200);

        setCollapsible(true);

        setSplit(true);

        TreePanel tree = new TreePanel();

        tree.setLines(false);

        tree.setUseArrows(true);

        tree.setRootVisible(false);

        BaseTreeModel m = new BaseTreeModel();

        m.setChildren(Example.getExamples());

        tree.setRootNode(m);

        tree.addItemClickHandler(new ItemClickHandler()
        {
            @Override
            public void onItemClick(ItemClickEvent event)
            {
                String link = Example.getLink(event.getRecord().get(Example.TEXT_FIELD));

                if ((link != null) && (false == (link = link.trim()).isEmpty()))
                {
                    History.newItem(link);
                }
            }
        });
        add(tree);
    }
}