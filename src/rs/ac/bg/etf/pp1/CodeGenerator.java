package rs.ac.bg.etf.pp1;

import java.io.ObjectOutputStream.PutField;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.HelperClass.ExprMinusFounder;
import rs.ac.bg.etf.pp1.HelperClass.NewArrayFounder;
import rs.ac.bg.etf.pp1.ast.Add;
import rs.ac.bg.etf.pp1.ast.AddoperationTerm;
import rs.ac.bg.etf.pp1.ast.CharFactor;
import rs.ac.bg.etf.pp1.ast.DesArray;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorArrayHelper;
import rs.ac.bg.etf.pp1.ast.DesignatorAssign;
import rs.ac.bg.etf.pp1.ast.DesignatorDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorIncrement;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.DesignatorStmtOptions;
import rs.ac.bg.etf.pp1.ast.Div;
import rs.ac.bg.etf.pp1.ast.ExprMinus;
import rs.ac.bg.etf.pp1.ast.FactoDesignator;
import rs.ac.bg.etf.pp1.ast.FactorArrayOpt;
import rs.ac.bg.etf.pp1.ast.FactorDesFindAny;
import rs.ac.bg.etf.pp1.ast.FactorNew;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodDeclList;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.Mull;
import rs.ac.bg.etf.pp1.ast.MullopFact;
import rs.ac.bg.etf.pp1.ast.NumFactor;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private boolean retFound = false;
	private int mainPc;
	private int only_jmp;
	private int only_jmp2;
	private int only_jmp3;
	private int jmpEqAdress;
	private int jmpLeQAdress;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(MethodTypeName methodTypeName) {
		if ("main".equalsIgnoreCase(methodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();

		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);

		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
		retFound = false;
	}

	public void visit(MethodDecl methodDecl) {
		if (!retFound) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
	}

	public void visit(ReturnStmt returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		retFound = true;
	}

	public void visit(DesignatorStatement designatorStatement) {
		if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorAssign.class) {
			if (designatorStatement.getDesignator().obj.getType().getKind() != Struct.Array) {
				Code.store(designatorStatement.getDesignator().obj);
			} else {
				NewArrayFounder naf = new NewArrayFounder();
				designatorStatement.traverseBottomUp(naf);
				if (!naf.getFound()) {
					if (designatorStatement.getDesignator().obj.getType().getElemType() == MyTab.intType) {
						Code.put(Code.astore);
					} else {
						Code.put(Code.bastore);
					}
				} else {
					Code.store(designatorStatement.getDesignator().obj);
				}
			}
		} else if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorIncrement.class) {
			Code.load(designatorStatement.getDesignator().obj);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(designatorStatement.getDesignator().obj);
		} else if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorDecrement.class) {
			Code.load(designatorStatement.getDesignator().obj);
			Code.put(Code.const_m1);
			Code.put(Code.add);
			Code.store(designatorStatement.getDesignator().obj);
		}
	}

	public void visit(DesignatorArrayHelper designatorArrayHelper) {
		Designator des = (Designator) designatorArrayHelper.getParent().getParent();
		Code.load(des.obj);
	}

	public void visit(DesArray desArray) {
		if (desArray.getParent().getParent().getClass() == FactoDesignator.class) {
			Designator des = (Designator) desArray.getParent();
			if (des.obj.getType().getElemType() == MyTab.intType) {
				Code.put(Code.aload);
			} else {
				Code.put(Code.baload);
			}
		}
	}

	public void visit(NumFactor numFactor) {
		Obj con = MyTab.insert(Obj.Con, "$", numFactor.struct);
		con.setLevel(0);
		con.setAdr(numFactor.getN1());
		Code.load(con);
		if (numFactor.getParent().getParent().getClass() == ExprMinus.class) {
			Code.put(Code.neg);
		}
	}

	public void visit(CharFactor charFactor) {
		Obj con = MyTab.insert(Obj.Con, "$", charFactor.struct);
		con.setLevel(0);
		con.setAdr(charFactor.getC1());
		Code.load(con);
	}

	public void visit(FactoDesignator factoDesignator) {
		if (factoDesignator.getDesignator().obj.getType().getKind() != Struct.Array)
			Code.load(factoDesignator.getDesignator().obj);
		if (factoDesignator.getParent().getParent().getClass() == ExprMinus.class) {
			Code.put(Code.neg);
		}
	}

	public void visit(AddoperationTerm addoperationTerm) {
		if (addoperationTerm.getAddop().getClass() == Add.class) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}

	public void visit(MullopFact mullopFact) {
		if (mullopFact.getMullop().getClass() == Mull.class) {
			Code.put(Code.mul);
		} else if (mullopFact.getMullop().getClass() == Div.class) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct.getKind() == Struct.Array) {
			if (printStmt.getExpr().struct.getElemType() == MyTab.intType || printStmt.getExpr().struct.getElemType() == MyTab.boolType) {
				Code.loadConst(5);
				Code.put(Code.print);
			} else {
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
		} else if (printStmt.getExpr().struct == MyTab.intType || printStmt.getExpr().struct == MyTab.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(FactorNew factorNew) {
		if (factorNew.getType().struct == MyTab.intType) {
			Code.put(Code.newarray);
			Code.put(Code.const_1);
		} else {
			Code.put(Code.newarray);
			Code.loadConst(0);
		}
	}

	/*public void visit(FindAnyStmt findAnyStmt) {
		Obj dest = findAnyStmt.getDesignator().obj;
		Obj source = findAnyStmt.obj;
		Code.put(Code.dup);
		Code.store(dest);
		Code.load(source);
		Code.put(Code.const_m1);
		only_jmp = Code.pc;
		Code.put(Code.const_1); // Odavde pocinje petlja
		Code.put(Code.add);
		Code.put(Code.dup_x2);
		Code.put(Code.dup);
		Code.load(source);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.lt, 0);
		jmpLeQAdress = Code.pc - 2;
		if (source.getType().getElemType() == MyTab.intType) { // Odavde ucitavam element niza
			Code.put(Code.aload);
		} else {
			Code.put(Code.baload);
		}
		Code.putFalseJump(Code.inverse[Code.eq], 0);
		jmpEqAdress = Code.pc - 2;
		Code.load(dest); // Ponovo ucitavam vrednost koju trazim
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.load(source);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.putJump(only_jmp);
		Code.fixup(jmpLeQAdress);
		Code.put(Code.pop);
		Code.loadConst(0);
		Code.store(dest);
		Code.putJump(0);
		Code.fixup(jmpEqAdress);
		only_jmp2 = Code.pc - 2;
		Code.put(Code.pop);
		Code.put(Code.const_1);
		Code.store(dest);
		Code.fixup(only_jmp2);
	}
*/
	
	public void visit(FactorDesFindAny factorDesFindAny) {
		Obj source = factorDesFindAny.getFindAnyHelper().obj;
		Code.put(Code.dup);
		Code.load(source);
		Code.put(Code.const_m1);
		only_jmp = Code.pc;
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.put(Code.dup_x2);
		Code.put(Code.dup);
		Code.load(source);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.lt, 0);
		jmpLeQAdress = Code.pc - 2;
		if (source.getType().getElemType() == MyTab.intType) { // Odavde ucitavam element niza
			Code.put(Code.aload);
		} else {
			Code.put(Code.baload);
		}
		Code.putFalseJump(Code.inverse[Code.eq], 0);
		jmpEqAdress = Code.pc - 2;
		Code.put(Code.dup2);
		Code.put(Code.pop);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.load(source);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.putJump(only_jmp);
		Code.fixup(jmpLeQAdress);
		//Nije pronadjen element ocisti expr stack
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.loadConst(0);
		Code.putJump(0);
		Code.fixup(jmpEqAdress);
		only_jmp2 = Code.pc - 2;
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.loadConst(1);
		Code.fixup(only_jmp2);
	}
	
	public void visit(ReadStmt readStmt) {
		if(readStmt.getDesignator().obj.getType() == MyTab.charType) {
			Code.put(Code.bread);
		}else {
			Code.put(Code.read);
		}
		Code.store(readStmt.getDesignator().obj);
	}
}
