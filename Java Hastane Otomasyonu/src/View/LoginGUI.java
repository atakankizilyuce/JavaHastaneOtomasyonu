package View;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 800);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoþ geldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel.setBounds(156, 323, 404, 56);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(145, 390, 404, 289);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta giriþi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("T.C. Numaranýz :");
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 42, 141, 27);
		w_hastaLogin.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Þifreniz :");
		lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 90, 141, 27);
		w_hastaLogin.add(lblNewLabel_2);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("MS PGothic", Font.PLAIN, 16));
		fld_hastaTc.setBounds(144, 46, 126, 20);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();			
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btn_register.setBounds(42, 169, 141, 50);
		w_hastaLogin.add(btn_register);
		
		JButton btn_hastaLogin = new JButton("Giri\u015F Yap"); //Hasta giriþ butonu
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length()==0 || fld_hastaPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					boolean key = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
								}
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Böyle bir hasta bulunamadý lütfen kayýt olunuz !");
					}
				}
			}
		});
		btn_hastaLogin.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		btn_hastaLogin.setBounds(221, 169, 141, 50);
		w_hastaLogin.add(btn_hastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(144, 96, 126, 20);
		w_hastaLogin.add(fld_hastaPass);
		
		JPanel w_doktorLogin = new JPanel();
		w_doktorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþi", null, w_doktorLogin, null);
		w_doktorLogin.setLayout(null);
		
		JPanel w_hastaLogin_1 = new JPanel();
		w_hastaLogin_1.setLayout(null);
		w_hastaLogin_1.setBackground(Color.WHITE);
		w_hastaLogin_1.setBounds(0, 0, 399, 261);
		w_doktorLogin.add(w_hastaLogin_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C. Numaran\u0131z :");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(10, 42, 141, 27);
		w_hastaLogin_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("\u015Eifreniz :");
		lblNewLabel_2_1.setFont(new Font("Segoe UI Black", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(10, 90, 94, 27);
		w_hastaLogin_1.add(lblNewLabel_2_1);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("MS PGothic", Font.PLAIN, 16));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(144, 46, 126, 20);
		w_hastaLogin_1.add(fld_doctorTc);
		
		JButton btn_doctorLogin = new JButton("Giri\u015F Yap"); //doktor giriþ butonu
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doctorTc.getText().length()==0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}
				else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) { //eðer giriþ yapan doktorun rütbesi baþhekim ise
								if(rs.getString("type").equals("basHekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								if(rs.getString("type").equals("doktor")) {//eðer giriþ yapan kiþinin rütbesi doktor ise
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
									
								}
								
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_doctorLogin.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		btn_doctorLogin.setBounds(38, 171, 327, 50);
		w_hastaLogin_1.add(btn_doctorLogin);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(144, 96, 126, 20);
		w_hastaLogin_1.add(fld_doctorPass);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("ss.gif")));
		lbl_logo.setBounds(174, 11, 328, 302);
		w_pane.add(lbl_logo);
	}
}
