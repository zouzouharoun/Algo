import java.util.*;

public interface Algorithms {
    Random rng = new Random();


    static void main(String[] args) {
        // Initialiser les tableaux de monstres et de trésors
        int[][] monsters = new int[16][10];
        int[][] treasures = new int[16][10];
        int[] heroPosition = {0, monsters[0].length/2};

        // Ajouter un horodatage avant l'exécution de la méthode
        long startTime = System.currentTimeMillis();
        
        // Appeler la méthode à mesurer
        GT.generateMonstersAndTreasures(monsters, treasures);
        
        // Ajouter un horodatage après l'exécution de la méthode
        long endTime = System.currentTimeMillis();
        
        // Calculer la différence pour obtenir le temps d'exécution en millisecondes
        long executionTime = endTime - startTime;
        
        // Afficher le temps d'exécution
        System.out.println("Temps d'exécution : " + executionTime + " ms");
        // Your code here
        
        

        // Afficher le plateau de jeu généré
        printBoard(monsters, treasures);

        // Vérifier si  y a vraiment au minimum 2 monstres
        for (int i = 0; i < monsters.length; i++) {
            System.out.println(GT.isTreasuresLessThanOrEqualToMonstersTotal(monsters[i],treasures[i]));
            System.out.println(GT.testTreasures(treasures[i]));

            System.out.println(GT.testMonsters(monsters[i]));}



        // Afficher le plateau de jeu mélangé
        int[][] mixedTable = GS.mixMonstersAndTreasures(monsters, treasures);
        System.out.println("Plateau de jeu avec monstres et trésors mélangés :");
        GS.printBoard(mixedTable);
        State currentState = new State(heroPosition, 100, 0, monsters, treasures, 0, 0);
        GS.greedySolution(currentState);
    }



    static void printBoard(int[][] monstersToFill, int[][] treasuresToFill) {
        // Afficher le plateau de jeu
        System.out.println("Monsters:");
        for (int[] row : monstersToFill) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Treasures:");
        for (int[] row : treasuresToFill) {
            System.out.println(Arrays.toString(row));
        }
    }





    /* --- Generate & Test --- */
    interface GT {

        
        
        static void generateMonstersAndTreasures(int[][] monstersToFill, int[][] treasuresToFill) {



            for (int i = 0; i < monstersToFill.length; i++) {
                for (int j = 0; j < monstersToFill[i].length; j++) {
                    if(i!=0 && j!=monstersToFill[0].length/2){

                        // Choisir aléatoirement le contenu de la case

                    
                        double prob=rng.nextDouble();
                        if (prob <= 2.0 /6.0 ) {
                            //génerer un monstre

                            monstersToFill[i][j] = 1 + rng.nextInt(50);
                            // si on est dans la derniere case de la rangé et qu'on a tjr pas 2 monstres pas besoin d'aller a la fin de la boucle pour regénérer
                            if(j==monstersToFill[i].length-1 && !testMonsters(monstersToFill[i])){
                                while (!testMonsters(monstersToFill[i])){
                                    generateMonsters(treasuresToFill, monstersToFill, i);

                                }
                            }

                        } else {

                            if (2.0/6.0 < prob&&prob <= 3.0 / 6.0){
                                //generer un tresor
                                treasuresToFill[i][j] = 1 + rng.nextInt(99);
                                if(j==treasuresToFill[i].length-1 && (!testTreasures(treasuresToFill[i])||!isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i]))){
                                    while (!testTreasures(treasuresToFill[i])||!isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i])){
                                        generateTreasures(treasuresToFill, monstersToFill,i );

                                    }
                                }
                           
                            }
                            
                        }
                    
                    }
                }

               

                //verifier si y'a deux monstres minimum sinon regenerer
                while (!testMonsters(monstersToFill[i])){
                    for (int l = 0; l < monstersToFill[i].length; l++) {

                        

                        if (rng.nextDouble() <= 1.0 / 3.0 && !isCellOccupied(treasuresToFill, i,l )) {
                            monstersToFill[i][l] = 1 + rng.nextInt(50);

                        }
                        
                    }
                    monstersToFill[0][monstersToFill[0].length/2] = 0;

                
            
                }
               
                
                while (!testTreasures(treasuresToFill[i])||!isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i])){
                    
                    generateTreasures(treasuresToFill, monstersToFill, i);
                            
                                
                        
                }
            }
                    
                    
                    
                   

                
        }
                
           


        
        //verifier s'il ya deux monstres
        static boolean testMonsters(int[] row) {
            int count = 0;
            for (int cell : row) {
                if (cell >= 1 && cell <= 50) {
                    count++;
                }
            }
            return count >= 2;



        }

        static boolean testTreasures(int[] row) {
            int count = 0;
            for (int cell : row) {
                if (cell >= 1 && cell <= 99) {
                    count++;
                }
            }
            return count <= 2;



        }
        static boolean isTreasuresLessThanOrEqualToMonstersTotal(int[] monstersRow, int[] treasuresRow) {
            int monstersSum = 0;
            int treasuresSum = 0;

            for (int i = 0; i < monstersRow.length; i++) {
                monstersSum += monstersRow[i];
                treasuresSum += treasuresRow[i];
            }

            return treasuresSum <= monstersSum;
        }

        //verifier si la case ne contient pas de tresors ou de monstres(necessaires lors de la regeneration)
        static boolean isCellOccupied(int[][] matrix, int row, int column) {

            return matrix[row][column] > 0 ;
        }
        static void generateTreasures(int[][] treasuresToFill, int[][] monstersToFill, int row) {
            
            // Remplir la rangée de trésors avec de nouveaux trésors
            
            for (int l = 0; l < treasuresToFill[row].length; l++) {
                if(row!=0&&l!=treasuresToFill[row].length/2){
                    treasuresToFill[row][l] = 0;
                    if (rng.nextDouble() <= 1.0 / 6.0 && !isCellOccupied(monstersToFill, row, l)) {
                        treasuresToFill[row][l] = 1 + rng.nextInt(99);
                    }
                }
        
            }  
        
            
        
    
        }
        static void generateMonsters(int[][] treasuresToFill, int[][] monstersToFill, int row) {
            
            // Remplir la rangée de monstres
            
            for (int l = 0; l < treasuresToFill[row].length; l++) {
                if(row!=0&&l!=treasuresToFill[row].length/2){
                    
                    if (rng.nextDouble() <= 1.0 / 3.0 && !isCellOccupied(treasuresToFill, row, l)) {
                        monstersToFill[row][l] = 1 + rng.nextInt(50);
                    }
                }
        
            }  
        
            
        
    
        }
    }
    

        


    /* --- Divide & Conquer --- */
    interface DC {

        /* --- Algorithm used : MergeSort --- */

        static void sortLevel(int[][] monstersToSort, int[][] treasuresToSort) {
            
            // Calculate the initial values for each row
            int[] rowValues = new int[monstersToSort.length];
            for (int i = 0; i < monstersToSort.length; i++) {
                rowValues[i] = sumRow(monstersToSort[i], treasuresToSort[i]);
            }
            
            // Perform the merge sort
            merge_sort(rowValues, monstersToSort, treasuresToSort, 0, monstersToSort.length - 1);
            
        }


        /* --- MergeSort --- */

        /*@  normal behaviour
          @  requires treasuresToSort.length == monstersToSort.length && (\for all int n treasuresToSort[n].length == monstersToSort[n].length);
          @  requires monstersToSort !=null && monstersToSort.length >0;
          @  requires 0<= start && end<= tab.length);

          @  ensure \old(rowValues.length) == rowValues.length == monstersToSort.length == treasuresToSort.length
          @  ensures \forall int i; 0 <= i && i < \old(rowValues.length ) ;
          @  (\exists int j; 0 <= j && j < treasuresToSort.length ;
          @       rowValues[j] == \old(rowValues[i]) && treasuresToSort[j] == \old(treasuresToSort[i] &&& monstersToSort[j] == \old(monstersToSort[i])));
          @  ensures (\forall int i ; 0<= i && i < rowValues.length - 1; rowValues[i] <= tab[i+1]) ;
        @*/
        static void merge_sort (int[] rowValues,int[][] monstersToSort,int[][] treasuresToSort,int start,int end){
            
            int mid;
            if (start<end)
            {
                mid = (start + end) / 2;
                merge_sort(rowValues, monstersToSort, treasuresToSort, start, mid);
                merge_sort(rowValues, monstersToSort, treasuresToSort, mid+1, end);
                merge(rowValues, monstersToSort, treasuresToSort, start, mid, end);
            }
        }

        /* --- Merge --- */
        /*@  requires 0 <= start && start ‹ mid && mid <= end && end < rowValues.length;
          @  requires rowValues != null && rowValues.length > 0;
          @  requires rowValues.length == array_rows.length == monstersToSort.length == treasuresToSort.length;        

          @ ensures \old (array_rows. length ) == array_rows.length == row_values. length == monstersToSort.length == treasuresToSort.length;
          @ ensures \old (rowValues.length ) == row_values. length;
          @ ensures (\forall int i; 0 <= 1 && 1 < \old (rowValues.length ) ;
          @ (\exists int j: 0 <=j && j < rowValues.length;
                tab [j] == \old (tab[i])));
          @ ensures (\forall int i; start <= i && i < end; rowValues[i] <= rowValues[i+1]) ;
        @*/
        static void merge(int[] rowValues,int[][] monstersToSort,int[][] treasuresToSort,int start,int mid, int end){
            int[] array_rows = new int [rowValues.length];
            int[][] array_monsters = new int [monstersToSort.length][monstersToSort[0].length];
            int[][] array_treasures = new int [treasuresToSort.length][treasuresToSort[0].length];

            int i, j ;
            for (i = start; i<= mid; i++) {
                array_rows[i] = rowValues[i];
                array_monsters[i] = monstersToSort[i];
                array_treasures[i] = treasuresToSort[i];
            }
            for (j = mid + 1; j <= end; j++){
                array_rows[end + mid +1 - j] = rowValues[j];
                array_monsters[end + mid +1 - j] = monstersToSort[j];
                array_treasures[end + mid +1 - j] = treasuresToSort[j];
            }
            i = start;
            j = end;
            for (int k = start; k<= end; k++) {
                if(array_rows[i] <= array_rows[j]){
                    rowValues[k] = array_rows[i];
                    monstersToSort[k] = array_monsters[i];
                    treasuresToSort[k] = array_treasures[i];
                    i++;
                }
                else{
                    rowValues[k] = array_rows[j];
                    monstersToSort[k] = array_monsters[j];
                    treasuresToSort[k] = array_treasures[j];
                    j--;
                }
            }
        }
        /* --- Utility functions for DC --- */
        static int sumRow(int[] row_monsters, int[] row_treasures) {
            int sum = 0;
            for (int i = 0; i < row_monsters.length; i++) {
                sum += row_treasures[i] - row_monsters[i];
            }
            return sum;
        }

    }



    /* --- Greedy Search --- */

    interface GS {
            /*@ [First, OpenJML doc]
            //@ requires State != null;
            --- [Second, authors info]
            * Specification: Rayane | Last update: 10/05/24
            * Implementation: Rayane | Last update : 10/05/24
            * Test/Debug: Rayane | Last update : 10/05/24
            ***/
            public static int greedySolution(State state) {
                // Afficher le plateau de jeu mélangé
                int[][] mixedTable = GS.mixMonstersAndTreasures(state.monsters, state.treasures);
                System.out.println("Plateau de jeu avec monstres et trésors mélangés :");
                GS.printBoard(mixedTable);

                // Afficher la meilleur direction a prendre
                List<List<int[]>> paths = GS.getPaths(state.heroPos, mixedTable, 5, new ArrayList<>(), new ArrayList<>(), new HashSet<>());
                int score = GS.getBest(paths, mixedTable, 100);
                System.out.println("Joueur Position: [" + state.heroPos[0] + ", " + state.heroPos[1] + "]");
                System.out.print("Score à atteindre: " + score);

            return score;}

            /*@ [First, OpenJML doc]
            //@ requires joueur != null && board != null && visited != null;
            //@ ensures (\forall int[] move; \result.contains(move); board[move[0]][move[1]] != -1) &&
              (\forall int[] move; \result.contains(move); !visited.contains(move[0] + "," + move[1]));
            --- [Second, authors info]
            * Specification: Rayane | Last update: 10/05/24
            * Implementation: Rayane | Last update : 10/05/24
            * Test/Debug: Rayane | Last update : 10/05/24
            ***/

            public static List<int[]> getOptions(int[] joueur, int[][] board, Set<String> visited) {
                List<int[]> moves = new ArrayList<>();
                int row = joueur[0];
                int col = joueur[1];
                int[][] directions = {{1, 0}, {0, -1}, {0, 1}};
                for (int[] dir : directions) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && !visited.contains(newRow + "," + newCol)) {
                        moves.add(new int[]{newRow, newCol});
                    }
                }
                return moves;
            }

            /*@ [First, OpenJML doc]
            //@ requires joueur != null && board != null && path != null && paths != null && visited != null && steps >= 0;
            //@ ensures paths != null && (\forall List<int[]> p; paths.contains(p); (\forall int[] move; p.contains(move); board[move[0]][move[1]] != -1)
            @ pure
            --- [Second, authors info]
            * Specification: Rayane | Last update: 10/05/24
            * Implementation: Rayane | Last update : 10/05/24
            * Test/Debug: Rayane | Last update : 10/05/24
            ***/
            public static List<List<int[]>> getPaths(int[] joueur, int[][] board, int steps, List<int[]> path, List<List<int[]>> paths, Set<String> visited) {
                if (steps == 0) {
                    paths.add(new ArrayList<>(path));
                    return paths;
                }
                for (int[] nextPos : getOptions(joueur, board, visited)) {
                    visited.add(nextPos[0] + "," + nextPos[1]);
                    path.add(nextPos);
                    getPaths(nextPos, board, steps - 1, path, paths, visited);
                    path.remove(path.size() - 1);
                    visited.remove(nextPos[0] + "," + nextPos[1]);
                }
                return paths;
            }
            /*@ [First, OpenJML doc]
            //@ requires paths != null && board != null && pv >= 0;
            // @ ensures \result != null && (\forall int[] move; \result.contains(move); board[move[0]][move[1]] != -1);
            @ pure
            --- [Second, authors info]
            * Specification: Rayane | Last update: 10/05/24
            * Implementation: Rayane | Last update : 10/05/24
            * Test/Debug: Rayane | Last update : 10/05/24
            ***/
            public static int getBest(List<List<int[]>> paths, int[][] board, int pv) {
                List<Integer> values = new ArrayList<>();
                for (List<int[]> path : paths) {
                    int v1 = pv;
                    for (int[] pos : path) {
                        int x = pos[0];
                        int y = pos[1];
                        int v = board[x][y];
                        v1 += v;
                    }
                    values.add(v1);
                }
                int best = Collections.max(values);
                int index = values.indexOf(best);
                //return paths.get(index);
                return best;
            }


           // public static String getDirection(int[] joueur, List<int[]> direction) {
        //    if (joueur[1] < direction.get(0)[1]) {
        //          return "right";
        //     } else if (joueur[1] > direction.get(0)[1]) {
        //          return "left";
        //       } else if (joueur[0] < direction.get(0)[0]) {
        //            return "down";
        //        }
        //        return null;
        //    }

            /* --- Utility functions for GS --- */

            static void printBoard(int[][] gameBoard) {
                for (int[] row : gameBoard) {
                    System.out.println(Arrays.toString(row));
                }
            }

            // Fonction pour mélanger les tableaux de monstres et de trésors en un seul tableau
            static int[][] mixMonstersAndTreasures(int[][] monsters, int[][] treasures) {
                int[][] mixedTable = new int[monsters.length][monsters[0].length];
                for (int i = 0; i < monsters.length; i++) {
                    for (int j = 0; j < monsters[i].length; j++) {
                        if (monsters[i][j] != 0) {
                            mixedTable[i][j] = -monsters[i][j]; // Making monster health negative
                        } else {
                            mixedTable[i][j] = treasures[i][j];
                        }
                    }
                }
                return mixedTable;
            }
        }





    /* --- Dynamic Programming --- */
    interface DP {
        static String perfectSolution(State state) {
            // Initialiser la table de mémoire pour stocker les sous-résultats
            String[][] memoTable = new String[state.monsters.length][state.monsters[0].length];
            
            // Appeler la fonction auxiliaire récursive pour calculer la solution optimale
            String optimalSequence = calculateOptimalSequence(state, memoTable);
            
            return optimalSequence;
        }

        static String calculateOptimalSequence(State state, String[][] memoTable) {
            int[][] monsters = state.monsters;
            int[][] treasures = state.treasures;
            int[] heroPos = state.heroPos;
            State currentState = state;
        
            int numRows = monsters.length;
            int numCols = monsters[0].length;
        
            // Vérifie si le sous-problème a déjà été résolu et le récupérer à partir de la table de mémoire si possible
            if (memoTable[heroPos[0]][heroPos[1]] != null) {
                return memoTable[heroPos[0]][heroPos[1]];
            }
        
            StringBuilder optimalSequence = new StringBuilder();
        
            // Vérifie les cases adjacentes pour trouver le meilleur trésor ou le monstre le plus faible
            int[] dx = {-1, 1, 0}; // Déplacement horizontal
            int[] dy = {0, 0, 1}; // Déplacement vertical
            int maxTreasure = -1;
            int minMonster = Integer.MAX_VALUE;
            int bestDirection = -1;
        
            for (int i = 0; i < 3; i++) {
                int newRow = heroPos[0] + dx[i];
                int newCol = heroPos[1] + dy[i];
                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols && !isCellOccupied(optimalSequence, newRow, newCol)) {
                    if (treasures[newRow][newCol] > 0 && treasures[newRow][newCol] > maxTreasure) {
                        maxTreasure = treasures[newRow][newCol];
                        bestDirection = i;
                    }
                    else if (monsters[newRow][newCol] > 0 && monsters[newRow][newCol] < minMonster) {
                        minMonster = monsters[newRow][newCol];
                        bestDirection = i;
                    }
                    else {
                        bestDirection = 2;
                    }
                }
            }


            //Vérifie si le héros n'est pas à la fin du plateau (à modifier)
            if (heroPos[0] != numRows - 1){
                // Simule le déplacement du héros vers la direction avec le meilleur trésor ou le monstre le plus faible
                if (bestDirection != -1) {
                    int newRow = heroPos[0] + dx[bestDirection];
                    int newCol = heroPos[1] + dy[bestDirection];
                    optimalSequence.append("(").append(newRow).append(",").append(newCol).append(")");
                    // Mets à jour la table de mémoire avec le résultat calculé
                    memoTable[heroPos[0]][heroPos[1]] = optimalSequence.toString();
                    // Mets à jour la position du chemin à prendre dans l'état
                    currentState.heroPos = new int[]{newRow, newCol};
                    //Appel récursif de la fonction avec la meilleur position mise à jour dans le currentState
                    optimalSequence = new StringBuilder(calculateOptimalSequence(currentState, memoTable));
                    
                }
            }
            return optimalSequence.toString();
        }
        
        // Vérifie si la cellule a déjà été visitée
        static boolean isCellOccupied(StringBuilder path, int row, int col) {
            return path.toString().contains("(" + row + "," + col + ")");
        }
        
    }

    /* --- Common utility functions --- */
    //TODO (if you have any)

}

