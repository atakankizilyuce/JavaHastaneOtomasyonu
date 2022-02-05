package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {
	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTcno;
	private JPasswordField fld_dPass;
	private JTextField fld_doctorID;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JTable table_clinic;
	private JPopupMenu clinicMenu;
	private JTable table_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
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
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		// Doktor model
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC No";
		colDoctorName[3] = "Þifre";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		try {
			for (int i = 0; i < bashekim.getDoctorList().size(); i++) { //model paketinde bulunan bashekim sýnýfýndaki doktor listeleme metodu çaðrýlýyor.
				doctorData[0] = bashekim.getDoctorList().get(i).getId();
				doctorData[1] = bashekim.getDoctorList().get(i).getName();
				doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
				doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
				doctorModel.addRow(doctorData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Klinik Model
		//kliniklerin listelenir
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adý";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		//Worker Model
		//hangi doktor hangi klinikde
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];
		

		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoþgeldiniz, Sayýn " + bashekim.getName());
		lblNewLabel.setBounds(10, 11, 569, 35);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(610, 14, 114, 29);
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBackground(Color.WHITE);
		w_tab.setBounds(10, 111, 714, 339);
		w_pane.add(w_tab);

		JPanel w_doctor = new JPanel();
		w_doctor.setToolTipText("");
		w_tab.addTab("Doktor Y\u00F6netimi", null, w_doctor, null);
		w_doctor.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(557, 11, 71, 28);
		w_doctor.add(lblNewLabel_1);

		fld_dName = new JTextField();
		fld_dName.setBounds(557, 38, 121, 20);
		w_doctor.add(fld_dName);
		fld_dName.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("T.C. No");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(557, 57, 71, 28);
		w_doctor.add(lblNewLabel_1_1);

		fld_dTcno = new JTextField();
		fld_dTcno.setColumns(10);
		fld_dTcno.setBounds(557, 82, 121, 20);
		w_doctor.add(fld_dTcno);

		JLabel lblNewLabel_1_1_1 = new JLabel("\u015Eifre");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(557, 103, 71, 20);
		w_doctor.add(lblNewLabel_1_1_1);

		JButton btn_addDoctor = new JButton("Ekle"); //doktor ekle butonu
		btn_addDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dName.getText().length() == 0 || fld_dPass.getText().length() == 0
						|| fld_dTcno.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = bashekim.addDoctor(fld_dTcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addDoctor.setBounds(557, 156, 121, 34);
		w_doctor.add(btn_addDoctor);

		fld_dPass = new JPasswordField();
		fld_dPass.setBounds(557, 125, 121, 20);
		w_doctor.add(fld_dPass);

		fld_doctorID = new JTextField();
		fld_doctorID.setEditable(false);
		fld_doctorID.setBounds(557, 226, 121, 20);
		w_doctor.add(fld_doctorID);
		fld_doctorID.setColumns(10);

		JLabel lblNewLabel_1_2 = new JLabel("Kullan\u0131c\u0131 ID");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(557, 201, 71, 28);
		w_doctor.add(lblNewLabel_1_2);

		JButton btn_delDoctor = new JButton("Sil"); //doktor sil butonu
		btn_delDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorID.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz !");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean control = bashekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_doctorID.setText(null);
								updateDoctorModel();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delDoctor.setBounds(557, 257, 121, 34);
		w_doctor.add(btn_delDoctor);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 11, 535, 289);
		w_doctor.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel); //hazýrlanan verilerin table'a eklenmesi
		w_scrollDoctor.setViewportView(table_doctor);
		

		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {

				}

			}
		});

		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) { //baþhekimin ekranýnda listelenen doktorlarýn bilgilerinin deðiþtirilebildiði kýsým
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectpass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					try {
						boolean control = bashekim.updateDoctor(selectID, selectTcno, selectpass, selectName);
						if (control) {
							Helper.showMsg("success");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(Color.WHITE);
		w_tab.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 261, 289);
		w_clinic.add(w_scrollClinic);
		
		JPopupMenu clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //sýralanan kliniklere güncelleme iþlemlerini yapabildiðimiz kýsým
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
		deleteMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { //seçilen kliniðin silme iþleminin yapýldýðý kýsým
				if (Helper.confirm("sure")) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());
				try {
					if(clinic.deleteClinic(selID)) {
						Helper.confirm("success");
						updateClinicModel();
					}else {
						Helper.showMsg("error");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				}
			}
		
		});
		
		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		w_scrollClinic.setViewportView(table_clinic);
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override 
			public void mousePressed(MouseEvent e) {//týklanan kliniðin seçildiði kýsým
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		JLabel lblNewLabel_1_3 = new JLabel("Poliklinik Ad\u0131");
		lblNewLabel_1_3.setBounds(312, 11, 85, 28);
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		w_clinic.add(lblNewLabel_1_3);

		fld_clinicName = new JTextField();
		fld_clinicName.setBounds(281, 38, 138, 28);
		fld_clinicName.setColumns(10);
		w_clinic.add(fld_clinicName);

		JButton btn_workerSelect = new JButton("Se\u00E7"); //seçilen klinikte randevusu bulunan doktorlarý listelediðimiz kýsým
		btn_workerSelect.setBounds(281, 143, 138, 28);
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if(selRow >0) {
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i =0; i<bashekim.getClinicDoctorList(selClinicID).size(); i++) {
						workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
						workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
						workerModel.addRow(workerData);
						}
					}catch(SQLException e1){
						e1.printStackTrace();
						}
					table_worker.setModel(workerModel);	
					}else {
						Helper.showMsg("Lütfen bir poliklinik seçiniz !");
					}
				
			}
		});
		w_clinic.add(btn_workerSelect);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(429, 11, 270, 289);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);
		
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(281, 221, 138, 28);
		for (int i=0; i<bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : "+item.getValue());
		});
		w_clinic.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle"); //seçilen kliniðe seçilen doktorun eklendiði kýsým
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_clinic.getSelectedRow();
				if(selRow>0) { 
					String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicID = Integer.parseInt(selClinic);
					Item doctorItem = (Item) select_doctor.getSelectedItem();
					try {
						boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
						if(control) {
							Helper.showMsg("success");
							DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for(int i =0; i<bashekim.getClinicDoctorList(selClinicID).size(); i++) {
								workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
								workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
								workerModel.addRow(workerData);
							}
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					Helper.showMsg("Lütfen bir poliklinik seçiniz !");
				}
			}
		});
		btn_addWorker.setBounds(281, 260, 138, 28);
		w_clinic.add(btn_addWorker);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Poliklinik Se\u00E7");
		lblNewLabel_1_3_1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
		lblNewLabel_1_3_1.setBounds(312, 115, 85, 28);
		w_clinic.add(lblNewLabel_1_3_1);
		
		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_clinicName.getText().length()==0) {
					Helper.showMsg("fill");
				}else {
					try {
						if(clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addClinic.setBounds(281, 72, 138, 28);
		w_clinic.add(btn_addClinic);

	}

	public void updateDoctorModel() throws SQLException { // doktorlar için iþlem bittikten sonra güncel bir þekilde sýralanmasý için oluþturduðumuz metot
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);

		}
	}
	public void updateClinicModel() throws SQLException { // klinikler için iþlem bittikten sonra güncel bir þekilde sýralanmasý için oluþturduðumuz metot
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel(); 
		clearModel.setRowCount(0);
		for(int i = 0; i< clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
