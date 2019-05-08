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
public class Jr extends SingleRegisterInstruction {
    public Jr(final MIPSRegister rd) {
        super("jr", rd);
    }
}