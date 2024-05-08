import java.util.*;

public interface Algorithms {
    Random rng = new Random();

    static void main(String[] args) {
        // Your code here
        
        int rows = 11; // Nombre de lignes
        int cols = 7; // Nombre de colonnes

        // Créer des tableaux pour représenter le plateau de jeu
        int[][] monstersToFill = new int[rows][cols];
        int[][] treasuresToFill = new int[rows][cols];

        // Générer les monstres et les trésors
        GT.generateMonstersAndTreasures(monstersToFill, treasuresToFill);

        // Afficher le plateau de jeu généré
        printBoard(monstersToFill, treasuresToFill);

        // Vérifier si  y a vraiment au minimum 2 monstres
        for (int i = 0; i < monstersToFill.length; i++) {
            System.out.println(GT.isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i]));
            System.out.println(GT.testTreasures(treasuresToFill[i]));

            System.out.println(GT.testMonsters(monstersToFill[i]));}
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
                            if(j==monstersToFill[i].length && !testMonsters(monstersToFill[i])){
                                while (!testMonsters(monstersToFill[i])){
                                    for (int l = 0; l < monstersToFill[i].length; l++) {

                                        

                                            if (rng.nextDouble() <= 1.0 / 3.0 && !isCellOccupied(treasuresToFill, i,l )) {
                                                monstersToFill[i][l] = 1 + rng.nextInt(50);

                                            }
                                        
                                    }
                                    monstersToFill[0][monstersToFill[0].length/2] = 0;

                                }
                            }

                        } else {

                            if (2.0/6.0 < prob&&prob <= 3.0 / 6.0)
                                //generer un tresor
                                treasuresToFill[i][j] = 1 + rng.nextInt(99);
                            if(j==treasuresToFill[i].length && !testTreasures(treasuresToFill[i])){
                                while (!testTreasures(treasuresToFill[i])){
                                    for (int l = 0; l < treasuresToFill[i].length; l++) {
                                        treasuresToFill[i][l] = 0;}
                                    for (int l = 0; l < treasuresToFill[i].length; l++) {
                                        

                                        

                                            if (rng.nextDouble() <= 1.0 / 6.0 && !isCellOccupied(monstersToFill, i,l )) {
                                                treasuresToFill[i][l] = 1 + rng.nextInt(99);

                                            }
                                        
                                    }
                                    treasuresToFill[0][treasuresToFill[0].length/2] = 0;

                                }
                            }
                        }
                    
                }}

               

                //verifier si y'a deux monstres minimum sinon regenerer
                while (!testMonsters(monstersToFill[i])){
                    for (int l = 0; l < monstersToFill[i].length; l++) {

                        

                            if (rng.nextDouble() <= 1.0 / 3.0 && !isCellOccupied(treasuresToFill, i,l )) {
                                monstersToFill[i][l] = 1 + rng.nextInt(50);

                            }
                        
                    }
                    monstersToFill[0][monstersToFill[0].length/2] = 0;

                
            
                }
                while(!isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i])){
                    for (int l = 0; l < treasuresToFill[i].length; l++) {
                        treasuresToFill[i][l] = 0;   }        
                    for (  int l = 0; l < treasuresToFill[i].length; l++) {

                        

                            if (rng.nextDouble() <= 1.0 / 6.0 && !isCellOccupied(monstersToFill, i,l )) {
                                treasuresToFill[i][l] = 1 + rng.nextInt(99);

                            }
                        } treasuresToFill[0][treasuresToFill[0].length/2] = 0;
                    }
                
                while (!testTreasures(treasuresToFill[i])){
                    for (int l = 0; l < treasuresToFill[i].length; l++) {
                        treasuresToFill[i][l] = 0;}
                    for (int l = 0; l < treasuresToFill[i].length; l++) {

                        

                            if (rng.nextDouble() <= 1.0 / 6.0 && !isCellOccupied(monstersToFill, i,l )) {
                                treasuresToFill[i][l] = 1 + rng.nextInt(99);

                            }
                        
                        if(l==treasuresToFill[i].length){
                            while(!isTreasuresLessThanOrEqualToMonstersTotal(monstersToFill[i],treasuresToFill[i])){
                                for ( l = 0; l < treasuresToFill[i].length; l++) {
                                    treasuresToFill[i][l] = 0;}
                                for ( l = 0; l < treasuresToFill[i].length; l++) {
        
                                    
        
                                        if (rng.nextDouble() <= 1.0 / 6.0 && !isCellOccupied(monstersToFill, i,l )) {
                                            treasuresToFill[i][l] = 1 + rng.nextInt(99);
        
                                        }
                                    }} treasuresToFill[0][treasuresToFill[0].length/2] = 0;
                                }
                    }
                    treasuresToFill[0][treasuresToFill[0].length/2] = 0;
                   

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
    }

        


    /* --- Divide & Conquer --- */
    interface DC {
        static void sortLevel(int[][] monstersToSort, int[][] treasuresToSort) {
            //TODO
        }

        /* --- Utility functions for DC --- */
        //TODO (if you have any)
    }

    /* --- Greedy Search --- */
    interface GS {
        static int greedySolution(State state) {
            //TODO
            return 0;       }

        /* --- Utility functions for GS --- */
        //TODO (if you have any)
    }

    /* --- Dynamic Programming --- */
    interface DP {
        static String perfectSolution(State state) {
            //TODO
            return "";
        }

        /* --- Utility functions for DP --- */
        //TODO (if you have any)
    }

    /* --- Common utility functions --- */
    //TODO (if you have any)

}

