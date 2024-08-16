import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame
{
    public MyFrame()
    {
        this.setSize(600,800);
		this.getContentPane().setBackground(Color.red);
        this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Tetris");
		this.setLocationRelativeTo(null);
    }
}
