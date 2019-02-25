/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
public class TheCParser {
    public static void main(String[] args) throws Exception {
// create a CharStream that reads from standard input
        CharStream input = CharStreams.fromFileName(".\\src\\Parser\\aC.c");
// create a lexer that feeds off of input CharStream
        CLexer cLexer = new CLexer(input);
// create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(cLexer);
// create a parser that feeds off the tokens buffer
        CParser parser = new CParser(tokens);
        ParseTree tree = parser.declarationList(); // begin parsing at init rule
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
    }
}
