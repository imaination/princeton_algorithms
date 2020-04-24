package assignment_1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int gridLen;
    private final int begin;
    private final int end;
    private final WeightedQuickUnionUF connections;
    private final WeightedQuickUnionUF connections2; // to avoid backwash problem
    private int count;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid must be greater than 0");
        }
        
        begin = 0;
        end = n*n+1;
        gridLen = n;
        grid = new boolean[n][n];
        connections = new WeightedQuickUnionUF(n*n + 2);
        connections2 = new WeightedQuickUnionUF(n*n + 1);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        for (int i = 1; i <= n; i++) {
            connections.union(begin, i);
            connections2.union(begin, i);
            connections.union(end-i, end);
        }
        
    }

    // opens the site (row, col) if it is not open already
    
    public void open(int row, int col) {
        checkExpectation(row, col);
        if (!isOpen(row, col)){
            grid[row-1][col-1] = true;
            if (col != 1 && isOpen(row, col - 1)) {
                connections.union(index(row, col), index(row, col-1));
                connections2.union(index(row, col), index(row, col-1));
            }
            if (col != gridLen && isOpen(row, col + 1)) {
                connections.union(index(row, col), index(row, col+1));
                connections2.union(index(row, col), index(row, col+1));
            }
            if (row != gridLen && isOpen(row + 1, col)) {
                connections.union(index(row, col), index(row+1, col));
                connections2.union(index(row, col), index(row+1, col));
            }
            if (row != 1 && isOpen(row - 1, col)) {
                connections.union(index(row, col), index(row-1, col));
                connections2.union(index(row, col), index(row-1, col));
            }
            count ++;
        }
    }

    // is the site (row, col) open?
    
    public boolean isOpen(int row, int col) {
        checkExpectation(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    
    public boolean isFull(int row, int col) {
        checkExpectation(row, col);
        if (!grid[row-1][col-1]) return false;
        else return connections.find(begin) == connections.find(index(row, col)) && connections2.find(begin) == connections2.find(index(row, col));
    }

    // returns the number of open sites
    
    public int numberOfOpenSites() {
        return count;
    }
    
    // does the system percolate?
    
    public boolean percolates() {
        if (gridLen==1) {
            return isOpen(1, 1);
        }else {
            return connections.find(begin) == connections.find(end);   
        }
    }
    
    private void checkExpectation(int row, int col) {
        if (row < 1 || col < 1 || row > gridLen || col > gridLen) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }
    
    private int index(int row, int col) {
        return gridLen * (row - 1) + col;
    }
    
    public static void main(String[] args) {
        int n = 1;
        Percolation p = new Percolation(n);
        
//        p.open(1,1);
//        p.open(2,1);
//        p.open(3,1);
         for (int i = 0; i < n; i++) {
             for (int j = 0; j < n; j++) {
                 System.out.print(p.grid[i][j]);
                 System.out.print("\t");
             }
             System.out.print("\n");
         }
         System.out.print("isopen   = " + p.isOpen(1, 1));
         System.out.print("\n");
         System.out.print("percolates: " + p.percolates());
         System.out.print("\n");
         System.out.print("numberofopensies     = " + p.numberOfOpenSites());
         System.out.print("\n");
         System.out.print("isfull: " + p.isFull(1, 1));
    }
}
