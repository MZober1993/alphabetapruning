package model;

import java.util.function.BiFunction;

/**
 * @author Matthias on 18.07.2017.
 */
public enum PruneMode {
    ALPHA(Math::max, (a, b) -> a < b), BETA(Math::min, (a, b) -> a > b);

    private BiFunction<Double, Double, Double> valFunc;
    private BiFunction<Double, Double, Boolean> cutFunc;

    PruneMode(BiFunction<Double, Double, Double> valFunc, BiFunction<Double, Double, Boolean> cutFunc) {
        this.valFunc = valFunc;
        this.cutFunc = cutFunc;
    }

    public void branchPrune(Branch branch){
        GenericTree a = branch.getA();
        GenericTree b = branch.getB();
        a.prune();
        b.prune();
        Double left = a.getPruneValue();
        Double right = b.getPruneValue();
        Double result = valFunc.apply(left,right);
        Boolean calculateRightAgain = cutFunc.apply(left,right);
        if(calculateRightAgain){
            b.setPruneValue(Double.MIN_VALUE);
            b.prune();
            right = b.getPruneValue();
            result = Math.max(left, right);
        }
        branch.setPruneValue(result);
    }

    /**
     * PruneMode left nodes, because the pruneValue of left nodes is set to MIN.
     * pruneMode() can also be used to recalculate right values, is pruneValue is set to MIN.
     */
    public void nodePrune(Node node){
        if(node.getPruneValue().equals(Double.MIN_VALUE)){
            node.setPruneValue(valFunc.apply(node.getA(), node.getB()));
        }
    }
}
