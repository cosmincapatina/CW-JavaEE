package lpl.ast;

import ir.ast.IRExp;
import ir.ast.IRExpBinOp;
import ir.ast.IROp;

import static lpl.util.IRFactory.*;

public class BinaryOpExp extends Exp {

	public final Exp exp1;
	public final Exp exp2;
	public final String op;
	
	public BinaryOpExp(Exp exp1, Exp exp2, String op) {
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.op = op;
	}
	
	@Override
	public IRExp compile(FunDecl fd) {
		IRExp e1;
		IROp op;
		IRExp e2;
		switch (this.op) {
		case "+":
			e1 = exp1.compile(fd);
			op = IROp.ADD;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		case "-":
			e1 = exp1.compile(fd);
			op = IROp.SUB;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		case "*":
			e1 = exp1.compile(fd);
			op = IROp.MUL;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		case "<":
			e1 = exp1.compile(fd);
			op = IROp.LT;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		case "==":
			e1 = exp1.compile(fd);
			op = IROp.EQ;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		default:
			e1 = exp1.compile(fd);
			op = IROp.ADD;
			e2 = exp2.compile(fd);
			return new IRExpBinOp(e1, op, e2);
		}
	}

}
