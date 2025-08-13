package org.jfrog.license.a;

import java.io.UnsupportedEncodingException;
import java.util.Random;

// ObfuscatedString
public final class a
{
	private static final String a;
	private final String b;

	static {
		a = new String(new char[] { 'U', 'T', 'F', '8' });
	}

	private static final long a(final byte[] array, final int n) {
		long n2 = 0L;
		int min = Math.min(array.length, n + 8);
		while (--min >= n) {
			n2 = (n2 << 8 | (array[min] & 0xFF));
		}
		return n2;
	}

	private static final void a(long n, final byte[] array, final int n2) {
		for (int min = Math.min(array.length, n2 + 8), i = n2; i < min; ++i) {
			array[i] = (byte)n;
			n >>= 8;
		}
	}

	public static String a(final String s) {
		if (s.indexOf(0) != -1) {
			throw new IllegalArgumentException(new a(new long[] { 2598583114197433456L, -2532951909540716745L, 1850312903926917213L, -7324743161950196342L, 3319654553699491298L }).toString());
		}
		byte[] bytes;
		try {
			bytes = s.getBytes(a);
		}
		catch (UnsupportedEncodingException ex) {
			throw new AssertionError(ex);
		}
		final Random random = new Random();
		long nextLong;
		do {
			nextLong = random.nextLong();
		} while (nextLong == 0L);
		final Random random2 = new Random(nextLong);
		final StringBuffer sb = new StringBuffer(new a(new long[] { -6733388613909857970L, -557652741307719956L, 563088487624542180L, 5623833171491374716L, -2309350771052518321L, 2627844803624578169L }).toString());
		a(sb, nextLong);
		for (int length = bytes.length, i = 0; i < length; i += 8) {
			final long n = a(bytes, i) ^ random2.nextLong();
			sb.append(", ");
			a(sb, n);
		}
		sb.append(new a(new long[] { 3426903566703633623L, -2851967657861944394L, 8678768925462458602L, 3688610158019998715L }).toString());
		sb.append(s.replaceAll("\\\\", new a(new long[] { 7866777055383403009L, -5101749501440392498L }).toString()).replaceAll("\"", new a(new long[] { -8797265930671803829L, -5738757606858957305L }).toString()));
		sb.append(new a(new long[] { -4228881123273879289L, 1823585417647083411L }).toString());
		return sb.toString();
	}

	private static final void a(final StringBuffer sb, final long n) {
		sb.append("0x");
		sb.append(Long.toHexString(n).toUpperCase());
		sb.append('L');
	}

	public a(final long[] array) {
		final int length = array.length;
		final byte[] array2 = new byte[8 * (length - 1)];
		final Random random = new Random(array[0]);
		for (int i = 1; i < length; ++i) {
			a(array[i] ^ random.nextLong(), array2, 8 * (i - 1));
		}
		String s;
		try {
			s = new String(array2, org.jfrog.license.a.a.a);
		}
		catch (UnsupportedEncodingException ex) {
			throw new AssertionError(ex);
		}
		final int index = s.indexOf(0);
		this.b = ((index != -1) ? s.substring(0, index) : s);
	}

	@Override
	public String toString() {
		return this.b;
	}
}
