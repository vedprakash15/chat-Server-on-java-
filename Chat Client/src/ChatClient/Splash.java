/**
 *
/**
 * @author RITS BHOPAL
 * vedprksh79@email.com
 * 9407511129
 */
package ChatClient;

import javax.swing.*;
import java.awt.*;

public class Splash {

    public static void main(String[] args) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - 480) / 2;
        int y = (screen.height - 500) / 2;
        JWindow win = new JWindow();
        SplashPanel s = new SplashPanel();
        win.setContentPane(s);
        win.setBounds(x, y, 480, 500);
        win.setVisible(true);
        try {
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(50);
                s.abc(i);

            }
        } catch (Exception e) {
        }
        win.setVisible(false);
        JFrame f = new JFrame("Client Login");
        f.setContentPane(new ChatJPanel1());
        f.setBounds(10, 10, 450, 325);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
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