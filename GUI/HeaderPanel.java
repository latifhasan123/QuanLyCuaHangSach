package view;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel(){

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0,60));
        setBackground(Color.WHITE);

        JLabel title = new JLabel("NovaBook Management");
        title.setFont(new Font("Segoe UI",Font.BOLD,20));

        add(title,BorderLayout.WEST);

        JLabel user = new JLabel("Admin");
        user.setFont(new Font("Segoe UI",Font.PLAIN,14));

        add(user,BorderLayout.EAST);
    }
}