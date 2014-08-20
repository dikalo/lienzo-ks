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

package com.ait.lienzo.ks.client.ui.components;

import java.util.ArrayList;
import java.util.Map;

import com.ait.toolkit.data.client.BaseModel;
import com.ait.toolkit.sencha.ext.client.ui.ComboBox;
import com.ait.toolkit.sencha.shared.client.data.Store;

public class KSComboBox extends ComboBox
{
    public KSComboBox(Map<String, String> values)
    {
        setQueryMode(LOCAL);

        setDisplayField("label");

        ArrayList<KSComboBoxModel> list = new ArrayList<KSComboBoxModel>();

        for (String label : values.keySet())
        {
            list.add(new KSComboBoxModel(label, values.get(label)));
        }
        setStore(new Store(list));

        if (false == list.isEmpty())
        {
            setValue(list.get(0).get("label"));
        }
    }

    private final static class KSComboBoxModel extends BaseModel
    {
        public KSComboBoxModel(String label, String value)
        {
            set("label", label);

            set("value", value);
        }
    }
}
