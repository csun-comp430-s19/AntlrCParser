package code_gen;

public class Slt extends ThreeRegisterInstruction {
    public Slt(final MIPSRegister rd,
               final MIPSRegister rs,
               final MIPSRegister rt) {
        super("slt", rd, rs, rt);
    }
} // Slt

               
