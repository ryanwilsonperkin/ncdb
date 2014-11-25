/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author ryan
 */
public class Query2 {
    final List<Record> records;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private int[] results;
    
    Query2(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
    }
    
    public String result() {
        int n_male = results[0];
        int n_female = results[1];
        int total = n_male + n_female;
        float p_male = (float) n_male / total;
        float p_female = (float) n_female / total;
        return String.format("$Q2,%d,%d,%.2f,%.2f", n_male, n_female, p_male, p_female);
    }
    
    private class Computation extends RecursiveTask<int[]> {
        final List<Record> records;
        final int start;
        final int end;
        final int threshold;

        Computation(List<Record> records, int start, int end, int threshold) {
            this.records = records;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected int[] compute() {
            if ((end - start) <= threshold) {
                return calculate();
            } else {
                Computation c1 = new Computation(records, start, (start + end) / 2, threshold);
                c1.fork();
                Computation c2 = new Computation(records, (start + end) / 2, end, threshold);
                return reduce(c2.invoke(), c1.join());
            }
        }

        private int[] calculate() {
            int[] result = new int[2];
            for (int i = start; i < end; i++) {
                Record r = records.get(i);
                if (r.person.sex == 'M') ++result[0];
                else if (r.person.sex == 'F') ++result[1];
            }
            return result;
        }

        private int[] reduce(int[] a, int[] b) {
            int[] c = new int[2];
            c[0] = a[0] + b[0];
            c[1] = a[1] + b[1];
            return c;
        }
    }
}
