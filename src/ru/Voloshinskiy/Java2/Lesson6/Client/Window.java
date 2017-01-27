package ru.Voloshinskiy.Java2.Lesson6.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Yuriy Fedorovich on 07.01.2017.
 */
public class Window extends JFrame {
    private JTextArea corField;
    private JPanel jpAuth;
    private String serverHost;
    private int serverPort;
    private Scanner dataInputStream = null;
    private PrintWriter dataOutputStream = null;
    private Socket socket = null;
    private String name = "somebody";

    public Window(String serverHost, int serverPort) throws IOException {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        initConnection();
        initGUI();
        serverListener();
    }

    private void initConnection() throws IOException {
            socket = new Socket(serverHost,serverPort);
            dataInputStream = new Scanner(socket.getInputStream());
            dataOutputStream = new PrintWriter(socket.getOutputStream());
    }

    private void initGUI(){
        setTitle("My chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 200, 500, 400);
        setLayout(new BorderLayout());
        initTopPanel();
        initCorrespondenceField();
        initMessageField();
        authentication();
        setVisible(true);
    }

    private void initTopPanel(){
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
                setName((String) JOptionPane.showInputDialog(null));
            }
        });

        miFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initCorrespondenceField(){
        JPanel jpCor = new JPanel();
        add(jpCor,BorderLayout.CENTER);

        jpCor.setLayout(new BorderLayout());
        corField = new JTextArea();
        corField.setLineWrap(true);
        corField.setWrapStyleWord(true);
        corField.setEditable(false);
        JScrollPane jsp = new JScrollPane(corField);
        jpCor.add(jsp);


    }

    private void initMessageField(){
        JPanel jpMes = new JPanel();
        add(jpMes,BorderLayout.SOUTH);
        jpMes.setLayout(new BorderLayout());
        JTextField jtf = new JTextField("Введите текст");
        jtf.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jtf.getText().equals("Введите текст"))
                    jtf.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg(jtf.getText());
                jtf.setText("");
            }
        });
        JButton jbSend = new JButton("Отправить");
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                sendMsg(jtf.getText());
                jtf.setText("");
            }
        });
        jpMes.add(jtf,BorderLayout.CENTER);
        jpMes.add(jbSend,BorderLayout.EAST);
    }

    private void authentication(){
        jpAuth = new JPanel();
        jpAuth = new JPanel(new GridLayout(2,3));

        JTextField loginField = new JTextField();
        loginField.setToolTipText("Enter your Login here");
        jpAuth.add(new JLabel("Login: "));
        jpAuth.add(loginField);

        JButton signInButton = new JButton("Sing In");
        jpAuth.add(signInButton);

        JTextField passwordField = new JTextField();
        passwordField.setToolTipText("Enter your Password here");
        jpAuth.add(new JLabel("Password: "));
        jpAuth.add(passwordField);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAuthCommand(loginField.getText(), passwordField.getText());
            }
        });
        add(jpAuth,BorderLayout.NORTH);
    }

    private void sendAuthCommand(String login, String password){
        dataOutputStream.println("___Login:___" + login + "___Password___" + password);
        dataOutputStream.flush();
    }

    private void serverListener(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                while(true) {
                    if (dataInputStream.hasNext()) {
                        msg = dataInputStream.nextLine();

                        if (msg.equalsIgnoreCase("SignIn_OK")) {
                            JOptionPane.showMessageDialog(null, "Аутентификация прошла успешно.");
                            jpAuth.setVisible(false);
                        }
                        else if (msg.equalsIgnoreCase("SignIn_Fail")){
                            JOptionPane.showMessageDialog(null,"Неверная пара логин/пароль, попробуйте ещё");
                        }
                        else
                            corField.append(dataInputStream.nextLine() + '\n');
                    }
                }
            }
        }).start();
    }

    public void sendMsg(String msg){
        dataOutputStream.println(name+": "+msg);
        dataOutputStream.flush();
    }

    public void setName(String name) {
        this.name = name;
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
