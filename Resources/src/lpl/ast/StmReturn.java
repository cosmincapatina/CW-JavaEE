package lpl.ast;

import java.util.LinkedList;
import java.util.List;
import static lpl.util.IRFactory.*;

import ir.ast.IRStm;

public class StmReturn extends Stm {

	public final Exp express;
	
	public StmReturn(Exp express) {
		this.express = express;
	}
	
	@Override
	public List<IRStm> compile(FunDecl fd) {
		List<IRStm> irstms = new LinkedList<>();
		irstms.add(MOVE(TEMP("RV"), express.compile(fd)));
		irstms.add(JUMP(NAME(fd.getEndLabel())));
		return irstms;
	}

}
