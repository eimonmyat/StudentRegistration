package forms;

import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Lecturer;
import services.LecturerServices;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;

public class LecturerPanel extends JPanel {
	private JTextField txtLecturer;
	private Lecturer lecturer;
	private LecturerServices lecturerService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Lecturer> origianlCategoryList = new ArrayList<>();
	private JTable tblLecturer;
	private JTextField txtLecturerID;
	
	/**
	 * Launch the application.
	 */
	
	public LecturerPanel() {
		setFont(new Font("Dialog", Font.BOLD, 30));
        initialize();
        initializeDependency();
        autoID();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
    }
	
	private void loadAllCategories(Optional<List<Lecturer>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCategoryList = this.lecturerService.findAllCategories();
        List<Lecturer> categoryList = optionalCategories.orElseGet(() -> origianlCategoryList);

        categoryList.forEach(c -> {
            Object[] row = new Object[2];
            row[0] = c.getId();
            row[1] = c.getName();
            dtm.addRow(row);
        });
    }

    private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("ID");
        dtm.addColumn("Lecturer");
        this.tblLecturer.setModel(dtm);
    }

    private void autoID() {
    	txtLecturerID.setText(String.valueOf((lecturerService.getAutoId("lecturerID","L-"))));
    }
    
    private void initializeDependency() {
        this.lecturerService = new LecturerServices();
        //this.categoryService.setProductRepo(new ProductService());
    }
	private void resetFormData() {
        txtLecturer.setText("");
	}
	/**
	 * Create the panel.
	 */
	public void initialize() {
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel_1 = new JLabel("Lecturer Name");
		
		JLabel lblNewLabel = new JLabel("Lecturer Id");
		
		JButton btnSave = new JButton("Save");
		
		JButton btnDelete = new JButton("Delete");
		
		JButton btnClose = new JButton("Cancel");
		
		txtLecturerID = new JTextField();
		txtLecturerID.setText("L-00000005");
		txtLecturerID.setEditable(false);
		txtLecturerID.setColumns(10);
		
		txtLecturer = new JTextField();
		txtLecturer.setColumns(10);
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String st[]=new String[1];

                if (null != lecturer && lecturer.getId() != null) {
                    lecturer.setName(txtLecturer.getText());
                    if (!lecturer.getName().isBlank()) {
                    	st[0]=(String)txtLecturer.getText();
                    	try {
                    		boolean ee=lecturerService.isduplicate(st);
                    		if(ee) {
                    			JOptionPane.showMessageDialog(null, "Duplicate Record");                   			
                    			resetFormData();
                    			autoID();
                    			loadAllCategories(Optional.empty());
                    			lecturer=null;
                    		}
                    		else {
                    			lecturerService.updateLecturer(txtLecturerID.getText(), lecturer);
                                resetFormData();
                                autoID();
                                //JOptionPane.showMessageDialog(null, "Update successful");
                                loadAllCategories(Optional.empty());
                                lecturer = null;
                    		}
                    	}catch(SQLException e1) {
                    		e1.printStackTrace();
                    	}
						
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                } else {
                	Lecturer category = new Lecturer();
                    category.setName(txtLecturer.getText());


                        if (null != category.getName() && !category.getName().isBlank()) {
                        	st[0]=(String)txtLecturer.getText();
                        	try {
                        		boolean ee=lecturerService.isduplicate(st);
                        		if(ee) {
                        			JOptionPane.showMessageDialog(null, "Duplicate Record");
                        			autoID();
                        			resetFormData();
                        			loadAllCategories(Optional.empty());
                        			category=null;
                        		}else
                        		{
                        			lecturerService.saveLecturer(txtLecturerID.getText(),category);
                                    resetFormData();
                                    autoID();
                                    JOptionPane.showMessageDialog(null, "Save successful");
                                    loadAllCategories(Optional.empty());
                        		}

                        } catch(SQLException e2) {
                        	e2.printStackTrace();
                        }
                        }
                        	else {
                            JOptionPane.showMessageDialog(null, "Enter Required Field!");
                        }
                }
            }
        });
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFormData();
				autoID();
    			loadAllCategories(Optional.empty());
    			lecturer = null;
    			
			}
		});
		 btnDelete.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (null != lecturer) {
	                    lecturerService.deleteLecturer(lecturer.getId() + "");
	                    JOptionPane.showMessageDialog(null, "Delete successfully");
	                    resetFormData();
	                    autoID();
	                    loadAllCategories(Optional.empty());
	                    lecturer = null;
	                } else {
	                    JOptionPane.showMessageDialog(null, "Choose Lecturer");
	                }
	            }
	        });
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(32)
									.addComponent(btnSave)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDelete)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClose))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(33)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtLecturerID, Alignment.TRAILING)
										.addComponent(txtLecturer, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))))))
					.addGap(68))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtLecturerID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(txtLecturer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnDelete)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(118, Short.MAX_VALUE))
		);
		
		tblLecturer = new JTable();
		scrollPane.setViewportView(tblLecturer);
		this.tblLecturer.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblLecturer.getSelectionModel().isSelectionEmpty()) {

                String id = tblLecturer.getValueAt(tblLecturer.getSelectedRow(), 0).toString();

                lecturer = lecturerService.findById(id);
                txtLecturerID.setText(lecturer.getId());
                txtLecturer.setText(lecturer.getName());

            }
        });
		contentPane.setLayout(gl_contentPane);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(contentPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPane, GroupLayout.PREFERRED_SIZE, 424, Short.MAX_VALUE)
		);
		setLayout(groupLayout);

	}
}
