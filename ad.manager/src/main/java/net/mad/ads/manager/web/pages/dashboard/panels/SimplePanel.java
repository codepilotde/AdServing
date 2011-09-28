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
