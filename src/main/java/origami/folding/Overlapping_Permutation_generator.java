package origami.folding;

public class Overlapping_Permutation_generator {//Permutations with repeat generator
    //A class that efficiently generates overlapping permutations of faces according to the SubFace configuration by modifying the class that generates permutations with duplicates.

    int[] ij;//Store permutations with duplicates.
    int numDigits;//The number of digits in the permutations with repetition. For example, if the number of digits is 5, it will generate a permutation made up of numbers from 1 to 5.
    int i_traveler;//Traveler's position
    int[] map;//Map image. The number that came out by the way that each side is now. The current state of the road is not counted.
    GuideMap guides; //For some reason, this line is ok to compile, but I get an error during execution, but the line below works fine.


    public Overlapping_Permutation_generator(int k) {
        numDigits = k;
        i_traveler = 0;
        ij = new int[k + 10]; // Store permutations with duplicates.
        map = new int[k + 10]; // Map image. The number that came out by the way that each side is now. The current state of the road is not counted.
        guides = new GuideMap(k + 10);

        reset();
    }

    // Go from the current permutation to the next permutation.
    // Advance the kth digit by one, and set all k-1 digits and below to 1. The return value is the number of digits changed as a permutation
    // Return 0 if the current permutation is the last one.
    public int next(int moving_digit) {
        //Traveler movement When moving in the direction of increasing the number of digits
        // Follow the value of each ij [] as a guide. When the number of digits returns from the larger one to the smaller one, the value of each ij [] is changed.
        // The map is a guide for each road, and contains information on the guides with less than that number of digits (including that). Specifically, the number of each group that came out so far.
        // i_tabibito == numDigits goes back one digit. When I got back, I immediately corrected the location (road guide) and the map there.
        // If i_tabibito <numDigits, ij [i_tabibito] == Gsuu will return by one digit. When I got back, I immediately corrected the location (road guide) and the map there.
        // If i_tabibito <numDigits, ij [i_tabibito] <Gsuu, proceed in the direction of increasing by one digit. As soon as you proceed, correct the location (road guide) and map there. The guide should be as young as possible.
        i_traveler = moving_digit;
        int ireturn = moving_digit;

        while (i_traveler <= numDigits) {

            if (i_traveler == 0) {
                i_traveler++;
                ij[i_traveler] = 0;// Proceed to the larger number of digits
            }

            ij[i_traveler] = rebuildGuide(ij[i_traveler]);

            if (ij[i_traveler] <= numDigits) {// Proceed to the larger number of digits
                i_traveler++;
                ij[i_traveler] = 0;
                if (i_traveler == numDigits + 1) {
                    break;
                }
            } else {// Return to the one with the smaller number of digits
                i_traveler--;
                ireturn = i_traveler;
            }
        }

        return ireturn;
    }

    private int rebuildGuide(int ig) {

        for (int i = 1; i <= numDigits; i++) {
            map[i] = 0;
        }

        for (int i = 1; i <= i_traveler - 1; i++) {
            map[ij[i]] = map[ij[i]] + 1;
        }

        // It is necessary to consider whether there are any bugs here.
        int ignew = ig;
        while (true) {
            ignew++;
            if (ignew > numDigits) {
                break;
            }
            boolean exit_flg = map[ignew] != 1;

            for (int i = 1; i <= guides.get(ignew, 0); i++) {
                if (map[guides.get(ignew, i)] == 0) {
                    exit_flg = false;
                }
            }

            if (exit_flg) {
                break;
            }

        }

        return ignew;
    }

    public int getPermutation(int i) {
        return ij[i];
    }

    //Make the permutation the very first
    public void reset() {
        next(0);
    }

    //Handing over of guide information
    public void addGuide(int iM, int i) {
        guides.add(iM, i);
    }

    /**
     * Guide. Create in SubFace. Register the face directly above a face in front of the permutations with repetition machine
     * Image of guide map. Look at the map and decide what to do next. The number that came out by the way that each side is now. The current state of the road is not counted.
     */
    static class GuideMap {
        // guide [i] [j] represents the surface that should have appeared before the surface i appeared. guide [i] [0] is the number of such faces.
        int[][] guide;
        int numDigits;

        public GuideMap(int digit) {
            numDigits = digit;

            guide = new int[digit + 11][50];
            for (int i = 0; i <= digit + 10; i++) {
                guide[i][0] = 0;
            }
        }

        public void add(int Menidid, int ueMenidid) {
            guide[Menidid][0] = guide[Menidid][0] + 1;
            guide[Menidid][guide[Menidid][0]] = ueMenidid;
        }

        public int get(int faceIndex, int i) {
            return guide[faceIndex][i];
        }
    }
}