package cn.edu.ruc.iir.rainbow.layout.cost;

import cn.edu.ruc.iir.rainbow.layout.cost.domain.Coordinate;
import cn.edu.ruc.iir.rainbow.layout.cost.domain.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RealSeekCost implements SeekCost
{
    private List<Line> segments = null;
    private long interval = 0;
    private double K = 0;

    public RealSeekCost(long interval, List<Coordinate> points)
    {
        this.interval = interval;
        segments = new ArrayList<>();
        Collections.sort(points);

        for (int i = 0; i < points.size() - 1; ++i)
        {
            Coordinate point = points.get(i);
            double x = point.getX();
            double y = point.getY();
            Coordinate point1 = points.get(i + 1);
            double x1 = point1.getX();
            double y1 = point1.getY();
            double slope = (y1 - y) / (x1 - x);
            Line line = new Line(point, slope);
            segments.add(line);
        }

        Coordinate lastPoint = points.get(points.size() - 1);
        this.K = lastPoint.getY() / Math.sqrt(lastPoint.getX());
    }

    @Override
    public double calculate(double distance)
    {
        int id = (int) (distance / this.interval);
        if (id < this.segments.size())
        {
            return this.segments.get(id).getY(distance);
        } else
        {
            return this.K * Math.sqrt(distance);
        }
    }
}
