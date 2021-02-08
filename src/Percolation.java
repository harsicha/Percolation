import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private final int size;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private int numberOfOpenSites;
    private String roots;
    private boolean percolates;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.openSites = new boolean[n * n];
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
        this.numberOfOpenSites = 0;
        this.roots = " ";
        this.percolates = false;
    }
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        int value = row * col - 1 + (row - 1) * (size - col);
        if (!isOpen(row, col)) {
            openSites[value] = true;
            this.numberOfOpenSites++;
        }

        // If the current row is a top or bottom row then add it to the roots string. Element with suffix 'b' means a
        // bottom row root and element with suffix 't' means top row root.
        if (row == 1) {
            roots = roots + (value) + "t ";
        } else if (row == size) {
            roots = roots + value + "b ";
        }

    //    System.out.println(roots);

        int previousRoot = -1;
        int previousRoot1 = -1;
        int newRoot = -1;
        int newRoot1 = -1;

        // Check if element in the previous row, same column is open and if it is then union them both.
        if (row > 1 && isOpen(row - 1, col)) {

            int oneSizeLower = row * col - 1 - size + (row - 1) * (size - col);

            previousRoot = weightedQuickUnionUF.find(value);
            previousRoot1 = weightedQuickUnionUF.find(oneSizeLower);

            weightedQuickUnionUF.union(oneSizeLower, value);

            newRoot = weightedQuickUnionUF.find(value);
            newRoot1 = weightedQuickUnionUF.find(oneSizeLower);

            // Replace the previous root of the current row, col with the new root of the current row, col.
            replace(previousRoot, newRoot);

            // Replace the previous root of the element in the previous row, same column with the new root of the same element.
            replace(previousRoot1, newRoot1);

            percolates = percolates || roots.contains(" " + newRoot + "t") && roots.contains(" " + newRoot + "b");
            percolates = percolates || roots.contains(" " + newRoot1 + "t") && roots.contains(" " + newRoot1 + "b");

       //     System.out.println(roots);

        }

        // Check if the element in the previous column, same row is open and if it is then union them.
        if (col > 1 && isOpen(row, col - 1)) {

            int oneMinus = row * col - 2 + (row - 1) * (size - col);

            previousRoot = weightedQuickUnionUF.find(value);
            previousRoot1 = weightedQuickUnionUF.find(oneMinus);

            weightedQuickUnionUF.union(oneMinus, value);

            newRoot = weightedQuickUnionUF.find(value);
            newRoot1 = weightedQuickUnionUF.find(oneMinus);

            // Replace the previous root of the current row, col with the new root of the current row, col.
            replace(previousRoot, newRoot);

            // Replace the previous root of element in the previous column, same row with the new root of the same element.
            replace(previousRoot1, newRoot1);

            percolates = percolates || roots.contains(" " + newRoot + "t") && roots.contains(" " + newRoot + "b");
            percolates = percolates || roots.contains(" " + newRoot1 + "t") && roots.contains(" " + newRoot1 + "b");

        //    System.out.println(roots);

        }

        // Check if the element in the next row, same column is open and if it is then union it.
        if (row < size && isOpen(row + 1, col)) {

            int oneSizeUpper = row * col - 1 + size + (row - 1) * (size - col);

            previousRoot = weightedQuickUnionUF.find(value);
            previousRoot1 = weightedQuickUnionUF.find(oneSizeUpper);

            weightedQuickUnionUF.union(oneSizeUpper, value);

            newRoot = weightedQuickUnionUF.find(value);
            newRoot1 = weightedQuickUnionUF.find(oneSizeUpper);

            // Replace the previous root of the current row, col with the new root of the current row, col.
            replace(previousRoot, newRoot);

            // Replace the previous root of the element in the next row, same column with the new root of the same element.
            replace(previousRoot1, newRoot1);

            percolates = percolates || roots.contains(" " + newRoot + "t") && roots.contains(" " + newRoot + "b");
            percolates = percolates || roots.contains(" " + newRoot1 + "t") && roots.contains(" " + newRoot1 + "b");

        //    System.out.println(roots);

        }

        // Check if the element in the next column, same row is open and if it is then union it.
        if (col < size && isOpen(row, col + 1)) {

            int onePlus = row * col + (row - 1) * (size - col);

            previousRoot = weightedQuickUnionUF.find(value);
            previousRoot1 = weightedQuickUnionUF.find(onePlus);

            weightedQuickUnionUF.union(onePlus, value);

            newRoot = weightedQuickUnionUF.find(value);
            newRoot1 = weightedQuickUnionUF.find(onePlus);

            // Replace the previous root of the current row, col with the new root of the current row, col.
            replace(previousRoot, newRoot);

            // Replace the previous root of the element in the next column, same row with the new root of the same element.
            replace(previousRoot1, newRoot1);

            percolates = percolates || roots.contains(" " + newRoot + "t") && roots.contains(" " + newRoot + "b");
            percolates = percolates || roots.contains(" " + newRoot1 + "t") && roots.contains(" " + newRoot1 + "b");

        //    System.out.println(roots);

        }

    }
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        return openSites[row * col - 1 + (row - 1) * (size - col)];
    }
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }

        // Checks if the current row, col has the same root as one of the elements in the top row.
        return roots.contains(" " + weightedQuickUnionUF.find(row * col - 1 + (row - 1) * (size - col)) + "t");
    }
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }
    public boolean percolates() {
        return percolates;
    }

    // Replace the previous top or bottom root with the new root.
    private void replace(int currentValue, int root) {
        roots = roots.replaceFirst(" " + currentValue + "t", " " + root + "t");
        roots = roots.replaceFirst(" " + currentValue + "b", " " + root + "b");
    }
}
