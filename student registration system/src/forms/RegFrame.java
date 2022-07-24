package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class RegFrame extends JFrame {
	private JFrame frame;
	private JPanel contentPane;
	JpanelLoader jLoader=new JpanelLoader();
	JPanel panel_2 = new JPanel();
	studentPanel stuPanel=new studentPanel();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegFrame window = new RegFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public RegFrame() {
		initialize();
		jLoader.jPanelLoader(panel_2, stuPanel);
	}
	/**
	 * Create the frame.
	 */
	public void initialize() {
		frame=new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 955, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 0));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 929, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE))
		);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1344, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 593, Short.MAX_VALUE)
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Student Registration System");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		
		JLabel lblNewLabel_1_1 = new JLabel("Control");
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ControlPanel control=new ControlPanel();
				jLoader.jPanelLoader(panel_2, control);;
			}
		});
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Home");
		lblNewLabel_1_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				studentPanel stu=new studentPanel();
				jLoader.jPanelLoader(panel_2, stu);
			}
		});
		lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("List");
		lblNewLabel_1_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegistrationPanel list=new RegistrationPanel();
				jLoader.jPanelLoader(panel_2, list);
			}
		});
		lblNewLabel_1_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_1_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_1_2, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addGap(143))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_1, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_1_1_2, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
