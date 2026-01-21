package com.carsell.platform.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/*

Given an m x n 2D binary grid grid which represents a
map of '1's (land) and '0's (water),
return the number of islands.

An island is surrounded by water
and is formed by connecting adjacent lands horizontally or vertically.
You may assume all four edges of the grid are all surrounded by water.
Example 1:

Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
Example 2:

Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] is '0' or '1'.
public int numIslands(char[][] grid) {

    }

 */
public  class Matrix {

    static Land[] prev = null;
    static UUID uuid = UUID.randomUUID();

    @Data
    @AllArgsConstructor
    static class Land {
        private Long id;
        private String name;
        private UUID uuid;

        public Land(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static UUID uuidFactory(Land current){
//        if(!prev.name.equals(current.name)){
//            uuid = UUID.randomUUID();
//        }
        return uuid;
    }

    public static void main(String[] args) {

        List<Land> lands = new ArrayList<Land>();


        String[][] grid = {
                {"1", "0", "1", "1", "0"},
                {"0", "1", "0", "0", "0"},
                {"1", "1", "0", "1", "0"},
                {"0", "0", "0", "1", "1"}
        };
        // matrix calculate

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[i].length; j++) {
                String nodeValue = grid[i][j];
                Land currentLand = new Land(Long.valueOf( i + j), nodeValue);
                currentLand.uuid = uuidFactory(currentLand);
                lands.add(currentLand);
//                prev = currentLand;
            }

        }


        // result
        Map<String, Long> landsMap = lands.stream().collect(Collectors.groupingBy(
                l -> l.uuid.toString(),
                Collectors.counting()
        ));

        int resultIslands = landsMap.keySet().size();
    }
}

