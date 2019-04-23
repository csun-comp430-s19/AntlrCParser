package type_checker;

import type_checker_syntax.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

// Tests for expressions that need no scopes.
// This means:
// -No variables
// -No structures
// -No functions
// -No pointers (which depend on variables or structures)
public class TypecheckerExpTest {
    // use null if there should be a type error
    public void assertExpType(final Type expected, final Exp exp) {
        try {
            final Type received = Typechecker.expTypeForTesting(exp);
            assertTrue("Expected type error; got: " + received.toString(),
                       expected != null);
            assertEquals(expected, received);
        } catch (final TypeErrorException e) {
            assertTrue("Unexpected type error: " + e.getMessage(),
                       expected == null);
        }
    }

    @Test
    public void testIntExp() {
        assertExpType(new IntType(),
                      new IntExp(42));
    }

    

    @Test
    public void testBoolExp() {
        assertExpType(new BoolType(),
                      new BoolExp(true));
    }

    
    @Test
    public void testSizeof() {
        assertExpType(new IntType(),
                      new SizeofExp(new BoolType()));
    }

    @Test
    public void testBinopPlusInts() {
        assertExpType(new IntType(),
                      new BinopExp(new IntExp(1),
                                   new PlusOp(),
                                   new IntExp(2)));
    }

    
    
} // TypecheckerTest

