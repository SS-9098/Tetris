import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Board extends JPanel
{
    public Board()
    {
        this.setBackground(Color.red);
        this.setLayout(new GridLayout(16,10));
        this.setBounds(0,200,586,563);
        this.setVisible(true);
        this.setOpaque(true);
    }
}
