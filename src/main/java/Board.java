import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Board extends JPanel
{
    public Board()
    {
        this.setBackground(Color.red);
        this.setLayout(new GridLayout(16,10));
        this.setMinimumSize(new Dimension(586,503));
        this.setPreferredSize(new Dimension(586,563));
        this.setVisible(true);
        this.setOpaque(true);
    }
}
