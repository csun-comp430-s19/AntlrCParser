/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code_gen;

/**
 *
 * @author Victoria
 */
public class Jal implements MIPSInstruction {
    public final MIPSLabel jumpTo;

    public Jal(final MIPSLabel jumpTo) {
        this.jumpTo = jumpTo;
    }

    public String toString() {
        return (MIPSInstruction.INDENT + "jal " +
                jumpTo.getName());
    }

    public boolean equals(final Object other) {
        return (other instanceof Jal &&
                ((Jal)other).jumpTo.equals(jumpTo));
    }

    public int hashCode() {
        return jumpTo.hashCode();
    }
} // Jal
