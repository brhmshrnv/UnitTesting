package az.ibrahimshirinov.unittesting.mincostpath;

public class MinCostPath {
    public int find(int[][] matrix, Cell start, Cell target) {

        validateIfTheCellsOutOfMatrixBound(matrix, start);
        validateIfTheCellsOutOfMatrixBound(matrix, target);

        if (start.equals(target)) {
            return matrix[start.row()][start.column()];
        }

        Cell currentCell = start;
        int cost = 0;
        while (!currentCell.equals(target)) {
            cost += matrix[currentCell.row()][currentCell.column()];
            currentCell = new Cell(currentCell.row(), currentCell.column()+1);
        }
        cost +=matrix[currentCell.row()][currentCell.column()];


        return cost;
    }

    private void validateIfTheCellsOutOfMatrixBound(int[][] matrix, Cell cell) {
        if (cell.row() >= matrix.length || cell.row() < 0) {
            throw new IllegalArgumentException();
        }
        if (cell.column() >= matrix[0].length || cell.column() < 0) {
            throw new IllegalArgumentException();
        }
    }
}
