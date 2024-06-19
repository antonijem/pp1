package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActParsExpr;
import rs.ac.bg.etf.pp1.ast.ActualParsList;
import rs.ac.bg.etf.pp1.ast.AddoperationTerm;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.BoolFactor;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.CharFactor;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstDecl;
import rs.ac.bg.etf.pp1.ast.ConstDeclListName;
import rs.ac.bg.etf.pp1.ast.ConstDeclarationList;
import rs.ac.bg.etf.pp1.ast.ConstName;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorAssign;
import rs.ac.bg.etf.pp1.ast.DesignatorDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorFuncCall;
import rs.ac.bg.etf.pp1.ast.DesignatorIncrement;
import rs.ac.bg.etf.pp1.ast.DesignatorStatement;
import rs.ac.bg.etf.pp1.ast.ExprMinus;
import rs.ac.bg.etf.pp1.ast.ExprNoMinus;
import rs.ac.bg.etf.pp1.ast.FactoDesignator;
import rs.ac.bg.etf.pp1.ast.FactorArrayOpt;
import rs.ac.bg.etf.pp1.ast.FactorDesFindAny;
import rs.ac.bg.etf.pp1.ast.FactorDesOpt;
import rs.ac.bg.etf.pp1.ast.FactorExpr;
import rs.ac.bg.etf.pp1.ast.FactorNew;
import rs.ac.bg.etf.pp1.ast.FindAnyHelper;
import rs.ac.bg.etf.pp1.ast.FormParsListName;
import rs.ac.bg.etf.pp1.ast.FormParsName;
import rs.ac.bg.etf.pp1.ast.FormalPars;
import rs.ac.bg.etf.pp1.ast.FormalParsList;
import rs.ac.bg.etf.pp1.ast.IsArray;
import rs.ac.bg.etf.pp1.ast.MethType;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MethodTypeVoid;
import rs.ac.bg.etf.pp1.ast.MethodVariableDecl;
import rs.ac.bg.etf.pp1.ast.MullopFact;
import rs.ac.bg.etf.pp1.ast.NoAddopTerm;
import rs.ac.bg.etf.pp1.ast.NoMullopFacot;
import rs.ac.bg.etf.pp1.ast.NumConst;
import rs.ac.bg.etf.pp1.ast.NumFactor;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.RetExpr;
import rs.ac.bg.etf.pp1.ast.ReturnStmt;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Term;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarDeclIns;
import rs.ac.bg.etf.pp1.ast.VariableDecl;
import rs.ac.bg.etf.pp1.ast.VariableDeclList;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	int nVars;
	Struct currConstType = null;
	int numCnstVal, boolCnstVal;
	char chCnstVal;
	Struct currVarType = null;
	Obj currentMethod = null;
	boolean returnFound = false;
	Struct currentRetType = null;
	int numFormPars = 0;
	LinkedList<Obj> actParsList = null;

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgName progName) {
		progName.obj = MyTab.insert(Obj.Prog, progName.getProgName(), MyTab.noType);
		MyTab.openScope();
	}

	public void visit(Program program) {
		nVars = MyTab.currentScope.getnVars();
		MyTab.chainLocalSymbols(program.getProgName().obj);
		MyTab.closeScope();
	}

//Obradjivanje konstanti
//----------------------------------------------------

	public void visit(ConstName constName) {
		if (MyTab.currentScope.findSymbol(constName.getConstName()) != null) {
			report_error("Konstanta sa imenom " + constName.getConstName() + " je vec deklarisana. ", constName);
		}

		Obj constNode = MyTab.insert(Obj.Con, constName.getConstName(), currConstType);
		if (constName.getConst().getClass() == NumConst.class) {
			NumConst nmCnst = (NumConst) constName.getConst();
			constNode.setAdr(nmCnst.getNum());
		} else if (constName.getConst().getClass() == CharConst.class) {
			CharConst chCnst = (CharConst) constName.getConst();
			constNode.setAdr(chCnst.getCh());
		} else if (constName.getConst().getClass() == BoolConst.class) {
			BoolConst blCnst = (BoolConst) constName.getConst();
			if (blCnst.getB().equalsIgnoreCase("true")) {
				constNode.setAdr(1);
			} else {
				constNode.setAdr(0);
			}
		}
		report_info("Prepoznata konstanta " + constName.getConstName(), constName);
	}

	public void visit(ConstDecl constDecl) {
		currConstType = null;
	}

	public void visit(ConstDeclListName constDeclListName) {
		if (MyTab.currentScope.findSymbol(constDeclListName.getConstName()) != null) {
			report_error("Konstanta sa imenom " + constDeclListName.getConstName() + " je vec deklarisana. ",
					constDeclListName);
		}
		Obj constNode = MyTab.insert(Obj.Con, constDeclListName.getConstName(), currConstType);
		switch (currConstType.getKind()) {
		case Struct.Int:
			constNode.setAdr(numCnstVal);
			break;
		case Struct.Char:
			constNode.setAdr(chCnstVal);
			break;
		case Struct.Bool:
			constNode.setAdr(boolCnstVal);
		default:
			break;
		}

		report_info("Prepoznata konstanta " + constDeclListName.getConstName(), constDeclListName);
	}

	public void visit(NumConst numConst) {
		if (currConstType.getKind() != Struct.Int) {
			report_error("Tip i vrednost konstante nisu kompatibilni. ", numConst);
		}
		numCnstVal = numConst.getNum();
	}

	public void visit(CharConst charConst) {
		if (currConstType.getKind() != Struct.Char) {
			report_error("Tip i vrednost konstante nisu kompatibilni. ", charConst);
		}
		chCnstVal = charConst.getCh();
	}

	public void visit(BoolConst boolConst) {
		if (currConstType.getKind() != Struct.Bool) {
			report_error("Tip i vrednost konstante nisu kompatibilni. ", boolConst);
		}
		if (boolConst.getB().equalsIgnoreCase("true")) {
			boolCnstVal = 1;
		} else {
			boolCnstVal = 0;
		}
	}

//Kraj obradjivanja konstanti
//----------------------------------------------------

//Obradjivanje Expr
//----------------------------------------------------

	public void visit(ExprMinus exprMinus) {
		if (exprMinus.getAddopTerm().getClass() != NoAddopTerm.class) {
			if (!exprMinus.getTerm().struct.compatibleWith(exprMinus.getAddopTerm().struct)) {
				report_error("Nekompatibilni tipovi ", exprMinus);
			}
		}
		if (exprMinus.getTerm().struct != MyTab.intType) {
			report_error("Tip mora biti Int ", exprMinus);
		}
		exprMinus.struct = exprMinus.getTerm().struct;
	}

	public void visit(ExprNoMinus exprNoMinus) {
		if (exprNoMinus.getAddopTerm().getClass() != NoAddopTerm.class) {
			if (!exprNoMinus.getTerm().struct.compatibleWith(exprNoMinus.getAddopTerm().struct)) {
				report_error("Nekompatibilni tipovi ", exprNoMinus);
			}
		}
		exprNoMinus.struct = exprNoMinus.getTerm().struct;
	}

	public void visit(AddoperationTerm addopTerm) {
		if (addopTerm.getAddopTerm().getClass() != NoAddopTerm.class) {
			if (!addopTerm.getAddopTerm().struct.compatibleWith(addopTerm.getTerm().struct)) {
				report_error("Nekompatibilni tipovi ", addopTerm);
			}
		}
		addopTerm.struct = addopTerm.getTerm().struct;
	}

	public void visit(Term term) {
		if (term.getMullopFactor().getClass() != NoMullopFacot.class) {
			if (term.getFactor().struct.getKind() == Struct.Array) {
				if (term.getMullopFactor().struct.getKind() == Struct.Array) {
					if (term.getFactor().struct.getElemType() != MyTab.intType
							&& term.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", term);
					}
				} else {
					if (term.getFactor().struct.getElemType() != MyTab.intType
							&& term.getMullopFactor().struct != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", term);
					}
				}
			} else if (term.getMullopFactor().struct.getKind() == Struct.Array) {
				if (term.getFactor().struct.getKind() == Struct.Array) {
					if (term.getFactor().struct.getElemType() != MyTab.intType
							&& term.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", term);
					}
				} else {
					if (term.getFactor().struct != MyTab.intType
							&& term.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", term);
					}
				}
			} else if (term.getFactor().struct != MyTab.intType && term.getMullopFactor().struct != MyTab.intType) {
				report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", term);
			}
		}
		term.struct = term.getFactor().struct;
	}

	public void visit(MullopFact mullopFact) {
		if (mullopFact.getMullopFactor().getClass() != NoMullopFacot.class) {
			if (mullopFact.getFactor().struct.getKind() == Struct.Array) {
				if (mullopFact.getMullopFactor().struct.getKind() == Struct.Array) {
					if (mullopFact.getFactor().struct.getElemType() != MyTab.intType
							&& mullopFact.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
					}
				} else {
					if (mullopFact.getFactor().struct.getElemType() != MyTab.intType
							&& mullopFact.getMullopFactor().struct != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
					}
				}
			} else if (mullopFact.getMullopFactor().struct.getKind() == Struct.Array) {
				if (mullopFact.getFactor().struct.getKind() == Struct.Array) {
					if (mullopFact.getFactor().struct.getElemType() != MyTab.intType
							&& mullopFact.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
					}
				} else {
					if (mullopFact.getFactor().struct != MyTab.intType
							&& mullopFact.getMullopFactor().struct.getElemType() != MyTab.intType) {
						report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
					}
				}
			} else if (mullopFact.getFactor().struct != MyTab.intType
					&& mullopFact.getMullopFactor().struct != MyTab.intType) {
				report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
			}
		} else {
			if (mullopFact.getFactor().struct.getKind() == Struct.Array) {
				if (mullopFact.getFactor().struct.getElemType() != MyTab.intType) {
					report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
				}
			} else if (mullopFact.getFactor().struct != MyTab.intType) {
				report_error("Prilikom koriscenja operatora (* , /, % ) tipovi moraju biti Int. ", mullopFact);
			}
		}
		mullopFact.struct = mullopFact.getFactor().struct;
	}

	public void visit(NumFactor numFactor) {
		numFactor.struct = MyTab.intType;
	}

	public void visit(CharFactor charFactor) {
		charFactor.struct = MyTab.charType;
	}

	public void visit(BoolFactor boolFactor) {
		boolFactor.struct = MyTab.boolType;
	}

	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}

	public void visit(FactoDesignator factoDesignator) {
		if (factoDesignator.getFactorDesignatorOption().getClass() == FactorDesOpt.class) {
			if (factoDesignator.getDesignator().obj.getKind() != Obj.Meth) {
				report_error("Designator mora biti metoda ", factoDesignator);
			}
			if (factoDesignator.getDesignator().obj.getLevel() != actParsList.size()) {
				report_error("Broj stvarnih argumenata se razlikuje od broja prosledjenih argumenata metode "
						+ factoDesignator.getDesignator().getDesName() + " ", factoDesignator);
			}
			if (!checkArguments(factoDesignator.getDesignator().obj.getLocalSymbols().toArray(), actParsList)) {
				report_error("Stvarni argumenti se ne slazu sa formalnim argumentima metode "
						+ factoDesignator.getDesignator().getDesName() + " ", factoDesignator);
			}
		} else if (factoDesignator.getParent().getParent().getParent().getClass() == ActParsExpr.class
				|| factoDesignator.getParent().getParent().getParent().getParent().getClass() == ActParsExpr.class
				|| factoDesignator.getParent().getParent().getParent().getParent().getParent()
						.getClass() == ActParsExpr.class) {
			actParsList = new LinkedList<Obj>();
			actParsList.add(factoDesignator.getDesignator().obj);
		} else if (factoDesignator.getParent().getParent().getParent().getClass() == ActualParsList.class
				|| factoDesignator.getParent().getParent().getParent().getParent().getClass() == ActualParsList.class
				|| factoDesignator.getParent().getParent().getParent().getParent().getParent()
						.getClass() == ActualParsList.class) {
			actParsList.add(factoDesignator.getDesignator().obj);
		}

		factoDesignator.struct = factoDesignator.getDesignator().obj.getType();
	}

	public void visit(FactorArrayOpt factorArrayOpt) {
		if (factorArrayOpt.getExpr().struct != MyTab.intType) {
			report_error("Unutar [] mora biti tip Int ", factorArrayOpt);
		}
	}

	public void visit(FactorNew factorNew) {
		factorNew.struct = new Struct(Struct.Array, factorNew.getType().struct);
	}

	public void visit(RetExpr retExpr) {
		currentRetType = retExpr.getExpr().struct;
	}

	public void visit(FactorDesFindAny factorDesFindAny) {
		factorDesFindAny.struct = MyTab.boolType;
	}
	
	public void visit(FindAnyHelper findAnyHelper) {
		findAnyHelper.obj = MyTab.find(findAnyHelper.getDesName());
	}

//Kraj obradjivanja expr
//----------------------------------------------------

//Obradjivanja promenljvih
//----------------------------------------------------

	public void visit(VarDeclIns varDeclIns) {
		if (MyTab.currentScope.findSymbol(varDeclIns.getVarName()) != null) {
			report_error("Ime promenljive " + varDeclIns.getVarName() + " je vec zauzeto. ", varDeclIns);
		}
		Obj varNode;
		if (varDeclIns.getArrayDecl().getClass() == IsArray.class) {
			varNode = MyTab.insert(Obj.Var, varDeclIns.getVarName(), new Struct(Struct.Array, currVarType));
		} else {
			varNode = MyTab.insert(Obj.Var, varDeclIns.getVarName(), currVarType);
		}
		if (varDeclIns.getParent().getParent().getClass() == MethodVariableDecl.class) {
			report_info("Prepoznata lokalna promenljiva " + varDeclIns.getVarName(), varDeclIns);
		} else if (varDeclIns.getParent().getParent().getClass() == VariableDecl.class) {
			report_info("Prepoznata globalna promenljiva " + varDeclIns.getVarName(), varDeclIns);
		}
	}

	public void visit(VariableDeclList varDeclList) {
		if (MyTab.currentScope.findSymbol(varDeclList.getVarName()) != null) {
			report_error("Ime promenljive " + varDeclList.getVarName() + " je vec zauzeto. ", varDeclList);
		}
		Obj varNode;
		if (varDeclList.getArrayDecl().getClass() == IsArray.class) {
			varNode = MyTab.insert(Obj.Var, varDeclList.getVarName(), new Struct(Struct.Array, currVarType));
		} else {
			varNode = MyTab.insert(Obj.Var, varDeclList.getVarName(), currVarType);
		}
		if (varDeclList.getParent().getParent().getParent().getClass() == MethodVariableDecl.class) {
			report_info("Prepoznata lokalna promenljiva " + varDeclList.getVarName(), varDeclList);
		} else if (varDeclList.getParent().getParent().getParent().getClass() == VariableDecl.class) {
			report_info("Prepoznata globalna promenljiva " + varDeclList.getVarName(), varDeclList);
		}

	}

//Kraj obradjivanja promenljivih
//----------------------------------------------------
//Obradjivanje designatora
//----------------------------------------------------

	public void visit(Designator designator) {
		Obj objNode = MyTab.find(designator.getDesName());
		if (objNode == MyTab.noObj) {
			report_error("Designator sa imenom " + designator.getDesName() + " nije definisan. ", designator);
		} else {
			designator.obj = objNode;
		}
	}

	public void visit(DesignatorAssign designatorAssign) {
		designatorAssign.struct = designatorAssign.getExpr().struct;
	}

	public void visit(DesignatorStatement designatorStatement) {
		if (designatorStatement.getDesignator().obj.getKind() == Obj.Con) {
			report_error("Designator " + designatorStatement.getDesignator().getDesName() + " mora biti promenljiva ",
					designatorStatement);
		}
		if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorAssign.class) {
			if (designatorStatement.getDesignator().obj.getType().getKind() == Struct.Array) {
				if (designatorStatement.getDesignatorStmtOptions().struct.getKind() == Struct.Array) {
					if (!(designatorStatement.getDesignator().obj.getType()
							.getElemType() == designatorStatement.getDesignatorStmtOptions().struct.getElemType())) {
						report_error("Tip promenljive " + designatorStatement.getDesignator().obj.getName()
								+ " i vrednost koja joj se dodeljuje su nekompatibilni", designatorStatement);
					}
				} else {
					if (!(designatorStatement.getDesignator().obj.getType()
							.getElemType() == designatorStatement.getDesignatorStmtOptions().struct)) {
						report_error("Tip promenljive " + designatorStatement.getDesignator().obj.getName()
								+ " i vrednost koja joj se dodeljuje su nekompatibilni", designatorStatement);
					}
				}
			} else if (designatorStatement.getDesignatorStmtOptions().struct.getKind() == Struct.Array) {
				if (designatorStatement.getDesignator().obj.getType().getKind() == Struct.Array) {
					if (!(designatorStatement.getDesignator().obj.getType()
							.getElemType() == designatorStatement.getDesignatorStmtOptions().struct.getElemType())) {
						report_error("Tip promenljive " + designatorStatement.getDesignator().obj.getName()
								+ " i vrednost koja joj se dodeljuje su nekompatibilni", designatorStatement);
					}
				} else {
					if (!(designatorStatement.getDesignator().obj
							.getType() == designatorStatement.getDesignatorStmtOptions().struct.getElemType())) {
						report_error("Tip promenljive " + designatorStatement.getDesignator().obj.getName()
								+ " i vrednost koja joj se dodeljuje su nekompatibilni", designatorStatement);
					}
				}
			} else if (!(designatorStatement.getDesignator().obj
					.getType() == designatorStatement.getDesignatorStmtOptions().struct)) {
				report_error("Tip promenljive " + designatorStatement.getDesignator().obj.getName()
						+ " i vrednost koja joj se dodeljuje su nekompatibilni", designatorStatement);
			}
		} else if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorIncrement.class
				|| designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorDecrement.class) {
			if (designatorStatement.getDesignator().obj.getType() != MyTab.intType) {
				report_error("Prilikom koriscenja (++ ili --) designator mora biti tipa Int ", designatorStatement);
			}
		} else if (designatorStatement.getDesignatorStmtOptions().getClass() == DesignatorFuncCall.class) {
			if (designatorStatement.getDesignator().obj.getKind() != Obj.Meth) {
				report_error("Designator " + designatorStatement.getDesignator().getDesName() + " mora biti metoda ",
						designatorStatement);
			}
			if (designatorStatement.getDesignator().obj.getLevel() != actParsList.size()) {
				report_error("Broj stvarnih argumenata se razlikuje od broja prosledjenih argumenata metode "
						+ designatorStatement.getDesignator().getDesName() + " ", designatorStatement);
			}
			if (!checkArguments(designatorStatement.getDesignator().obj.getLocalSymbols().toArray(), actParsList)) {
				report_error("Stvarni argumenti se ne slazu sa formalnim argumentima metode "
						+ designatorStatement.getDesignator().getDesName() + " ", designatorStatement);
			}
		}
	}

	public void visit() {

	}

//Kraj obradjivanja designatora
//----------------------------------------------------

//Obradjivanje metoda
//----------------------------------------------------

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != MyTab.noType) {
			report_error("Semanticka greska, funkcija " + currentMethod.getName() + " nema return iskaz. ", methodDecl);
		} else if (returnFound) {
			if (currentMethod.getType() != currentRetType) {
				report_error("Tip povratne vrednosti i povratna vrednost funkcije " + currentMethod.getName()
						+ " se ne slazu ", methodDecl);
			}
		}
		currentMethod.setLevel(numFormPars);
		MyTab.chainLocalSymbols(currentMethod);
		MyTab.closeScope();
		numFormPars = 0;
		returnFound = false;
		currentMethod = null;
		currentRetType = null;
	}

	public void visit(MethodTypeName methodTypeName) {
		currentMethod = MyTab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodType().struct);
		methodTypeName.obj = currentMethod;
		MyTab.openScope();
	}

	public void visit(MethodTypeVoid methodTypeVoid) {
		methodTypeVoid.struct = MyTab.noType;
	}

	public void visit(MethType methodType) {
		methodType.struct = methodType.getType().struct;
	}

	public void visit(ReturnStmt returnStmt) {
		returnFound = true;
		Struct currMethyRetType = currentMethod.getType();
		// if(currMethyRetType.compatibleWith(returnStmt.getReturnExpr().))
	}

	public void visit(FormParsName formalPars) {
		Obj objNode = MyTab.find(formalPars.getFormParamName());
		if (objNode != MyTab.noObj) {
			report_error("Parametar sa imenom " + formalPars.getFormParamName() + " vec postoji ", formalPars);
		}
		MyTab.insert(Obj.Var, formalPars.getFormParamName(), formalPars.getType().struct);
		numFormPars++;
	}

	public void visit(FormParsListName formalParsList) {
		Obj objNode = MyTab.find(formalParsList.getFormParamName());
		if (objNode != MyTab.noObj) {
			report_error("Parametar sa imenom " + formalParsList.getFormParamName() + " vec postoji ", formalParsList);
		}
		MyTab.insert(Obj.Var, formalParsList.getFormParamName(), formalParsList.getType().struct);
		numFormPars++;
	}

	public void visit(ActParsExpr actParsExpr) {

	}

//Kraj obradjivanja metoda
//----------------------------------------------------

//Obradjivanje statement
// ----------------------------------------------------

	public void visit(ReadStmt readStmt) {
		if (readStmt.getDesignator().obj.getKind() == Obj.Var || readStmt.getDesignator().obj.getKind() == Obj.Fld) {

		} else {
			report_error("Designator " + readStmt.getDesignator().getDesName()
					+ " mora oznacavati promenljivu ili element niza", readStmt);
		}
	}

	public void visit(PrintStmt printStmt) {
		if (printStmt.getExpr().struct.getKind() == Struct.Array) {
			if (printStmt.getExpr().struct.getElemType() == MyTab.intType
					|| printStmt.getExpr().struct.getElemType() == MyTab.charType
					|| printStmt.getExpr().struct.getElemType() == MyTab.boolType) {

			} else {
				report_error("Expr mora biti tipa int, char ili bool ", printStmt);
			}
		} else if (printStmt.getExpr().struct == MyTab.intType || printStmt.getExpr().struct == MyTab.charType
				|| printStmt.getExpr().struct == MyTab.boolType) {

		} else {
			report_error("Expr mora biti tipa int, char ili bool ", printStmt);
		}
	}

	/*
	 * public void visit(FindAnyStmt findAnyStmt) { Obj objNode =
	 * MyTab.find(findAnyStmt.getDesName()); if (objNode.getType().getKind() !=
	 * Struct.Array) { report_error("Designator " + objNode.getName() +
	 * " mora oznacavati jednodimenzionalni niz ", findAnyStmt); } findAnyStmt.obj =
	 * objNode; if (findAnyStmt.getDesignator().obj.getType().getKind() !=
	 * Struct.Bool) { report_error("Designator " +
	 * findAnyStmt.getDesignator().getDesName() +
	 * " mora oznacavati promenljivu tipa bool ", findAnyStmt); } }
	 */
// Kraj obradjivanja statement
// ----------------------------------------------------

	public void visit(Type type) {
		Obj typeNode = MyTab.find(type.getTypeName());
		if (typeNode == MyTab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = MyTab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = MyTab.noType;
			}
		}
		if (type.getParent().getClass() == ConstDecl.class) {
			currConstType = type.struct;
		} else if (type.getParent().getClass() == VarDecl.class) {
			currVarType = type.struct;
		}
	}

	public boolean passed() {
		return !errorDetected;
	}

	private boolean checkArguments(Object[] formPars, LinkedList<Obj> actPars) {
		boolean retVal = true;
		for (int i = 0; i < actPars.size(); i++) {
			Obj pom = (Obj) formPars[i];
			if (!pom.getType().compatibleWith(actPars.get(i).getType())) {
				retVal = false;
				break;
			}
		}
		return retVal;
	}
}
