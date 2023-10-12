package trees;

public class ConiferTree extends Tree {

    public ConiferTree(String species, String trunk, String branches, String leaves, int age, double height, double growthRate) {
        super(species, trunk, branches, leaves, age, height,growthRate);
    }

    public ConiferTree(TreeType treeType) {
        if(treeType.isConifer()) {
            this.age = treeType.getAge();
            this.height = treeType.getHeight();
            this.branches = treeType.getBranches();
            this.leaves = treeType.getLeaves();
            this.trunk = treeType.getTrunk();
            this.species = treeType.getSpecies();
            this.growthRate = treeType.getGrowthRate();
        }
        else throw new IllegalArgumentException("Cannot create Conifer tree from Deciduous tree");
    }

    @Override
    public void growth()
    {
        height+=growthRate;
        System.out.println("Conifer Tree has grown, it's now " + height + " meters");
    }

    @Override
    public String toString() {
        return "ConiferTree{" +
                "species='" + species + '\'' +
                ", trunk='" + trunk + '\'' +
                ", branches='" + branches + '\'' +
                ", leaves='" + leaves + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", growthRate=" + growthRate +
                '}';
    }
}
