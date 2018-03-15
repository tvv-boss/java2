package ru.geekbrains.java2.dz.dz4.TymkivVitaly;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

    public class Chat extends JFrame{
        JPanel jptop = new JPanel(new GridLayout());
        JPanel jpbottom = new JPanel(new GridLayout());

        JButton jbutton = new JButton(" Enter ");
        JTextArea jtextarea = new JTextArea();
        JScrollPane jscrollpane = new JScrollPane(jtextarea);
        JTextField jtextfield = new JTextField();

        JMenuBar mainMenu = new JMenuBar();
        JMenu mFile = new JMenu("File");
        JMenu mHelp = new JMenu("Help");
        JMenuItem miFileExit = new JMenuItem("Exit");
        JMenuItem miHelpAbout = new JMenuItem("About");


        Chat() throws IOException {
            super(" Chat ");
            Font font = new Font(null,Font.BOLD,14);
            Font fontcur = new Font(null,Font.ITALIC,13);
            setSize(400, 500);
            setMinimumSize(new Dimension(400, 500));
            mFile.setFont(font);
            mHelp.setFont(font);
            miFileExit.setFont(font);
            miHelpAbout.setFont(font);
            jbutton.setFont(fontcur);
            jtextarea.setLineWrap(true);
            jtextarea.setEditable(false);

            jbutton.addActionListener(e -> {
                sendMessage();
            });
            jtextfield.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) sendMessage();
                }
            });

            jptop.add(jscrollpane);
            jpbottom.add(jtextfield);
            jpbottom.add(jbutton);

            add(jptop);
            add("South", jpbottom);

            setJMenuBar(mainMenu);
            mainMenu.add(mFile);
            mainMenu.add(mHelp);
            mFile.add(miFileExit);
            mHelp.add(miHelpAbout);
            miFileExit.addActionListener(e -> System.exit(0));
            miHelpAbout.addActionListener(e -> JOptionPane.showMessageDialog(null," Chat ", "About", JOptionPane.INFORMATION_MESSAGE));
            jtextarea.setBackground(Color.lightGray);
            jtextfield.setBackground(Color.white);
            jbutton.setBackground(Color.gray);
            jptop.setBorder(BorderFactory.createLineBorder(Color.black));
            jpbottom.setBorder(BorderFactory.createLineBorder(Color.black));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        void sendMessage() {
            String out = jtextfield.getText();
            if (!out.isEmpty()) {
                jtextarea.append(getTime() + ": " + out + "\n");
                jtextfield.setText("");
                jtextfield.grabFocus();
            }
        }
        //time
        public String getTime() {
            return new SimpleDateFormat("HH:mm:ss").format(new Date());
        }

    }


