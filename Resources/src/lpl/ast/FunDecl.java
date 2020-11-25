package lpl.ast;

import ir.ast.IRStm;

import java.util.LinkedList;
import java.util.List;
import lpl.util.FreshNameGenerator;
import static lpl.util.IRFactory.*;

public class FunDecl {

    public final String functionName;
    public final List<String> formalParameters;
    public final List<Stm> body;
    
    private final String endLabel;

    public FunDecl(String functionName, List<String> formalParameters, List<Stm> body) {
        this.functionName = functionName;
        this.formalParameters = formalParameters;
        this.body = body;
        endLabel = FreshNameGenerator.makeName(functionName + "@" + "end");
    }
    
    public int getOffset(String name) {
    	return getParameterIndex(name) + 2;
    }
    
    /**
     * Compile this function declaration to IR code.
     * @return the compiled version of this function declaration
     */
    public List<IRStm> compile() {
    	List<IRStm> irstms = new LinkedList<>();
    	irstms.add(LABEL(functionName));
        irstms.add(PROLOGUE(formalParameters.size(), 0));
        irstms.add(MOVE(TEMP("RV"), CONST(0)));
        for (Stm stm : body) {
        	irstms.addAll(stm.compile(this));
        }
    	irstms.add(LABEL(endLabel));
    	irstms.add(EPILOGUE(formalParameters.size(), 0));
    	return irstms;
    }
    
    /**
     * A convenient (guaranteed unique) label that you can use to label the end
     * of a compiled function body.
     * @return a label unique to this method
     */
    public String getEndLabel() {
        return endLabel;
    }
    
    /**
     * Look up the position of a parameter name in the formal parameter list for
     * this function declaration.
     * @param name
     * @return the position of this parameter; the first position is zero
     */
    public int getParameterIndex(String name) {
        int arity = formalParameters.size();
        int pos = -1;
        for (int i = 0; i < arity; ++i) {
            if (name.equals(formalParameters.get(i))) {
                if (pos == -1) {
                    pos = i;
                } else {
                    throw new IllegalStateException("Duplicate formal parameter " + name + " in function " + functionName);
                }
            }
        }
        if (pos == -1) {
            throw new IllegalStateException("Undefined formal parameter " + name + " in function " + functionName);
        }
        return pos;
    }
    
}
