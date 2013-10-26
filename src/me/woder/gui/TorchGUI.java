package me.woder.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JFrame;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import me.woder.bot.Client;
import me.woder.bot.ThreadMainLoop;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TorchGUI extends JPanel{
    private static final long serialVersionUID = 1L;
    public DefaultStyledDocument doc = new DefaultStyledDocument();;
    public JFrame frame;
    public TorchGUI torchs = this;
    public PRadar pradar;
    public HashMap<String, AttributeSet> attributes;
    private JTextField textField;
    Client c;
    JTextPane chat;
    JTextArea status;
    JLabel news;
    String text = "i have written source code for scrolling text messages from "+  
            "right to left using applets,but the requirement is that the text should be "+  
            "scrolled from bottom of the page and it has start from corner of window "+  
            "applet.Please,can anyone suggest me on how to do this. ";      
    
    final StyleContext cont = StyleContext.getDefaultStyleContext();
    final AttributeSet black = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,0,0));
    final AttributeSet blue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,0,170));
    final AttributeSet green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,170,0));
    final AttributeSet dark_aqua = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(0,170,170));
    final AttributeSet dark_red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,0,0));
    final AttributeSet purple = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,0,170));
    final AttributeSet orange = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,170,0));
    final AttributeSet grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(170,170,170));
    final AttributeSet dark_grey = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,85,85));
    final AttributeSet indigo = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,85,255));
    final AttributeSet bright_green = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,255,85));
    final AttributeSet aqua = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(85,255,255));
    final AttributeSet red = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,85,85));
    final AttributeSet pink = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,85,255));
    final AttributeSet yellow = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,255,85));
    final AttributeSet white = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, new Color(255,255,255));
    final AttributeSet reset = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.black);
    
    final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.black);

    /**
     * Create the application.
     */
    public TorchGUI(Client c) {
        this.c = c;
        attributes = new HashMap<String, AttributeSet>();
        attributes.put("0", black);
        attributes.put("1", blue);
        attributes.put("2", green);
        attributes.put("3", dark_aqua);
        attributes.put("4", dark_red);
        attributes.put("5", purple);
        attributes.put("6", orange);
        attributes.put("7", grey);
        attributes.put("8", dark_grey);
        attributes.put("9", indigo);
        attributes.put("a", bright_green);
        attributes.put("b", aqua);
        attributes.put("c", red);
        attributes.put("d", pink);
        attributes.put("e", yellow);        
        attributes.put("f", black);
        attributes.put("r", reset);
        initialize();      
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame("TorchBot " + c.version);
        //frame.setBounds(100, 100, 944, 555);
        frame.setPreferredSize(new Dimension(944, 555));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JScrollPane scrollPane = new JScrollPane();
        
        chat = new JTextPane(doc);
        DefaultCaret caret = (DefaultCaret)chat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane.setViewportView(chat);
        chat.setEditable(false);
        
        
        textField = new JTextField();
        textField.setColumns(10);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                processCommand(textField.getText());
            }
        });
        
        status = new JTextArea();
        status.setEditable(false);
        
        JButton btnNewButton = new JButton("Send");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                processCommand(textField.getText());
            }
        });
        
        news = new JLabel(text, Label.RIGHT);
        
        JButton btnNewButton_1 = new JButton("Connect");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String s = JOptionPane.showInputDialog(null,"Enter server name and port, seperated by a ';' (Leave blank for server in config)", "Connect to a server", JOptionPane.PLAIN_MESSAGE);
                if ((s != null) && (s.length() > 0)) {
                    String[] bo = s.split(";");
                    startB(bo[0], bo[1]);
                }else{
                    startB();
                }
            }
        });
        
        JButton btnNewButton_2 = new JButton("Disconnect");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                stopB();
            }
        });
        
        JButton btnNewButton_3 = new JButton("Place holder");
        
        JButton btnNewButton_4 = new JButton("Place holder");
        
        JButton btnNewButton_5 = new JButton("Place holder");
        
        JButton btnNewButton_6 = new JButton("Place holder");
        
        JButton btnNewButton_7 = new JButton("Place holder");
        
        JButton btnNewButton_8 = new JButton("Place holder");
        
        pradar = new PRadar();
        
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(10)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 520, GroupLayout.PREFERRED_SIZE)
                            .addGap(10)
                            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(status, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED))
                                .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                    .addComponent(pradar, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)))
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_8, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNewButton_7, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(textField, GroupLayout.PREFERRED_SIZE, 447, GroupLayout.PREFERRED_SIZE)
                            .addGap(7)
                            .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                            .addGap(10)
                            .addComponent(news, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE))))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(11)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(pradar, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(status, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(2)
                            .addComponent(btnNewButton_1)
                            .addGap(11)
                            .addComponent(btnNewButton_2)
                            .addGap(11)
                            .addComponent(btnNewButton_8)
                            .addGap(11)
                            .addComponent(btnNewButton_3)
                            .addGap(11)
                            .addComponent(btnNewButton_4)
                            .addGap(11)
                            .addComponent(btnNewButton_5)
                            .addGap(11)
                            .addComponent(btnNewButton_6)
                            .addGap(11)
                            .addComponent(btnNewButton_7)))
                    .addGap(11)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addComponent(news, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
        );
        frame.getContentPane().setLayout(groupLayout);
        frame.pack();
        this.repaint();
        scrollTheText();
    }
    
    public void tick(){
        pradar.repaint();
    }
    
    public void startB(String server, String port){
        Thread loop = new Thread(new ThreadMainLoop(c,server, port),"T1");
        loop.start();
    }
    
    public void startB(){
        Thread loop = new Thread(new ThreadMainLoop(c),"T1");
        loop.start();
    }
    
    public void stopB(){
        c.stopBot("Leaving");
    }
    
    public void processCommand(String text){
        c.chandle.processConsoleCommand(text);
        textField.setText("");
    }
    
    public void scrollTheText(){  
      Timer tmr = new Timer();  
      tmr.scheduleAtFixedRate(new TimerTask()  
      {  
        public void run()  
        {  
          text = new StringBuffer(text.substring(1)).append(text.substring(0,1)).toString();  
          news.setText(text);  
        }  
      },500,100);  
    }  
    
    public void addText(String text){
        Logger.getLogger("me.woder.chat").log(Level.FINE, text);
        text = text + "\n";
        String[] lines = text.split("�");

        for (int i = 1; i < lines.length; i++){
            String line = lines[i];
            String key = line.substring(0, 1);
            String theText = line.substring(1);
            AttributeSet attribute = attributes.get(key);

            try{
                int len = doc.getLength();
                doc.insertString(len, theText, attribute);
            }catch (BadLocationException e){
                e.printStackTrace();
            }
        }
    }
}
