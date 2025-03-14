package com.example.util;

public class Factura {
	Calculadora calc;
	RepositoryInt r;	
	
	public Factura(Calculadora calc, RepositoryInt r) {
		super();
		this.calc = calc;
		this.r=r;
	}
	
	public double calcTotal(int cantidad, double precio) {
		return calc.suma(cantidad, precio);
	}
	
	public void emitir() {
		r.guardar();
	}
}
