package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormParsListName;
import rs.ac.bg.etf.pp1.ast.FormParsName;
import rs.ac.bg.etf.pp1.ast.VarDeclIns;
import rs.ac.bg.etf.pp1.ast.VariableDeclList;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor{
		
		public void visit(FormParsName formParsName) {
			count++;
		}
		
		public void visit(FormParsListName formParsListName) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor{
		
		public void visit(VarDeclIns varDeclIns) {
			count++;
		}
		
		public void visit(VariableDeclList variableDeclList) {
			count++;
		}
	}
}
