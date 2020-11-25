package lpl.ast;

import ir.ast.IRExp;

public class ExpParen extends Exp {

	public final Exp express;
	
	public ExpParen(Exp express) {
		this.express = express;
	}
	
	@Override
	public IRExp compile(FunDecl fd) {
		return express.compile(fd);
	}

}
