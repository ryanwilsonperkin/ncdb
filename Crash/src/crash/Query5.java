/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crash;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author ryan
 */
public class Query5 {
    final List<Collision> collisions;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private int[] results;
    
    Query5(List<Collision> collisions, ForkJoinPool thread_pool, int threshold) {
        this.collisions = collisions;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(collisions, 0, collisions.size(), threshold));
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
        final List<Collision> collisions;
        final int start;
        final int end;
        final int threshold;

        Computation(List<Collision> collisions, int start, int end, int threshold) {
            this.collisions = collisions;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected int[] compute() {
            if ((end - start) <= threshold) {
                return calculate();
            } else {
                Computation c1 = new Computation(collisions, start, (start + end) / 2, threshold);
                c1.fork();
                Computation c2 = new Computation(collisions, (start + end) / 2, end, threshold);
                return reduce(c2.invoke(), c1.join());
            }
        }

        private int[] calculate() {
            int[] result = new int[13];
            for (int i = start; i < end; i++) {
                Collision c = collisions.get(i);
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
