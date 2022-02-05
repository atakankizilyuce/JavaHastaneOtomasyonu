package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;
import Model.User;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JLabel lblNewLabel;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 330);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 11, 71, 28);
		w_pane.add(lblNewLabel_1);
		
		fld_name = new JTextField();
		fld_name.setColumns(10);
		fld_name.setBounds(10, 38, 264, 20);
		w_pane.add(fld_name);
		
		JLabel lblNewLabel_1_1 = new JLabel("T.C. Numarasý");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 69, 154, 28);
		w_pane.add(lblNewLabel_1_1);
		
		fld_tcno = new JTextField();
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 96, 264, 20);
		w_pane.add(fld_tcno);
		
		lblNewLabel = new JLabel("Þifre");
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 127, 71, 28);
		w_pane.add(lblNewLabel);
		
		fld_pass = new JPasswordField();
		fld_pass.setBounds(10, 154, 264, 20);
		w_pane.add(fld_pass);
		
		JButton btn_register = new JButton("Kayýt Ol");//kayýt ol butonu
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length()==0 || fld_pass.getText().length() ==0 || fld_name.getText().length() ==0 ) {
					Helper.showMsg("fill");
				}else {
					try {
						boolean control = hasta.register(fld_tcno.getText(), fld_pass.getText(), fld_name.getText());
						if(control) {
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							dispose();
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_register.setBounds(10, 185, 264, 36);
		w_pane.add(btn_register);
		
		JButton btn_backto = new JButton("Geri Dönüþ");//ana ekrana dönmek için oluþturulan buton
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_backto.setBounds(10, 232, 264, 36);
		w_pane.add(btn_backto);
	}
}
