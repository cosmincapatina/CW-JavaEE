package lpl.ast;

import java.util.LinkedList;
import java.util.List;

import ir.ast.IRExp;
import static lpl.util.IRFactory.*;

public class ExpFunCall extends Exp {

	public final String functionName;
	public final List<Exp> parameterExpresses;
	
	public ExpFunCall(String functionName, List<Exp> parameterExpresses) {
		this.functionName = functionName;
		this.parameterExpresses = parameterExpresses;
	}
	
	@Override
	public IRExp compile(FunDecl fd) {
		List<IRExp> irargs = new LinkedList<>();
		for (Exp parameterExpress : parameterExpresses) {
			irargs.add(parameterExpress.compile(fd));
		}
		return CALL(NAME(functionName), irargs);
	}

}
