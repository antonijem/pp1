// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class FactoDesignator extends Factor {

    private Designator Designator;
    private FactorDesignatorOption FactorDesignatorOption;

    public FactoDesignator (Designator Designator, FactorDesignatorOption FactorDesignatorOption) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FactorDesignatorOption=FactorDesignatorOption;
        if(FactorDesignatorOption!=null) FactorDesignatorOption.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FactorDesignatorOption getFactorDesignatorOption() {
        return FactorDesignatorOption;
    }

    public void setFactorDesignatorOption(FactorDesignatorOption FactorDesignatorOption) {
        this.FactorDesignatorOption=FactorDesignatorOption;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FactorDesignatorOption!=null) FactorDesignatorOption.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FactorDesignatorOption!=null) FactorDesignatorOption.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FactorDesignatorOption!=null) FactorDesignatorOption.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactoDesignator(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorDesignatorOption!=null)
            buffer.append(FactorDesignatorOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactoDesignator]");
        return buffer.toString();
    }
}
