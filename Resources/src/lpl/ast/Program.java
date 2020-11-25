package lpl.ast;

import ir.ast.IRProgram;
import ir.ast.IRExp;
import ir.ast.IROp;
import ir.ast.IRStm;

import java.util.LinkedList;
import java.util.List;

import static lpl.util.IRFactory.*;

public class Program {
    
    public final List<FunDecl> functionDeclarations;
    
    public Program(List<FunDecl> functionDeclarations) {
        this.functionDeclarations = functionDeclarations;
    }
    
    public IRProgram compile() {
    	List<IRStm> irstms = new LinkedList<>();
        List<IRExp> irargs = new LinkedList<>();
        FunDecl topLevel = functionDeclarations.get(0);
        for (int i = 0; i < topLevel.formalParameters.size(); ++i) {
            irargs.add(MEM(BINOP(TEMP("FP"), IROp.SUB, CONST(i + 1))));
        }

        irstms.add(EXP(CALL(NAME(topLevel.functionName), irargs)));
        irstms.add(JUMP(NAME("_END")));

        for (FunDecl functionDeclaration: functionDeclarations) {
        	irstms.addAll(functionDeclaration.compile());
  		}
        
        irstms.add(LABEL("cons"));
        irstms.add(PROLOGUE(2, 0));
        irstms.add(MOVE(TEMP("SS"), CALL(NAME("_malloc"), CONST(2))));
        IRExp firstAddressExp = TEMP("SS");
        IRExp firstParamExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(2)));
        irstms.add(MOVE(MEM(firstAddressExp), firstParamExp));
        IRExp secondAddressExp = BINOP(firstAddressExp, IROp.ADD, CONST(1));
        IRExp secondParamExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(3)));
        irstms.add(MOVE(MEM(secondAddressExp), secondParamExp));
        irstms.add(MOVE(TEMP("RV"), firstAddressExp));
    	irstms.add(EPILOGUE(2, 0));
    	
    	irstms.add(LABEL("fst"));
    	irstms.add(PROLOGUE(1, 0));
    	firstParamExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(2)));
    	irstms.add(MOVE(TEMP("RV"), MEM(firstParamExp)));
    	irstms.add(EPILOGUE(1, 0));
       
    	irstms.add(LABEL("snd"));
    	irstms.add(PROLOGUE(1, 0));
    	firstParamExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(2)));
    	irstms.add(MOVE(TEMP("RV"), MEM(BINOP(firstParamExp, IROp.ADD, CONST(1)))));
    	irstms.add(EPILOGUE(1, 0));
    	
    	irstms.add(LABEL("empty"));
    	irstms.add(PROLOGUE(0, 0));
    	irstms.add(MOVE(TEMP("RV"), CONST(0)));
    	irstms.add(EPILOGUE(0, 0));
    	
    	irstms.add(LABEL("isempty"));
    	irstms.add(PROLOGUE(1, 0));
    	firstParamExp = MEM(BINOP(TEMP("FP"), IROp.ADD, CONST(2)));
    	irstms.add(MOVE(TEMP("RV"), BINOP(firstParamExp, IROp.EQ, CONST(0))));
    	irstms.add(EPILOGUE(1, 0));
    	
        return new IRProgram(irstms);
    }
}
