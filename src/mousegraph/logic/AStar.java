package mousegraph.logic;

import mousegraph.model.Node;
import mousegraph.model.Point;

import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class AStar {
    public static LinkedList<String> solve(Map<String, LinkedList<Node>> m, String from, String to, Point dest) {

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
            double minn = Double.MAX_VALUE;
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
            if (chosen.equals(to)) break;
            LinkedList<Node> connected;
            connected = m.get(chosen);
            for (Node x : connected) {
                double newCost = minn + x.getWt() + heuristic(x.getPoint(), dest);
                if (cost.get(x.getName()) == null) {
                    cost.put(x.getName(), newCost);
                    parent.put(x.getName(), chosen);
                } else {
                    double oldCost = cost.get(x.getName()) + heuristic(x.getPoint(), dest);
                    if (oldCost > newCost) {
                        cost.put(x.getName(), newCost);
                        parent.put(x.getName(), chosen);
                    }
                }
            }
            vis.put(chosen, 1);
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

    private static double heuristic(Point a, Point b) {
        return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
    }
}
