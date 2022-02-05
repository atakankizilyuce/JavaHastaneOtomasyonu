package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;
import Model.Appointment;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData =null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData =null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public HastaGUI(Hasta hasta) throws SQLException {
		
		doctorModel = new DefaultTableModel(); //doktorlar hastalar için listeleniyor
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];
		
		whourModel = new DefaultTableModel(); //uygun saatler hastalar için listeleniyor
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		
		appointModel = new DefaultTableModel(); //hastalarýn aldýðý randevular listeleniyor
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "Doktor";
		colAppoint[2] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
		for (int i =0; i<appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
	
		
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoþ geldiniz Sayýn "+ hasta.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 590, 35);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		btnNewButton.setBounds(610, 14, 114, 29);
		w_pane.add(btnNewButton);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBackground(Color.WHITE);
		w_tab.setBounds(10, 89, 714, 361);
		w_pane.add(w_tab);
		
		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);
		
		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(20, 41, 255, 281);
		w_appointment.add(w_scrollDoctor);
		
		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);
		
		JLabel lblNewLabel_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1.setBounds(98, 11, 121, 28);
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		w_appointment.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Poliklinik Ad\u0131");
		lblNewLabel_1_1.setBounds(319, 41, 85, 28);
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		w_appointment.add(lblNewLabel_1_1);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(301, 68, 121, 22);
		select_clinic.addItem("--Poliklinik Seç--");
		for (int i = 0; i< clinic.getList().size(); i++) { //polikliniklerin hastalar için sýralandýðý kýsým
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(select_clinic.getSelectedIndex() !=0) {
					JComboBox  c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
					try {
						for (int i =0; i< clinic.getClinicDoctorList(item.getKey()).size();i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}
			}
			
		});
		w_appointment.add(select_clinic);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Doktor Se\u00E7");
		lblNewLabel_1_3_1.setBounds(319, 101, 85, 28);
		lblNewLabel_1_3_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		w_appointment.add(lblNewLabel_1_3_1);
		
		JButton btn_selDoctor = new JButton("Se\u00E7"); //seçilen doktorun randevu zamanlarýný hastalarýn görebildiði kýsým
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row>=0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i =0; i<whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_whour.setModel(whourModel);
					selectDoctorID=id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
				}else {
					Helper.showMsg("Lütfen bir doktor seçiniz");
				}
			}
		});
		btn_selDoctor.setBounds(301, 129, 120, 28);
		w_appointment.add(btn_selDoctor);
		
		JLabel lblNewLabel_1_2 = new JLabel("Uygun Saatler");
		lblNewLabel_1_2.setBounds(520, 11, 121, 28);
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		w_appointment.add(lblNewLabel_1_2);
		
		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(444, 41, 255, 281);
		w_appointment.add(w_scrollWhour);
		
		table_whour = new JTable();
		w_scrollWhour.setViewportView(table_whour);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Randevu ");
		lblNewLabel_1_3_1_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_3_1_1.setBounds(319, 189, 85, 28);
		w_appointment.add(lblNewLabel_1_3_1_1);
		
		JButton btn_addAppoint = new JButton("Randevul Al"); //seçilen doktor ve randevu zamanýnýn veritabanýna eklenmesi için ilgili buton
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if(selRow>=0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName, hasta.getName(), date);
						if(control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz !");
				}
			}
		});
		btn_addAppoint.setBounds(301, 217, 120, 28);
		w_appointment.add(btn_addAppoint);
		
		JPanel w_appoint = new JPanel();
		w_tab.addTab("Randevularým", null, w_appoint, null);
		w_appoint.setLayout(null);
		
		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 689, 311);
		w_appoint.add(w_scrollAppoint);
		
		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
	}
	public void updateWhourModel(int doctor_id) throws SQLException {//hastalar için tekrardan uygun saatleri listelenen kýsým
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i =0; i< whour.getWhourList(doctor_id).size();i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		
	}
 }
	
	public void updateAppointModel(int hasta_id) throws SQLException { //hastalar için güncellenmiþ bir biçimde randevularýn listelenmesi
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i =0; i<appoint.getHastaList(hasta_id).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
		
	}
}
