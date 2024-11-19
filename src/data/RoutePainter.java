package data;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class RoutePainter implements org.jxmapviewer.painter.Painter<JXMapViewer> {

    private final List<GeoPosition> route;

    public RoutePainter(List<GeoPosition> route) {
        this.route = route;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < route.size() - 1; i++) {
            Point2D pt1 = map.getTileFactory().geoToPixel(route.get(i), map.getZoom());
            Point2D pt2 = map.getTileFactory().geoToPixel(route.get(i + 1), map.getZoom());
            g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
        }
    }
}