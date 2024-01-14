import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class editor extends JFrame implements ActionListener {

    JTextArea t;
    JFrame f;
    editor(){
        f=new JFrame("Text Editor");
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetallicLookAndFeel");


            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {

        }
        //Initializing the Text Area
        t=new JTextArea();
        //Initializing the menuBar
        JMenuBar m=new JMenuBar();

        //Initializing the File Menu
        JMenu f1=new JMenu("File");

        //Creating the Individual menu items
        JMenuItem m1=new JMenuItem("New");
        JMenuItem m2=new JMenuItem("Open");
        JMenuItem m3=new JMenuItem("Save");
        JMenuItem m4=new JMenuItem("Print");

        //Adding the ActionListener
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);
        m4.addActionListener(this);

        //Adding the menuitems to file menu
        f1.add(m1);
        f1.add(m2);
        f1.add(m3);
        f1.add(m4);

        JMenu f2=new JMenu("Edit");

        //Creating the Individual menu items
        JMenuItem m5=new JMenuItem("Cut");
        JMenuItem m6=new JMenuItem("Copy");
        JMenuItem m7=new JMenuItem("Paste");

        //Adding the ActionListener
        m5.addActionListener(this);
        m6.addActionListener(this);
        m7.addActionListener(this);
        //Adding the menuitems to file menu
        f2.add(m5);
        f2.add(m6);
        f2.add(m7);

        JMenuItem c=new JMenuItem("Exit");
        c.addActionListener(this);

        m.add(f1);
        m.add(f2);
        m.add(c);

        f.add(t);
        JScrollPane s=new JScrollPane(t);
        f.add(s);
        f.setJMenuBar(m);
        f.setSize(500,500);
        f.show();
    }
    public void actionPerformed(ActionEvent e){
        String s=e.getActionCommand();
        if(s.equals("New")){
            t.setText("");
        }
        else if(s.equals("Open")){
            JFileChooser j=new JFileChooser("f:");
            int r= j.showOpenDialog(null);
            if(r==JFileChooser.APPROVE_OPTION){
                File fi = new File(j.getSelectedFile().getAbsoluteFile().toURI());

                try{
                    String s1="",s2="";

                    FileReader fr=new FileReader(fi);
                    BufferedReader br=new BufferedReader(fr);

                    s2= br.readLine();

                    while((s1= br.readLine())!=null){
                        s2=s2+"\n"+s1;
                    }

                    t.setText(s2);
                } catch (Exception et) {
                    JOptionPane.showMessageDialog(f,et.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(f,"Operation Cancelled");
            }
        }
        else if(s.equals("Save")){
            JFileChooser j=new JFileChooser("f:");
            int r= j.showSaveDialog(null);
            if(r==JFileChooser.APPROVE_OPTION){
                File fi=new File(j.getSelectedFile().getAbsoluteFile().toURI());

                try{
                    FileWriter wr=new FileWriter(fi);

                    BufferedWriter bw=new BufferedWriter(wr);

                    bw.write(t.getText());

                    bw.flush();
                    bw.close();
                }
                catch (Exception et) {
                    JOptionPane.showMessageDialog(f,et.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(f,"Operation Cancelled");
            }
        }
        else if(s.equals("Print")){
            try{
                t.print();
            }
            catch(Exception et){
                JOptionPane.showMessageDialog(f,et.getMessage());
            }
        }
        else if(s.equals("Cut")){
            t.cut();
        }
        else if(s.equals("Copy")){
            t.copy();
        }
        else if(s.equals("Paste")){
            t.paste();
        }
        else if(s.equals("Exit")){
            f.setVisible(false);
        }
    }

    public static void main(String[] args) {
        editor e=new editor();
    }
}