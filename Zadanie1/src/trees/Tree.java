package trees;

abstract class Tree {
    protected String species;
    protected String trunk;
    protected String branches;
    protected String leaves;
    protected int age;
    protected double height;

    protected double growthRate;
    public Tree(String species, String trunk, String branches, String leaves, int age, double height, double growthRate) {
        this.species = species;
        this.trunk = trunk;
        this.branches = branches;
        this.leaves = leaves;
        this.age = age;
        this.height = height;
        this.growthRate = growthRate;
    }

    public Tree() {
    }

    public abstract void growth();

}
