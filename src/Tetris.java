import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris extends Main implements ActionListener, KeyListener
{
    JFrame frame;
    JPanel board;
    JPanel port;
    JLabel[][] cells;
    JLabel[][] port_cells;
    int count;
    int piece_type;
    int[][] piece_bounds = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    int[] piece_pos = {5,0};
    int[][] blocks;//keeps tracks of pieces that are placed
    boolean transfer;//True if piece moved from port to board
    Timer timer;
    Timer falling;
    Color piece_color;

    public void Initialize()
    {
        timer = new Timer(1,this);
        falling = new Timer(300, this);
        frame = new MyFrame();
        board = new Board();
        port = new JPanel();
        cells = new JLabel[10][16];
        port_cells = new JLabel[10][6];
        blocks = new int[10][16];

        port.setLayout(new GridLayout(6,10));
        port.setBounds(0,0,586,200);
        port.setBackground(Color.red);

        for(int i=0; i<16; i++)
        {
            for(int j=0; j<10; j++)
            {
                blocks[j][i] = 0;
                cells[j][i] = new JLabel();
                cells[j][i].setBorder(new LineBorder(Color.BLACK, 2));
                cells[j][i].setBackground(Color.GRAY);
                cells[j][i].setOpaque(true);
                board.add(cells[j][i]);
                if(i<6)
                {
                    port_cells[j][i] = new JLabel();
                    port_cells[j][i].setBorder(new LineBorder(Color.BLACK, 2));
                    port_cells[j][i].setBackground(Color.GRAY);
                    port_cells[j][i].setOpaque(true);
                    port.add(port_cells[j][i]);
                }
            }
        }

        frame.add(port);
        frame.add(board);
        frame.addKeyListener(this);
        frame.setVisible(true);

        timer.start();
        start();
    }

    public void start()
    {
        new_piece();
    }

    public void new_piece()
    {
        transfer = false;
        piece_pos[0] = 3;
        piece_pos[1] = 0;
        piece_type = (int) (Math.random() * 6);
        switch((int)(Math.random()*4))
        {
            case 0:
                piece_color = Color.red;
                break;
            case 1:
                piece_color = Color.blue;
                break;
            case 2:
                piece_color = Color.green;
                break;
            default:
                piece_color = Color.yellow;
        }
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
                piece_bounds[i][j] = 0;
        }
        if(piece_type == 0)
        {
            piece_bounds[0][0] = 1;
            piece_bounds[1][1] = 1;
            piece_bounds[0][1] = 1;
            piece_bounds[1][0] = 1;
        }
        else if(piece_type == 1)
        {
            piece_bounds[0][0] = 1;
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
            piece_bounds[3][0] = 1;
        }
        else if(piece_type == 2)
        {
            piece_bounds[0][0] = 1;
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
            piece_bounds[1][1] = 1;
        }
        else if(piece_type == 3)
        {
            piece_bounds[0][0] = 1;
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
            piece_bounds[0][1] = 1;
        }
        else if(piece_type == 4)
        {
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
            piece_bounds[0][1] = 1;
            piece_bounds[1][1] = 1;
        }
        else if(piece_type == 5)
        {
            piece_bounds[1][0] = 1;
            piece_bounds[0][0] = 1;
            piece_bounds[2][1] = 1;
            piece_bounds[1][1] = 1;
        }
        falling.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < 4; i++) //Always painting the current piece
        {
            for(int j = 0; j < 4; j++)
            {
                if(piece_bounds[i][j]==1 && piece_pos[1]+j > 6 && !transfer)
                {
                    piece_pos[1]=0;
                    transfer = true;
                }
                if(piece_bounds[i][j]==1 && piece_pos[0]+i<10)
                {
                    if(transfer)
                        cells[piece_pos[0]+i][piece_pos[1]+j].setBackground(piece_color);
                    else if(piece_pos[1]+j <6)
                        port_cells[piece_pos[0]+i][piece_pos[1]+j].setBackground(piece_color);
                }
            }
        }

        if(transfer)//Always painting the empty cells
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    if (piece_bounds[i][j] == 0 && piece_pos[0] + i<10 && piece_pos[1] + j<16)
                    {
                        if (blocks[piece_pos[0] + i][piece_pos[1] + j] == 0)
                            cells[piece_pos[0] + i][piece_pos[1] + j].setBackground(Color.gray);
                    }
                }
            }
            for (int i =0; i < 10; i++)
            {
                for (int j = 0; j< 16; j++)
                {
                    if((i<piece_pos[0] || i>piece_pos[0]+4) && (j<piece_pos[1] || j>piece_pos[1]+4))
                    {
                        if(blocks[i][j] == 0)
                            cells[i][j].setBackground(Color.gray);
                    }
                    if(j<6)
                        port_cells[i][j].setBackground(Color.gray);
                }
            }
        }
        else
        {
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    if (piece_bounds[i][j] == 0 && piece_pos[0]+i<10 && piece_pos[1]+j<6 && piece_pos[0]+i>=0)
                        port_cells[piece_pos[0] + i][piece_pos[1] + j].setBackground(Color.gray);
                }
            }
            for (int i =0; i < 10; i++)
            {
                for (int j = 0; j < 6; j++)
                {
                    if ((i<piece_pos[0] || i>piece_pos[0]+4) && (j<piece_pos[1] || j>piece_pos[1]+4))
                        port_cells[i][j].setBackground(Color.gray);
                }
            }
        }

        if(e.getSource() == falling)
        {
            outer:
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    if (piece_bounds[i][j] == 1 && piece_pos[0]+i<10)
                    {
                        if (transfer)
                            cells[piece_pos[0]+i][piece_pos[1]+j].setBackground(Color.gray);
                        else if (piece_pos[1]+j < 6)
                            port_cells[piece_pos[0]+i][piece_pos[1]+j].setBackground(Color.gray);
                        if (piece_pos[1]+j+1 > 15)//Stops the piece when it reaches the bottom
                        {
                            falling.stop();
                            board_update();
                            new_piece();
                            break outer;
                        }
                        else if (blocks[piece_pos[0]+i][piece_pos[1]+j+1] == 1 && transfer)//Stops the piece when collision is detected
                        {
                            falling.stop();
                            board_update();
                            new_piece();
                            break outer;
                        }
                    }
                }
            }
             piece_pos[1]++;
        }

        for(int i=0;i<16;i++)//Checks if a line needs to be cleared
        {
            count =0;
            for(int j=0;j<10;j++)
            {
                if(blocks[j][i]==1)
                    count++;
            }
            if(count==10)
                line_clear(i);
        }

        for(int i=0;i<10;i++)//Game over Condition
        {
            if(blocks[i][0]==1)
            {
                System.out.println("GAME OVER!!!");
                timer.stop();
                falling.stop();
            }
        }
    }


    public void rotate()
    {
        if(piece_type == 1)
            line_rotate();
        else if(piece_type == 2)
            t_rotate();
        else if(piece_type == 3)
            l_rotate();
        else if(piece_type == 4)
            s_rotate();
        else
            s_rotate2();
    }

    public void line_rotate()
    {
        if(piece_bounds[1][0] == 1)
        {
            if(transfer)
            {
                if(blocks[piece_pos[0]+1][piece_pos[1]+1]==1 || blocks[piece_pos[0]+1][piece_pos[1]+2]==1 || blocks[piece_pos[0]+1][piece_pos[1]+3]==1)
                    return;
                if(piece_pos[1]+3>15)
                    return;
            }
            piece_pos[0]++;
            piece_bounds[1][0] = 0;
            piece_bounds[2][0] = 0;
            piece_bounds[3][0] = 0;
            piece_bounds[0][1] = 1;
            piece_bounds[0][2] = 1;
            piece_bounds[0][3] = 1;
        }
        else
        {
            if(piece_pos[0]+2>9)
                return;
            if(piece_pos[0]-1<0)
                return;
            if(transfer)
            {
                if(blocks[piece_pos[0]-1][piece_pos[1]] == 1 || blocks[piece_pos[0]+1][piece_pos[1]] == 1 || blocks[piece_pos[0]+2][piece_pos[1]] == 1)
                    return;
            }
            piece_pos[0]--;
            piece_bounds[0][1] = 0;
            piece_bounds[0][2] = 0;
            piece_bounds[0][3] = 0;
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
            piece_bounds[3][0] = 1;
        }
    }
    public void t_rotate()
    {
        if(piece_bounds[0][1]==0 && piece_bounds[2][1]==0)
        {
            if(blocks[piece_pos[0]][piece_pos[1]+1] == 1 || blocks[piece_pos[0]+1][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[1]+2>15 || piece_pos[0]+1 > 9)
                return;
            piece_bounds[0][1] = 1;
            piece_bounds[1][2] = 1;
            piece_bounds[0][0] = 0;
            piece_bounds[2][0] = 0;

        }
        else if(piece_bounds[0][0]==0 && piece_bounds[0][2]==0 && piece_bounds[1][2]==1)
        {
            if(blocks[piece_pos[0]+2][piece_pos[1]+1] == 1)
                return;
            if(piece_pos[1]+1 > 15)
                return;
            piece_bounds[1][2] = 0;
            piece_bounds[2][1] = 1;
        }
        else if(piece_bounds[0][0]==0 && piece_bounds[2][0]==0 && piece_bounds[2][1]==1)
        {
            if(blocks[piece_pos[0]][piece_pos[1]] == 1 || blocks[piece_pos[0]][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[1]+2 > 15)
                return;
            piece_bounds[1][0] = 0;
            piece_bounds[2][1] = 0;
            piece_bounds[0][0] = 1;
            piece_bounds[0][2] = 1;
        }
        else
        {
            if(blocks[piece_pos[0]+1][piece_pos[1]] == 1 || blocks[piece_pos[0]+2][piece_pos[1]] == 1)
                return;
            if(piece_pos[0]+2>9)
                return;
            piece_bounds[0][1] = 0;
            piece_bounds[0][2] = 0;
            piece_bounds[1][0] = 1;
            piece_bounds[2][0] = 1;
        }
    }
    public void l_rotate()
    {
        if(piece_bounds[2][0]==1 && piece_bounds[2][1]==0)
        {
            if(blocks[piece_pos[0]+1][piece_pos[1]+1] == 1 || blocks[piece_pos[0]+1][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[0]+1>9 || piece_pos[1]+2>15)
                return;
            piece_bounds[2][0]=0;
            piece_bounds[0][1]=0;
            piece_bounds[1][1]=1;
            piece_bounds[1][2]=1;
        }
        else if(piece_bounds[0][0]==1 && piece_bounds[1][2]==1 && piece_bounds[1][1]==1)
        {
            if(blocks[piece_pos[0]+2][piece_pos[1]+1] == 1 || blocks[piece_pos[0]+2][piece_pos[1]] == 1 || blocks[piece_pos[0]][piece_pos[1]+1] == 1)
                return;
            if(piece_pos[0]+2>9 || piece_pos[1]+1>15)
                return;
            piece_bounds[0][0]=0;
            piece_bounds[1][0]=0;
            piece_bounds[1][2]=0;
            piece_bounds[2][0]=1;
            piece_bounds[2][1]=1;
            piece_bounds[0][1]=1;
        }
        else if(piece_bounds[2][0]==1 && piece_bounds[0][1]==1)
        {
            if(blocks[piece_pos[0]][piece_pos[1]] == 1 || blocks[piece_pos[0]+1][piece_pos[1]+2] == 1 || blocks[piece_pos[0]][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[0]+1>9 || piece_pos[1]+2>15)
                return;
            piece_bounds[2][0]=0;
            piece_bounds[1][1]=0;
            piece_bounds[2][1]=0;
            piece_bounds[0][0]=1;
            piece_bounds[0][2]=1;
            piece_bounds[1][2]=1;
        }
        else
        {
            if(blocks[piece_pos[0]+1][piece_pos[1]] == 1 || blocks[piece_pos[0]+2][piece_pos[1]] == 1)
                return;
            if(piece_pos[0]+2>9)
                return;
            piece_bounds[0][2]=0;
            piece_bounds[1][2]=0;
            piece_bounds[1][0]=1;
            piece_bounds[2][0]=1;
        }
    }
    public void s_rotate()
    {
        if(piece_bounds[2][0]==1 && piece_bounds[0][1]==1)
        {
            if(blocks[piece_pos[0]][piece_pos[1]] == 1 || blocks[piece_pos[0]+1][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[0]+1>9 || piece_pos[1]+2>15)
                return;
            piece_bounds[2][0]=0;
            piece_bounds[1][0]=0;
            piece_bounds[0][0]=1;
            piece_bounds[1][2]=1;
        }
        else
        {
            if(blocks[piece_pos[0]+2][piece_pos[1]] == 1 || blocks[piece_pos[0]+1][piece_pos[1]] == 1)
                return;
            if(piece_pos[0]+2>9 || piece_pos[1]>15)
                return;
            piece_bounds[2][0]=1;
            piece_bounds[1][0]=1;
            piece_bounds[0][0]=0;
            piece_bounds[1][2]=0;
        }
    }
    public void s_rotate2()
    {
        if(piece_bounds[2][1]==1)
        {
            if(blocks[piece_pos[0]][piece_pos[1]+1] == 1 || blocks[piece_pos[0]][piece_pos[1]+2] == 1)
                return;
            if(piece_pos[0]>9 || piece_pos[1]+2>15)
                return;
            piece_bounds[0][0]=0;
            piece_bounds[2][1]=0;
            piece_bounds[0][1]=1;
            piece_bounds[0][2]=1;
        }
        else
        {
            if(blocks[piece_pos[0]][piece_pos[1]] == 1 || blocks[piece_pos[0]+2][piece_pos[1]] == 1)
                return;
            if(piece_pos[0]+2>9 || piece_pos[1]>15)
                return;
            piece_bounds[0][0]=1;
            piece_bounds[2][1]=1;
            piece_bounds[0][1]=0;
            piece_bounds[0][2]=0;
        }
    }

    public void line_clear(int line)
    {
        for(int i=0;i<10;i++)
        {
            blocks[i][line]=0;
            cells[i][line].setBackground(Color.gray);
        }
        for(int i=line;i>0;i--)
        {
            for(int j=0;j<10;j++)
            {
                if(blocks[j][i-1]==1)
                {
                    cells[j][i].setBackground(cells[j][i-1].getBackground());
                    cells[j][i-1].setBackground(Color.gray);
                    blocks[j][i]=1;
                    blocks[j][i-1]=0;
                }
            }
        }
    }

    public void board_update()//update positions of pieces when new piece is placed
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                if(piece_bounds[i][j] == 1)
                {
                    cells[piece_pos[0]+i][piece_pos[1]+j].setBackground(piece_color);
                    blocks[piece_pos[0] + i][piece_pos[1] + j] = 1;
                }
            }
        }
    }
     @Override
    public void keyPressed(KeyEvent e)
    {
        limit:
        if(e.getKeyChar() == 'd')//Move falling piece right
        {
            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    if(piece_bounds[i][j]==1)
                    {
                        if(piece_pos[0]+i+1>9)
                            break limit;
                        if (transfer && blocks[piece_pos[0] + i + 1][piece_pos[1] + j] == 1)
                            break limit;
                    }
                }
            }
            for(int i=0;i<4;i++)
            {
                if(piece_bounds[0][i]==1)
                {
                    if(transfer)
                        cells[piece_pos[0]][piece_pos[1]+i].setBackground(Color.gray);
                    else
                        port_cells[piece_pos[0]][piece_pos[1]+i].setBackground(Color.gray);
                }
            }
            piece_pos[0]++;
        }

        limit:
        if(e.getKeyChar() == 'a')//Move falling piece left
        {
            for(int i=0;i<4;i++)
            {
                for(int j=0;j<4;j++)
                {
                    if(piece_bounds[i][j] == 1)
                    {
                        if(piece_pos[0]-1<0)
                            break limit;
                        if(transfer && blocks[piece_pos[0]+i-1][piece_pos[1]+j] == 1)
                            break limit;
                    }
                }
            }
            for(int i=0;i<4;i++)
            {
                if(piece_bounds[3][i]==1)
                {
                    if(transfer)
                        cells[piece_pos[0]+3][piece_pos[1]+i].setBackground(Color.gray);
                    else
                        port_cells[piece_pos[0]+3][piece_pos[1]+i].setBackground(Color.gray);
                }
            }
            piece_pos[0]--;
        }

        if(e.getKeyChar() == ' ')
            rotate();
    }
    @Override
    public void keyTyped(KeyEvent e)
    {

    }
    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
