import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame
{
    public MyFrame()
    {
        this.setSize(810,800);
		this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Tetris");
		this.setLocationRelativeTo(null);
    }
}
