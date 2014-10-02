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

package com.ait.lienzo.ks.client.views;

import com.ait.lienzo.client.core.shape.GridLayer;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.toolkit.sencha.ext.client.core.Component;

public interface IViewComponent
{
    public Component asViewComponent();

    public boolean isActive();

    public boolean activate();

    public boolean suspend();

    public String getSourceURL();

    public String getSimpleClassName();

    public LienzoPanel getLienzoPanel();

    public GridLayer getBackgroundLayer();
}
