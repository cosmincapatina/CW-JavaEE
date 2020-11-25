package lpl.ast;

import java.util.LinkedList;
import java.util.List;
import ir.ast.IRExp;
import ir.ast.IROp;
import ir.ast.IRStm;
import static lpl.util.IRFactory.*;

public class StmAssign extends Stm {

	public final String id;
	public final Exp express;
	
	public StmAssign(String id, Exp express) {
		this.id = id;
		this.express = express;
	}
	
	@Override
	public List<IRStm> compile(FunDecl fd) {
		List<IRStm> irstms = new LinkedList<>();
        IRExp leftExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(fd.getOffset(id))));
        IRExp rightExp = express.compile(fd);
        irstms.add(MOVE(leftExp, rightExp));
        return irstms;
	}

}
