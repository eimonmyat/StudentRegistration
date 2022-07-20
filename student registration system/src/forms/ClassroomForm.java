package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Classroom;
//import entities.Lecturer;
import services.ClassroomService;
//import services.ProductService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClassroomForm extends JFrame {

	private JPanel contentPane;
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
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassroomForm frame = new ClassroomForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ClassroomForm() {
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
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 488, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Classroom Id");
		
		txtClassroom = new JTextField();
		txtClassroom.setColumns(10);
		
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
		JScrollPane scrollPane = new JScrollPane();
		
		txtClassroomID = new JTextField();
		txtClassroomID.setEditable(false);
		txtClassroomID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Classroom Name");
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					dispose();
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
		
		JLabel lblNewLabel_2 = new JLabel("Class size");
		
		txtClassSize = new JTextField();
		txtClassSize.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_2))
							.addGap(33)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(txtClassSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(txtClassroomID, Alignment.TRAILING)
										.addComponent(txtClassroom, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnSave)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnDelete)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnClose)))))
					.addGap(39))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtClassroomID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(txtClassroom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(txtClassSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete)
						.addComponent(btnClose))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(45))
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
		contentPane.setLayout(gl_contentPane);
	}
}