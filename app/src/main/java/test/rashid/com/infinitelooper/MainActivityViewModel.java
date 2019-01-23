package test.rashid.com.infinitelooper;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivityViewModel extends ViewModel {

    private String TAG = MainActivityViewModel.class.getSimpleName();

    // Arraylist to add fibnacci series number
    private ArrayList<ArrayList<Integer>> fibonacciList = new ArrayList<>();

    /**
     * Livedata is Obseravable object which emit data when new value is inserted.
     * VLiveData is a data holder class that can be observed within a given lifecycle.
     * THe value in livedata is observed by an observer on a main thread and update the UI accordingly
     */
    private static MutableLiveData<String> fibonacciLiveDataObserver;


    // Initializing the livedata
    LiveData<String> initializeObservable() {
        if (fibonacciLiveDataObserver == null) {
            fibonacciLiveDataObserver = new MutableLiveData<>();

        }
        return fibonacciLiveDataObserver;
    }


    // Setting the initial Value i.e 0 and 1
    void setInitialValue() {
        initializeObservable();
        fibonacciList.add(new ArrayList<>(Collections.singletonList(0)));
        fibonacciList.add(new ArrayList<>(Collections.singletonList(1)));
        fibonacciLiveDataObserver.setValue("0");
        fibonacciLiveDataObserver.setValue("1");
    }

    /**
     * When viewmodel call onclear
     **/
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "on cleared called");
    }

    /**
     * Generating fibonacci series for given range
     **/
    void fibonacciSeriesFromStartToEnd(int start, int end) {
                for (int counter = start; counter <= end; counter++) {

                    // Reversing the list to make it easier for calculation
                    Collections.reverse(fibonacciList.get(counter - 1));
                    // Calculating the next number
                    ArrayList<Integer> numberList = addTwoList(fibonacciList.get(counter), fibonacciList.get(counter - 1));
                    // Setting the value in livedata so that observer can pick the number and display it in recycler view
                    fibonacciLiveDataObserver.setValue(listToString(numberList));
                    // Adding the number in the fibonaccilist to make it available to calculate the new number
                    fibonacciList.add(numberList);
                }
    }

    /**
     * Converting list of number to String.
     * This is the ArrayList of the number that need to be display (Result of fibonacci series addition)
     * Could have used Integer, but stick with String, since we are not doing any further operation on the result
     * despite displaying.
     *
     **/

    private String listToString(ArrayList<Integer> numberList) {
        StringBuilder b = new StringBuilder("");
        for (int i : numberList) {
            b.append(String.valueOf(i));
        }
        return b.toString();
    }

    /**
     * This is the mainfunction that add two List.
     * Here two list is notthing but two number that are stored as a List, so to accomodate increasing digits, as
     * the loop gonna run infinite time.
     *
     **/
    private ArrayList<Integer> addTwoList(ArrayList<Integer> list1, ArrayList<Integer> list2){
        ArrayList<Integer> resultList = new ArrayList<>();
        // Getting the list with larger value. SO that loop can be run on that list
        ArrayList<Integer> list3 = (list1.size()>= list2.size()) ? list1 : list2;
       // Getting the list with smaller value.
        ArrayList<Integer> list4 = (list1.size()>= list2.size()) ? list2 : list1;
        // Reverse the list so the lists with different size can easily be calculate
        // e.g. to add 1346 to 456 we have to start from back side i.e. 6+6. To perform this opertion I am reversing the list
        Collections.reverse(list3);
        Collections.reverse(list4);
        int carry = 0;
        int result = 0;
        for(int i = 0; i<list3.size(); i++) {
            // adding resul
            result = result + list3.get(i);
            // checking if second list has number on this digits or not
            if(list4.size() > i) {
                // if number is present add that number.
                result = result + list4.get(i);
            }
            /// calculate the carryforward number

             result = result + carry;
            carry = result / 10;
            result = result % 10;
            resultList.add(result);
            result = 0;

        }
        // if number is single digit and has carry forward value we need to add that
        if(carry > 0) {
            resultList.add(carry);
        }

        // Reversing back the result to get the actual number.
        Collections.reverse(resultList);
        return  resultList;
    }
}

