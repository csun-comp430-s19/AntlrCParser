package code_gen;

public class Seq extends ThreeRegisterInstruction {
    public Seq(final MIPSRegister rd,
               final MIPSRegister rs,
               final MIPSRegister rt) {
        super("seq", rd, rs, rt);
    }
} // Seq
