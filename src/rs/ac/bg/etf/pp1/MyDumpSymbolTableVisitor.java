package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class MyDumpSymbolTableVisitor extends DumpSymbolTableVisitor {

	@Override
	public void visitStructNode(Struct structToVisit) {
		if(structToVisit.getKind() == Struct.Bool) {
			output.append("bool");
		}else if(structToVisit.getElemType() != null && structToVisit.getElemType().getKind() == Struct.Bool) {
			output.append("bool");
		}
		super.visitStructNode(structToVisit);
	}
}
