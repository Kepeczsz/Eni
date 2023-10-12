package trees;


public class DeciduousTree extends Tree {
    public DeciduousTree() {
    }
    public DeciduousTree(TreeType treeType) {
        if(!treeType.isConifer()) {
            this.age = treeType.getAge();
            this.height = treeType.getHeight();
            this.branches = treeType.getBranches();
            this.leaves = treeType.getLeaves();
            this.trunk = treeType.getTrunk();
            this.species = treeType.getSpecies();
            this.growthRate = treeType.getGrowthRate();
        }
        else throw new IllegalArgumentException("Cannot create Deciduous tree from Conifer tree");
    }

    public DeciduousTree(String species, String trunk, String branches, String leaves, int age, double height, double growthRate) {
        super(species, trunk, branches, leaves, age, height, growthRate);
    }

    @Override
    public void growth() {
        height+=growthRate;
        System.out.println("Deciduous Tree has grown, it's now " + height + " meters");
    }

    public void changeLeavesState(){
        if(leaves.contains( species + " Leaves"))
        {
            leaves = "Leaves fell off";
        }
        else
        {
            leaves = species + " Leaves";
        }
    }

    @Override
    public String toString() {
        return "DeciduousTree{" +
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
