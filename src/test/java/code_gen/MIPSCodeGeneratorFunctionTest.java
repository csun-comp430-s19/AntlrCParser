package code_gen;

import code_gen_syntax.ReturnExpStmt;
import code_gen_syntax.FunctionName;
import code_gen_syntax.VariableExp;
import code_gen_syntax.IfStmt;
import code_gen_syntax.EqualsOp;
import code_gen_syntax.ReturnVoidStmt;
import code_gen_syntax.IntType;
import code_gen_syntax.Variable;
import code_gen_syntax.VariableDeclaration;
import code_gen_syntax.VoidType;
import code_gen_syntax.Stmt;
import code_gen_syntax.IntExp;
import code_gen_syntax.Exp;
import code_gen_syntax.FieldName;
import code_gen_syntax.PrintStmt;
import code_gen_syntax.BinopExp;
import code_gen_syntax.PlusOp;
import code_gen_syntax.FunctionCallExp;
import code_gen_syntax.FunctionCallStmt;
import code_gen_syntax.Type;
import code_gen_syntax.StructureName;
import code_gen_syntax.FunctionDefinition;
import code_gen.MIPSCodeGenerator;
import static code_gen.MIPSCodeGeneratorStatementTest.vardec;
import static code_gen.MIPSCodeGeneratorStatementTest.stmts;
import static code_gen.MIPSCodeGeneratorStatementTest.printVar;
import static code_gen.MIPSCodeGeneratorStatementTest.assign;
import static code_gen.MIPSCodeGeneratorStatementTest.stmts;
import static code_gen.MIPSCodeGeneratorStatementTest.vardec;
import code_gen_syntax.WhileStmt;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

// last function is main, which is assumed a void return type
public class MIPSCodeGeneratorFunctionTest extends MIPSCodeGeneratorTestBase<FunctionDefinition[]> {
    protected void doCompile(final MIPSCodeGenerator gen, final FunctionDefinition[] functions) {
        assert(functions.length > 0);

        // main needs to be first so we fall into it
        final FunctionDefinition main = functions[functions.length - 1];
        gen.compileMainFunctionDefinition(main);
        for (int index = 0; index < functions.length - 1; index++) {
            gen.compileFunctionDefinition(functions[index]);
        }
    }

    public static Map<FunctionName, FunctionDefinition> functionMap(final FunctionDefinition[] functions) {
        final Map<FunctionName, FunctionDefinition> result = new HashMap<FunctionName, FunctionDefinition>();
        for (final FunctionDefinition def : functions) {
            assert(!result.containsKey(def.name));
            result.put(def.name, def);
        }
        return result;
    }
    
    public void assertResultF(final File expected,
                              final Map<StructureName, LinkedHashMap<FieldName, Type>> structDecs,
                              final FunctionDefinition... functions) throws IOException {
        assertResult(expected,
                     functions,
                     structDecs,
                     functionMap(functions));
    }

    public void assertResultF(final File expected,
                              final FunctionDefinition... functions) throws IOException {
        assertResultF(expected,
                      new HashMap<StructureName, LinkedHashMap<FieldName, Type>>(),
                      functions);
    }

    public static FunctionDefinition mkMain(final Stmt stmt) {
        return new FunctionDefinition(new VoidType(),
                                      new FunctionName("main"),
                                      new VariableDeclaration[0],
                                      stmt);
    }

    @Test
    public void testCallFunctionAddsParams() throws IOException {
        final FunctionName foo = new FunctionName("foo");
        final Variable x = new Variable("x");
        final Variable y = new Variable("y");
        File expected= new File("src//fun//expected26.txt");
        assertResultF(expected,
                      new FunctionDefinition(new IntType(),
                                             foo,
                                             new VariableDeclaration[] {
                                                 new VariableDeclaration(new IntType(), x),
                                                 new VariableDeclaration(new IntType(), y)
                                             },
                                             new ReturnExpStmt(new BinopExp(new VariableExp(x),
                                                                            new PlusOp(),
                                                                            new VariableExp(y)))),
                      mkMain(new PrintStmt(new FunctionCallExp(foo,
                                                               new Exp[] {
                                                                   new IntExp(1),
                                                                   new IntExp(2)
                                                               }))));
    }
    
    @Test
    public void testCanTakeStructureGetSecond() throws IOException {
        // void foo(TwoInts s) {
        //   print(s.y);
        // }
        // void main() {
        //   foo(TwoInts(1, 2));
        // }

        final FunctionName foo = new FunctionName("foo");
          final Variable x = new Variable("x");
        final Variable y = new Variable("y");
        File expected = new File("src//fun//expected28.txt");
        assertResultF(expected,new FunctionDefinition(new IntType(),
                                             foo,
                                             new VariableDeclaration[] {
                                                 new VariableDeclaration(new IntType(), x),
                                                 new VariableDeclaration(new IntType(), y)
                                             },
                                             new ReturnExpStmt(new BinopExp(new VariableExp(x),
                                                                            new PlusOp(),
                                                                            new VariableExp(y)))),
                      mkMain(new FunctionCallStmt(foo,
                                                  new Exp[] {  new IntExp(1),
                                                               new IntExp(2)
                                                               })
                                                  ));
    }
    
    
    @Test
    public void testFibonacci() throws IOException {
        // int fib(int x) {
        //   if (x == 0) {
        //     return 0;
        //   } else if (x == 1) {
        //     return 1;
        //   } else {
        //     return fib(x - 1) + fib(x - 2);
        //   }
        // }
        // void main() {
        //   print(fib(7));
        // }

        final FunctionName fib = new FunctionName("fib");
        final Variable x = new Variable("x");
        final Exp recursiveCase = new BinopExp(new FunctionCallExp(fib,
                                                                   new Exp[] {
                                                                       new BinopExp(new VariableExp(x),
                                                                                    new PlusOp(),
                                                                                    new IntExp(1))
                                                                   }),
                                               new PlusOp(),
                                               new FunctionCallExp(fib,
                                                                   new Exp[] {
                                                                       new BinopExp(new VariableExp(x),
                                                                                    new PlusOp(),
                                                                                    new IntExp(2))
                                                                   }));
        File expected= new File("src//fun//expected27.txt");
        assertResultF(expected,
                      new FunctionDefinition(new IntType(),
                                             fib,
                                             new VariableDeclaration[] {
                                                 new VariableDeclaration(new IntType(), x)
                                             },
                                             new IfStmt(new BinopExp(new VariableExp(x),
                                                                     new EqualsOp(),
                                                                     new IntExp(0)),
                                                        new ReturnExpStmt(new IntExp(0)),
                                                        new IfStmt(new BinopExp(new VariableExp(x),
                                                                                new EqualsOp(),
                                                                                new IntExp(1)),
                                                                   new ReturnExpStmt(new IntExp(1)),
                                                                   new ReturnExpStmt(recursiveCase)))),
                      mkMain(new PrintStmt(new FunctionCallExp(fib,
                                                               new Exp[] {
                                                                   new IntExp(7)
                                                               }))));
    }
  
    @Test
    public void testPrintConstantExplicitReturn() throws IOException {
        File expected= new File("src//fun//expected29.txt");
        assertResultF(expected,
                      mkMain(stmts(new PrintStmt(new IntExp(1)),
                                   new ReturnVoidStmt())));
    }

    @Test
    public void testPrintConstantImplicitReturn() throws IOException {
        File expected= new File("src//fun//expected30.txt");
        assertResultF(expected,
                      mkMain(new PrintStmt(new IntExp(1))));
    }

    @Test
    public void testCallFunctionReturnsConstantInt() throws IOException {
        File expected= new File("src//fun//expected31.txt");
        final FunctionName foo = new FunctionName("foo");
        assertResultF(expected,
                      new FunctionDefinition(new IntType(),
                                             foo,
                                             new VariableDeclaration[0],
                                             new ReturnExpStmt(new IntExp(1))),
                      mkMain(new PrintStmt(new FunctionCallExp(foo, new Exp[0]))));
    }
     @Test
    public void functionWithWile() throws IOException {
        final Variable x = new Variable("x");
        final FunctionName foo = new FunctionName("foo");
        File expected= new File("src//fun//expected32.txt");
        assertResultF(expected,
                      new FunctionDefinition(new IntType(),
                                             foo,
                                             new VariableDeclaration[0],
                                             stmts(vardec("x", new IntType(), new IntExp(0)),
                           new WhileStmt(new BinopExp(new VariableExp(x),
                                                      new EqualsOp(),
                                                      new IntExp(0)),
                                         
                           new ReturnExpStmt(new IntExp(10))),
                                             new ReturnExpStmt (new IntExp(10)))),
                      mkMain(new PrintStmt(new FunctionCallExp(foo, new Exp[0]))));
    }
    
}

