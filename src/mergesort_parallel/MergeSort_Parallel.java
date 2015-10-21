/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mergesort_parallel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Saul Chavez
 */
public class MergeSort_Parallel {

    public void sort (Integer[] a) { 
        Integer[] helper = new Integer[a.length];
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new MergeSortTask(a, helper, 0, a.length-1));
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
        }
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
