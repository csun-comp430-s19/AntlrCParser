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
import org.apache.commons.io.*;


public class MIPSCodeGeneratorTest {
    @Rule public TestName name = new TestName();
   

    public void assertResult(final File expected, final Exp exp) throws IOException {
        assertResult(expected, exp, new HashMap<StructureName, LinkedHashMap<FieldName, Type>>());
    }
    
    public void assertResult(final File expected,
                             final Exp exp,
                             final Map<StructureName, LinkedHashMap<FieldName, Type>> structDecs) throws IOException {
            final MIPSCodeGenerator gen = new MIPSCodeGenerator(structDecs);
            File outputFile = new File("src//output.txt"); 
            gen.compileExpression(exp);
            gen.writeCompleteFile(outputFile, true);
            boolean isTwoEqual = FileUtils.contentEquals(expected, outputFile);
            assertEquals(true, isTwoEqual);
    }            
            
    @Test
    public void testIntLiteral() throws IOException {
        File expected = new File("src//expected1.txt");
        assertResult(expected, new IntExp(1));
    }
    
    @Test
    public void testBoolLiteralTrue() throws IOException {
        File expected = new File("src//expected2.txt");
        assertResult(expected, new BoolExp(true));
    }
     
    @Test
    public void testBoolLiteralFalse() throws IOException {
        File expected = new File("src//expected3.txt");
        assertResult(expected, new BoolExp(false));
    }

    @Test
    public void testEqualsIntTrue() throws IOException {
        File expected = new File("src//expected4.txt");
        assertResult(expected, new BinopExp(new IntExp(42),
                                     new EqualsOp(),
                                     new IntExp(42)));
    }

    @Test
    public void testEqualsIntFalse() throws IOException {
        File expected = new File("src//expected11.txt");
        assertResult(expected, new BinopExp(new IntExp(42),
                                     new EqualsOp(),
                                     new IntExp(43)));
    }

    @Test
    public void testEqualsBoolTrue() throws IOException {
        File expected = new File("src//expected5.txt");
        assertResult(expected, new BinopExp(new BoolExp(false),
                                     new EqualsOp(),
                                     new BoolExp(false)));
    }

    @Test
    public void testEqualsBoolFalse() throws IOException {
        File expected = new File("src//expected6.txt");
        assertResult(expected, new BinopExp(new BoolExp(true),
                                     new EqualsOp(),
                                     new BoolExp(false)));
    }
    
    @Test
    public void testPlus() throws IOException {
        // 2 + 3 = 5
        File expected = new File("src//expected7.txt");
        assertResult(expected, new BinopExp(new IntExp(2),
                                     new PlusOp(),
                                     new IntExp(3)));
    }

        
    @Test
    public void testSizeofInt() throws IOException {
        File expected = new File("src//expected8.txt");
        assertResult(expected, new SizeofExp(new IntType()));
    }

    @Test
    public void testSizeofBool() throws IOException {
        File expected = new File("src//expected9.txt");
        assertResult(expected, new SizeofExp(new BoolType()));
    }

    

    @Test
    public void testSizeofPointer() throws IOException {
        File expected = new File("src//expected10.txt");
        assertResult(expected, new SizeofExp(new PointerType(new IntType())));
    }
    
    

    

    

   
} // MIPSCodeGeneratorTest

