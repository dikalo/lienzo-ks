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

import java.util.List;

import com.ait.lienzo.charts.client.core.axis.CategoryAxis;
import com.ait.lienzo.charts.client.core.axis.NumericAxis;
import com.ait.lienzo.charts.client.core.model.DataTable;
import com.ait.lienzo.charts.client.core.model.DataTableColumn.DataTableColumnType;
import com.ait.lienzo.charts.client.core.xy.BarChart;
import com.ait.lienzo.charts.client.core.xy.XYChartData;
import com.ait.lienzo.charts.client.core.xy.XYChartSeries;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class BarChartViewComponent extends AbstractToolBarViewComponent
{
    private final KSButton m_add = new KSButton("Add Series");

    private final KSButton m_mod = new KSButton("Bump NYC");

    public BarChartViewComponent()
    {
        Layer layer = new Layer();

        DataTable tabl = new DataTable();
        // The department column.
        tabl.addColumn("department", DataTableColumnType.STRING);
        // The department amount column for NYC office.
        tabl.addColumn("employee_count_nyc", DataTableColumnType.NUMBER);
        // The department amount column for London office.
        tabl.addColumn("employee_count_london", DataTableColumnType.NUMBER);

        tabl.addValue("department", "Engineering");

        tabl.addValue("department", "Services");

        tabl.addValue("department", "Management");

        tabl.addValue("department", "Sales");

        tabl.addValue("department", "Support");

        tabl.addValue("employee_count_nyc", 16754.37);

        tabl.addValue("employee_count_nyc", 26743.29);

        tabl.addValue("employee_count_nyc", 32964.77);

        tabl.addValue("employee_count_nyc", 48639.92);

        tabl.addValue("employee_count_nyc", 58547.45);

        tabl.addValue("employee_count_london", 56547.88);

        tabl.addValue("employee_count_london", 41943.77);

        tabl.addValue("employee_count_london", 36432.28);

        tabl.addValue("employee_count_london", 26432.53);

        tabl.addValue("employee_count_london", 17658.17);

        // Create the two series to display.
        XYChartSeries nyc = new XYChartSeries("NYC", ColorName.DEEPSKYBLUE, "employee_count_nyc");

        XYChartSeries london = new XYChartSeries("London", ColorName.DARKGREY, "employee_count_london");

        final XYChartData data = new XYChartData(tabl).setCategoryAxisProperty("department").addSeries(nyc).addSeries(london);

        // Create the chart options.
        final BarChart chart = new BarChart(data);

        chart.setX(100);

        chart.setY(100);

        chart.setName("Employees per department");

        chart.setWidth(800);

        chart.setHeight(700);

        chart.setFontFamily("Verdana");

        chart.setFontStyle("bold");

        chart.setFontSize(12);

        chart.setCategoriesAxis(new CategoryAxis("Department"));

        chart.setValuesAxis(new NumericAxis("Employees"));

        chart.build();

        layer.add(chart);

        getLienzoPanel().add(layer);

        getLienzoPanel().setBackgroundLayer(getBackgroundLayer());

        getWorkingContainer().add(getLienzoPanel());

        m_add.setWidth(90);

        getToolBarContainer().add(m_add);

        m_add.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                final int size = data.getSeries().length;

                if (size < 5)
                {
                    final DataTable tabl = data.getDataTable();

                    final List<ColorName> colors = ColorName.getValues();

                    final XYChartSeries series = new XYChartSeries("Barcelona" + size, colors.get(size * 10), "employee_count_bcn" + size);

                    final double initial = 10000 * size;

                    tabl.addColumn("employee_count_bcn" + size, DataTableColumnType.NUMBER);

                    tabl.addValue("employee_count_bcn" + size, 30 + initial);

                    tabl.addValue("employee_count_bcn" + size, 40 + initial + 10);

                    tabl.addValue("employee_count_bcn" + size, 50 + initial + 20);

                    tabl.addValue("employee_count_bcn" + size, 60 + initial + 30);

                    tabl.addValue("employee_count_bcn" + size, 70 + initial + 40);

                    data.addSeries(series);

                    chart.setData(data);
                }
            }
        });
        m_mod.setWidth(90);

        getToolBarContainer().add(m_mod);

        m_mod.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                final DataTable tabl = data.getDataTable();

                if (tabl.getColumn("employee_count_nyc") != null)
                {
                    tabl.setValue("employee_count_nyc", 0, tabl.getNumericValue("employee_count_nyc", 0) + 4000);
                }
                chart.setData(data);
            }
        });
    }
}
