/**
 *
/**
 * @author RITS BHOPAL
 * vedprksh79@email.com
 * 9407511129
 */
package ChatServer;

import java.io.InputStream;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.sql.*;

public class ChatJPanel2 extends javax.swing.JPanel implements Runnable {

    /** Creates new form ChatJPanel2 */
    public ChatJPanel2() {
        users = new Vector();
        receiver = new Thread(this);
        communicator = new Thread(new A());
        try {
            ds = new DatagramSocket(MainConstants.CONNECTION_PORT);
            ds1 = new DatagramSocket(MainConstants.COMMUNICATION_PORT);
            Properties pro = new Properties();
            InputStream in = getClass().getResourceAsStream("Message.properties");
            pro.load(in);
            String userName = pro.getProperty("UserName");
            String password = pro.getProperty("Password");
            String url = pro.getProperty("url");
            System.out.println("Enter your UserName"+userName);
            System.out.println("Enter your Password"+password);
            System.out.println("Enter your url"+url);

            Class.forName(pro.getProperty("drivername")).newInstance();
            con = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to List to Port" + e.toString());
        }
        receiver.start();
        communicator.start();
        initComponents();
    }

    class A implements Runnable {

        public void run() {
            while (true) {
                byte b[] = new byte[256];
                DatagramPacket dp = new DatagramPacket(b, b.length);
                try {
                    ds1.receive(dp);
                    String str = new String(dp.getData(), 0, dp.getLength());
                    if (str.startsWith("USERS")) {
                        for (int i = 0; i < users.size(); i++) {
                            User u = (User) users.elementAt(i);
                            String us = "USERS:" + u.getUserName();
                            InetAddress ip = u.getIp();
                            int port = u.getPort();
                            byte arr[] = us.getBytes();
                            DatagramPacket dp1 = new DatagramPacket(arr, arr.length, dp.getAddress(), dp.getPort());
                            ds1.send(dp1);
                            String us1 = "USERS:" + un;
                            byte arr1[] = us1.getBytes();
                            DatagramPacket dp2 = new DatagramPacket(arr1, arr1.length, ip, port);
                            ds1.send(dp2);
                        }
                    }

                    if (str.startsWith("REMOVE")) {
                        String username = str.substring(str.indexOf(":") + 1);
                        for (int i = 0; i < users.size(); i++) {
                            User u = (User) users.elementAt(i);
                            String usr = u.getUserName();
                            if (usr.equals(username)) {
                                users.removeElementAt(i);
                                dm.removeElement(username);
                            }
                            InetAddress ip = u.getIp();
                            int port = u.getPort();
                            byte arr[] = str.getBytes();
                            DatagramPacket dp1 = new DatagramPacket(arr, arr.length, ip, port);
                            ds1.send(dp1);
                        }

                    }

                    if (str.startsWith("SENDALL")) {
                        String msg = str.substring(str.indexOf(":") + 1);
                        jTextArea1.append(msg + "\n");
                        for (int i = 0; i < users.size(); i++) {
                            User u = (User) users.elementAt(i);
                            InetAddress ip = u.getIp();
                            int port = u.getPort();
                            byte arr[] = str.getBytes();
                            jTextArea1.setCaretPosition(jTextArea1.getText().length());
                            DatagramPacket dp1 = new DatagramPacket(arr, arr.length, ip, port);
                            ds1.send(dp1);
                        }
                    }
                    if (str.startsWith("PERSONAL")) {
                        int aa = str.indexOf(":");
                        int bb = str.indexOf(":", aa + 1);
                        int cc = str.indexOf(":", bb + 1);
                        String from = str.substring(aa + 1, bb);
                        String to = str.substring(bb + 1, cc);
                        String msg = str.substring(cc + 1);
                        jTextArea1.append("From : " + from + " to " + to + " Message : " + msg + "\n");
                        for (int i = 0; i < users.size(); i++) {
                            User u = (User) users.elementAt(i);
                            InetAddress ip = u.getIp();
                            int port = u.getPort();
                            byte arr[] = str.getBytes();
                            String un = u.getUserName();
                            jTextArea1.setCaretPosition(jTextArea1.getText().length());
                            if (un.equals(to)) {
                                DatagramPacket dp1 = new DatagramPacket(arr, arr.length, ip, port);
                                ds1.send(dp1);
                            }
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ChatJPanel2.this, "Unable to Communicate");
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Chatting Application Server");
        f.setContentPane(new ChatJPanel2());
        f.setBounds(10, 10, 600, 450);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run() {
        while (true) {
            boolean flag = true;
            byte b[] = new byte[256];
            DatagramPacket dp = new DatagramPacket(b, b.length);
            try {
                ds.receive(dp);
                String str = new String(b, 0, b.length);

                un = str.substring(0, str.indexOf(","));
                String ps = str.substring(str.indexOf(",") + 1, str.lastIndexOf(","));
                InetAddress ia = dp.getAddress();
                int port = dp.getPort();
                str = str.trim();
                System.out.println(str);
                if (str.endsWith("ON")) {
                    System.out.println("Here");
                    Statement stmt = con.createStatement();
                    int x = stmt.executeUpdate("Insert into myusers values('" + un + "','" + ps + "')");
                    if (x != 1) {
                        flag = false;
                    }
                    stmt.close();
                } else {
                    Statement stmt = con.createStatement();
                    ResultSet x = stmt.executeQuery("Select * from myusers where username= '" + un + "' and password ='" + ps + "'");
                    if (!x.next()) {
                        flag = false;
                    }
                    stmt.close();
                }
                if (flag) {
                    byte arr[] = "CONNECTED".getBytes();
                    DatagramPacket dp1 = new DatagramPacket(arr, arr.length, ia, port);
                    ds.send(dp1);
                    User u = new User(un, ps, ia, port);
                    users.add(u);
                    dm.addElement(un);
                } else {
                    byte arr[] = "NOTCONNECTED".getBytes();
                    DatagramPacket dp1 = new DatagramPacket(arr, arr.length, ia, port);
                    ds.send(dp1);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Unable to Receive Packet");
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        dm = new DefaultListModel();
        jList1 = new javax.swing.JList(dm);
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        setBackground(new java.awt.Color(227, 225, 225));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Server", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 24), new java.awt.Color(0, 0, 0))); // NOI18N
        setLayout(null);

        jButton1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jButton1.setText("Send To All");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(330, 360, 130, 31);

        jPanel1.setBackground(new java.awt.Color(163, 165, 173));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "User", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 18), new java.awt.Color(51, 51, 255))); // NOI18N

        jList1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jScrollPane3.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1);
        jPanel1.setBounds(20, 50, 230, 300);

        jPanel2.setBackground(new java.awt.Color(163, 165, 173));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Massege", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 18), new java.awt.Color(51, 51, 255))); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2);
        jPanel2.setBounds(260, 50, 310, 300);
        add(jTextField1);
        jTextField1.setBounds(20, 360, 300, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            String ss = "SENDALL:Administrator:" + jTextField1.getText();
            byte b[] = ss.getBytes();
            InetAddress ia = InetAddress.getByName(MainConstants.SERVER_IP);
            DatagramPacket dp = new DatagramPacket(b, b.length, ia, MainConstants.COMMUNICATION_PORT);
            ds.send(dp);
            jTextField1.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to List to Port");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed
    DatagramSocket ds, ds1;
    Connection con;
    Vector users;
    Thread receiver;
    Thread communicator;
    DefaultListModel dm;
    String un;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}

/**
* RITS BHOPAL:CSE 3rd year(6th sem.)"
* SUBMITED BY THE COMMON EFFORT OF"
* 1.VED PRAKASH (0132CS101114)"
* 2.VIKRANT KISHORE (0132CS101117)"
* 3.SHRI KANT (0132CS101092)"
*              VED    ,            VIKRANT           SHRIKANT
*    Phone : 9407511129,        09300676191,      8269571212
*   Email : vedprksh79@gmail.com,getclosetoviki@gmail.com,shrikant8291@gmail.com
*/
