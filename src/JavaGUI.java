import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class JavaGUI {
    public static ResultSet rs;
    public static String name="", street="", city="", state="", zip="";
    public static int id;

    public static void main(String[] args) {
        JFrame f = new JFrame();
        JLabel labelId = new JLabel("id: ");
        JLabel labelName = new JLabel("Name: ");
        JLabel labelStreet = new JLabel("Street: ");
        JLabel labelCity = new JLabel("City: ");
        JLabel labelState = new JLabel("State: ");
        JLabel labelZip = new JLabel("Zip: ");
        JTextField textId = new JTextField(10);
        JTextField textName = new JTextField(10);
        JTextField textStreet = new JTextField(10);
        JTextField textCity = new JTextField(10);
        JTextField textState = new JTextField(10);
        JTextField textZip = new JTextField(10);

        JButton buttonNext = new JButton("Next");
        JButton buttonPrev = new JButton("Previous");
        JButton buttonDelete = new JButton("Delete");
        JButton buttonAdd = new JButton("Add");
        JButton buttonUpdate = new JButton("Update");
        ConnectToSQL connectToSQL = new ConnectToSQL();
        Connection con = null;
        Statement st = null;

        try{
            con = connectToSQL.getConnection(3306, "test_create_db");
            st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("SELECT * FROM data");
            if(rs.next()){
                id=rs.getInt("id");
                name=rs.getString("name");
                street=rs.getString("street");
                city=rs.getString("city");
                state=rs.getString("state");
                zip=rs.getString("zip");

                textId.setText(String.valueOf(id));
                textName.setText(name);
                textStreet.setText(street);
                textCity.setText(city);
                textState.setText(state);
                textZip.setText(zip);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        JPanel p=new JPanel(new GridLayout(3,2));
        p.add(labelId); p.add(textId);
        p.add(labelName); p.add(textName);
        p.add(labelStreet); p.add(textStreet);
        p.add(labelCity); p.add(textCity);
        p.add(labelState); p.add(textState);
        p.add(labelZip); p.add(textZip);

        //Button Code Block
        p.add(buttonNext);
        buttonNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if(rs.next()) {
                        id=rs.getInt("id");
                        name = rs.getString("name");
                        street = rs.getString("street");
                        city = rs.getString("city");
                        state = rs.getString("state");
                        zip = rs.getString("zip");

                        textId.setText(String.valueOf(id));
                        textName.setText(name);
                        textStreet.setText(street);
                        textCity.setText(city);
                        textState.setText(state);
                        textZip.setText(zip);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        p.add(buttonPrev);
        buttonPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if(rs.previous()) {
                        id=rs.getInt("id");
                        name = rs.getString("name");
                        street = rs.getString("street");
                        city = rs.getString("city");
                        state = rs.getString("state");
                        zip = rs.getString("zip");

                        textId.setText(String.valueOf(id));
                        textName.setText(name);
                        textStreet.setText(street);
                        textCity.setText(city);
                        textState.setText(state);
                        textZip.setText(zip);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        p.add(buttonAdd);
        Statement finalSt = st;
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    id = Integer.parseInt(textId.getText());
                    name = textName.getText();
                    street = textStreet.getText();
                    city = textCity.getText();
                    state = textState.getText();
                    zip = textZip.getText();

                    finalSt.executeUpdate("INSERT INTO `data`(`id`, `name`, `street`, `city`, `state`, `zip`) " +
                            "VALUES ('" + id + "','" + name + "','" + street + "','" + city + "','" + state + "','" + zip + "')");

                    rs=finalSt.executeQuery("SELECT * FROM data");
                    if(rs.next()){
                        id=rs.getInt("id");
                        name = rs.getString("name");
                        street = rs.getString("street");
                        city = rs.getString("city");
                        state = rs.getString("state");
                        zip = rs.getString("zip");

                        textId.setText(String.valueOf(id));
                        textName.setText(name);
                        textStreet.setText(street);
                        textCity.setText(city);
                        textState.setText(state);
                        textZip.setText(zip);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        p.add(buttonDelete);
        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    finalSt.executeUpdate("DELETE FROM `data` WHERE id=" + id);
                    rs=finalSt.executeQuery("SELECT * FROM data");
                    if(rs.next()){
                        id=rs.getInt("id");
                        name = rs.getString("name");
                        street = rs.getString("street");
                        city = rs.getString("city");
                        state = rs.getString("state");
                        zip = rs.getString("zip");

                        textId.setText(String.valueOf(id));
                        textName.setText(name);
                        textStreet.setText(street);
                        textCity.setText(city);
                        textState.setText(state);
                        textZip.setText(zip);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        p.add(buttonUpdate);
        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    int currentId = id;
                    id = Integer.parseInt(textId.getText());
                    name = textName.getText();
                    street = textStreet.getText();
                    city = textCity.getText();
                    state = textState.getText();
                    zip = textZip.getText();

                    finalSt.executeUpdate("UPDATE `data` SET `id`='" + id + "',`name`='" + name + "',`street`='"
                    + street + "',`city`='" + city + "',`state`='" + state + "',`zip`='" + zip + "' WHERE id=" + currentId);

                    rs=finalSt.executeQuery("SELECT * FROM data");

                    if(rs.next()){
                        id=rs.getInt("id");
                        name = rs.getString("name");
                        street = rs.getString("street");
                        city = rs.getString("city");
                        state = rs.getString("state");
                        zip = rs.getString("zip");

                        textId.setText(String.valueOf(id));
                        textName.setText(name);
                        textStreet.setText(street);
                        textCity.setText(city);
                        textState.setText(state);
                        textZip.setText(zip);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });

        f.add(p);
        f.setVisible(true);
        f.pack();
    }
}