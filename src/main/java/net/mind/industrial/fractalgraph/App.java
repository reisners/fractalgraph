package net.mind.industrial.fractalgraph;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.math3.complex.Complex;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * Hello world!
 *
 */
@Slf4j
public class App
{
    private final int width, height, originx, originy, scale;
    private final GeometryGenerator geometryGenerator = new GeometryGenerator();

    private final SVGGenerator svgGenerator;

    private final Color color1 = Color.decode("#D3102C");
    private final Color color2 = Color.decode("#489CD4");
    private final Color color3 = Color.decode("#9C7FB4");
    private final Color color4 = Color.decode("#CEDBE1");

    public App(int width, int height, int originx, int originy, int scale) {
        this.width = width;
        this.height = height;
        this.originx = originx;
        this.originy = originy;
        this.scale = scale;
        this.svgGenerator = new SVGGenerator(new Dimension(width, height));
    }

    public static void main(String[] args )
    {
        int index = 0;
        int width = Integer.parseInt(args[index++]);
        int height = Integer.parseInt(args[index++]);
        int originx = Integer.parseInt(args[index++]);
        int originy = Integer.parseInt(args[index++]);
        int scale = Integer.parseInt(args[index++]);
        try {
            new App(width, height, originx, originy, scale).run();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void run() throws IOException {
        double rotate1 = 0;

        double scaleProgression = Math.pow(0.25, 1.0/8); // scale down to 1/4th in 8 steps

        // first image
        double scale1 = scale;
        Complex[] world = geometryGenerator.generate(5);
        int index = 0;

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo2(view, g2d, 0.5f);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo3(view, g2d, 0.5f);
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo3(view, g2d, 1);
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo4(view, g2d, 0.5f);
            drawGeo3(view, g2d, 1);
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo4(view, g2d, 1);
            drawGeo3(view, g2d, 1);
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

        renderSVG(rotate1, scaleProgression, scale1, world, index++, (g2d, view) -> {
            drawGeo5(view, g2d, 0.5f);
            drawGeo4(view, g2d, 1);
            drawGeo3(view, g2d, 1);
            drawGeo2(view, g2d, 1);
            drawGeo1(view, g2d);
        });

    }

    @Data
    private static class Geometry {
        private final Point2D[] view;
        private final int index;
        private final double scaleProgression;
    }

    private void renderSVG(double rotate1, double scaleProgression, double scale1, Complex[] world, int index, BiConsumer<SVGGraphics2D, Geometry> biConsumer) throws IOException {
        svgGenerator.generateSVG(g2d -> {
            double scale = scale1 * Math.pow(scaleProgression, index);
            biConsumer.accept(g2d, new Geometry(transform(world, scale, rotate1), index, scaleProgression));
        }, new FileWriter("img/img"+index+".svg"));
    }

    private void drawGeo5(Geometry geometry, SVGGraphics2D g2d, float phase) {
        Point2D[] view = geometry.getView();
        drawLine(g2d, view[7], view[6], phase);
        drawLine(g2d, view[8], view[4], phase);
        drawLine(g2d, view[9], view[5], phase);
    }

    private void drawGeo4(Geometry geometry, SVGGraphics2D g2d, float phase) {
        Point2D[] view = geometry.getView();
        drawLine(g2d, view[10], view[7], phase);
        drawLine(g2d, view[11], view[8], phase);
        drawLine(g2d, view[12], view[9], phase);
    }

    private void drawGeo3(Geometry geometry, SVGGraphics2D g2d, float phase) {
        Point2D[] view = geometry.getView();
        drawLine(g2d, view[7], view[11], phase);
        drawLine(g2d, view[8], view[12], phase);
        drawLine(g2d, view[9], view[10], phase);
    }

    private void drawGeo2(Geometry geometry, SVGGraphics2D g2d, float phase) {
        Point2D[] view = geometry.getView();
        drawLine(g2d, view[4], view[7], phase);
        drawLine(g2d, view[5], view[8], phase);
        drawLine(g2d, view[6], view[9], phase);
    }

    private void drawGeo1(Geometry geometry, SVGGraphics2D g2d) {
        Point2D[] view = geometry.getView();
        double scale0 = 10 * Math.pow(geometry.getScaleProgression(), geometry.getIndex()-12);
        double scale1 = 10 * Math.pow(geometry.getScaleProgression(), geometry.getIndex()-8);
        double scale2 = 10 * Math.pow(geometry.getScaleProgression(), geometry.getIndex()-4);
        double scale3 = 10 * Math.pow(geometry.getScaleProgression(), geometry.getIndex());

        log.info("index={} scale0={} scale1={} scale2={} scale3={}", geometry.getIndex(), scale0, scale1, scale2, scale3);

        drawLine(g2d, view[1], view[2], 1);
        drawLine(g2d, view[2], view[3], 1);
        drawLine(g2d, view[3], view[1], 1);
        drawLine(g2d, view[4], view[5], 1);
        drawLine(g2d, view[5], view[6], 1);
        drawLine(g2d, view[6], view[4], 1);
        drawCircle(g2d, view[4], scale3, color2);
        drawCircle(g2d, view[5], scale3, color3);
        drawCircle(g2d, view[6], scale3, color1);
        drawCircle(g2d, view[7], scale2, color3);
        drawCircle(g2d, view[8], scale2, color1);
        drawCircle(g2d, view[9], scale2, color2);
        drawCircle(g2d, view[10], scale1, color1);
        drawCircle(g2d, view[11], scale1, color2);
        drawCircle(g2d, view[12], scale1, color3);
        drawCircle(g2d, view[13], scale0, color2);
        drawCircle(g2d, view[14], scale0, color3);
        drawCircle(g2d, view[15], scale0, color1);
    }

    private void drawCircle(SVGGraphics2D g2d, Point2D pcenter, double radius, Color color) {
        int iradius = (int) Math.round(radius);
        g2d.setColor(color);
        g2d.fillOval(pcenter.getX()-iradius, pcenter.getY()-iradius, 2*iradius, 2*iradius);
    }

    private void drawLine(SVGGraphics2D g2d, Point2D pstart, Point2D pend, float phase) {
        float length = (float) Math.sqrt(Math.pow(pstart.getX()-pend.getX(), 2)+Math.pow(pstart.getY()-pend.getY(), 2));
        g2d.setStroke(new BasicStroke(length/20));
        g2d.setColor(color4);
        if (phase == 1) {
            g2d.drawLine(pstart.getX(), pstart.getY(), pend.getX(), pend.getY());
        } else {
            g2d.drawLine(pstart.getX(), pstart.getY(), Math.round(pstart.getX() + (pend.getX() - pstart.getX())*phase/2), Math.round(pstart.getY() + (pend.getY() - pstart.getY())*phase/2));
            g2d.drawLine(pend.getX(), pend.getY(), Math.round(pend.getX() + (pstart.getX() - pend.getX())*phase/2), Math.round(pend.getY() + (pstart.getY() - pend.getY())*phase/2));
        }
    }

    private Point2D[] transform(Complex[] worldPoints, double scale, double rotate) {
        Complex scaleRotate = Complex.valueOf(0, rotate).exp().multiply(scale);
        Point2D[] viewPoints = new Point2D[worldPoints.length];
        for (int i = 0; i < viewPoints.length; i++) {
            Complex scaledRotated = worldPoints[i].multiply(scaleRotate);
            viewPoints[i] = Point2D.of(originx + Math.round(scaledRotated.getReal()), originy + Math.round(scaledRotated.getImaginary()));
        }
        return viewPoints;
    }
}
