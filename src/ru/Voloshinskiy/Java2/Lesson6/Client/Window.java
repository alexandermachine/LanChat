package ru.Voloshinskiy.Java2.Lesson6.Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Yuriy Fedorovich on 07.01.2017.
 */
public class Window extends JFrame {
    private Scanner dataInputStream = null;
    private PrintWriter dataOutputStream = null;


    public Window(String SERVER_HOST, int SERVER_PORT) throws IOException {
        ConnectToServer connection = new ConnectToServer(SERVER_HOST, SERVER_PORT);
        dataInputStream = connection.getDataInputStream();
        dataOutputStream = connection.getDataOutputStream();

        setTitle("My chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 200, 500, 400);
        setLayout(new BorderLayout());


        JPanel jpCor = new JPanel();
        add(jpCor,BorderLayout.CENTER);
        JPanel jpMes = new JPanel();
        add(jpMes,BorderLayout.SOUTH);

        JMenuBar mainMenu = new JMenuBar();
        JMenu mFile = new JMenu("File");
        JMenuItem miFileSetName = new JMenuItem("Set name");
        JMenuItem miFileExit = new JMenuItem("Exit");

        setJMenuBar(mainMenu);

        mainMenu.add(mFile);

        mFile.add(miFileSetName);
        mFile.addSeparator(); // разделительная линия
        mFile.add(miFileExit);

        miFileSetName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection.setName((String) JOptionPane.showInputDialog(null));
            }
        });

        miFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        //***********переписка***********
        jpCor.setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        jta.setEditable(false);
        JScrollPane jsp = new JScrollPane(jta);
        jpCor.add(jsp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                    if (dataInputStream.hasNext())
                        jta.append(dataInputStream.nextLine()+'\n');
            }
        }).start();
        //*******************************

        //*******Ввод сообщения**********
        jpMes.setLayout(new BorderLayout());
        JTextField jtf = new JTextField("Введите текст");
        jtf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                jtf.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection.sendMsg(jtf.getText());
                //dataOutputStream.println(jtf.getText()+'\n');
                jtf.setText("");
            }
        });

        JButton jbSend = new JButton("Отправить");
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection.sendMsg(jtf.getText());
                //dataOutputStream.println(jtf.getText()+'\n');
                jtf.setText("");
            }
        });
        jpMes.add(jtf,BorderLayout.CENTER);
        jpMes.add(jbSend,BorderLayout.EAST);
        //*******************************


        setVisible(true);
    }


    private void writeToFile(String str){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("Correspondence.txt");
            pw.append(str);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
