import trees.ConiferTree;
import trees.DeciduousTree;
import trees.TreeType;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        TreeType Oak = TreeType.OAK;
        TreeType Fir = TreeType.FIR;
        DeciduousTree tree = new DeciduousTree(Oak);
        ConiferTree coniferTree = new ConiferTree(Fir);

        System.out.println(tree);
        tree.growth();
        System.out.println(tree + "\n");

        System.out.println(coniferTree);
        coniferTree.growth();
        System.out.println(coniferTree);
        }
    }