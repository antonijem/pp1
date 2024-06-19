package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.DesignatorAssign;
import rs.ac.bg.etf.pp1.ast.ExprMinus;
import rs.ac.bg.etf.pp1.ast.FactorArrayOpt;
import rs.ac.bg.etf.pp1.ast.Term;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class HelperClass extends VisitorAdaptor {
	protected boolean found;
	
	public boolean getFound() {
		return found;
	}
	
	public static class ExprMinusFounder extends HelperClass {
		
		public void visit(ExprMinus exprMinus) {
			found = true;
		}
		public void visit(Term term) {
			System.out.println("debug Usao u term");
		}
	}
	
	public static class NewArrayFounder extends HelperClass{
		public void visit(FactorArrayOpt factorArrayOpt) {
			found = true;
		}
	}
	
	public static class AssignArrayFounder extends HelperClass{
		public void visit(DesignatorAssign designatorAssign) {
			found = true;
		}
	}
}
