// generated with ast extension for cup
// version 0.8
// 24/7/2023 15:32:8


package rs.ac.bg.etf.pp1.ast;

public class MullopFact extends MullopFactor {

    private MullopFactor MullopFactor;
    private Mullop Mullop;
    private Factor Factor;

    public MullopFact (MullopFactor MullopFactor, Mullop Mullop, Factor Factor) {
        this.MullopFactor=MullopFactor;
        if(MullopFactor!=null) MullopFactor.setParent(this);
        this.Mullop=Mullop;
        if(Mullop!=null) Mullop.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public MullopFactor getMullopFactor() {
        return MullopFactor;
    }

    public void setMullopFactor(MullopFactor MullopFactor) {
        this.MullopFactor=MullopFactor;
    }

    public Mullop getMullop() {
        return Mullop;
    }

    public void setMullop(Mullop Mullop) {
        this.Mullop=Mullop;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MullopFactor!=null) MullopFactor.accept(visitor);
        if(Mullop!=null) Mullop.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MullopFactor!=null) MullopFactor.traverseTopDown(visitor);
        if(Mullop!=null) Mullop.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MullopFactor!=null) MullopFactor.traverseBottomUp(visitor);
        if(Mullop!=null) Mullop.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MullopFact(\n");

        if(MullopFactor!=null)
            buffer.append(MullopFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mullop!=null)
            buffer.append(Mullop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MullopFact]");
        return buffer.toString();
    }
}
