package com.sopovs.moradanen.fan.charts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

import com.sopovs.moradanen.fan.dto.CumulativeGoals;

@Getter
public class DataTable {

    private final List<Column> cols;
    private final List<Row> rows;

    public DataTable(List<Column> cols, List<Row> rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public DataTable(List<CumulativeGoals> cumulativeGoals) {
        List<Long> teamIds = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        List<List<Integer>> goals = new ArrayList<>();
        int numRows = -1;
        int currentRow = -1;
        Long currentTeamId = null;
        for (CumulativeGoals cumulativeGoal : cumulativeGoals) {
            if (currentTeamId != cumulativeGoal.getTeam().getId()) {
                currentTeamId = cumulativeGoal.getTeam().getId();
                teamIds.add(currentTeamId);
                // TODO I18n
                teamNames.add(cumulativeGoal.getTeam().getTitle());
                if (numRows > 0 && currentRow != numRows) {
                    // TODO Exception message
                    throw new IllegalStateException();
                }
                currentRow = 0;
            }
            if (goals.size() <= currentRow) {
                goals.add(new ArrayList<Integer>());
            }
            goals.get(currentRow).add(cumulativeGoal.getGoals());
            currentRow++;
            if (numRows < currentRow) {
                numRows = currentRow;
            }
        }
        List<Column> cols = new ArrayList<>();
        cols.add(new Column("gameId", "Game Id"));
        for (int i = 0; i < teamIds.size(); i++) {
            cols.add(new Column("team-".concat(String.valueOf(teamIds.get(i))), teamNames.get(i)));
        }
        this.cols = Collections.unmodifiableList(cols);

        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Cell> cells = new ArrayList<>(teamIds.size() + 1);
            cells.add(new Cell(String.valueOf(i + 1)));
            for (int j = 0; j < teamIds.size(); j++) {
                cells.add(new Cell(String.valueOf(goals.get(i).get(j))));
            }
            rows.add(new Row(cells));
        }

        this.rows = Collections.unmodifiableList(rows);

    }

    @Getter
    public static class Row {
        private final List<Cell> c;

        public Row(List<Cell> c) {

            this.c = c;
        }
    }

    @Getter
    public static class Cell {
        private final String v;

        public Cell(String v) {

            this.v = v;
        }
    }

    @Getter
    public static class Column {
        private final String id;
        private final String label;
        private final String type;

        public Column(String id, String label, String type) {
            this.id = id;
            this.label = label;
            this.type = type;
        }

        public Column(String id, String label) {
            this.id = id;
            this.label = label;
            this.type = "string";
        }
    }

}
