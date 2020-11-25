package lpl.util;

import ir.ast.IRExp;
import ir.ast.IRExpBinOp;
import ir.ast.IRExpCall;
import ir.ast.IRExpConst;
import ir.ast.IRExpESeq;
import ir.ast.IRExpMem;
import ir.ast.IRExpName;
import ir.ast.IRExpTemp;
import ir.ast.IROp;
import ir.ast.IRStm;
import ir.ast.IRStmCJump;
import ir.ast.IRStmEpilogue;
import ir.ast.IRStmExp;
import ir.ast.IRStmJump;
import ir.ast.IRStmLabel;
import ir.ast.IRStmMoveMem;
import ir.ast.IRStmMoveTemp;
import ir.ast.IRStmNoop;
import ir.ast.IRStmPrologue;
import ir.ast.IRStmSeq;
import java.util.List;

public class IRFactory {

    /* ********************************************************************** */
    /*                                                                        */
    /* Tip: use a static import in the classes where you want to use these    */
    /* factory methods:                                                       */
    /*                                                                        */
    /*    import static lpl.util.IRFactory.*                                  */
    /*                                                                        */
    /* These factory methods methods are not strictly necessary but they      */
    /* greatly simplify the code that you have to write. For example, instead */
    /* of:                                                                    */
    /*                                                                        */
    /*    new IRStmMoveTemp("x",                                              */
    /*                      new IRExpBinOp(new IRExpTemp("y"),                */
    /*                                     IROp.EQ,                           */
    /*                                     new IRExpConst(7)                  */
    /*                                    )                                   */
    /*                     )                                                  */
    /*                                                                        */
    /* you can write:                                                         */
    /*                                                                        */
    /*    MOVE(TEMP("x"), BINOP(TEMP("y"), IROp.EQ, CONST(7)))                */
    /*                                                                        */
    /* ********************************************************************** */

    /* ************************************************ */
    /* Convenience factory methods for building IRStms. */
    /* ************************************************ */
    public static IRStm MOVE(IRExp el, IRExp er) {
        if (el instanceof IRExpTemp) {
            return new IRStmMoveTemp(((IRExpTemp) el).name, er);
        } else if (el instanceof IRExpMem) {
            return new IRStmMoveMem(((IRExpMem) el).e, er);
        } else {
            throw new Error("Left-expression of MOVE must be either a TEMP or a MEM, not: " + el);
        }
    }

    public static IRStmNoop NOOP = new IRStmNoop();

    public static IRStmJump JUMP(IRExp e) {
        return new IRStmJump(e);
    }

    public static IRStmCJump CJUMP(IRExp e1, IROp op, IRExp e2, String trueLabel, String falseLabel) {
        return new IRStmCJump(e1, op, e2, trueLabel, falseLabel);
    }

    public static IRStmExp EXP(IRExp e) {
        return new IRStmExp(e);
    }

    public static IRStmLabel LABEL(String name) {
        return new IRStmLabel(name);
    }

    public static IRStm SEQ(IRStm... stms) {
        int n = stms.length;
        if (n == 0) {
            return NOOP;
        }
        IRStm stm = stms[n - 1];
        for (int i = n - 2; i >= 0; --i) {
            stm = new IRStmSeq(stms[i], stm);
        }
        return stm;
    }

    public static IRStm SEQ(List<IRStm> stms) {
        return SEQ(stms.toArray(new IRStm[0]));
    }

    public static IRStmPrologue PROLOGUE(int params, int locals) {
        return new IRStmPrologue(params, locals);
    }

    public static IRStmEpilogue EPILOGUE(int params, int locals) {
        return new IRStmEpilogue(params, locals);
    }

    /* ************************************************ */
    /* Convenience factory methods for building IRExps. */
    /* ************************************************ */
    public static IRExpBinOp BINOP(IRExp e1, IROp op, IRExp e2) {
        return new IRExpBinOp(e1, op, e2);
    }

    public static IRExpCall CALL(IRExp f, IRExp... args) {
        return new IRExpCall(f, args);
    }

    public static IRExpCall CALL(IRExp f, List<IRExp> args) {
        return new IRExpCall(f, args);
    }

    public static IRExpConst CONST(int n) {
        return new IRExpConst(n);
    }

    public static IRExpMem MEM(IRExp e) {
        return new IRExpMem(e);
    }

    public static IRExpTemp TEMP(String name) {
        return new IRExpTemp(name);
    }

    public static IRExpName NAME(String labelName) {
        return new IRExpName(labelName);
    }

    public static IRExpESeq ESEQ(IRStm s, IRExp e) {
        return new IRExpESeq(s, e);
    }

}
