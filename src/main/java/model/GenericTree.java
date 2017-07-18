package model;

import java.util.List;

public abstract class GenericTree<A, B> {

    private A a;
    private B b;
    private Double pruneValue = Double.MIN_VALUE;

    protected PruneMode pruneMode;

    public GenericTree(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public PruneMode getPruneMode(){
        return this.pruneMode;
    }

    public Double getPruneValue() {
        return pruneValue;
    }

    public void setPruneValue(Double pruneValue) {
        this.pruneValue = pruneValue;
    }

    public Node getLeftNode() {
        Object tmp = this.a;
        while (tmp instanceof Branch) {
            tmp = ((Branch) tmp).getA();
        }
        if (tmp instanceof Node) {
            return (Node) tmp;
        } else throw new IllegalStateException("Tree should not contain other objects than branches or nodes");
    }

    public Integer calcDepth() {
        Object tmp = this.a;
        Integer depth = 1;
        while (tmp instanceof Branch) {
            tmp = ((Branch) tmp).getA();
            depth++;
        }
        depth++;
        return depth;
    }

    public abstract void setPruneMode(PruneMode pruneMode);

    public abstract List<Double> findAllValues();

    public abstract void prune();
}
