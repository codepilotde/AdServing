/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.manager.web.pages.dashboard.panels;

import nl.topicus.whighcharts.components.WHighChart;
import nl.topicus.whighcharts.options.chart.WHighChartChartOptionsType;
import nl.topicus.whighcharts.options.series.ValueSeries;
import nl.topicus.whighcharts.options.series.ValueSeriesEntry;

import org.apache.wicket.markup.html.panel.Panel;

public class SimplePanel extends Panel {

	public SimplePanel(String id) {
		super(id);

		WHighChart<Integer, ValueSeriesEntry<Integer>> chart = new WHighChart<Integer, ValueSeriesEntry<Integer>>(
				"container");
		chart.getOptions().getExporting().setEnabled(true);
		chart.getOptions().getChart().setType(WHighChartChartOptionsType.area);
		chart.getOptions().getTitle()
				.setText("Area chart with negative values");

		chart.getOptions()
				.getxAxis()
				.setCategories("Apples", "Oranges", "Pears", "Grapes",
						"Bananas");

		chart.getOptions().getTooltip()
				.setFormatter("return ''+ this.series.name +': '+ this.y +'';");

		chart.getOptions().getCredits().setEnabled(false);

		chart.getOptions().addSeries(
				new ValueSeries<Integer>(5, 3, 4, 7, 2).setName("John"));
		chart.getOptions().addSeries(
				new ValueSeries<Integer>(2, -2, -3, 2, 1).setName("Jane"));
		chart.getOptions().addSeries(
				new ValueSeries<Integer>(3, 4, 4, -2, 5).setName("Joe"));

		add(chart);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4776960790710882654L;

}
