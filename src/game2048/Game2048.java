/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game2048;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ACER
 */
public class Game2048 extends JFrame implements KeyListener,ActionListener{
    private MyPanel mp;
    private JMenuBar mbr;
    private JMenu opm;
    private JMenuItem rmn,exit;
    Random ran=new Random();
    boolean mer=true;
    public Game2048() {
        this.setTitle("2048");
        this.setLayout(null);
        this.setSize(440,550);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mbr=new JMenuBar();
        opm=new JMenu("Option");
        rmn=new JMenuItem("Restart");
        rmn.addActionListener(this);
        opm.add(rmn);
        exit=new JMenuItem("Exit");
        exit.addActionListener(this);
        opm.add(exit);
        mbr.add(opm);
        this.setJMenuBar(mbr);
         this.addKeyListener(this);
         this.fun();
        this.requestFocusInWindow();
        this.repaint();
        this.setSize(440, 520);
        
    }
    public void fun() {
            mp=new MyPanel(this);
            mp.setBounds(10, 10, 400, 440);
            this.add(mp);
    }
    public static void main(String[] args) {
        Game2048 gb=new Game2048();
    }
@Override
    public void keyTyped(KeyEvent ke) {
        System.out.print("sdfg");
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(mp!=null) {
            mer=true;
      switch(ke.getKeyCode()) {
          case KeyEvent.VK_LEFT :
              for(int j=0;j<3;j++) {
              mp.moveL();
              mp.paint(mp.getGraphics());
              try {
                  Thread.sleep(100);
              }
              catch(Exception rt) {
                  
              }
          }
          mp.another();
              break;
          case KeyEvent.VK_RIGHT :
              for(int j=0;j<3;j++) {
              mp.moveR();
              mp.paint(mp.getGraphics());
              try {
                  Thread.sleep(100);
              }
              catch(Exception rt) {
                  
              }
          }
          mp.another();
              break;
          case KeyEvent.VK_UP :
          for(int j=0;j<3;j++) {
              mp.moveU();
              mp.paint(mp.getGraphics());
              try {
                  Thread.sleep(100);
              }
              catch(Exception rt) {
                  
              }
          }
          mp.another();
              break; 
          case KeyEvent.VK_DOWN :
          for(int j=0;j<3;j++) {
              mp.moveD();
              mp.paint(mp.getGraphics());
              try {
                  Thread.sleep(100);
              }
              catch(Exception rt) {
                  
              }
          }
          mp.another();
              break;    
      }   
    }
    }      

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if((JMenuItem)ae.getSource()==rmn) {
            this.remove(mp);
            this.fun();
            this.addKeyListener(this);
            this.repaint();
            this.requestFocusInWindow();
        }
        else {
            System.exit(0);
        }
    }

    
    
    public class MyPanel extends JPanel {
    Game2048 gm;
    ArrayList<Marble> mb=new ArrayList<>();
    ArrayList<Marble> db=new ArrayList<>();
    Marble amo[][]=new Marble[4][4];
    public ArrayList<Integer> avail=new ArrayList<>();
    int rx,ry;
    long score,highsc;
    Random rn=new Random();
    FileReader fr;
    FileWriter fw;
    public MyPanel(Game2048 gf) {
        gm=gf;
        try {
                this.highScoreManager();
            } catch (IOException ex) {
                System.out.println("errr");
                highsc=0;
            }
        for(int i=0;i<16;i++) {
            avail.add((Integer)i);
        }
        rx=rn.nextInt(4);
        ry=rn.nextInt(4);
        mb.add(new Marble((int)Math.pow(2, (double)rn.nextInt(2)+1),rx,ry));
        avail.remove((Integer)(ry*4+rx));
        rx=rn.nextInt(4);
        ry=rn.nextInt(4);
        mb.add(new Marble((int)Math.pow(2, (double)rn.nextInt(2)+1),rx,ry));
        if(avail.contains((Integer)ry*4+rx)) {
        avail.remove((Integer)(ry*4+rx));
        }
        this.check();
        //this.addKeyListener(mp);
    }
    public void pass(Game2048 temp) {
        gm=temp;
    }
    public void highScoreManager() throws IOException {
        try {
            fr=new FileReader("C:\\sbrGames\\sbr.txt");
        }
        catch(Exception ew) {
            (new File("C:\\sbrGames")).mkdirs();
            (new File("C:\\sbrGames\\sbr.txt")).createNewFile();
            fr=new FileReader("C:\\sbrGames\\sbr.txt");
        }
        BufferedReader br=new BufferedReader(fr);
        try {
            highsc=Long.parseLong(br.readLine());
        }
        catch(Exception er) {
            highsc=0;
        }
    }
    public void merge(Marble ta,Marble tb) {
        tb.x=ta.x;
        tb.y=ta.y;
        tb.change();
        score+=tb.val;
        mb.remove(ta);
    }
    public void check() {
        for(int i=0;i<mb.size();i++){
            for(int j=0;j<mb.size();j++) {
                if(i!=j) {
                    if((mb.get(i).x==mb.get(j).x)&&(mb.get(i).y==mb.get(j).y)) {
                        db.add(mb.get(i));
                        mb.get(j).change();
                    }
                }
            }
        }
        for(int i=0;i<db.size()-1;i++) {
            //avail.add((Integer)4*db.get(i).y+db.get(i).x);
            mb.remove(db.get(i));
        }
        db.clear();
        repaint();
    }
    public void uptodate() {
        for(int i=0;i<db.size();i++) {
            //avail.add((Integer)4*db.get(i).y+db.get(i).x);
            //System.out.println(db.get(i).x+" "+db.get(i).y);
            mb.remove(db.get(i));
        }
        db.clear();
        this.repaint();
    }
    public boolean isGameOver() {
        for(Marble tem:mb) {
            amo[tem.x][tem.y]=tem;
        }
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
               if(j<3) {
                   if(amo[i][j].val==amo[i][j+1].val) {
                       return false;
                   }
                   if(amo[j][i].val==amo[j+1][i].val) {
                       return false;
                   }
               } 
            }
        }
        return true;
    }
    public void drawGameOver(Graphics g) {
       g.setColor(Color.yellow);
       g.setFont(new Font("Cooper",Font.BOLD,32));
       g.drawString("GAME OVER", 100, 210);
       
    }
    public void another() {
        try {
        int temp=avail.get(ran.nextInt(mp.avail.size()));
        mb.add(new Marble((int)Math.pow(2, (double)ran.nextInt(2)+1),temp%4,temp/4));
        avail.remove((Integer)temp);
        repaint();
      }
      catch(Exception er) {
          if(isGameOver()) {
           this.drawGameOver(this.getGraphics());
           gm.removeKeyListener(gm);
          //System.out.println("Game over");
          try {
            fw=new FileWriter("C:\\sbrGames\\sbr.txt");
            if(score>highsc) {
            fw.write(Long.toString(score));
            }
            else {
               fw.write(Long.toString(highsc)); 
            }
            fw.close();
        }
        catch(Exception ew) {
            System.out.println("Game over errr");
        }
          }
      }
    }
    public void moveL() {
        int re=0;
        for(Marble tem:mb) {
            amo[tem.x][tem.y]=tem;
        }
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
            if(amo[i][j]!=null) {
            if(amo[i][j].x>0) {
                if((avail.contains(4*amo[i][j].y+amo[i][j].x-1))) {
                    avail.add((Integer)(4*amo[i][j].y+amo[i][j].x));
                    avail.remove((Integer)(4*amo[i][j].y+amo[i][j].x-1));
                    amo[i][j].x--;
                }
                
                try {
                if(amo[i-1][j]!=null&&amo[i-1][j].val==amo[i][j].val&&mer) {
                    avail.add((Integer)(4*amo[i][j].y+amo[i][j].x));
                    this.merge(amo[i-1][j], amo[i][j]);
                    re++;
                    //amo[i][j].x--;
                    //this.check();
                    
                    //db.add(amo[i][j]);
                    //this.uptodate();
                    //amo[i-1][j].change();
                }
                }
                catch(Exception er) {
                    System.out.println((i-1)+" "+j);
                }
            }
        }
        }
        }
        if(re!=0)
            mer=false;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
         amo[i][j]=null;       
            }
        }
        //repaint();
        //this.another();
    }
    public void moveU() {
        int re=0;
        for(Marble tem:mb) {
            amo[tem.x][tem.y]=tem;
        }
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
            if(amo[j][i]!=null) {
            if(amo[j][i].y>0) {
                if((avail.contains(4*(amo[j][i].y-1)+amo[j][i].x))) {
                    avail.add((Integer)(4*amo[j][i].y+amo[j][i].x));
                    avail.remove((Integer)(4*(amo[j][i].y-1)+amo[j][i].x));
                    amo[j][i].y--;
                }
                try {
                if(amo[j][i-1]!=null&&amo[j][i-1].val==amo[j][i].val&&mer) {
                    avail.add((Integer)(4*amo[j][i].y+amo[j][i].x));
                    this.merge(amo[j][i-1], amo[j][i]);
                    re++;
                    //amo[i][j].y--;
                    //this.check();
                    
                    //db.add(amo[i][j]);
                    //this.uptodate();
                    //amo[j][i-1].change();
                }
                }
                catch(Exception er) {
                    System.out.println(j+" "+(i-1));
                }
            }
        }
        }
        }
        if(re!=0)
            mer=false;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
         amo[i][j]=null;       
            }
        }
        //repaint();
        //this.another();
    }
    public void moveR() {
        int re=0;
        for(Marble tem:mb) {
            amo[tem.x][tem.y]=tem;
        }
        for(int i=3;i>=0;i--) {
            for(int j=3;j>=0;j--) {
            if(amo[i][j]!=null) {
            if(amo[i][j].x<3) {
                if((avail.contains(4*amo[i][j].y+amo[i][j].x+1))) {
                    avail.add((Integer)(4*amo[i][j].y+amo[i][j].x));
                    avail.remove((Integer)(4*amo[i][j].y+amo[i][j].x+1));
                    amo[i][j].x++;
                }
                try {
                if(amo[i+1][j]!=null&&amo[i+1][j].val==amo[i][j].val&&mer) {
                    avail.add((Integer)(4*amo[i][j].y+amo[i][j].x));
                    this.merge(amo[i+1][j], amo[i][j]);
                    re++;
                    //amo[i][j].x++;
                    //this.check();
                    
                    //db.add(amo[i][j]);
                    //this.uptodate();
                    //amo[i+1][j].change();
                }
                }
                catch(Exception er) {
                    System.out.println((i+1)+" "+j);
                }
            }
        }
        }
        }
        if(re!=0)
            mer=false;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
         amo[i][j]=null;       
            }
        }
        //repaint();
        //this.another();
    }
    public void moveD() {
        int re=0;
        for(Marble tem:mb) {
            amo[tem.x][tem.y]=tem;
        }
        for(int i=3;i>=0;i--) {
            for(int j=3;j>=0;j--) {
            if(amo[j][i]!=null) {
            if(amo[j][i].y<3) {
                if((avail.contains(4*(amo[j][i].y+1)+amo[j][i].x))) {
                    avail.add((Integer)(4*(amo[j][i].y)+amo[j][i].x));
                    avail.remove((Integer)(4*(amo[j][i].y+1)+amo[j][i].x));
                    amo[j][i].y++;
                }
                try {
                    if(amo[j][i+1]!=null&&amo[j][i+1].val==amo[j][i].val&&mer) {
                    avail.add((Integer)(4*(amo[j][i].y)+amo[j][i].x));
                    this.merge(amo[j][i+1], amo[j][i]);
                    re++;
                    //amo[i][j].y++;
                    //this.check();
                    
                    //db.add(amo[i][j]);
                    //this.uptodate();
                    //amo[j][i+1].change();
                }
                }
                catch(Exception er) {
                    System.out.println(j+" "+(i+1));
                }
            }
        }
        }
        }
        if(re!=0)
            mer=false;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
         amo[i][j]=null;       
            }
        }
        //repaint();
        //this.another();
    }
    @Override
    public void paint(Graphics g) {
      g.setColor(new Color(255,255,255));
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g.setFont(new Font("Cooper",Font.BOLD,32));
      for(int j=0;j<mb.size();j++) {
              mb.get(j).render(g);
          }
      g.setColor(Color.green);
      g.setFont(new Font("Cooper",Font.BOLD,20));
      g.drawString("Score="+score, 20, 420);
      g.setColor(Color.red);
      g.drawString("Highscore="+highsc, 220, 420);
    }
    
}
    public class Marble {
        String st="2";
        public int var=0,x,y,val=2;
        public int R=254,G=0,B=0,cr,cg,cb;
        Color cl=new Color(0,0,2);
        public Marble(int va,int a,int b) {
            val=va;
            for(int i=va;i<2;i=i/2) {
                var++;
            }
            st=Integer.toString(val);
            this.x=a;
            this.y=b;
        }
        public void change() {
            val*=2;
            var++;
            st=Integer.toString(val);
        } 
        public void render(Graphics g) {
            //g.setColor(new Color(R,G,B));
            g.setColor(new Color(200-var*10,200-var*10,255));
            g.fillRoundRect(10+x*100, 10+y*100, 80, 80, 20, 20);
            g.setColor(new Color(255,255,255));
            g.drawString(st, 40+(x*100)-((st.length()-1)*8), 60+y*100);
        }
    }
}
