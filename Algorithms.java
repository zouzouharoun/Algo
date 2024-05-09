import java.util.*;

public interface Algorithms {
    Random rng = new Random();

    static void main(String[] args) {
        // Initialiser les tableaux de monstres et de trésors
        int[][] monsters = new int[11][7];
        int[][] treasures = new int[11][7];
        
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

