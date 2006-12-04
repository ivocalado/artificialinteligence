package br.ufal.ic.se.models;

public class GoalsException extends Exception {
	GoalsException() {
		super("Objetivo alcançado");
	}
	GoalsException(String mes) {
		super(mes);
	}
}
