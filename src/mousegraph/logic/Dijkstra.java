package mousegraph.logic;

import mousegraph.model.Node;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class Dijkstra {
    public static LinkedList<String> solve(Map<String, LinkedList<Node>> m, String from, String to) {

        LinkedList<String> vertices = new LinkedList<>();

        for (Map.Entry<String, LinkedList<Node>> it : m.entrySet()) {
            vertices.add(it.getKey());
        }

        Map<String, Integer> vis = new TreeMap<>();
        Map<String, Double> cost = new TreeMap<>();
        Map<String, String> parent = new TreeMap<>();
        cost.put(from, (double) 0);
        int n = m.size();
        for (int i = 0; i < n - 1; i++) {
            String chosen = "";
            double minn = 100000000;
            for (String s : vertices) {
                if (vis.get(s) == null) {
                    if (cost.get(s) == null) continue;
                    if (cost.get(s) < minn) {
                        minn = cost.get(s);
                        chosen = s;
                    }
                }
            }
            if (chosen.equals("")) continue;
            vis.put(chosen, 1);
            LinkedList<Node> connected;
            connected = m.get(chosen);
            for (Node x : connected) {
                double newCost = minn + x.getWt();
                if (cost.get(x.getName()) == null) {
                    cost.put(x.getName(), newCost);
                    parent.put(x.getName(), chosen);
                } else {
                    double oldCost = cost.get(x.getName());
                    if (oldCost > newCost) {
                        cost.put(x.getName(), newCost);
                        parent.put(x.getName(), chosen);
                    }
                }
            }
        }
        /////
        Stack<String> ans = new Stack<>();
        if (cost.get(to) == null) {
            return new LinkedList<>();
        } else {
            LinkedList<String> ll = new LinkedList<>();
            ans.push(to);
            while (parent.get(to) != null) {
                ans.push(parent.get(to));
                to = parent.get(to);
            }
            while (!ans.empty()) {
                String x = ans.pop();
                ll.add(x);
            }
            return ll;
        }
    }
}
