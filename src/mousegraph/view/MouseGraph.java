package mousegraph.view;

import mousegraph.logic.Check;
import mousegraph.logic.AStar;
import mousegraph.model.Node;
import mousegraph.model.Point;
import mousegraph.model.Shapes;
import mousegraph.utils.Strings;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.*;


public class MouseGraph extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
    int mode = 1;
    Map<String, mousegraph.model.Point> vertex;
    Map<String, LinkedList<Node>> graph;
    LinkedList<Node> edges;
    LinkedList<LinkedList<String>> path;

    Timer timer = new Timer(10, this);

    JButton mode1, mode2, mode3, mode4, aStar, stop, exportGraph;
    JLabel label1, label2, label3, label4;

    mousegraph.model.Point press = null, release = null, curr = null;
    String press_name = null, release_name = null;

    LinkedList<String> obj_from = new LinkedList<>(), obj_to = new LinkedList<>();
    LinkedList<Double> obj_x = new LinkedList<>(), obj_y = new LinkedList<>();
    LinkedList<Double> obj_currx = new LinkedList<>(), obj_curry = new LinkedList<>();
    LinkedList<Integer> obj_mode = new LinkedList<>(), idx = new LinkedList<>();

    public MouseGraph() {
        vertex = new TreeMap<>();
        graph = new TreeMap<>();
        edges = new LinkedList<>();
        path = new LinkedList<>();

        mode1 = new JButton(Strings.AddDeleteVertex);
        mode1.setBounds(15, 450, 200, 20);
        mode2 = new JButton(Strings.AddEdge);
        mode2.setBounds(15, 480, 200, 20);
        mode3 = new JButton(Strings.MoveEdge);
        mode3.setBounds(15, 510, 200, 20);
        mode4 = new JButton(Strings.EditEdge);
        mode4.setBounds(15, 540, 200, 20);
        aStar = new JButton(Strings.StartAnimation);
        aStar.setBounds(400, 450, 200, 20);
        stop = new JButton(Strings.StopAnimation);
        stop.setBounds(400, 480, 200, 20);
        add(stop);
        exportGraph = new JButton(Strings.ExportGraph);
        exportGraph.setBounds(400, 510, 200, 20);
        add(exportGraph);


        label1 = new JLabel(Strings.ON);
        label1.setForeground(Color.GREEN);
        label1.setBounds(250, 450, 150, 20);
        add(label1);
        label2 = new JLabel(Strings.OFF);
        label2.setForeground(Color.RED);
        label2.setBounds(250, 480, 150, 20);
        add(label2);
        label3 = new JLabel(Strings.OFF);
        label3.setForeground(Color.RED);
        label3.setBounds(250, 510, 150, 20);
        add(label3);
        label4 = new JLabel(Strings.OFF);
        label4.setForeground(Color.RED);
        label4.setBounds(250, 540, 150, 20);
        add(label4);

        JLabel mode = new JLabel(Strings.Regime);
        mode.setBounds(90, 425, 150, 25);
        mode.setFont(new Font("Serif", Font.PLAIN, 20));
        add(mode);
        JLabel status = new JLabel(Strings.Status);
        status.setBounds(240, 425, 150, 25);
        status.setFont(new Font("Serif", Font.PLAIN, 20));
        add(status);
        JLabel features = new JLabel(Strings.Features);
        features.setBounds(440, 425, 150, 25);
        features.setFont(new Font("Serif", Font.PLAIN, 20));
        add(features);

        exportGraph.addActionListener(ee -> {
            Comparator<Node> comp = (a, b) -> Integer.compare(a.getName().compareTo(b.getName()), 0);
            String path = JOptionPane.showInputDialog(Strings.EnterFilePath);
            PrintStream o = null;
            try {
                o = new PrintStream(path);
            } catch (Exception e) {
                System.out.println(" Error \n");
            }
            System.setOut(o);

            int size = 0;

            for (Map.Entry<String, mousegraph.model.Point> ignored : vertex.entrySet()) {
                size++;
            }
            System.out.println(size);
            for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
                System.out.println(it.getKey() + " " + it.getValue().getX() + " " + it.getValue().getY());
            }
            size = 0;
            for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
                LinkedList<Node> l = it.getValue();
                l.sort(comp);
                for (Node ignored : l) {
                    size++;
                }
            }
            System.out.println(size);
            for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
                LinkedList<Node> l = it.getValue();
                l.sort(comp);
                for (Node x : l) {
                    System.out.println(it.getKey() + " " + x.getName() + " " + x.getWt());
                }
            }

        });

        mode1.addActionListener(ee -> {
            obj_mode.clear();
            MouseGraph.this.mode = 1;
            timer.stop();
            label1.setText(Strings.ON);
            label1.setForeground(Color.GREEN);
            label2.setText(Strings.OFF);
            label2.setForeground(Color.RED);
            label3.setText(Strings.OFF);
            label3.setForeground(Color.RED);
            label4.setText(Strings.OFF);
            label4.setForeground(Color.RED);
        });
        mode2.addActionListener(ee -> {
            obj_mode.clear();
            MouseGraph.this.mode = 2;
            timer.stop();
            label1.setText(Strings.OFF);
            label1.setForeground(Color.RED);
            label2.setText(Strings.ON);
            label2.setForeground(Color.GREEN);
            label3.setText(Strings.OFF);
            label3.setForeground(Color.RED);
            label4.setText(Strings.OFF);
            label4.setForeground(Color.RED);
        });
        mode3.addActionListener(ee -> {
            obj_mode.clear();
            MouseGraph.this.mode = 3;
            timer.stop();
            label1.setText(Strings.OFF);
            label1.setForeground(Color.RED);
            label2.setText(Strings.OFF);
            label2.setForeground(Color.RED);
            label3.setText(Strings.ON);
            label3.setForeground(Color.GREEN);
            label4.setText(Strings.OFF);
            label4.setForeground(Color.RED);
        });
        mode4.addActionListener(ee -> {
            obj_mode.clear();
            MouseGraph.this.mode = 4;
            timer.stop();
            label1.setText(Strings.OFF);
            label1.setForeground(Color.RED);
            label2.setText(Strings.OFF);
            label2.setForeground(Color.RED);
            label3.setText(Strings.OFF);
            label3.setForeground(Color.RED);
            label4.setText(Strings.ON);
            label4.setForeground(Color.GREEN);
        });


        aStar.addActionListener(ee -> {
            MouseGraph.this.mode = -1;

            label1.setText(Strings.OFF);
            label1.setForeground(Color.RED);
            label2.setText(Strings.OFF);
            label2.setForeground(Color.RED);
            label3.setText(Strings.OFF);
            label3.setForeground(Color.RED);
            label4.setText(Strings.OFF);
            label4.setForeground(Color.RED);

            JFrame f1 = new JFrame(Strings.Input);

            JPanel p1 = new JPanel();
            p1.setLayout(new GridLayout(3, 2));

            JLabel l1 = new JLabel(Strings.StartVertex);
            JLabel l2 = new JLabel(Strings.DestVertex);
            JTextField t1 = new JTextField();
            JTextField t2 = new JTextField();
            JButton b1 = new JButton(Strings.OK);
            JButton b2 = new JButton(Strings.Cancel);
            p1.add(l1);
            p1.add(t1);
            p1.add(l2);
            p1.add(t2);
            p1.add(b1);
            p1.add(b2);
            f1.add(p1);
            f1.setResizable(false);
            f1.setSize(450, 100);
            f1.setVisible(true);
            f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            b2.addActionListener(ee12 -> f1.dispose());

            b1.addActionListener(ee1 -> {
                if ((t1.getText() == null) || (t2.getText() == null)) {
                    mousegraph.model.Error.display(Strings.EnterAllFields);
                    f1.dispose();
                } else if (!vertex.containsKey(t1.getText()) || (!vertex.containsKey(t2.getText()))) {
                    mousegraph.model.Error.display(Strings.EnterValidVertices);
                    f1.dispose();
                } else {
                    String dest = t2.getText();
                    path.add(AStar.solve(graph, t1.getText(), dest, vertex.get(dest)));
                    if (path.getLast().size() == 0) {
                        mousegraph.model.Error.display(Strings.NoPathExists);
                        f1.dispose();
                    } else {
                        f1.dispose();
                        idx.add(0);
                        obj_from.add(path.getLast().get(0));
                        obj_to.add(path.getLast().get((1) % path.getLast().size()));

                        obj_currx.add((double) vertex.get(path.getLast().get(0)).getX());
                        obj_curry.add((double) vertex.get(path.getLast().get(0)).getY());
                        obj_x.add(((double) vertex.get(obj_to.getLast()).getX() - (double) vertex.get(obj_from.getLast()).getX()) / (double) 100);
                        obj_y.add(((double) vertex.get(obj_to.getLast()).getY() - (double) vertex.get(obj_from.getLast()).getY()) / (double) 100);

                        JFrame fa = new JFrame();
                        JPanel pa = new JPanel();
                        JLabel la = new JLabel(Strings.ChooseAShape);
                        pa.setLayout(new GridLayout(7, 1));
                        JButton b1a = new JButton(Strings.Circle);
                        JButton b2a = new JButton(Strings.Plus);
                        JButton b3a = new JButton(Strings.Cross);
                        JButton b4a = new JButton(Strings.Triangle);
                        JButton b5a = new JButton(Strings.Square);
                        JButton b6a = new JButton(Strings.Cancel);
                        fa.add(pa);
                        pa.add(la);
                        pa.add(b1a);
                        pa.add(b2a);
                        pa.add(b3a);
                        pa.add(b4a);
                        pa.add(b5a);
                        pa.add(b6a);


                        fa.setResizable(false);
                        fa.setSize(80, 250);
                        fa.setVisible(true);
                        fa.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        b1a.addActionListener(e -> {
                            obj_mode.add(1);
                            fa.dispose();
                            timer.start();
                        });
                        b2a.addActionListener(e -> {
                            obj_mode.add(2);
                            fa.dispose();
                            timer.start();
                        });
                        b3a.addActionListener(e -> {
                            obj_mode.add(3);
                            fa.dispose();
                            timer.start();
                        });
                        b4a.addActionListener(e -> {
                            obj_mode.add(4);
                            fa.dispose();
                            timer.start();
                        });
                        b5a.addActionListener(e -> {
                            obj_mode.add(5);
                            fa.dispose();
                            timer.start();
                        });
                        b6a.addActionListener(e -> {
                            obj_mode.add(-1);
                            fa.dispose();
                        });
                    }
                }
            });

        });

        stop.addActionListener(ee -> {
            obj_from.clear();
            obj_to.clear();
            obj_x.clear();
            obj_y.clear();
            obj_currx.clear();
            obj_curry.clear();
            obj_mode.clear();
            path.clear();
            timer.stop();
            MouseGraph.this.mode = -1;
            repaint();
        });


        add(aStar);
        add(mode1);
        add(mode2);
        add(mode3);
        add(mode4);

        setSize(900, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }


    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < obj_mode.size(); i++) {

            if (obj_mode.get(i) == 1) {
                g.setColor(Color.BLACK);
                g.fillOval(obj_currx.get(i).intValue(), obj_curry.get(i).intValue(), 15, 15);

            }

            if (obj_mode.get(i) == 2) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.GREEN);
                g2.fill(Shapes.plus(obj_currx.get(i).intValue(), obj_curry.get(i).intValue()));

            }

            if (obj_mode.get(i) == 4) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.PINK);
                g2.fill(Shapes.triangle(obj_currx.get(i).intValue(), obj_curry.get(i).intValue()));

            }

            if (obj_mode.get(i) == 3) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(Color.YELLOW);
                g2.fill(Shapes.cross(obj_currx.get(i).intValue(), obj_curry.get(i).intValue()));
            }

            if (obj_mode.get(i) == 5) {
                g.setColor(Color.ORANGE);
                g.fillRect(obj_currx.get(i).intValue() - 7, obj_curry.get(i).intValue() - 7, 14, 14);
            }
        }

        if (mode == 2) {
            g.setColor(Color.BLUE);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3.0f));
            g2.setPaint(Color.BLUE);
            if (press != null) g2.drawLine(press.getX(), press.getY(), curr.getX(), curr.getY());
        }

        if (mode == 3) {
            if (press_name != null) vertex.put(press_name, curr);
        }

        g.setColor(Color.BLUE);
        for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3.0f));

            LinkedList<Node> l = it.getValue();
            for (Node x : l) {
                g2.setPaint(Color.BLUE);
                g2.drawLine(vertex.get(it.getKey()).getX() + 7, vertex.get(it.getKey()).getY() + 7, vertex.get(x.getName()).getX() + 7, vertex.get(x.getName()).getY() + 7);
                g2.setColor(Color.BLACK);
                g2.drawString(Double.toString(x.getWt()), (float) (vertex.get(it.getKey()).getX() + 7 + vertex.get(x.getName()).getX() + 7) / 2f, (float) (vertex.get(it.getKey()).getY() + 7 + vertex.get(x.getName()).getY() + 7) / 2f);
            }
        }


        for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
            g.setColor(Color.RED);
            int x = it.getValue().getX();
            int y = it.getValue().getY();
            g.fillOval(x, y, 15, 15);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.drawString(it.getKey(), x, y);
        }

        if (mode == 3) {
            if (press_name != null) vertex.remove(press_name);
        }


    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (mode == 1) {
            press = null;
            release = null;
            curr = null;
            press_name = null;
            release_name = null;
            int flag = 1;
            int X = e.getXOnScreen(), Y = e.getYOnScreen();
            String v = "";
            for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
                int x = it.getValue().getX();
                int y = it.getValue().getY();
                if ((x <= 15 + X && x >= X - 15) && (y <= 15 + Y && y >= Y - 15)) {
                    v = it.getKey();
                    flag = 0;
                }
            }
            final String v1 = v;
            if (flag == 1) {
                Graphics g = getGraphics();
                g.setColor(Color.RED);
                String name = JOptionPane.showInputDialog(Strings.EnterVertexName);
                if (name != null) {
                    if (vertex.containsKey(name)) mousegraph.model.Error.display(Strings.NameAlreadyUsed);
                    else {
                        g.fillOval(e.getXOnScreen() - 7, e.getYOnScreen() - 7, 15, 15);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setColor(Color.BLACK);
                        g2d.drawString(name, e.getXOnScreen() - 7, e.getYOnScreen() - 7);
                        vertex.put(name, new mousegraph.model.Point(e.getXOnScreen() - 7, e.getYOnScreen() - 7));
                    }
                }
            } else {
                JFrame f = new JFrame(Strings.Delete);
                JPanel p = new JPanel();
                p.setLayout(new GridLayout(3, 1));
                JLabel l = new JLabel(Strings.DeleteVertexQuestion);
                JButton b1 = new JButton(Strings.Yes);
                JButton b2 = new JButton(Strings.No);
                p.add(l);
                p.add(b1);
                p.add(b2);
                f.setSize(100, 100);
                f.add(p);
                f.setVisible(true);
                f.setResizable(false);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                b1.addActionListener(ee -> {
                    vertex.remove(v1);
                    graph.remove(v1);
                    Map<String, LinkedList<Node>> graph1 = new TreeMap<>();
                    for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
                        graph1.put(it.getKey(), new LinkedList<>());
                        for (Node x : it.getValue()) {
                            if (!x.getName().equals(v1)) graph1.get(it.getKey()).add(x);
                        }
                        graph = graph1;
                    }
                    repaint();
                    f.dispose();
                });
                b2.addActionListener(ee -> f.dispose());
            }
        }

        if (mode == 2) {
            press = null;
            release = null;
            curr = null;
            press_name = null;
            release_name = null;
            int X = e.getXOnScreen(), Y = e.getYOnScreen();
            for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
                int x = it.getValue().getX();
                int y = it.getValue().getY();
                if ((x <= 15 + X && x >= X - 15) && (y <= 15 + Y && y >= Y - 15)) {
                    press_name = it.getKey();
                    press = new mousegraph.model.Point(x + 7, y + 7);
                }
            }
        }
        if (mode == 3) {
            press = null;
            release = null;
            curr = null;
            press_name = null;
            release_name = null;
            int X = e.getXOnScreen(), Y = e.getYOnScreen();
            for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
                int x = it.getValue().getX();
                int y = it.getValue().getY();
                if ((x <= 15 + X && x >= X - 15) && (y <= 15 + Y && y >= Y - 15)) {
                    press_name = it.getKey();
                    press = new mousegraph.model.Point(x, y);
                    break;
                }
            }
            if (press_name != null) {
                if (!edges.isEmpty()) edges.clear();
                edges = graph.get(press_name);
                graph.remove(press_name);
                vertex.remove(press_name);
                curr = press;
            }
        }

        if (mode == 4) {
            mousegraph.model.Point B = new mousegraph.model.Point(e.getX(), e.getY());
            String from = null, to = null;
            for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
                for (Node x : it.getValue()) {
                    if (Check.inLine(vertex.get(it.getKey()), B, vertex.get(x.getName()))) {
                        from = it.getKey();
                        to = x.getName();
                    }
                }
            }
            if (from != null) {
                final String fromm = from, too = to;
                JFrame fx = new JFrame(Strings.ChangeDetails);
                JPanel px = new JPanel();
                px.setLayout(new GridLayout(3, 1));
                JButton edit = new JButton(Strings.EditEdge);
                JButton delete = new JButton(Strings.DeleteEdge);
                JButton cancel = new JButton(Strings.Cancel);
                px.add(edit);
                px.add(delete);
                px.add(cancel);
                fx.add(px);
                fx.setSize(320, 120);
                fx.setVisible(true);
                fx.setResizable(false);
                fx.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cancel.addActionListener(ee -> fx.dispose());
                edit.addActionListener(ee -> {
                    try {
                        fx.dispose();
                        String name = JOptionPane.showInputDialog(Strings.EnterNewWeight);
                        int newWeight = Integer.parseInt(name);

                        int c = 0;
                        for (Node xx : graph.get(fromm)) {
                            if (xx.getName().equals(too)) graph.get(fromm).remove(c);
                            c++;
                        }
                        graph.get(fromm).add(new Node(too, newWeight, vertex.get(too)));
                        c = 0;
                        for (Node xx : graph.get(too)) {
                            if (xx.getName().equals(fromm)) graph.get(too).remove(c);
                            c++;
                        }
                        graph.get(too).add(new Node(fromm, newWeight, vertex.get(fromm)));
                        repaint();
                    } catch (NumberFormatException eeee) {
                        mousegraph.model.Error.display(Strings.EnterValidInteger);
                    }
                });
                delete.addActionListener(ee -> {
                    fx.dispose();

                    int c = 0;
                    for (Node xx : graph.get(fromm)) {
                        if (xx.getName().equals(too)) graph.get(fromm).remove(c);
                        c++;
                    }
                    c = 0;
                    for (Node xx : graph.get(too)) {
                        if (xx.getName().equals(fromm)) graph.get(too).remove(c);
                        c++;
                    }


                    repaint();
                });

            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (mode == 2) {
            curr = new mousegraph.model.Point(e.getX(), e.getY());
            repaint();
        }
        if (mode == 3) {
            curr = new mousegraph.model.Point(e.getX(), e.getY());
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (mode == 2) {
            release = null;
            int X = e.getXOnScreen(), Y = e.getYOnScreen();
            for (Map.Entry<String, mousegraph.model.Point> it : vertex.entrySet()) {
                int x = it.getValue().getX();
                int y = it.getValue().getY();
                if ((x <= 15 + X && x >= X - 15) && (y <= 15 + Y && y >= Y - 15)) {
                    release_name = it.getKey();
                    release = new mousegraph.model.Point(x + 7, y + 7);
                }
            }
            if (press != null && release != null && (!Objects.equals(release_name, press_name))) {
                if (!graph.containsKey(press_name)) {
                    graph.put(press_name, new LinkedList<>());
                }
                if (!graph.containsKey(release_name)) {
                    graph.put(release_name, new LinkedList<>());
                }
                int flag = 1;
                for (Node x : graph.get(press_name)) {
                    if (x.getName().equals(release_name)) {
                        mousegraph.model.Error.display(Strings.EdgeAlreadyPresent);
                        flag = 0;
                    }
                }
                if (flag == 1) {
                    String name = JOptionPane.showInputDialog(Strings.EnterWeight);
                    if (name != null) {
                        try {
                            int wt = Integer.parseInt(name);
                            graph.get(press_name).add(new Node(release_name, wt, release));
                            graph.get(release_name).add(new Node(press_name, wt, press));
                        } catch (NumberFormatException ee) {
                            mousegraph.model.Error.display(Strings.EnterValidInteger);
                        }
                    }
                }
            }
            press = null;
            repaint();
        }

        if (mode == 3) {
            vertex.put(press_name, curr);
            graph.put(press_name, new LinkedList<>());
            for (Node x : edges) {
                graph.get(press_name).add(x);
            }
            for (Map.Entry<String, LinkedList<Node>> it : graph.entrySet()) {
                LinkedList<Node> nodes = it.getValue();
                for (int i = 0; i < nodes.size(); i++) {
                    if (Objects.equals(nodes.get(i).getName(), press_name)) {
                        nodes.get(i).setPoint(new Point(e.getX(), e.getY()));
                    }
                }
                it.setValue(nodes);
            }
            press_name = null;
            repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < obj_mode.size(); i++) {
            if (obj_mode.get(i) != -1) {
                obj_currx.set(i, obj_currx.get(i) + obj_x.get(i));
                obj_curry.set(i, obj_curry.get(i) + obj_y.get(i));

                if (Math.abs(obj_currx.get(i).intValue() - vertex.get(obj_to.get(i)).getX()) < 2 && Math.abs(obj_curry.get(i).intValue() - vertex.get(obj_to.get(i)).getY()) < 2) {
                    idx.set(i, idx.get(i) + 1);
                    if (idx.get(i) == path.get(i).size() - 1) idx.set(i, 0);
                    obj_from.set(i, path.get(i).get((idx.get(i)) % path.get(i).size()));
                    obj_to.set(i, path.get(i).get((idx.get(i) + 1) % path.get(i).size()));
                    obj_currx.set(i, (double) vertex.get(obj_from.get(i)).getX());
                    obj_curry.set(i, (double) vertex.get(obj_from.get(i)).getY());
                    obj_x.set(i, ((double) vertex.get(obj_to.get(i)).getX() - (double) vertex.get(obj_from.get(i)).getX()) / (double) 100);
                    obj_y.set(i, ((double) vertex.get(obj_to.get(i)).getY() - (double) vertex.get(obj_from.get(i)).getY()) / (double) 100);
                }
            }
        }
        repaint();
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
