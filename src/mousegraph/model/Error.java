package mousegraph.model;

import javax.swing.*;

public class Error {
    public static void display(String s) {
        JFrame jam = new JFrame();
        JOptionPane.showMessageDialog(jam, s);
        jam.dispose();
    }
}


