package net.mind.industrial.fractalgraph;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SVGGenerator {

    private final Dimension dimension;

    public void generateSVG(Consumer<SVGGraphics2D> renderer, Writer writer) throws SVGGraphics2DIOException {
        // Get a DOMImplementation.
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        svgGenerator.setSVGCanvasSize(dimension);

        // Ask the renderer to render into the SVG Graphics2D implementation.
        renderer.accept(svgGenerator);

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        svgGenerator.stream(writer, useCSS);

    }
}
