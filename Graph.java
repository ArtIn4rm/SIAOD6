package com.company;
import java.util.ArrayList;
import java.lang.Integer;
import java.util.Arrays;

class Graph {
    private ArrayList<ArrayList<Line>> graph;

    Graph(){
        graph = new ArrayList<>();
    }

    void addNode(){
        ArrayList<Line> buff = new ArrayList<>();
        graph.add(buff);
    }

    private void sort(int start){
        boolean isSorted = false;
        Line buff;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < graph.get(start).size() - 1; i++) {
                if(graph.get(start).get(i).end > graph.get(start).get(i+1).end){
                    isSorted = false;

                    buff= graph.get(start).get(i);
                    graph.get(start).set(i, graph.get(start).get(i+1));
                    graph.get(start).set(i+1, buff);
                }
            }
        }
    }

    void addConnection(int start, int end, int weight){
        if(graph.size() > start){
            boolean isEnd = false;
            for(int i = 0; i < graph.get(start).size(); i++){
                if(graph.get(start).get(i).end == end){
                    isEnd = true;
                }
            }
            if(!isEnd){
                graph.get(start).add(new Line(end, weight));
                sort(start);
            }
        }
    }

    void remove(int node){
        graph.remove(node);
        for(ArrayList<Line> i : graph) {
            for(int j = 0; j < i.size(); j++){
                if(i.get(j).end == node){
                    i.remove(j);
                    break;
                }
            }
        }
    }

    int findMin(int start, int end, boolean isOut){
        ArrayList<Integer> S = new ArrayList<>();
        S.add(start);
        int w = start;
        int[] D = new int[graph.size()];
        Arrays.fill(D, 10000);
        for(int i = 0; i < graph.get(start).size(); i++){
            D[graph.get(start).get(i).end] = graph.get(start).get(i).weight;
        }
        D[start] = 0;
        int way = 0;
        while (S.size() != graph.size()) {
            int min = 10000;
            int minIndex = start;

            for(int i = 0; i < graph.get(w).size(); i++){
                if(w == start) break;
                if(graph.get(w).get(i).end == start) continue;
                if(D[graph.get(w).get(i).end] > way + graph.get(w).get(i).weight){
                    D[graph.get(w).get(i).end] = way + graph.get(w).get(i).weight;
                }
            }

            for(int i = 0; i < D.length; i++){
                boolean is = false;
                for(int j : S){
                    if(j == i) {is = true; break;}
                }
                if(!is){
                    if(min > D[i]){
                        min = D[i];
                        minIndex = i;
                    }
                }
            }

            way = D[minIndex];
            w = minIndex;
            S.add(w);
        }

        if(isOut) System.out.println("Shortest : " + D[end]);

        return D[end];
    }

    void arrayCopy(ArrayList<Integer> list, ArrayList<Integer> end){
        for(Integer i : list){
            end.add(i);
        }
    }

    int wayWeight(int first, int last){
        for(Line i : graph.get(first)){
            if(i.end == last){
                return i.weight;
            }
        }
        return 10000;
    }

    void allPath(int start, int end){
        ArrayList<ArrayList<Integer>> all = new ArrayList<>();
        for(int i = 0; i < graph.get(start).size(); i++){
           all.add(new ArrayList<>());
           all.get(all.size() - 1).add(graph.get(start).get(i).end);
        }

        int longest = 0;
        ArrayList<Integer> longList = new ArrayList<>();

        boolean isEnd = false;
        boolean isFinalEnd = false;
        while(!isFinalEnd){
            if(isEnd) isFinalEnd = true;
            for(int i = 0; i < all.size(); i++){
                if(all.get(i).get(all.get(i).size() - 1) == end) {
                    int length = 0;
                    int previous = start;
                    System.out.print(start + " ");
                    for (int j : all.get(i)) {
                        System.out.print(j + " ");
                        length += wayWeight(previous, j);
                        previous = j;
                    }
                    if(longest < length){
                        longest = length;
                        longList.clear();
                        longList.add(start);
                        arrayCopy(all.get(i), longList);
                    }
                    System.out.print("\n");
                }

                if(findMin(all.get(i).get(all.get(i).size() - 1), end, false) == 10000)
                    all.remove(all.get(i));
                else{
                    for(int k = 0; k < graph.get(all.get(i).get(all.get(i).size() - 1)).size(); k++){
                        if(graph.get(all.get(i).get(all.get(i).size() - 1)).get(k).end != start){
                            all.add(new ArrayList<>());
                            arrayCopy(all.get(i), all.get(all.size() - 1));
                            all.get(all.size() - 1).add(graph.get(all.get(i).get(all.get(i).size() - 1)).get(k).end);
                        }
                    }

                    all.remove(all.get(i));
                }
            }

            for(ArrayList<Integer> z : all){
                if(z.get(z.size() - 1) != end){
                    isEnd = false;
                    break;
                } else {
                    isEnd = true;
                }
            }
        }

        System.out.print("Longest : ");
        for(Integer i : longList){
            System.out.print(i + " ");
        }
        System.out.print("\n");
    }

    void centre(){
        int ex[] = new int[graph.size()];
        for(int i = 0; i < ex.length; i++){
            int max = 0;
            for(int j = 0; j < ex.length; j++){
                if(j != i){
                    int buff = findMin(i, j, false);
                    if(max < buff && buff != 10000){
                        max = buff;
                    }
                }
            }
            if(max == 0){
                ex[i] = 10000;
            } else {
                ex[i] = max;
            }

        }

        int minIndex = -1;
        int min = 10000;
        for(int i = 0; i < ex.length; i++){
            //System.out.print(ex[i] + " ");
            if(ex[i] < min){
                min = ex[i];
                minIndex = i;
            }
        }

        System.out.println("Centre : " + minIndex);
    }
}
