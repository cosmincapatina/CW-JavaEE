package lpl.ast;

import java.util.LinkedList;
import java.util.List;
import ir.ast.IRStm;
import static lpl.util.IRFactory.*;

public class StmPrintchar extends Stm {

	public final Exp express;
	
	public StmPrintchar(Exp express) {
		this.express = express;
	}
	
	@Override
	public List<IRStm> compile(FunDecl fd) {
		List<IRStm> irstms = new LinkedList<>();
        irstms.add(EXP(CALL(NAME("_printchar"), express.compile(fd))));
        return irstms;
	}

}
