/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergesort_parallel;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Saul Chavez
 */
public class MergeSort_Parallel {

    public void sort (Integer[] a) { 
        Integer[] helper = new Integer[a.length];
        System.out.println(Arrays.toString(a));
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new MergeSortTask(a, helper, 0, a.length-1));
        System.out.println(Arrays.toString(helper));
    }
    
    private class MergeSortTask extends RecursiveAction {
        private static final long serialVersionUID = -749935388568367268L;
        private final Integer[] a;
        private final Integer[] helper;
        private final int lo;
        private final int hi;
        
        public MergeSortTask(Integer[] a, Integer[] helper, int lo, int hi) { 
            this.a = a;
            this.helper = helper;
            this.lo = lo;
            this.hi = hi;
        }
        
        @Override
        protected void compute() { 
            if(lo < hi) {
                int mid = lo + (hi - lo) / 2;
                MergeSortTask left = new MergeSortTask(a, helper, lo, mid);
                MergeSortTask right = new MergeSortTask ( a, helper, mid + 1, hi );
                invokeAll( left, right );
                merge(this.a, this.helper, this.lo, mid + 1, this.hi);
            } else {
                return;
            }
        }
        private void merge(Integer[] a, Integer[] helper, int lo, int mid, int hi) {
            int leftEnd = mid - 1;
            int tmpPos = lo;
            int numElements = hi - lo + 1;
            while ( lo <= leftEnd && mid <= hi) { //Main loop
                if(a[lo] < a[mid]){
                    helper[tmpPos++] = a[lo++];
                } else {
                    helper[tmpPos++] = a[mid++];
                }
            }
            while(lo <= leftEnd){
                helper[tmpPos++] = a[lo++];              //Copy the rest of first half
            }
            while(mid <= hi){
                helper[tmpPos++] = a[mid++];             //Copy rest of right half
            }
            for (int i = 0; i < numElements; i ++, hi--){ //Copy TmpArray back
                a[hi] = helper[hi];
            }
        }
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Integer[] array = {8,7,6,9,5,3,3,4,1,2};
        new MergeSort_Parallel().sort(array);
    }
    
}
