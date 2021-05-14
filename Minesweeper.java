/**
 * Minesweeper
 * Author - Jay Sharma
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class Minesweeper extends Frame implements MouseListener
{
    int rows=10, columns=10, mines=15, square=40, noofmarked=0;
    int board[][];
    boolean opened[][];
    boolean marked[][];
    int mode=2;
    boolean gameover = false,won=false;
    boolean done = false;
    public static void main(String args[])
    {
        Minesweeper h = new Minesweeper();
        h.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}});
    }
    Minesweeper()
    {
        super("Minesweeper");
        setLayout(null);
        setVisible(true);
        setBounds(0,0,400,450);
        addMouseListener(this);
    }
    public void allocate()
    {
        board = new int[rows][columns];
        int allocated = 0;
        while(allocated<mines)
        {
            int x = (int)(Math.random()*rows);
            int y = (int)(Math.random()*columns);
            if(board[x][y]!=-1&&!(x==0&&y==0))
            {
                board[x][y]=-1;
                allocated++;
            }
        }
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                if(board[i][j]!=-1)
                {
                    int neighbours=0;
                    if(i!=0)
                    {
                        if(j!=0)
                        {
                            if(board[i-1][j-1]==-1)
                                neighbours++;
                        }
                        if(j!=(columns-1))
                        {
                            if(board[i-1][j+1]==-1)
                                neighbours++;
                        }
                        if(board[i-1][j]==-1)
                            neighbours++;
                    }
                    if(i!=(rows-1))
                    {
                        if(j!=0)
                        {
                            if(board[i+1][j-1]==-1)
                                neighbours++;
                        }
                        if(j!=(columns-1))
                        {
                            if(board[i+1][j+1]==-1)
                                neighbours++;
                        }
                        if(board[i+1][j]==-1)
                            neighbours++;
                    }
                    if(j!=0)
                    {
                        if(board[i][j-1]==-1)
                            neighbours++;
                    }
                    if(j!=(columns-1))
                    {
                        if(board[i][j+1]==-1)
                            neighbours++;
                    }
                    board[i][j] = neighbours;
                }
            }
        }
    }

    public void reset()
    {
        gameover=false;
        won = false;
        noofmarked = 0;
        opened = new boolean[rows][columns];
        marked = new boolean[rows][columns];
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                opened[i][j]=false;
            }
        }
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++)
            {
                marked[i][j]=false;
            }
        }
    }

    public void init()
    {
        setSize(columns*square+100,rows*square+100);
        allocate();
        reset();
        addMouseListener(this);
    }

    public void paint(Graphics g)
    {       
        g.translate(0,30);
        if(mode==1)
        {
            setSize(columns*square+100,rows*square+100+50);
            Image m = new ImageIcon("Mine.png").getImage();
            Image f = new ImageIcon("Flag.png").getImage();
            g.setColor(Color.white);
            g.fillRect(0,0,columns*square+100,rows*square+100);
            g.setColor(new Color(0,160,192));
            g.fillRect(50,50,columns*square,rows*square);
            g.setColor(new Color(0,0,200));
            for(int i=0;i<=rows;i++)
            {
                g.drawLine(50,50+i*square,50+columns*square,50+i*square);
            }
            for(int j=0;j<=columns;j++)
            {
                g.drawLine(50+j*square,50,50+j*square,50+rows*square);
            }
            g.setFont(new Font("Calibri",Font.PLAIN,14));
            g.setColor(Color.red);
            g.drawString("Marked "+noofmarked+" / "+mines+" Mines",50,rows*square+80);
            for(int i=0;i<columns;i++)
            {
                for(int j=0;j<rows;j++)
                {
                    if(opened[j][i])
                    {
                        if(square>=40)
                        g.setFont(new Font("Cambria Math",Font.BOLD,16));
                        else if(square>=30)
                        g.setFont(new Font("Cambria Math",Font.BOLD,14));
                        else if(square>=20)
                        g.setFont(new Font("Cambria Math",Font.BOLD,12));
                        else 
                        g.setFont(new Font("Cambria Math",Font.BOLD,10));
                        if(board[j][i]==-1)
                            g.drawImage(m,51+i*square,51+j*square,square-1,square-1,this);
                        else if(board[j][i]==0)
                        {
                            g.setColor(new Color(220,220,255));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                        }
                        else if(board[j][i]==1)
                        {
                            g.setColor(new Color(60,60,170));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==2)
                        {
                            g.setColor(new Color(85,85,255));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==3)
                        {
                            g.setColor(new Color(0,170,255));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==4)
                        {
                            g.setColor(new Color(0,255,170));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==5)
                        {
                            g.setColor(new Color(0,255,0));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==6)
                        {
                            g.setColor(new Color(255,255,0));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==7)
                        {
                            g.setColor(new Color(255,170,0));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+48+square/2,j*square+55+square/2);
                        }
                        else if(board[j][i]==8)
                        {
                            g.setColor(new Color(255,0,0));
                            g.fillRect(51+i*square,51+j*square,square-1,square-1);
                            g.setColor(Color.black);
                            g.drawString(String.valueOf(board[j][i]),i*square+50+square/2,j*square+55+square/2);
                        }
                    }
                    if(marked[j][i])
                    {
                        g.drawImage(f,51+i*square,51+j*square,square-1,square-1,this);
                    }
                }
            }
            g.setFont(new Font("Cambria Math",Font.PLAIN,15));
            if(gameover)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch(Exception e){}
                g.setColor(Color.white);
                g.fillRoundRect((columns*square-240)/2+51,(rows*square-200)/2+51,239,199,50,50);
                g.setColor(Color.red);
                g.drawString("You Stepped on a Mine",(columns*square-240)/2+90,(rows*square-200)/2+80);
                g.drawString("Game Over",(columns*square-240)/2+130,(rows*square-200)/2+100);
                g.setColor(Color.green);
                g.fillRect((columns*square-240)/2+80,(rows*square-200)/2+110,180,30);
                g.fillRect((columns*square-240)/2+80,(rows*square-200)/2+150,180,30);
                g.fillRect((columns*square-240)/2+80,(rows*square-200)/2+190,180,30);
                g.setColor(Color.blue);
                g.drawString("Play Same Board Again",(columns*square-240)/2+90,(rows*square-200)/2+130);
                g.drawString("New Board",(columns*square-240)/2+130,(rows*square-200)/2+170);
                g.drawString("Settings",(columns*square-240)/2+138,(rows*square-200)/2+210);
            }
            if(won)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch(Exception e){}
                g.setColor(Color.white);
                g.fillRoundRect((columns*square-240)/2+51,(rows*square-160)/2+51,239,159,50,50);
                g.setColor(Color.blue);
                g.drawString("You Discovered All Mines",(columns*square-240)/2+85,(rows*square-160)/2+80);
                g.drawString("You Won",(columns*square-240)/2+135,(rows*square-160)/2+100);
                g.setColor(Color.green);
                g.fillRect((columns*square-240)/2+80,(rows*square-160)/2+110,180,30);
                g.fillRect((columns*square-240)/2+80,(rows*square-160)/2+150,180,30);
                g.setColor(Color.blue);
                g.drawString("New Board",(columns*square-240)/2+130,(rows*square-160)/2+130);
                g.drawString("Settings",(columns*square-240)/2+138,(rows*square-160)/2+170);
            }
        }
        else if(mode==2)
        {
            setSize(400,560);
            g.setColor(Color.green);
            g.fillRect(0,0,450,600);
            g.setColor(Color.yellow);
            g.fillRect(50,60,300,30);
            g.fillRect(50,100,300,30);
            g.fillRect(50,140,300,30);
            g.fillRect(50,180,300,30);
            g.fillRect(50,220,300,30);
            g.fillRect(50,270,300,200);
            g.setFont(new Font("Lucida Calligraphy",Font.PLAIN,16));
            g.setColor(Color.black);
            g.drawString("SETTINGS",50,40);
            g.setFont(new Font("Cambria",3,14));
            g.drawString("1. VERY EASY - 7 X 7 Grid, 5 Mines",60,80);
            g.drawString("2. EASY - 10 X 10 Grid, 15 Mines",60,120);
            g.drawString("3. MEDIUM - 16 X 16 Grid, 48 Mines",60,160);
            g.drawString("4. HARD - 25 X 25 Grid, 150 Mines",60,200);
            g.drawString("5. EXTREME - 40 X 40 Grid, 400 Mines",60,240);
            g.drawString("6. CUSTOM :",60,290);
            g.drawString("Number    of    Rows  :",75,330);
            g.drawString("Number  of  Columns :",75,370);
            g.drawString("Number   of   Mines  :",75,410);
            if(rows<10)
            g.drawString(""+rows,281,330);
            else
            g.drawString(""+rows,276,330);
            if(columns<10)
            g.drawString(""+columns,281,370);
            else
            g.drawString(""+columns,276,370);
            if(mines<10)
            g.drawString(""+mines,281,410);
            else if(mines<100)
            g.drawString(""+mines,276,410);
            else
            g.drawString(""+mines,271,410);
            g.setColor(Color.green);
            g.fillRect(240,315,20,20);
            g.fillRect(310,315,20,20);
            g.fillRect(240,355,20,20);
            g.fillRect(310,355,20,20);
            g.fillRect(240,395,20,20);
            g.fillRect(310,395,20,20);
            g.drawRect(265,315,40,20);
            g.drawRect(265,355,40,20);
            g.drawRect(265,395,40,20);
            g.fillRect(170,430,60,30);
            g.setColor(Color.black);
            g.drawString("_",247,325);
            g.drawString("_",247,365);            
            g.drawString("_",247,405);
            g.drawString("+",314,330);
            g.drawString("+",314,370);
            g.drawString("+",314,410);
            g.drawString("Done",180,450);
        }
    }

    public void open(int x,int y)
    {
        opened[x][y]=true;
        if(board[x][y]==0)
        {
            if(x!=0)
            {
                if(y!=0)
                {
                    if(!opened[x-1][y-1]&&!marked[x-1][y-1])
                        open(x-1,y-1);
                }
                if(y!=(columns-1))
                {
                    if(!opened[x-1][y+1]&&!marked[x-1][y+1])
                        open(x-1,y+1);
                }
                if(!opened[x-1][y]&&!marked[x-1][y])
                    open(x-1,y);
            }
            if(x!=(rows-1))
            {
                if(y!=0)
                {
                    if(!opened[x+1][y-1]&&!marked[x+1][y-1])
                        open(x+1,y-1);
                }
                if(y!=(columns-1))
                {
                    if(!opened[x+1][y+1]&&!marked[x+1][y+1])
                        open(x+1,y+1);
                }
                if(!opened[x+1][y]&&!marked[x+1][y])
                    open(x+1,y);
            }
            if(y!=0)
            {
                if(!opened[x][y-1]&&!marked[x][y-1])
                    open(x,y-1);
            }
            if(y!=(columns-1))
            {
                if(!opened[x][y+1]&&!marked[x][y+1])
                    open(x,y+1);
            }
        }
        else if(board[x][y]==-1)
        {
            gameover=true;
        }
    }

    public void mark(int x,int y)
    {
        if(!marked[x][y]&&noofmarked<mines)
        {
            marked[x][y]=true;
            noofmarked++;
        }
        else if(marked[x][y])
        {
            marked[x][y]=false;
            noofmarked--;
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY()-30;
        if(mode==1)
        {
            int inx = (x-50)/square;
            int iny = (y-50)/square;
            if(!gameover&&inx<columns&&iny<=rows&&!won)
            {
                if(e.getButton()==MouseEvent.BUTTON1)
                {
                    if(!marked[iny][inx]&&!done)
                    {
                        open(iny,inx);
                        done = true;
                    }
                }
                if(e.getButton()==MouseEvent.BUTTON3)
                {
                    if(!opened[iny][inx]&&!done)
                    {
                        mark(iny,inx);
                        done = true;
                    }
                }
                won=true;
                if(gameover)
                won = false;
                for(int i=0;i<rows;i++)
                {
                    for(int j=0;j<columns;j++)
                    {
                        if(!(opened[i][j]||marked[i][j]))
                            won=false;
                    }
                }
                repaint();
            }
            if(gameover&&!done)
            {
                if(x>=(columns*square-220)/2+80&&x<=(columns*square-220)/2+260&&y>=(rows*square-220)/2+110&&y<=(rows*square-220)/2+140)
                {
                    reset();
                    done = true;
                    repaint();
                }
                if(x>=(columns*square-220)/2+80&&x<=(columns*square-220)/2+260&&y>=(rows*square-220)/2+150&&y<=(rows*square-220)/2+180)
                {
                    reset();
                    allocate();
                    done = true;
                    repaint();
                }
                if(x>=(columns*square-220)/2+80&&x<=(columns*square-220)/2+260&&y>=(rows*square-220)/2+190&&y<=(rows*square-220)/2+220)
                {
                    mode=2;
                    done = true;
                    repaint();
                }
            }
            if(won&&!done)
            {
                if(x>=(columns*square-240)/2+80&&x<=(columns*square-240)/2+260&&y>=(rows*square-160)/2+110&&y<=(rows*square-160)/2+140)
                {
                    reset();
                    allocate();
                    done = true;
                    repaint();
                }
                if(x>=(columns*square-240)/2+80&&x<=(columns*square-240)/2+260&&y>=(rows*square-160)/2+150&&y<=(rows*square-160)/2+180)
                {
                    mode=2;
                    done = true;
                    repaint();
                }
            }
        }
    }
    public void mousePressed(MouseEvent e)
    {
        done = false;
        int x = e.getX();
        int y = e.getY()-30;
        if(mode==2)
        {
            if(x>=50&&x<=350&&y>=60&&y<=90&&!done)
            {
                rows=7;
                columns=7;
                mines=5;
                square=70;
                reset();
                allocate();
                mode=1;
                done = true;
                repaint();
            }
            if(x>=50&&x<=350&&y>=100&&y<=130&&!done)
            {
                rows=10;
                columns=10;
                mines=15;
                square=50;
                reset();
                allocate();
                mode=1;
                done = true;
                repaint();
            }
            if(x>=50&&x<=350&&y>=140&&y<=170&&!done)
            {
                rows=16;
                columns=16;
                mines=48;
                square=34;
                reset();
                allocate();
                mode=1;
                done = true;
                repaint();
            }
            if(x>=50&&x<=350&&y>=180&&y<=210&&!done)
            {
                rows=25;
                columns=25;
                mines=150;
                square=23;
                reset();
                allocate();
                mode=1;
                done = true;
                repaint();
            }
            if(x>=50&&x<=350&&y>=220&&y<=250&&!done)
            {
                rows=40;
                columns=40;
                mines=400;
                square=14;
                reset();
                allocate();
                mode=1;
                done = true;
                repaint();
            }
            if(x>=240&&x<=260&&y>=315&&y<=335&&rows>5)
            {
                rows--;
                done = true;
                repaint();
            }
            if(x>=310&&x<=330&&y>=315&&y<=335&&rows<50)
            {
                rows++;
                done = true;
                repaint();
            }
            if(x>=240&&x<=260&&y>=355&&y<=375&&columns>5)
            {
                columns--;
                done = true;
                repaint();
            }
            if(x>=310&&x<=330&&y>=355&&y<=375&&columns<50&&columns<(int)(rows*2.5))
            {
                columns++;
                done = true;
                repaint();
            }
            if(x>=240&&x<=260&&y>=395&&y<=415&&mines>1)
            {
                mines--;
                done = true;
                repaint();
            }
            if(x>=310&&x<=330&&y>=395&&y<=415&&mines<(rows*columns)-1)
            {
                mines++;
                done = true;
                repaint();
            }
            if(x>=170&&x<=230&&y>=430&&y<=460&&mines<(rows*columns))
            {
                if(rows==5)
                square = 200;
                else if(rows==6)
                square = 160;
                else if(rows==7)
                square = 140;
                else if(rows <9)
                square = 120;
                else if(rows<11)
                square = 100;
                else if(rows<13)
                square = 90;
                else if(rows<15)
                square = 80;
                else if(rows<18)
                square = 68;
                else if(rows<21)
                square = 60;
                else if(rows<25)
                square = 48;
                else if(rows<31)
                square = 40;
                else if(rows<37)
                square = 32;
                else if(rows<45)
                square = 24;
                else
                square = 20;
                mode = 1;
                reset();
                allocate();
                done = true;
                repaint();
            }
        }
    }

    public void mouseExited(MouseEvent e){}

    public void mouseEntered(MouseEvent e){}

    public void mouseClicked(MouseEvent e){}

}
