package lpl.ast;

import ir.ast.IRExp;
import static lpl.util.IRFactory.*;

public class ExpInteger extends Exp {

    public final int integer;

    public ExpInteger(int integer) {
        this.integer = integer;
    }

    @Override
    public IRExp compile(FunDecl fd) {
        return CONST(integer);
    }
}
