// spim opens, loads file, doesnt run the file though
// run manually, returns results to console, but not back to Java program
// testi fails because it gets nothing back from spim :(

//program in c-- >> expected
// compile(program)
//compare to expected

//run(compile(program)) == expected




package code_gen;



import syntax.IntType;
import syntax.Type;
import syntax.SizeofExp;
import syntax.BinopExp;
import syntax.BoolExp;
import syntax.Exp;
import syntax.CastExp;
import syntax.PlusOp;
import syntax.PointerType;
import syntax.FieldName;
import syntax.StructureName;
import syntax.BoolType;
import syntax.EqualsOp;
import syntax.IntExp;
import code_gen.MIPSCodeGenerator;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TestName;

public class MIPSCodeGeneratorTest {
    @Rule public TestName name = new TestName();

    public int parseOutput(final String[] spimOutput) {
        assert(spimOutput.length == 2);
        return Integer.parseInt(spimOutput[1]);
    } // parseOutput

    public void assertResult(final int expected, final Exp exp) throws IOException {
        assertResult(expected, exp, new HashMap<StructureName, LinkedHashMap<FieldName, Type>>());
    }
    
    public void assertResult(final int expected,
                             final Exp exp,
                             final Map<StructureName, LinkedHashMap<FieldName, Type>> structDecs) throws IOException {
        boolean wantToSaveFile = true; // for debugging

        final File file = File.createTempFile(name.getMethodName(),
                                              ".asm",
                                              new File("C:/testPrograms/"));
        boolean testPassed = false;
        try {
            final MIPSCodeGenerator gen = new MIPSCodeGenerator(structDecs, null); //added second parameter "null"
            gen.compileExpression(exp);
            gen.writeCompleteFile(file); // removed second parameter "true"
            final String[] output = SPIMRunner.runFile(file);
            final int received = parseOutput(output);
            if (wantToSaveFile) {
                assertEquals("Expected: " + expected + " Received: " + received + " File: " +
                             file.getAbsolutePath(),
                             expected,
                             received);
            } else {
                assertEquals(expected, received);
            }
            testPassed = true;
        } finally {
            if (!wantToSaveFile || testPassed) {
                file.delete();
            }
        }
    } // assertResult

    @Test
    public void testIntLiteral() throws IOException {
        assertResult(1, new IntExp(1));
    }

    @Test
    public void testBoolLiteralTrue() throws IOException {
        assertResult(1, new BoolExp(true));
    }

    @Test
    public void testBoolLiteralFalse() throws IOException {
        assertResult(0, new BoolExp(false));
    }

    @Test
    public void testEqualsIntTrue() throws IOException {
        assertResult(1, new BinopExp(new IntExp(42),
                                     new EqualsOp(),
                                     new IntExp(42)));
    }

    @Test
    public void testEqualsIntFalse() throws IOException {
        assertResult(0, new BinopExp(new IntExp(42),
                                     new EqualsOp(),
                                     new IntExp(43)));
    }

    @Test
    public void testEqualsBoolTrue() throws IOException {
        assertResult(1, new BinopExp(new BoolExp(false),
                                     new EqualsOp(),
                                     new BoolExp(false)));
    }

    @Test
    public void testEqualsBoolFalse() throws IOException {
        assertResult(0, new BinopExp(new BoolExp(true),
                                     new EqualsOp(),
                                     new BoolExp(false)));
    }

    @Test
    public void testPlus() throws IOException {
        // 2 + 3 = 5
        assertResult(5, new BinopExp(new IntExp(2),
                                     new PlusOp(),
                                     new IntExp(3)));
    }

        
    @Test
    public void testSizeofInt() throws IOException {
        assertResult(4, new SizeofExp(new IntType()));
    }

    @Test
    public void testSizeofBool() throws IOException {
        assertResult(4, new SizeofExp(new BoolType()));
    }

    

    @Test
    public void testSizeofPointer() throws IOException {
        assertResult(4, new SizeofExp(new PointerType(new IntType())));
    }

    

    

    

   
} // MIPSCodeGeneratorTest

