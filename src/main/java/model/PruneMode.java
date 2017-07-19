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

    public void branchPrune(Branch branch) {
        GenericTree a = branch.getA();
        GenericTree b = branch.getB();
        a.prune();
        Double leftPruneValue = a.getPruneValue();
        b.quickPrune();
        Double rightPruneValue = b.getPruneValue();
        Double result = valFunc.apply(leftPruneValue, rightPruneValue);
        Boolean calculateRightAgain = cutFunc.apply(leftPruneValue, rightPruneValue);
        if (calculateRightAgain) {
            b.prune();
            rightPruneValue = b.getPruneValue();
            result = valFunc.apply(leftPruneValue, rightPruneValue);
            b.setCut(false);
        } else {
            b.setCut(true);
        }
        branch.setPruneValue(result);
    }

    /**
     * PruneMode left nodes, because the pruneValue of left nodes is set to MIN.
     * pruneMode() can also be used to recalculate right values, is pruneValue is set to MIN.
     */
    public void nodePrune(Node node) {
        node.setPruneValue(valFunc.apply(node.getA(), node.getB()));
    }

    public void branchQuickPrune(Branch branch) {
        GenericTree a = branch.getA();
        GenericTree b = branch.getB();

        a.prune();
        branch.setPruneValue(a.getPruneValue());

        b.setCut(true);
    }

    public void nodeQuickPrune(Node node) {
        node.setPruneValue(node.getA());
    }
}
