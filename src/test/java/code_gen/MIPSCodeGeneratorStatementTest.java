package code_gen;


import syntax.VariableLhs;
import syntax.IntType;
import syntax.DereferenceLhs;
import syntax.VariableDeclarationInitializationStmt;
import syntax.Type;
import syntax.Variable;
import syntax.Exp;
import syntax.PrintStmt;
import syntax.VariableDeclaration;
import syntax.Stmt;
import syntax.PointerType;
import syntax.SequenceStmt;
import syntax.Lhs;
import syntax.FieldName;
import syntax.VariableExp;
import syntax.StructureName;
import syntax.AssignmentStmt;
import syntax.AddressOfExp;
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

public class MIPSCodeGeneratorStatementTest {
    final Map<StructureName, LinkedHashMap<FieldName, Type>> TWO_INTS =
        new HashMap<StructureName, LinkedHashMap<FieldName, Type>>() {{
            put(new StructureName("TwoInts"), new LinkedHashMap<FieldName, Type>() {{
                put(new FieldName("x"), new IntType());
                put(new FieldName("y"), new IntType());
            }});
        }};

    final Map<StructureName, LinkedHashMap<FieldName, Type>> DOUBLE_TWO_INTS =
        new HashMap<StructureName, LinkedHashMap<FieldName, Type>>() {{
            final StructureName twoInts = new StructureName("TwoInts");
            put(twoInts, TWO_INTS.get(twoInts));
            put(new StructureName("FourInts"), new LinkedHashMap<FieldName, Type>() {{
                }});
        }};

    @Rule public TestName name = new TestName();
    
    // TODO: code duplication with MIPSCodeGeneratorTest
    public int parseOutput(final String[] spimOutput) {
        assert(spimOutput.length == 2);
        return Integer.parseInt(spimOutput[1]);
    } // parseOutput

    public void assertResult(final int expected, final Stmt stmt) throws IOException {
        assertResult(expected, stmt, new HashMap<StructureName, LinkedHashMap<FieldName, Type>>());
    }
        
    public void assertResult(final int expected,
                             final Stmt stmt,
                             final Map<StructureName, LinkedHashMap<FieldName, Type>> structDecs) throws IOException {
        boolean wantToSaveFile = true; // for debugging

        final File file = File.createTempFile(name.getMethodName(),
                                              ".asm",
                                              new File("testPrograms"));
        boolean testPassed = false;
        try {
            final MIPSCodeGenerator gen = new MIPSCodeGenerator(structDecs);
            gen.compileStatement(stmt);
            gen.writeCompleteFile(file, false);
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

    public VariableDeclarationInitializationStmt vardec(final String variableName,
                                                        final Type type,
                                                        final Exp exp) {
        return new VariableDeclarationInitializationStmt(new VariableDeclaration(type,
                                                                                 new Variable(variableName)),
                                                         exp);
    }

    public static Stmt stmts(final Stmt... stmts) {
        assert(stmts.length > 0);
        Stmt result = stmts[stmts.length - 1];

        for (int index = stmts.length - 2; index >= 0; index--) {
            result = new SequenceStmt(stmts[index], result);
        }
        return result;
    }

    public static PrintStmt printVar(final String varName) {
        return new PrintStmt(new VariableExp(new Variable(varName)));
    }

    public static AssignmentStmt assign(final Lhs lhs, final Exp exp) {
        return new AssignmentStmt(lhs, exp);
    }
    
    public static AssignmentStmt assign(final String varName, final Exp exp) {
        return assign(new VariableLhs(new Variable(varName)), exp);
    }

    @Test
    public void testSingleIntVariableDeclaration() throws IOException {
        assertResult(1, stmts(vardec("x", new IntType(), new IntExp(1)),
                              printVar("x")));
    }

    @Test
    public void testDoubleIntVariableDeclarationGetFirst() throws IOException {
        assertResult(1, stmts(vardec("x", new IntType(), new IntExp(1)),
                              vardec("y", new IntType(), new IntExp(2)),
                              printVar("x")));
    }

    @Test
    public void testDoubleIntVariableDeclarationGetSecond() throws IOException {
        assertResult(2, stmts(vardec("x", new IntType(), new IntExp(1)),
                              vardec("y", new IntType(), new IntExp(2)),
                              printVar("y")));
    }

    @Test
    public void testSingleIntAssignment() throws IOException {
        assertResult(2, stmts(vardec("x", new IntType(), new IntExp(1)),
                              assign("x", new IntExp(2)),
                              printVar("x")));
    }

    @Test
    public void testTwoIntsAssignFirst() throws IOException {
        assertResult(3, stmts(vardec("x", new IntType(), new IntExp(1)),
                              vardec("y", new IntType(), new IntExp(2)),
                              assign("x", new IntExp(3)),
                              printVar("x")));
    }

    @Test
    public void testTwoIntsAssignSecond() throws IOException {
        assertResult(3, stmts(vardec("x", new IntType(), new IntExp(1)),
                              vardec("y", new IntType(), new IntExp(2)),
                              assign("y", new IntExp(3)),
                              printVar("y")));
    }
   
    @Test
    public void testAssignIntThroughPointer() throws IOException {
        // int x = 5;
        // int* p = &x;
        // *p = 7;
        // print(x)

        final DereferenceLhs deref = new DereferenceLhs(new VariableLhs(new Variable("p")));
        deref.setTypeAfterDereference(new IntType());
        
        assertResult(7,
                     stmts(vardec("x",
                                  new IntType(),
                                  new IntExp(5)),
                           vardec("p",
                                  new PointerType(new IntType()),
                                  new AddressOfExp(new VariableLhs(new Variable("x")))),
                           assign(deref,
                                  new IntExp(7)),
                           printVar("x")));
    }
}

   
