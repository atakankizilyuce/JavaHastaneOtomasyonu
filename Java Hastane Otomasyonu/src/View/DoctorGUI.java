package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public DoctorGUI(Doctor doctor) throws SQLException {

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) { //eklenen randevularýn sýralandýðý kýsým
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}

		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoþ geldiniz Sayýn," + doctor.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 211, 35);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çýkýþ Yap");
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
		w_tab.setBounds(10, 111, 714, 339);
		w_pane.add(w_tab);

		JPanel w_whour = new JPanel();
		w_whour.setBackground(Color.WHITE);
		w_tab.addTab("Çalýþma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 100, 20);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setModel(
				new DefaultComboBoxModel(new String[] { "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", //combo box yapýsýnda sýralanmasý için girilen saatler
						"12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00" }));
		select_time.setBounds(120, 11, 61, 22);
		w_whour.add(select_time);

		JButton btn_addWhour = new JButton("Randevu Ekle"); //seçilen bilgilerin veritabanýna eklendiði kýsým
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {

				}
				if (date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih girin");
				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		btn_addWhour.setBounds(191, 11, 128, 23);
		w_whour.add(btn_addWhour);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 42, 709, 258);
		w_whour.add(scrollPane);

		table_whour = new JTable(whourModel);
		scrollPane.setViewportView(table_whour);

		JButton btn_delWhour = new JButton("Randevu Sil"); //veritabanýna eklenen randevunun silinmesi için oluþturduðumuz buton
		btn_delWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String selectedRow = table_whour.getModel().getValueAt(selRow, 0).toString();
					int selID = Integer.parseInt(selectedRow);
					boolean control;
					try {
						control = doctor.deleteWhour(selID);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
		btn_delWhour.setBounds(571, 8, 128, 23);
		w_whour.add(btn_delWhour);

	}

	public void updateWhourModel(Doctor doctor) throws SQLException { // randevularýn güncel bir þekilde listelenmesi için oluþturduðumuz metot
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
			whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
			whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
}
