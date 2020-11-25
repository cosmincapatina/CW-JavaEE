package lpl.ast;

import ir.ast.IRExp;
import ir.ast.IROp;

import static lpl.util.IRFactory.*;

public class ExpId extends Exp {

	public final String id;
	
	public ExpId(String id) {
		this.id = id;
	}
	
	@Override
	public IRExp compile(FunDecl fd) {
		return MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(fd.getOffset(id))));
	}
}
