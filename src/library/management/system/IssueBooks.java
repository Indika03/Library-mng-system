/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.management.system;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static org.hsqldb.lib.StringUtil.isEmpty;

public class IssueBooks extends javax.swing.JFrame {

    Statement stmt;

    public IssueBooks() {
        initComponents();
        db.connect();

        try {
            stmt = db.connect().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(index.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableupdate();
        textISBN.requestFocus();
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
    }

    public void tableupdate() {
        try {
            //   connect();

            String sql = "select * from reserve";
            ResultSet rs = stmt.executeQuery(sql);

            ResultSetMetaData rsd = rs.getMetaData();
            int c = rsd.getColumnCount();
            DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
            dft.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getInt("ID"));
                    v2.add(rs.getInt("ISBN"));
                    v2.add(rs.getString("RegID"));
                    v2.add(rs.getString("BorrowDate"));
                    v2.add(rs.getString("ReturnDate"));

                }
                dft.addRow(v2);

            }
        } catch (SQLException ex) {

        }
    }

    public void ComboAuthor(String Aid) {
        try {

            String sql = "select * from author where AuthorID = '" + Aid + "'  ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                lblAuthor.setText(rs.getString("Name"));
            }
          
        } catch (SQLException ex) {

        }
    }

    public void bookdetails(String isbn) {
        try {
            String sql = "select * from book where ISBN = '" + isbn + "';  ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String AID = rs.getString("AuthorID");
                lblBookID.setText(rs.getString("Book_ID"));
                lblISBN.setText(rs.getString("ISBN"));
                lblTitle.setText(rs.getString("Title"));

                //  lblYear.setText(rs.getString("Year"));
                int n = rs.getInt("AvailCopies");
                if (n == 0) {
                    Color enterClr = new Color(200, 0, 0);
                    lblAvail.setForeground(enterClr);
                    lblAvail.setText(Integer.toString(n));
                    jButton1.setEnabled(false);
                } else {
                    Color enterClr = new Color(0, 255, 0);
                    lblAvail.setForeground(enterClr);
                    lblAvail.setText(rs.getString("AvailCopies"));
                }

                ComboAuthor(AID);
            }
           

        } catch (SQLException ex) {
            Logger.getLogger(IssueBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void memberdetails(String reg) {
        try {
            String sql = "select * from member where RegID = '" + reg + "'  ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                lblRegID.setText(rs.getString("RegID"));
                lblName.setText(rs.getString("Name"));
                String AS = (rs.getString("ActiveStatus"));
                int as = Integer.parseInt(AS);
                if (as == 1) {
                    lblActive.setText("Yes");
                     Color enterClr = new Color(0, 255, 0);
                    lblActive.setForeground(enterClr);
                } else {
                    Color enterClr = new Color(200, 0, 0);
                    lblActive.setForeground(enterClr);
                    lblActive.setText("No");
                    jButton1.setEnabled(false);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(IssueBooks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textRegID = new javax.swing.JTextField();
        textISBN = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        lblBookID = new javax.swing.JLabel();
        lblISBN = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblAuthor = new javax.swing.JLabel();
        lblAvail = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblActive = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblRegID = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        lblRid = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        lblBookID1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Issue / return Books");
        setAutoRequestFocus(false);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(153, 153, 255));
        jLabel5.setFont(new java.awt.Font("OCR A Extended", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/delete_16x16.gif"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 40, 20));

        jLabel8.setBackground(new java.awt.Color(153, 153, 255));
        jLabel8.setFont(new java.awt.Font("OCR A Extended", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/delete_16x16.gif"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 30, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Book Details");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Member Details");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(376, 51, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ISBN");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 94, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Reg ID");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        textRegID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textRegIDKeyReleased(evt);
            }
        });
        jPanel1.add(textRegID, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 190, -1));

        textISBN.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                textISBNPropertyChange(evt);
            }
        });
        textISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textISBNKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textISBNKeyReleased(evt);
            }
        });
        jPanel1.add(textISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 91, 190, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Author");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        lblBookID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBookID.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblBookID, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 180, 20));

        lblISBN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblISBN.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblISBN, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 180, 20));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 180, 20));

        lblAuthor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAuthor.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblAuthor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 180, 20));

        lblAvail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAvail.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblAvail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 180, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Reg ID");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, -1, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Name");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Active Status");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, -1, -1));

        lblActive.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblActive.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblActive, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 200, 17));

        lblName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 190, 200, 17));

        lblRegID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRegID.setForeground(new java.awt.Color(0, 255, 0));
        jPanel1.add(lblRegID, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 150, 200, 17));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/issue.png"))); // NOI18N
        jButton1.setText("Issue");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/erase-128.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, -1, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/delete_16x16.gif"))); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("ID");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 26, 20));

        lblRid.setForeground(new java.awt.Color(255, 255, 102));
        jPanel1.add(lblRid, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 56, 20));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/management/system/Images/return.png"))); // NOI18N
        jButton4.setText("Return");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, -1, -1));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 730, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Available");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        lblBookID1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBookID1.setForeground(new java.awt.Color(255, 255, 0));
        jPanel1.add(lblBookID1, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 156, 134, 14));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ISBN", "Reg ID", "Issued Date", "Returned date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 730, 240));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("ISBN");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Book ID");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Title");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 758, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textISBNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textISBNKeyReleased
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String ib = textISBN.getText();
            //  System.out.print(i);
            bookdetails(ib);
           // textRegID.requestFocus();
        }
    }//GEN-LAST:event_textISBNKeyReleased

    private void textISBNPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_textISBNPropertyChange

    }//GEN-LAST:event_textISBNPropertyChange

    private void textRegIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textRegIDKeyReleased
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String r = textRegID.getText();
        memberdetails(r);
           
        }
        
        
    }//GEN-LAST:event_textRegIDKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //  if(!empty(textISBN.getText() ) ){
        String IS = textISBN.getText();
        String reg = textRegID.getText();
        if (IS.length() == 0 || reg.length() == 0) {
            JOptionPane.showMessageDialog(null, "Enter the ISBN and Reg ID!", "Ooops!", JOptionPane.ERROR_MESSAGE);
        } else {
            try {

                String query = "insert into reserve values(0,'" + textISBN.getText() + "','" + textRegID.getText() + "',now(),null)";
                stmt.executeUpdate(query);
                String q = "update book set AvailCopies=AvailCopies-1 where Book_ID  ='" + lblBookID.getText() + "' ";
                stmt.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "Added");

                tableupdate();

                textRegID.setText("");
                textISBN.setText("");
                lblRegID.setText("");
                lblActive.setText("");
                lblName.setText("");
                lblBookID.setText("");
                lblISBN.setText("");
                lblTitle.setText("");
                lblAuthor.setText("");
                //    lblYear.setText("");
                lblAvail.setText("");
                textISBN.requestFocus();

            } catch (SQLException ex) {

            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        textRegID.setText("");
        textISBN.setText("");
        lblRegID.setText("");
        lblActive.setText("");
        lblName.setText("");
        lblBookID.setText("");
        lblISBN.setText("");
        lblTitle.setText("");
        lblAuthor.setText("");
        //  lblYear.setText("");
        lblAvail.setText("");
        lblRid.setText("");

        jButton1.setEnabled(true);
        jButton3.setEnabled(false);
        textISBN.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void textISBNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textISBNKeyPressed
        
    }//GEN-LAST:event_textISBNKeyPressed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel dft = (DefaultTableModel) jTable1.getModel();
        int selectedIndex = jTable1.getSelectedRow();

        jButton1.setEnabled(false);
        //  String r = dft.getValueAt(selectedIndex, 4).toString();
        //   System.out.print(r);
        Object r = dft.getValueAt(selectedIndex, 4);
        if (r == null || r.toString().isEmpty()) {
            jButton4.setEnabled(true);
            jButton3.setEnabled(true);
        } else {
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
        }
        //  jButton3.setEnabled(true);
        //  jButton4.setEnabled(true);
        textRegID.setText(dft.getValueAt(selectedIndex, 2).toString());
        textISBN.setText(dft.getValueAt(selectedIndex, 1).toString());
        lblRid.setText(dft.getValueAt(selectedIndex, 0).toString());
        String m = dft.getValueAt(selectedIndex, 2).toString();
        String b = dft.getValueAt(selectedIndex, 1).toString();

        memberdetails(m);
        bookdetails(b);

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int dialogResult = JOptionPane.showConfirmDialog(null, "DO YOU WANT TO DELETE THE RECORD ? ", "WARNING", JOptionPane.YES_NO_OPTION);

        if (lblRid.getText().length() != 0) {
            if (dialogResult == JOptionPane.YES_NO_OPTION) {
                try {

                    String query = "delete from reserve Where ID ='" + lblRid.getText() + "' ";
                    //  query = "delete from book Where ISBN=24245 ";
                    stmt.executeUpdate(query);

                    JOptionPane.showMessageDialog(this, "ROW DELETED SUCCESSFULLY. ");

                    textRegID.setText("");
                    textISBN.setText("");
                    lblRegID.setText("");
                    lblActive.setText("");
                    lblName.setText("");
                    lblBookID.setText("");
                    lblISBN.setText("");
                    lblTitle.setText("");
                    lblAuthor.setText("");
                    //   lblYear.setText("");
                    lblAvail.setText("");
                    lblRid.setText("");
                    jButton1.setEnabled(true);
                    textISBN.requestFocus();

                } catch (SQLException ex) {
                }
            } else {
                //   JOptionPane.showMessageDialog(this, "ROWS NOT DELETED ");
                JOptionPane.showMessageDialog(null, "Row not deleted!", "Ooops!", JOptionPane.ERROR_MESSAGE);
                textRegID.setText("");
                textISBN.setText("");
                lblRegID.setText("");
                lblActive.setText("");
                lblName.setText("");
                lblBookID.setText("");
                lblISBN.setText("");
                lblTitle.setText("");
                lblAuthor.setText("");
                //   lblYear.setText("");
                lblAvail.setText("");
                lblRid.setText("");

                jButton1.setEnabled(true);

            }
        }

        tableupdate();


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {

            String query = "update reserve set ReturnDate=now() where ID  ='" + lblRid.getText() + "' ";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Returned");
            System.out.print(lblBookID.getText());
            String q = "update book set AvailCopies = AvailCopies+1 where Book_ID = '" + lblBookID.getText() + "' ";
            stmt.executeUpdate(q);

            tableupdate();
            textRegID.setText("");
            textISBN.setText("");
            lblRegID.setText("");
            lblActive.setText("");
            lblName.setText("");
            lblBookID.setText("");
            lblISBN.setText("");
            lblTitle.setText("");
            lblAuthor.setText("");
            // lblYear.setText("");
            lblAvail.setText("");
            lblRid.setText("");
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton1.setEnabled(true);
            textISBN.requestFocus();

        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel ob = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        jTable1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(jTextField1.getText()));
    }//GEN-LAST:event_jTextField1KeyReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        home j = new home();
        j.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        textISBN.setText("");
        lblBookID.setText("");
            lblISBN.setText("");
            lblTitle.setText("");
            lblAuthor.setText("");
            lblAvail.setText("");
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
         textRegID.setText("");
         lblRegID.setText("");
            lblActive.setText("");
            lblName.setText("");
    }//GEN-LAST:event_jLabel8MouseClicked

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
            java.util.logging.Logger.getLogger(IssueBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IssueBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IssueBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IssueBooks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBooks().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblActive;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblAvail;
    private javax.swing.JLabel lblBookID;
    private javax.swing.JLabel lblBookID1;
    private javax.swing.JLabel lblISBN;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRegID;
    private javax.swing.JLabel lblRid;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField textISBN;
    private javax.swing.JTextField textRegID;
    // End of variables declaration//GEN-END:variables

    private boolean empty(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
