package es.upm.etsisi.symderivation.arith;

import es.upm.etsisi.symderivation.SymFunction;

/**
 * Multiplicative inverse function i.e. -^(-1).
 * 
 * Token notation: inv.
 * 
 * @author Angel Gonzalez-Prieto
 *
 */
public class SymInv extends SymPowNumeric{
	public SymInv(SymFunction arg) {
		super(arg, -1);
	}
}
