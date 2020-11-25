package lpl.ast;

import java.util.LinkedList;
import java.util.List;

import ir.ast.IRExp;
import ir.ast.IRStm;
import static lpl.util.IRFactory.*;

public class StmFunCall extends Stm {

	public final String functionName;
	public final List<Exp> parameterExpresses;
	
	public StmFunCall(String functionName, List<Exp> parameterExpresses) {
		this.functionName = functionName;
		this.parameterExpresses = parameterExpresses;
	}
	
	@Override
	public List<IRStm> compile(FunDecl fd) {
		List<IRStm> irstms = new LinkedList<>();
		List<IRExp> irargs = new LinkedList<>();
		for (Exp parameterExpress : parameterExpresses) {
			irargs.add(parameterExpress.compile(fd));
		}
		irstms.add(EXP(CALL(NAME(functionName), irargs)));
		return irstms;
	}

}
