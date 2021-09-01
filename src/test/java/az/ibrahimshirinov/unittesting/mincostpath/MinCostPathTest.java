package az.ibrahimshirinov.unittesting.mincostpath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinCostPathTest {


    /**
     * matrix, int[][], start cell, target cell, Cell(row,column)
     * start cell or target cell is out of matrix bound
     * start cell equal target cell then return cost of start cell
     * find min cost path for one row matrix
     * find min cost path for multi row matrix -
     * - right path cost, down path cost, diagonal path cost
     */

    MinCostPath minCostPath;

    @BeforeEach
    void init() {
        minCostPath = new MinCostPath();
    }

    private Cell cell(int row, int column) {
        return new Cell(row, column);
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when the start or target cell is out of matrix bound")
    void throwsIllegalArgumentExceptionWhenTheCellIsOutOfMatrixBound() {

        final int[][] matrix = {
                {4, 5, 6},
                {7, 8, 1}
        };

        int row = 2;
        int column = 1;

        assertAll("Start cell must be in matrix",
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, cell(row, column), new Cell(0, 2)), "Thrown IllegalArgument Exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, new Cell(-1, 1), new Cell(0, 2)), "Thrown IllegalArgument Exception")
        );

        assertAll("Target cell must be in matrix",
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, new Cell(0, 0), new Cell(2, 1)), "Thrown IllegalArgument Exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> minCostPath.find(matrix, new Cell(0, 0), new Cell(-1, 2)), "Thrown IllegalArgument Exception")
        );

    }


    @Test
    @DisplayName("Return the cost of start cell when the start cell equals to target cell")
    void returnCostStartCellWhenTheStartCellIsEqualToTargetCell() {

        final int[][] matrix = {
                {3, 5, 7, 9}
        };

        assertEquals(3, minCostPath.find(matrix, cell(0, 0), cell(0, 0)));
    }


    @Test
    @DisplayName("Find min cost for one row matrix")
    void findMinCOstPathForOneRowMatrix() {

        final int[][] matrix = {
                {3, 5, 7, 9}
        };

        assertEquals(8,minCostPath.find(matrix,cell(0,0),cell(0,1)));

    }
}
