package br.ufal.ic.se.models;

public class GoalsException extends Exception {
	GoalsException() {
		super("Objetivo alcan�ado");
	}
	GoalsException(String mes) {
		super(mes);
	}
}
