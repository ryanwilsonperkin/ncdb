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
public class Query5 {
    final List<Record> records;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private int[] results;
    
    Query5(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
    }
    
    public String result() {
        String configs = "";
        int worst_config = 0;
        int worst_count = 0;
        for (int i = 0; i < 13; i++) {
            if (results[i] > worst_count) {
                worst_count = results[i];
                worst_config = i;
            }
            if (i >= 1) {
                configs += results[i] + ",";
            }
        }
        configs += results[0];
        return String.format("$Q5,%d,%s",worst_config,configs);
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
            int[] result = new int[13];
            for (int i = start; i < end; i++) {
                Collision c = records.get(i).collision;
                if (c.road_config > 0) {
                    ++result[c.road_config];
                } else if (c.road_config == -2) {
                    ++result[0];
                }
            }
            return result;
        }

        private int[] reduce(int[] a, int[] b) {
            int[] c = new int[13];
            for (int i = 0; i < 13; i++) {
                c[i] = a[i] + b[i];
            }
            return c;
        }
    }
}
