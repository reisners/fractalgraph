package net.mind.industrial.fractalgraph;

import org.apache.commons.math3.complex.Complex;

/**
 * Emits coordinates of fractal points in world coordinates
 */
public class GeometryGenerator {

    final static Complex origin = Complex.ZERO;
    final static Complex rotator = Complex.valueOf(0, Math.PI*2/3).exp();

    private Complex nextGeneration(Complex pMinus2, Complex pMinus1) {
        return Complex.valueOf(pMinus2.getReal(), pMinus1.getImaginary() * 2);
    }

    Complex[] generate(int nGenerations) {
        if (nGenerations <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
        Complex[] v = new Complex[3 * nGenerations + 1];

        v[0] = origin;
        v[1] = rotator;
        v[2] = Complex.ONE;
        v[3] = rotator.pow(2);

        for (int k = 1; k < nGenerations; k++) {
            int base = 1 + 3*k;
            Complex factor = rotator;
            v[base] = v[base-1].add(v[base-2].subtract(v[base-1]).multiply(factor));
            v[base + 1] = v[base].multiply(factor.pow(2));
            v[base + 2] = v[base].multiply(factor);
        }

        return v;
    }
}
