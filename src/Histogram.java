import functionality.Picture;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Yaroslav Vasko on 27.11.2016.
 */
public class Histogram extends JFrame {
    private Picture picture;

    public  Histogram(Picture picture){
        super("Histogram RGB");
        this.picture = picture;

        final JFreeChart chart = createChart(createDataset());

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 300));
        setContentPane(chartPanel);
    }

    private XYDataset createDataset( )
    {
        XYSeriesCollection  dataset = new XYSeriesCollection ( );
        XYSeries red = new XYSeries("Red");
        XYSeries green = new XYSeries("Green");
        XYSeries blue = new XYSeries("Blue");

        int[] colorStat = picture.getColorStatistics();

        for(int i = 0; i < 256; i++){
            red.add(i, colorStat[i]);
            green.add(i, colorStat[256+i]);
            blue.add(i, colorStat[512+i]);
        }

        dataset.addSeries(red);
        dataset.addSeries(green);
        dataset.addSeries(blue);
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "RGB",      // chart title
                "Intensity",                      // x axis label
                "Number of pixels",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );


        chart.setBackgroundPaint(Color.lightGray);
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        chart.setBorderVisible(false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesShapesVisible(0,false);
        renderer.setSeriesShapesVisible(1,false);
        renderer.setSeriesShapesVisible(2,false);
        plot.setRenderer(renderer);
        return chart;
    }

}
