package forms;

import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class studentPanel extends JPanel {
	JpanelLoader jLoader=new JpanelLoader();
	stuNewReg stuReg=new stuNewReg();
	stuOldReg stuOld=new stuOldReg();
	JPanel panel_4 = new JPanel();
	JPanel panel = new JPanel();
	/**
	 * Create the panel.
	 */
	public studentPanel() {
		initialize();
		panel_4.removeAll();
		jLoader.jPanelLoader(panel_4, panel);
	}
	public void initialize() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
		);
		
		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
		);
		panel.setBorder(null);
		
		JLabel lblNewLabel_1 = new JLabel(" Select Student Type");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 26));
		
		JRadioButton rboNew = new JRadioButton("New Student");
		rboNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stuNewReg s2=new stuNewReg();
				jLoader.jPanelLoader(panel, stuReg);
				
			}
		});
		rboNew.setSelected(true);
		rboNew.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JRadioButton rboOld = new JRadioButton("Old Student");
		rboOld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stuOldReg s3=new stuOldReg();
				jLoader.jPanelLoader(panel, stuOld);
			}
		});
		
		ButtonGroup G=new ButtonGroup();
		G.add(rboNew);
		G.add(rboOld);
		rboOld.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(139)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 357, Short.MAX_VALUE)
							.addGap(154))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(rboOld, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
								.addComponent(rboNew, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
							.addGap(356))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(28)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(rboNew, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(rboOld, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(315, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		panel_4.setLayout(gl_panel_4);
		setLayout(groupLayout);

	}
}
