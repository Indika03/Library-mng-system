/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.management.system;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Indika
 */
public class Books extends javax.swing.JFrame {

    /**
     * Creates new form AddBooks
     */
    Connection con;
    Statement stmt;
    ResultSet rs;
    String query, query2;

    public Books() {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
       // txtID.setEditable(false);
        txtISBN.requestFocus();
        jTextField1.setText("Search by ISBN or Title");
        jTextField1.setForeground(new Color(153,153,153));
        
        db.connect();
        try {
            stmt = db.connect().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
      
        bookid();      
        tableupdate();
        ComboAuthor();
        ComboCategory();

    }
    
     public void bookid() {
      try{
        String sl = "SELECT `Book_ID`+1 b  FROM `book` order by `Book_ID` DESC limit 1 ";
        ResultSet rs = stmt.executeQuery(sl);
         while (rs.next()) {
       txtID.setText(rs.getString("b"));
         }
    
        } catch (SQLException ex) {

        }
     }

    public void tableupdate() {
        try {
            //   connect();

            String sql = "SELECT * FROM `book` join author on book.AuthorID = author.AuthorID join category on book.ctID = category.ctID ";
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
            dft.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("Book_ID"));
                    v2.add(rs.getString("ISBN"));
                    v2.add(rs.getString("Title"));
                    v2.add(rs.getString("Category"));
                    v2.add(rs.getString("Name"));
                    //   v2.add(rs.getInt("TotalCopies"));
                    v2.add(rs.getInt("AvailCopies"));
                    v2.add(rs.getString("Shel"));
                }
                dft.addRow(v2);

            }
        } catch (SQLException ex) {

        }
    }
    
    public void search() {
        try {
          String sql = "SELECT * FROM `book` join author on book.AuthorID = author.AuthorID join category on book.ctID = category.ctID where ISBN like '"+ jTextField1.getText()+"%' or  Title like '"+ jTextField1.getText()+"%'";
          ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
            dft.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getInt("Book_ID"));
                    v2.add(rs.getInt("ISBN"));
                    v2.add(rs.getString("Title"));
                    v2.add(rs.getString("Category"));
                    v2.add(rs.getString("Name"));
                    v2.add(rs.getInt("AvailCopies"));
                    v2.add(rs.getString("Shel"));
                }
                dft.addRow(v2);
            }
        } catch (SQLException ex) {

        }
    }

    public void ComboAuthor() {
        try {

            String sql = "select * from author";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                comboAuthor.addItem(rs.getString("Name"));
            }
        } catch (SQLException ex) {

        }
    }

    public void ComboCategory() {
        try {

            String sql = "select * from category";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                coboCategory.addItem(rs.getString("Category"));
            }
        } catch (SQLException ex) {

        }
    }
    
    
    public void addButton() {
        String id = txtID.getText();
        //int isbn = Integer.parseInt(txtISBN.getText());
        String isbn = txtISBN.getText();
        String title = txtTitle.getText();
        //  String author = txtAuthor.getText();
       
        int copy = Integer.parseInt(txtCopies.getText());
        String shelv = txtShelv.getText();
       
        String cate = coboCategory.getSelectedItem().toString();
        String author = comboAuthor.getSelectedItem().toString();
        // getAuthorID(author);
        int AID = getAuthorID(author);
        int CID = getCateID(cate);

        try {
            // Check the table fields in DB **************************************
            // *******************************************************************
            //   query = "insert into book values('"+0+"','" + isbn + "','" + title + "','" + desc + "','" + year + "','" + copy + "','" + shelv + "','" + 2 + "','" + 2 + "')";

            query = "insert into book values('0','" + isbn + "','" + title + "','" + copy + "','" + copy + "','" + shelv + "','" + AID + "','" + CID + "')";

            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Success");
            tableupdate();
            txtID.setText("");
            txtISBN.setText("");
            txtTitle.setText("");
            comboAuthor.setSelectedIndex(0);
            coboCategory.setSelectedIndex(0);
           
            txtCopies.setText("");
            txtShelv.setText("");
           bookid();
            txtID.requestFocus();

        } catch (SQLException ex) {

        }
    }

    private int getAuthorID(String authorName) {
        try {
            // System.out.println(authorName);
            String qs = "select AuthorID from author where Name= '" + authorName + "' ";
            // stmt.setString(authorName);

            ResultSet rs = stmt.executeQuery(qs);

            if (rs.next()) {
                return rs.getInt("AuthorID");
                // System.out.print( rs.getString("AuthorID"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private int getCateID(String category) {
        try {
            String qs = "select ctID from category where Category= '" + category + "' ";

            ResultSet rs = stmt.executeQuery(qs);

            if (rs.next()) {
                return rs.getInt("ctID");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        txtISBN = new javax.swing.JTextField();
        txtShelv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCopies = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        coboCategory = new javax.swing.JComboBox<>();
        comboAuthor = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtID = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Books");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Book ID");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ISBN");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Title");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Copies");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Shelv");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));
        jPanel1.add(txtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, 142, -1));
        jPanel1.add(txtISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 142, -1));

        txtShelv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtShelvKeyPressed(evt);
            }
        });
        jPanel1.add(txtShelv, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 142, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Author");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));
        jPanel1.add(txtCopies, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 142, -1));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "ISBN", "Title", "Category", "Author", "Copies", "Shelv"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 691, 210));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/Save-icon.png"))); // NOI18N
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 100, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/update icon.png"))); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 100, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/delete_16x16.gif"))); // NOI18N
        jButton3.setText("REMOVE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 100, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Category");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        jPanel1.add(coboCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, 142, -1));

        jPanel1.add(comboAuthor, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 142, -1));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/erase-128.png"))); // NOI18N
        jButton4.setText("CLEAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 200, 100, -1));

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 590, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Search");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        txtID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtID.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 50, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        addButton();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
        int selectedIndex = jTable1.getSelectedRow();
       // txtID.setEditable(false);
        jButton1.setEnabled(false);
        txtID.setText(dft.getValueAt(selectedIndex, 0).toString());

        txtISBN.setText(dft.getValueAt(selectedIndex, 1).toString());
        txtTitle.setText(dft.getValueAt(selectedIndex, 2).toString());
      
        coboCategory.setSelectedItem(dft.getValueAt(selectedIndex, 3).toString());
        comboAuthor.setSelectedItem(dft.getValueAt(selectedIndex, 4).toString());
        //  txtAuthor.setText(dft.getValueAt(selectedIndex, 5).toString());
        
        txtCopies.setText(dft.getValueAt(selectedIndex, 5).toString());
        txtShelv.setText(dft.getValueAt(selectedIndex, 6).toString());

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
        int selectedIndex = jTable1.getSelectedRow();

        String ss = dft.getValueAt(selectedIndex, 0).toString();

        int dialogResult = JOptionPane.showConfirmDialog(null, "DO YOU WANT TO DELETE THE RECORD ? ", "WARNING", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_NO_OPTION) {
            try {
                //   connect();
                query = "delete from book Where Book_ID='" + ss + "'; ";
                //  query = "delete from book Where ISBN=24245 ";
                stmt.executeUpdate(query);

                
                JOptionPane.showMessageDialog(this, "ROW DELETED SUCCESSFULLY. ");

                txtID.setText("");
                txtISBN.setText("");
                txtTitle.setText("");
                comboAuthor.setSelectedIndex(0);
                coboCategory.setSelectedIndex(0);
               
                txtCopies.setText("");
                txtShelv.setText("");
                bookid();
               // txtID.setEditable(true);
                jButton1.setEnabled(true);
                tableupdate();

            } catch (SQLException ex) {
            }
        } else {
            //  JOptionPane.showMessageDialog(this, "ROWS NOT DELETED ");
            JOptionPane.showMessageDialog(null, "Row not deleted!", "Ooops!", JOptionPane.ERROR_MESSAGE);
            txtID.setText("");
            txtISBN.setText("");
            txtTitle.setText("");
            comboAuthor.setSelectedIndex(0);
            coboCategory.setSelectedIndex(0);
            txtCopies.setText("");
            txtShelv.setText("");
            bookid();
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        /*   DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
        int selectedIndex = jTable1.getSelectedRow();

        String ss2 = dft.getValueAt(selectedIndex, 1).toString();
         */
        try {
            //  connect();
            String id = txtID.getText().toString();
            int isbn = Integer.parseInt(txtISBN.getText());
            String title = txtTitle.getText();
            // String author = txtAuthor.getText();
            int copy = Integer.parseInt(txtCopies.getText());
            String shelv = txtShelv.getText();
            String cate = (String) coboCategory.getSelectedItem();
            String author = (String) comboAuthor.getSelectedItem();

            int AID = getAuthorID(author);
            int CID = getCateID(cate);
          //  System.out.println(CID);
          //  System.out.println(AID);

            //  query = "update book set Title='" + title + "',Description='" + desc + "',ctID='" + cate + "',Author='" + author + "',Year='" + year + "',TotalCopies'" + copy + "',AvailCopies'" + copy + "',Shel'" + shelv + "',ISBN='" + isbn + "' Where Book_ID ='" + id + "';";
            query2 = " UPDATE book SET ISBN='" + isbn + "' ,Title='" + title + "' ,TotalCopies='" + copy + "' ,AvailCopies='" + copy + "' ,Shel='" + shelv + "' ,AuthorID='" + AID + "' ,ctID='" + CID + "' WHERE Book_ID= '" + id + "'  ";

// query = "insert into book values('" + isbn + "','" + title + "','" + desc + "','" + author + "','" + cate + "','" + year + "','" + copy + "','" + copy + "','" + shelv + "')";
            //    PreparedStatement stmt = con.prepareStatement(sql1);
/*
            stmt.setString(1, name1);
            stmt.setInt(2, cno1);
            stmt.setString(3, mail1);
            stmt.setInt(4, ss);
             */
            stmt.executeUpdate(query2);

            tableupdate();
            JOptionPane.showMessageDialog(this, "ROW UPDATED SUCCESSFULLY. ");

            txtID.setText("");
            txtISBN.setText("");
            txtTitle.setText("");
            comboAuthor.setSelectedIndex(0);
            coboCategory.setSelectedIndex(0);
            txtCopies.setText("");
            txtShelv.setText("");
           bookid();
            jButton1.setEnabled(true);

        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        txtID.setText("");
        txtISBN.setText("");
        txtTitle.setText("");
        comboAuthor.setSelectedIndex(0);
        coboCategory.setSelectedIndex(0);
        txtCopies.setText("");
        txtShelv.setText("");
        jButton1.setEnabled(true);
        bookid();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
     search();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        home j = new home();
        j.setVisible(true);
        this.dispose();
       // home.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        if( jTextField1.getText().equals("Search by ISBN or Title")){
        jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if( jTextField1.getText().equals("")){
        jTextField1.setText("Search by ISBN or Title");
        jTextField1.setForeground(new Color(153,153,153));
         }
    }//GEN-LAST:event_jTextField1FocusLost

    private void txtShelvKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShelvKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            addButton();
       }
    }//GEN-LAST:event_txtShelvKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Books().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> coboCategory;
    private javax.swing.JComboBox<String> comboAuthor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtCopies;
    private javax.swing.JLabel txtID;
    private javax.swing.JTextField txtISBN;
    private javax.swing.JTextField txtShelv;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
