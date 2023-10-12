package trees;

public enum TreeType {
    OAK("Oak", "Oak Trunk", "Oak Branches", "Oak Leaves", 0, 0, 1.5, false),
    MAPLE("Maple", "Maple Trunk", "Maple Branches", "Maple Leaves", 0, 0, 6.5, false),
    BIRCH("Birch", "Birch Trunk", "Birch Branches", "Birch Leaves", 0, 0, 2.5, false),
    ASH("Ash", "Ash Trunk", "Ash Branches", "Ash Leaves", 0, 0, 4.5, false),
    ASPEN("Aspen", "Aspen Trunk", "Aspen Branches", "Aspen Leaves", 0, 0, 2.5, false),
    PINE("Pine", "Pine Trunk", "Pine Branches", "Pine Leaves", 0, 0, 4.5, true),
    SPRUCE("Spruce", "Spruce Trunk", "Spruce Branches", "Spruce Leaves", 0, 0, 3.5, true),
    FIR("Fir", "Fir Trunk", "Fir Branches", "Fir Leaves", 0, 0, 2.5, true);

    private final String species;
    private final String trunk;
    private final String branches;
    private final String leaves;
    private final int age;
    private final double height;

    private final double growthRate;
    private final boolean isConifer;


    TreeType(String species, String trunk, String branches, String leaves, int age, double height, double growthRate, boolean isConifer) {
        this.species = species;
        this.trunk = trunk;
        this.branches = branches;
        this.leaves = leaves;
        this.age = age;
        this.height = height;
        this.growthRate = growthRate;
        this.isConifer = isConifer;
    }

    public String getSpecies() {
        return species;
    }

    public String getTrunk() {
        return trunk;
    }

    public String getBranches() {
        return branches;
    }

    public String getLeaves() {
        return leaves;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public boolean isConifer() {
        return isConifer;
    }

    public double getGrowthRate(){
        return growthRate;
    }
}
