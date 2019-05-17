# **Documentation**



SOFTWARE LICENCED UNDER MIT



## Overview

What It Is: an application made to compile the language we designed, C--, to MIPS assembly.

The design was largely impacted by our groups skillset. We were familiar with C, Java, and assembly so we chose these. Typically, a high-level language is compiled to a low-level language (like assembly) for a program to be executed so we followed this traditional model.



## Specifics

**What it CAN do**

​		basic calculations, arithmetic, recursive functions(like factorial)

**What it CANT do**

​		no return values from functions (only void), no dynamic memory allocation, no C structs, no floating point arithmetic, no data types other than integer and Boolean

**Who is it for**

​		C-- to MIPS was made for the purpose of educational fun, for those curious souls wishing to "look under the hood" at how a high level language is broken down into machine instructions.

**Limitations**: what NOT to use "C-- to MIPS" app for

​		NOT reccomended for large scale programming

​		NOT for object oriented applications



## Tutorial/Setup Guide

How to use the app:

​	Install process: clone or download the github repository to your local machine.

​	Package Management/Build Tool: Maven and Antlr

​	How to use compiler/interpreter: 

*see powerpoint slides 6-10*



## C-- Reference Guide

​	Language conventions are similar to that of C so those familiar with C should feel comfortable with using C--. Naming conventions look like my_variable, MY_CONSTANT, my_function but other naming schemes like camelCase are acceptable. Things that are not allowed in naming of variables and functions are starting them with a numeric or special character.

**Reserved Words/Keywords**

​	`int`	`bool`	`return`	`void`	`if`	`else`



### Syntax

type ::= int | bool | *type | funptr

retType ::= void

op ::= + | *					       

exp ::= exp op exp | var | i

comment ::= // str* | /* str* *\ 

stmt ::= vardec | vardef | fundef | funcall | ctrlstruct

vardec ::= type var;

vardef ::= var = exp;

fundef ::= void foo(funptr |type arg0,...,type argn) {exp*};

funcall ::= foo(funptr | type arg0,...,type argn);

ctrlstruct ::= if(exp) {stmt*} | 

while(exp) {stmt*}



## Conclusion

​	Our design choice was originally more ambitious than what we ended up with. In the end we restricted our language even further due to complications encountered when writing the compiler. Since we implemented the compiler in Java, the code for the ASTs and parsing got lengthy. Due to the small size of our group, in order to produce deliverables, we now have a very simple syntax. Features that were more complicated to implement were excluded. For example, multiplication, division and structs were omitted, and functions only return void. We chose to implement function pointers as our abstract of computation because C has a clunky syntax for such operation. We made it more like that of Rust's syntax.

​	The lexer and parser constructed using Antlr are functional. An Antlr tree structure is returned and printed to the screen, similar to an AST.

​	We hit a rough patch when trying to transform the Antlr output into an AST. We would have chosen a different implementation language, probably something with pattern matching so a dozen lines of code could be condensed into about two.

​	Our unit test coverage is working. However, our application is not fully integrated. The lexer and parser are integrated and take a program in C-- and produce an Antlr style AST. The type checker and code generator function as individual units, as shown through our comprehensive unit tests.

​	Some obstacles we had to overcome included starting as a 4 person team and ending with two, working as a cohesive unit and maintaining communication, and the challenge of "learning on the fly" due to a lack of prior knowledge of compilers. This project was a stretch but rightfully so. Looking back, we would have planned things out better and delegated work in a more structured manner to avoid duplicate effort and "wheel spinning." We would have had more meetings to code together as a group so if one of us got stuck we could help the other out, which proved to be a challenge when communicating over Slack and text.



 







