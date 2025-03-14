package com.example.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	public double suma(double x, double y) {
		return roundIEEE754(x+y);
	}
	
	public int suma(int x, int y) {
		return x+y;
	}
	
	public int divide(int x, int y) {
		return x/y;
	}
	
	public double divide(double x, double y) {
		if (y==0 ) {
			throw new ArithmeticException("/ by zero");
		}
		return roundIEEE754(x/y);
	}
	
	private double roundIEEE754(Double o) {
		return BigDecimal.valueOf(o)
				.setScale(16, RoundingMode.HALF_UP)
				.doubleValue();
		
	}
}
