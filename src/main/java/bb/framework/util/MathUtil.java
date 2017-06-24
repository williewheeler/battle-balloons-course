package bb.framework.util;

import java.util.Random;

/**
 * Created by willie on 6/24/17.
 */
public final class MathUtil {

	private MathUtil() {
	}

	private static final Random RANDOM = new Random();

	public static int nextRandomInt(int bound) {
		return RANDOM.nextInt(bound);
	}

	public static double nextRandomGaussian() {
		return RANDOM.nextGaussian();
	}
}
