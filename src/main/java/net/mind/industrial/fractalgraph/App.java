package net.mind.industrial.fractalgraph;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.math3.complex.Complex;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    private final int width, height, originx, originy, scale;
    private final GeometryGenerator geometryGenerator = new GeometryGenerator();

    private final SVGGenerator svgGenerator;

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

        double scaleProgression = Math.pow(0.25, 0.2); // scale down to 1/4th in fve steps

        // first image
        double scale1 = scale;
        Complex[] world = geometryGenerator.generate(5);
        Point2D[] view1 = transform(world, scale1, rotate1);
        svgGenerator.generateSVG(g2d -> {
            drawLine(g2d, view1[1], view1[2]);
            drawLine(g2d, view1[2], view1[3]);
            drawLine(g2d, view1[3], view1[1]);
            drawLine(g2d, view1[4], view1[5]);
            drawLine(g2d, view1[5], view1[6]);
            drawLine(g2d, view1[6], view1[4]);
        }, new FileWriter("img/img1.svg"));

        double scale2 = scale1 * scaleProgression;
        Point2D[] view2 = transform(world, scale2, rotate1);
        svgGenerator.generateSVG(g2d -> {
            drawLine(g2d, view2[1], view2[2]);
            drawLine(g2d, view2[2], view2[3]);
            drawLine(g2d, view2[3], view2[1]);
            drawLine(g2d, view2[4], view2[5]);
            drawLine(g2d, view2[5], view2[6]);
            drawLine(g2d, view2[6], view2[4]);
            drawLine(g2d, view2[4], view2[7]);
            drawLine(g2d, view2[5], view2[8]);
            drawLine(g2d, view2[6], view2[9]);
        }, new FileWriter("img/img2.svg"));

        double scale3 = scale2 * scaleProgression;
        Point2D[] view3 = transform(world, scale3, rotate1);
        svgGenerator.generateSVG(g2d -> {
            drawLine(g2d, view3[1], view3[2]);
            drawLine(g2d, view3[2], view3[3]);
            drawLine(g2d, view3[3], view3[1]);
            drawLine(g2d, view3[4], view3[5]);
            drawLine(g2d, view3[5], view3[6]);
            drawLine(g2d, view3[6], view3[4]);
            drawLine(g2d, view3[4], view3[7]);
            drawLine(g2d, view3[5], view3[8]);
            drawLine(g2d, view3[6], view3[9]);
            drawLine(g2d, view3[7], view3[11]);
            drawLine(g2d, view3[8], view3[12]);
            drawLine(g2d, view3[9], view3[10]);
        }, new FileWriter("img/img3.svg"));

        double scale4 = scale3 * scaleProgression;
        Point2D[] view4 = transform(world, scale4, rotate1);
        svgGenerator.generateSVG(g2d -> {
            drawLine(g2d, view4[1], view4[2]);
            drawLine(g2d, view4[2], view4[3]);
            drawLine(g2d, view4[3], view4[1]);
            drawLine(g2d, view4[4], view4[5]);
            drawLine(g2d, view4[5], view4[6]);
            drawLine(g2d, view4[6], view4[4]);
            drawLine(g2d, view4[4], view4[7]);
            drawLine(g2d, view4[5], view4[8]);
            drawLine(g2d, view4[6], view4[9]);
            drawLine(g2d, view4[7], view4[11]);
            drawLine(g2d, view4[8], view4[12]);
            drawLine(g2d, view4[9], view4[10]);
            drawLine(g2d, view4[10], view4[7]);
            drawLine(g2d, view4[11], view4[8]);
            drawLine(g2d, view4[12], view4[9]);
        }, new FileWriter("img/img4.svg"));

        double scale5 = scale4 * scaleProgression;
        Point2D[] view5 = transform(world, scale5, rotate1);
        svgGenerator.generateSVG(g2d -> {
            drawLine(g2d, view5[1], view5[2]);
            drawLine(g2d, view5[2], view5[3]);
            drawLine(g2d, view5[3], view5[1]);
            drawLine(g2d, view5[4], view5[5]);
            drawLine(g2d, view5[5], view5[6]);
            drawLine(g2d, view5[6], view5[4]);
            drawLine(g2d, view5[4], view5[7]);
            drawLine(g2d, view5[5], view5[8]);
            drawLine(g2d, view5[6], view5[9]);
            drawLine(g2d, view5[7], view5[11]);
            drawLine(g2d, view5[8], view5[12]);
            drawLine(g2d, view5[9], view5[10]);
            drawLine(g2d, view5[10], view5[7]);
            drawLine(g2d, view5[11], view5[8]);
            drawLine(g2d, view5[12], view5[9]);
            drawLine(g2d, view5[7], view5[6]);
            drawLine(g2d, view5[8], view5[4]);
            drawLine(g2d, view5[9], view5[5]);
        }, new FileWriter("img/img5.svg"));
    }

    private void drawLine(SVGGraphics2D g2d, Point2D pstart, Point2D pend) {
        g2d.drawLine(pstart.getX(), pstart.getY(), pend.getX(), pend.getY());
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
