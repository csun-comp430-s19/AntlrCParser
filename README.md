# C-- Compiler

see https://github.com/csun-comp430-s19/C-- for our team's original repository. We made this new one after some design changes.

Members: Curtis Bartlett, Victoria Latta

Parser for "C--" was written using Antlr

Our language is a stripped down C style language with simple computational abilities but lacks structures and dynamic memory allocation.
The compiler itself was implemented in Java.
The compiled output is MIPS assembly.

see <this link> for how to set up Antlr environment

Using Apache Netbeans 9.0 with Maven, JUnit testing plugin, JDK 1.8

# Abstract Syntax
`type ::= int | bool | *type | funptr`

`retType ::= void`

`op ::= + | *	`		

`exp ::= exp op exp | var | i`

`comment ::= // str* | /* str* *\`

`stmt ::= vardec | vardef | fundef | funcall | ctrlstruct`

`vardec ::= type var;`

`vardef ::= var = exp;`

`fundef ::= void foo(funptr |type arg0,...,type argn) {exp*};`

`funcall ::= foo(funptr | type arg0,...,type argn);`

`ctrlstruct ::= if(exp) {stmt*} | while(exp) {stmt*}`


