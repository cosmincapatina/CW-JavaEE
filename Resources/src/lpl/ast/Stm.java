package lpl.ast;

import ir.ast.IRStm;
import java.util.List;

public abstract class Stm {
    
    /**
     * Compile an LPL statement to a sequence of IR statements.
     * @param fd the function declaration that contains this statement.
     * @return the compiled version of this statement
     */
    public abstract List<IRStm> compile(FunDecl fd);
    
}
