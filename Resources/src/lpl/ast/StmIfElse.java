package lpl.ast;

import java.util.LinkedList;
import java.util.List;
import static lpl.util.IRFactory.*;

import ir.ast.IROp;
import ir.ast.IRStm;
import lpl.util.FreshNameGenerator;

public class StmIfElse extends Stm {

	public final Exp express;
	public final List<Stm> ifStatements;
	public final List<Stm> elseStatements;
	
	public StmIfElse(Exp express, List<Stm> ifStatements, List<Stm> elseStatements) {
		this.express = express;
		this.ifStatements = ifStatements;
		this.elseStatements = elseStatements;
	}
	
	@Override
	public List<IRStm> compile(FunDecl fd) {
		List<IRStm> stms = new LinkedList<>();
        String trueLabel = FreshNameGenerator.makeName(fd.functionName);
        String falseLabel = FreshNameGenerator.makeName(fd.functionName);
        String endLabel = FreshNameGenerator.makeName(fd.functionName);
        stms.add(CJUMP(CONST(0), IROp.LT, express.compile(fd), trueLabel, falseLabel));
        stms.add(LABEL(trueLabel));
        for(Stm ifStatement : ifStatements) {
        	stms.addAll(ifStatement.compile(fd));
        }
        stms.add(JUMP(NAME(endLabel)));
        stms.add(LABEL(falseLabel));
        for (Stm elseStatement : elseStatements) {
        	stms.addAll(elseStatement.compile(fd));
        }
        stms.add(LABEL(endLabel));
        return stms;
	}

}
