import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Bills {

    public static ArrayList<Integer> initiaizeBills() {
        ArrayList<Integer> billsList = new ArrayList<>(Arrays.asList(500, 500, 200,200,200,200,100,100,100,100,50,50,20,20,10,10));
        //ArrayList<Integer> billsList = new ArrayList<>(Arrays.asList(10));

        return billsList;
    }

    public static void deleteBillsfromList(ArrayList<Integer> billsList, ArrayList<Integer> billsToDeleteList) {
        for(int j=0; j<billsToDeleteList.size(); j++) {
            double temp = billsToDeleteList.get(j);
            for(int i=0; i<billsList.size(); i++) {
                if(temp == billsList.get(i)) {
                    billsList.remove(i);
                    break;
                }
            }
        }
    }

    public static void addBillsToList(ArrayList<Integer> BillsList, ArrayList<Integer> billsToAddList) {
        for(int i=0; i<billsToAddList.size(); i++) {
            BillsList.add(billsToAddList.get(i));
        }
        Collections.sort(BillsList);
        Collections.reverse(BillsList);

    }

    public static void printBills(ArrayList<Integer> billsList) {
        for(int i=0; i<billsList.size(); i++) {
            System.out.print(billsList.get(i) + " ");
        }
        System.out.println();
    }

    public static int CalculateBillSum(ArrayList<Integer> billsList) {
        int sum = 0;
        for(int i=0; i<billsList.size(); i++) {
            sum += billsList.get(i);
        }
        return sum;
    }
}
