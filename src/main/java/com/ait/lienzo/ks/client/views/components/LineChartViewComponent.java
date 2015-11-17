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

import com.ait.lienzo.charts.client.core.axis.CategoryAxis;
import com.ait.lienzo.charts.client.core.axis.NumericAxis;
import com.ait.lienzo.charts.client.core.model.DataTable;
import com.ait.lienzo.charts.client.core.model.DataTableColumn.DataTableColumnType;
import com.ait.lienzo.charts.client.core.xy.XYChartData;
import com.ait.lienzo.charts.client.core.xy.XYChartSeries;
import com.ait.lienzo.charts.client.core.xy.line.LineChart;
import com.ait.lienzo.charts.shared.core.types.ChartOrientation;
import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.ks.client.ui.components.KSButton;
import com.ait.lienzo.ks.client.views.AbstractToolBarViewComponent;
import com.ait.lienzo.shared.core.types.ColorName;
import com.ait.toolkit.sencha.ext.client.events.button.ClickEvent;
import com.ait.toolkit.sencha.ext.client.events.button.ClickHandler;

public class LineChartViewComponent extends AbstractToolBarViewComponent
{
    private boolean        m_dep = true;

    private final KSButton m_add = new KSButton("Departments");

    private final KSButton m_mod = new KSButton("Automobiles");

    private final KSButton m_dir = new KSButton("Vertical");

    public LineChartViewComponent()
    {
        final Layer layer = new Layer();

        final DataTable table = buildBarChartDataTable();

        final XYChartData data = buildDataDepartment(table);

        final LineChart lc = new LineChart();

        lc.setData(data);
        lc.setX(25);
        lc.setY(25);
        lc.setName("Expenses Per Department");
        lc.setWidth(500);
        lc.setHeight(500);
        lc.setFontFamily("Verdana");
        lc.setFontStyle("bold");
        lc.setFontSize(12);
        lc.setCategoriesAxis(new CategoryAxis("Department"));
        lc.setValuesAxis(new NumericAxis("Expenses"));
        lc.setResizable(true);
        lc.setOrientation(ChartOrientation.HORIZNONAL);

        layer.add(lc);

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
                m_dep = true;

                lc.setName("Expenses Per Department");

                lc.reload(buildDataDepartment(table));
            }
        });
        m_mod.setWidth(90);

        getToolBarContainer().add(m_mod);

        m_mod.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                m_dep = false;

                lc.setName("Automobile Departments");

                lc.reload(buildDataString(table));
            }
        });
        m_dir.setWidth(90);

        getToolBarContainer().add(m_dir);

        m_dir.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                if (ChartOrientation.VERTICAL == lc.getOrientation())
                {
                    m_dir.setText("Vertical");

                    lc.setOrientation(ChartOrientation.HORIZNONAL);
                }
                else
                {
                    m_dir.setText("Horizontal");

                    lc.setOrientation(ChartOrientation.VERTICAL);
                }
                if (m_dep)
                {
                    m_dep = false;

                    lc.setName("Automobile Departments");

                    lc.reload(buildDataString(table));
                }
                else
                {
                    m_dep = true;

                    lc.setName("Expenses Per Department");

                    lc.reload(buildDataDepartment(table));
                }
            }
        });
        lc.init();
    }

    protected DataTable buildBarChartDataTable()
    {
        DataTable table = new DataTable();

        table.addColumn("department", DataTableColumnType.STRING);

        table.addColumn("automobiles", DataTableColumnType.STRING);

        table.addColumn("nyc_office_expenses", DataTableColumnType.NUMBER);

        table.addColumn("london_office_expenses", DataTableColumnType.NUMBER);

        table.addValue("department", "Engineering");

        table.addValue("department", "Services");

        table.addValue("department", "Management");

        table.addValue("department", "Sales");

        table.addValue("department", "Support");

        table.addValue("automobiles", "BMW");

        table.addValue("automobiles", "Mercedes");

        table.addValue("automobiles", "Jaguar");

        table.addValue("automobiles", "Audi");

        table.addValue("automobiles", "Porche");

        table.addValue("nyc_office_expenses", 16754.37);

        table.addValue("nyc_office_expenses", 26743.29);

        table.addValue("nyc_office_expenses", 32964.77);

        table.addValue("nyc_office_expenses", 48639.92);

        table.addValue("nyc_office_expenses", 58547.45);

        table.addValue("london_office_expenses", 56547.88);

        table.addValue("london_office_expenses", 41943.77);

        table.addValue("london_office_expenses", 36432.28);

        table.addValue("london_office_expenses", 26432.53);

        table.addValue("london_office_expenses", 17658.17);

        return table;
    }

    protected XYChartData buildDataDepartment(final DataTable table)
    {
        XYChartSeries seriesNYC = new XYChartSeries("NYC", ColorName.DEEPSKYBLUE, "nyc_office_expenses");

        XYChartSeries seriesLondon = new XYChartSeries("London", ColorName.DARKGREY, "london_office_expenses");

        XYChartData data = new XYChartData(table).setCategoryAxisProperty("department").addSeries(seriesNYC);

        data.addSeries(seriesLondon);

        return data;
    }

    protected XYChartData buildDataString(DataTable table)
    {
        XYChartSeries seriesNYC = new XYChartSeries("NYC", ColorName.DEEPSKYBLUE, "nyc_office_expenses");

        XYChartSeries seriesLondon = new XYChartSeries("London", ColorName.DARKGREY, "london_office_expenses");

        XYChartData data = new XYChartData(table).setCategoryAxisProperty("automobiles").addSeries(seriesNYC);

        data.addSeries(seriesLondon);

        return data;
    }
}
