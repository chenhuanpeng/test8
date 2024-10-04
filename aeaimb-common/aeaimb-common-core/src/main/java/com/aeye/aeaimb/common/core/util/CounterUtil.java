package com.aeye.aeaimb.common.core.util;

public class CounterUtil {
	private static int counter = 0;

	public static synchronized int getNextNumber() {
		return ++counter;
	}
}
