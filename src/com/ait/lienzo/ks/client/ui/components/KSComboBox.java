
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
