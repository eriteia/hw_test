import java.io.*;
import java.util.*;

public class Juice {

    private static final int MAX_JUICE = 1000;
    
    public static void main(String args[]) {

        if (args.length == 0) { 
            System.err.println("Input Filename...");
            System.exit(1);
        }

        File file = new File(args[0]);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file).useDelimiter("[\\s\\n]+");;

            Integer[] testCaseCnt = Juice.readIntLine(scanner, 1);
            System.out.println("Total test cases : " + testCaseCnt[0]);

            for(int i = 0; i < testCaseCnt[0]; i++) {
                System.out.println("----");
                final Integer memberCnt = Juice.readIntLine(scanner, 1)[0];
                System.out.println(" Total member : " + memberCnt);

                final Integer[] row1Values = new Integer[memberCnt];
                final Integer[] row2Values = new Integer[memberCnt];
                final Integer[] row3Values = new Integer[memberCnt];
                final List<Integer[]> untested = new ArrayList<Integer[]>();
                final List<Integer[]> tested = new ArrayList<Integer[]>();

                // Read all data
                for(int j = 0; j < memberCnt; j++) {
                    final Integer[] data = Juice.readIntLine(scanner, 3);
                    row1Values[j] = data[0];
                    row2Values[j] = data[1];
                    row3Values[j] = data[2];

                    untested.add(data);
                }

                // Sort data by columns
                Arrays.sort(row1Values);
                Arrays.sort(row2Values);
                Arrays.sort(row3Values);

                // Max value in column
                final int maxCol1 = row1Values[memberCnt - 1];
                final int maxCol2 = row2Values[memberCnt - 1];
                final int maxCol3 = row3Values[memberCnt - 1];
                
                // Init indice
                int idx1 = -1;
                int idx2 = -1;
                int idx3 = -1;

                // Init Juice value
                int juice1 = 0;
                int juice2 = 0;
                int juice3 = 0;

                // Matched user values, **RESULT** values
                int resultCount = 0;

                // Temp instance for less GC
                Integer[] tmpResult = new Integer[3];
                Integer[] newValue = new Integer[3];

                while((juice1 + juice2 + juice3 <= MAX_JUICE)
                      || (idx1 <= memberCnt && idx2 <= memberCnt && idx3 <= memberCnt))  {

                    int val1 = row1Values[idx1++];
                    int val2 = row2Values[idx2++];
                    int val3 = row3Values[idx3++];

                    final int maxValueInRow = Math.max(Math.max(val1, val2), val3);
                    
                    for(int i = 0; i < 3; i++) {
                        newValue[0] = (i == 0) ? maxValueInRow : val1;
                        newValue[1] = (i == 1) ? maxValueInRow : val2;
                        newValue[2] = (i == 2) ? maxValueInRow : val3;
                        
                        final int newCount = checkAcceptableAndMove(untested, tested, newValue, false);
                        tmpResult[i] = newCount;
                    }

                    int tmpMaxValue = 0;
                    int tmpMaxCountIdx = -1;
                    for(int i = 0; i < 3; i++) { 
                        if(tmpMaxValue < tmpResult[i]) {
                            tmpMaxValue = tmpResult[i];
                            tmpMaxCountIdx = i;
                        }
                    }

                    //TODO Update idx by biggestCountIdx with MaxValueInRow
                    
                    //TODO Update each col's juice value by updated idx
                    
                    if(resultCount < newCount) {
                        resultCount = newCount;
                    }

                    
                }

                scanner.close();
                scanner = null;
            } catch (FileNotFoundException e) {
                if(scanner != null)
                    scanner.close();
            
                System.err.println(e);
                System.exit(1);
            }
        }                

        private static void checkAcceptableAndMove(List<Integer[]> untested,
                                                   List<Integer[]> tested,
                                                   Integer[] newValue,
                                                   boolean isMove) {
            int counts = 0;
            for(Integer[] item : untested) {
                if(item[0] <= newValue[0] && item[1] <= newValue[1] && item[2] <= newValue[2]) {
                    if(isMove) {
                        untested.remove(item);
                        tested.add(item);
                    }
                    counts++;
                }
            }
            
        }

        private static Integer[] readIntLine(Scanner scanner, int count) {
            Integer[] item = new Integer[count];
            for(int i = 0; i < count; i++) {
                item[i] = scanner.nextInt();
            }
        
            return item;
        }
    }
}
