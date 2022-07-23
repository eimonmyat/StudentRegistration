package forms;

import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Classroom;
import services.ClassroomService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClassroomPanel extends JPanel {
	private JTextField txtClassroom;
	private Classroom classroom;
	private ClassroomService classroomService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Classroom> origianlClassroomList = new ArrayList<>();
	private JTable tblClassroom;
	private JTextField txtClassroomID;
	private JTextField txtClassSize;
	
	/**
	 * Launch the application.
	 */
	public ClassroomPanel() {
        initialize();
        initializeDependency();
        autoID();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
    }
	
	private void loadAllCategories(Optional<List<Classroom>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlClassroomList = this.classroomService.findAllCategories();
        List<Classroom> ClassroomList = optionalCategories.orElseGet(() -> origianlClassroomList);

        ClassroomList.forEach(c -> {
            Object[] row = new Object[3];
            row[0] = c.getId();
            row[1] = c.getName();
            row[2]=String.valueOf(c.getUsers()) ;
            dtm.addRow(row);
        });
    }

    private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("ID");
        dtm.addColumn("Classroom");
        dtm.addColumn("Class Size");
        this.tblClassroom.setModel(dtm);
    }

    private void autoID() {
    	txtClassroomID.setText(String.valueOf((classroomService.getAutoId("classroomID","R-"))));
    }
    
    private void initializeDependency() {
        this.classroomService = new ClassroomService();
        //this.ClassroomService.setProductRepo(new ProductService());
    }
	private void resetFormData() {
        txtClassroom.setText("");
        txtClassSize.setText("");
	}
	/**
	 * Create the panel.
	 */
	public void initialize() {
		
		JLabel lblNewLabel = new JLabel("Classroom Id");
		
		txtClassroomID = new JTextField();
		txtClassroomID.setText("R-00000003");
		txtClassroomID.setEditable(false);
		txtClassroomID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Classroom Name");
		
		txtClassroom = new JTextField();
		txtClassroom.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Class size");
		
		txtClassSize = new JTextField();
		txtClassSize.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String st[]=new String[1];

                if (null != classroom && classroom.getId() != null) {
                    classroom.setName(txtClassroom.getText());
                    classroom.setUsers(Integer.parseInt(txtClassSize.getText()));
                    if (!classroom.getName().isBlank()) {
                    	st[0]=(String)txtClassroom.getText();
                    	try {
                    		boolean ee=classroomService.isduplicate(st);
                    		if(ee) {
                    			JOptionPane.showMessageDialog(null, "Duplicate Record");
                    			autoID();
                    			resetFormData();
                    			loadAllCategories(Optional.empty());
                    			classroom=null;
                    		}
                    		else {
                    			classroomService.updateClassroom(txtClassroomID.getText(), classroom);
                                resetFormData();
                                autoID();
                                //JOptionPane.showMessageDialog(null, "Update successful");
                                loadAllCategories(Optional.empty());
                                classroom = null;
                    		}
                    	}catch(SQLException e1) {
                    		e1.printStackTrace();
                    	}
						
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter Required Field!");
                    }
                } else {
                    Classroom classroom = new Classroom();
                    classroom.setName(txtClassroom.getText());
                    classroom.setUsers(Integer.parseInt(txtClassSize.getText()));
                    System.out.println(Integer.parseInt(txtClassSize.getText()));
                        if (null != classroom.getName() && !classroom.getName().isBlank()) {
                        	st[0]=(String)txtClassroom.getText();
                        	try {
                        		boolean ee=classroomService.isduplicate(st);
                        		if(ee) {
                        			JOptionPane.showMessageDialog(null, "Duplicate Record");
                        			autoID();
                        			resetFormData();
                        			loadAllCategories(Optional.empty());
                        			classroom=null;
                        		}else
                        		{
                        			classroomService.saveClassroom(txtClassroomID.getText(),classroom);
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
		
		JButton btnDelete = new JButton("Delete");
		 btnDelete.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (null != classroom) {
	                    classroomService.deleteClassroom(classroom.getId() + "");
	                    JOptionPane.showMessageDialog(null, "Delete successfully");
	                    resetFormData();
	                    autoID();
	                    loadAllCategories(Optional.empty());
	                    classroom = null;
	                } else {
	                    JOptionPane.showMessageDialog(null, "Choose Classroom");
	                }
	            }
	        });
		
		JButton btnClose = new JButton("Cancel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					resetFormData();
					autoID();
					loadAllCategories(Optional.empty());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(74)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(74)
									.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(txtClassSize, Alignment.LEADING)
									.addComponent(txtClassroom, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
									.addComponent(txtClassroomID, Alignment.LEADING)))))
					.addContainerGap(71, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(42)
							.addComponent(txtClassroomID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(txtClassroom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtClassSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnSave)
						.addComponent(btnDelete))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		
		tblClassroom = new JTable();
		scrollPane.setViewportView(tblClassroom);
		this.tblClassroom.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblClassroom.getSelectionModel().isSelectionEmpty()) {

                String id = tblClassroom.getValueAt(tblClassroom.getSelectedRow(), 0).toString();

                classroom = classroomService.findById(id);
                txtClassroomID.setText(classroom.getId());
                txtClassroom.setText(classroom.getName());
                txtClassSize.setText(String.valueOf(classroom.getUsers()));
            }
        });
		setLayout(groupLayout);

	}
}
