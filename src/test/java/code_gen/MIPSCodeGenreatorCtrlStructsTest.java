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
import syntax.*;

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

public class MIPSCodeGenreatorCtrlStructsTest
{

    /*
    final Map<StructureName, LinkedHashMap<FieldName, Type>> TWO_INTS =
        new HashMap<StructureName, LinkedHashMap<FieldName, Type>>() {{
            put(new StructureName("TwoInts"), new LinkedHashMap<FieldName, Type>() {{
                put(new FieldName("x"), new IntType());
                put(new FieldName("y"), new IntType());
            }});
        }};
     */
    //WHAT IS THIS FOR^^

    @Rule
    public TestName name = new TestName();

    // public int parseOutput() ???
    // public void assertResult() ???
    
    public static Stmt stmts(final Stmt... stmts)
    {
        assert (stmts.length > 0);
        Stmt result = stmts[stmts.length - 1];

        for (int index = stmts.length - 2; index >= 0; index--)
        {
            result = new SequenceStmt(stmts[index], result);
        }
        return result;
    }
    // what is going on here??^^
    public static IfStmt ifStrcts(final IfStmt... ifStrcts)
    {
        assert (ifStrcts.length > 0);
        IfStmt result = ifStrcts[ifStrcts.length - 1];
        
        for(int i = ifStrcts.length - 2; i >= 0; i--)
        {
            result = new IfStmt(ifStrcts[i], result);   //make IfStmtBlock class
                                                        //in syntax package?
        }
        return result;
    }
    
    public static IfStmt ifStrct()
    {
        result = new IfStmt(Exp guard, Stmt ifTrue, Stmt ifFalse);
    }
}
