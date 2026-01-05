package financeapp;

import javax.swing.*;

public class FinanceAppWindow extends JFrame {
        public FinanceAppWindow() {
            setTitle("Finance App - Aplikacija za upravljanje ličnim finansijama");
            setSize(900,550);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            FinanceTrackerForm form = new FinanceTrackerForm();
            setContentPane(form.getMainPanel());
            setVisible(true);
        }
}
