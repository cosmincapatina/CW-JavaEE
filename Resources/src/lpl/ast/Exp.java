package lpl.ast;

import ir.ast.IRExp;

public abstract class Exp {
    
    /**
     * Compile an LPL expression to an IR expression.
     * @param fd the function declaration that contains this expression.
     * @return the compiled version of this expression
     */
    public abstract IRExp compile(FunDecl fd);

}
