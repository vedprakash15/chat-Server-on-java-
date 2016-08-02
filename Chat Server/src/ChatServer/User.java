/**
 *
/**
 * @author RITS BHOPAL
 * vedprksh79@email.com
 * 9407511129
 */
package ChatServer;

import java.net.*;

public class User {

    String UserName;
    String Passward;
    InetAddress ip;
    int Port;

    public User() {
    }

    public User(String UserName, String Passward, InetAddress ip, int Port) {
        this.UserName = UserName;
        this.Passward = Passward;
        this.ip = ip;
        this.Port = Port;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassward() {
        return Passward;
    }

    public void setPassward(String Passward) {
        this.Passward = Passward;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
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