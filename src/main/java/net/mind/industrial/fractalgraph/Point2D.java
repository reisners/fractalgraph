package net.mind.industrial.fractalgraph;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

@Data
public class Point2D {
    private final int x, y;

    static Point2D of(double x, double y) {
        return new Point2D(round(x), round(y));
    }

    private static int round(double d) {
        return (int)Math.round(d);
    }
}
