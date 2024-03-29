package Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {

    static JPanel a1;
    JTextField text;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();

    static DataOutputStream outputStream;

    Server(){
        // Jpanel used as a header
        f.setLayout(null);



        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);


        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("images/profile.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile_pic = new JLabel(i6);
        profile_pic.setBounds(40,10,50,50);
        p1.add(profile_pic);



        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("images/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video_call = new JLabel(i9);
        video_call.setBounds(300,20,30,30);
        p1.add(video_call);



        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("images/call.png"));
        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel call = new JLabel(i12);
        call.setBounds(360,20,30,30);
        p1.add(call);



        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("images/dots.png"));
        Image i14 = i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel setting = new JLabel(i15);
        setting.setBounds(420,20,30,30);
        p1.add(setting);

        JLabel name = new JLabel("Minion");
        name.setBounds(105,20,200,16);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF" , Font.BOLD , 16));
        p1.add(name);

        JLabel status = new JLabel("Active now");
        status.setBounds(105,45,100,12);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF" , Font.BOLD , 12));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);


        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN-SERIF", Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("SEND");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);



         f.setSize(450,600);
         f.setLocation(200,10);
//         f.setUndecorated(true);
         f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
          try{
              String out = text.getText();
              JPanel p2 = formatLabel(out);

              a1.setLayout(new BorderLayout()); // a1 is the chat area

              JPanel right = new JPanel(new BorderLayout());
              right.add(p2,BorderLayout.LINE_END);
              vertical.add(right);
              vertical.add(Box.createVerticalStrut(15));

              a1.add(vertical , BorderLayout.PAGE_START);

              outputStream.writeUTF(out);

              text.setText("");

              f.repaint();
              f.validate();
              f.invalidate();

          }
          catch (Exception error){
              error.printStackTrace();
          }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date_time = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(date_time.format(cal.getTime()));

        panel.add(time);
        return panel;
    }

    public static JPanel formatLabel2(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel , BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(255, 204, 203));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date_time = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(date_time.format(cal.getTime()));

        panel.add(time);
        return panel;
    }

    public static void main(String[] args){
        new Server();

        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true){
            Socket s = skt.accept();
            DataInputStream inputStream = new DataInputStream(s.getInputStream());
            outputStream = new DataOutputStream(s.getOutputStream());

            while (true){

                String msg = inputStream.readUTF();
                JPanel panel = formatLabel2(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel , BorderLayout.LINE_START);
                vertical.add(left);


                f.validate();
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
