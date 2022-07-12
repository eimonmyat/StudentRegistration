package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CourseForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtFee;
	private JTextField txtCourseID;
	private JTextField txtCouseName;
	private JTable tblCourse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseForm frame = new CourseForm();
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
	public CourseForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 646, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Category Name");
		
		JLabel lblNewLabel_1 = new JLabel("Course ID");
		
		JLabel lblNewLabel_2 = new JLabel("Couse Name");
		
		JLabel lblNewLabel_3 = new JLabel("Fee");
		
		txtFee = new JTextField();
		txtFee.setColumns(10);
		
		txtCourseID = new JTextField();
		txtCourseID.setEditable(false);
		txtCourseID.setColumns(10);
		
		txtCouseName = new JTextField();
		txtCouseName.setColumns(10);
		
		JComboBox cboCategoryName = new JComboBox();
		
		JButton btnSave = new JButton("Save");
		
		JButton btnUpdate = new JButton("Update");
		
		JButton btnDelete = new JButton("Delete");
		
		JButton btnClose = new JButton("Close");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(60)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnSave)
								.addGap(30)
								.addComponent(btnUpdate)
								.addGap(37)
								.addComponent(btnDelete)
								.addGap(38)
								.addComponent(btnClose)
								.addGap(148))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_1)
									.addComponent(lblNewLabel_2)
									.addComponent(lblNewLabel_3)
									.addComponent(lblNewLabel))
								.addGap(57)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(cboCategoryName, 0, 103, Short.MAX_VALUE)
									.addComponent(txtFee, 103, 103, 103)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtCouseName)
										.addComponent(txtCourseID)))
								.addGap(325)))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cboCategoryName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtCourseID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtCouseName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(txtFee, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnUpdate)
						.addComponent(btnDelete)
						.addComponent(btnClose))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
		);
		
		tblCourse = new JTable();
		scrollPane.setViewportView(tblCourse);
		contentPane.setLayout(gl_contentPane);
	}
}
