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

import com.ait.lienzo.ks.client.ui.components.KSContainer;
import com.ait.lienzo.ks.client.ui.components.KSSimple;
import com.ait.toolkit.sencha.ext.client.layout.Align;
import com.ait.toolkit.sencha.ext.client.layout.BorderRegion;
import com.ait.toolkit.sencha.ext.client.layout.HBoxLayout;

public class HeaderPanel extends KSContainer
{
    public HeaderPanel()
    {
        setId("HeaderPanel");

        setRegion(BorderRegion.NORTH);

        setHeight(60);

        KSContainer inside = new KSContainer(new HBoxLayout(Align.MIDDLE));

        inside.setId("HeaderPanel-Inside");

        inside.setHeight(60);

        KSSimple title = new KSSimple("Ahomé Lienzo Kitchen Sink v 2.0.72-SNAPSHOT", 1);

        title.setId("HeaderPanel-Title");

        inside.add(title);

        add(inside);
    }
}
