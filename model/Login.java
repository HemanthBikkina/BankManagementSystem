import javax.swing.*;
public class Login extends JFrame {
    Login()
    {
        setSize(800,480);
        setVisible(true);
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    public void setSize(int i, int i1) {
        super.setSize(i,i1);
    }

    public static  void main(String[] args)
    {

        new Login();
    }
}
