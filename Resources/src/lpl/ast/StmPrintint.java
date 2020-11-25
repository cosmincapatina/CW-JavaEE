package lpl.ast;

import ir.ast.IRStm;
import java.util.ArrayList;
import java.util.List;
import static lpl.util.IRFactory.*;

public class StmPrintint extends Stm {
    
    public final Exp express;
    
    public StmPrintint(Exp express) {
        this.express = express;
    }

    @Override
    public List<IRStm> compile(FunDecl fd) {
        List<IRStm> irstms = new ArrayList<>();
        irstms.add(EXP(CALL(NAME("_printint"), express.compile(fd))));
        return irstms;
    }

}
