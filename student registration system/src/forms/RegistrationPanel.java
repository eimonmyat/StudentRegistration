package forms;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import entities.Course;
import entities.Registration;
import entities.Schedule;
import entities.Student;
import services.RegistrationServices;
import services.ScheduleServices;
import services.StudentService;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrationPanel extends JPanel {
	private JTable tblRegistration;
	Date date=new Date();
	ScheduleServices scheduleService=new ScheduleServices();
	RegistrationServices registrationService=new RegistrationServices();
	StudentService studentService=new StudentService();
	private List<Registration> origianlRegistrationList = new ArrayList<>();
	SimpleDateFormat dcn=new SimpleDateFormat("yyyy-MM-dd");
	Student s=new Student();
	private List<Schedule> scheduleList = new ArrayList<>();
	JComboBox cboScheduleSearch = new JComboBox();
	String cboResult;
	/**
	 * Create the panel.
	 */
	
	private DefaultTableModel dtm=new DefaultTableModel();
	private JTextField txtStuNameSearch;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationPanel frame = new RegistrationPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public RegistrationPanel() {
		initialize();
		cboFill();
		this.setTableDesign();
		this.loadAllRegistration(Optional.empty());
	}
	
	private void setTableDesign() {
    	//dtm.addColumn("No");
        dtm.addColumn("Registration ID");
        dtm.addColumn("Student Name");
        dtm.addColumn("Schedule ID");
        dtm.addColumn("Start Date");
        dtm.addColumn("End Date");
        dtm.addColumn("Start Time");
        dtm.addColumn("End Time");
        dtm.addColumn("Course");
        this.tblRegistration.setModel(dtm);
    }
	
	private void loadAllRegistration(Optional<List<Registration>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();
        
        this.origianlRegistrationList = this.registrationService.findAllRegistrations();
        List<Registration> categoryList = optionalCategories.orElseGet(() -> origianlRegistrationList);
        
        categoryList.forEach(c -> {
            Object[] row = new Object[9];
            row[0] = c.getId();
            String stu=c.getStudent().getId();
            s=studentService.findById(stu);
            row[1]=s.getName();
            row[2]=c.getSchedule().getId();
            String sch=c.getSchedule().getId();
            row[3]=registrationService.findName(sch, "startDate", "schedule");
            row[4]=registrationService.findName(sch, "endDate", "schedule");
            row[5]=registrationService.findName(sch, "starttime", "schedule");
            row[6]=registrationService.findName(sch, "endtime", "schedule");
            String co=scheduleService.getField("courseID", "schedule", sch).get(0);
            row[7]=scheduleService.findCourseName(co);
            dtm.addRow(row);
        });
    }
	 private void cboFill() {
	        scheduleList = scheduleService.findAllSchedule();
	        scheduleList.forEach(s -> this.cboScheduleSearch.addItem(s.getId()));
			
		}
	
	public void initialize() {
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("Student Name");
		
		txtStuNameSearch = new JTextField();
		txtStuNameSearch.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Schedule ID");
		
		
		cboScheduleSearch.addItem("-Select-");
		cboScheduleSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboScheduleSearch.getSelectedIndex()!=0) {
					cboResult=cboScheduleSearch.getSelectedItem().toString();
				}
				else
					loadAllRegistration(Optional.empty());
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.println(cboResult);
            	if(txtStuNameSearch!=null) {
            		String keyword = txtStuNameSearch.getText();
                    loadAllRegistration(Optional.of(origianlRegistrationList.stream().filter(b -> registrationService.findStudentName(b.getStudent().getId()).toLowerCase(Locale.ROOT)
                            .startsWith(keyword.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));
            	}
                if(cboScheduleSearch.getSelectedIndex()!=0) {
                	loadAllRegistration(Optional.of(origianlRegistrationList.stream().filter(b -> b.getSchedule().getId().toLowerCase(Locale.ROOT)
                            .startsWith(cboResult.toLowerCase(Locale.ROOT))).collect(Collectors.toList())));
                }
            }
        });
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadAllRegistration(Optional.empty());
				txtStuNameSearch.setText("");
				cboScheduleSearch.setSelectedIndex(0);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(72)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 906, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(43)
							.addComponent(txtStuNameSearch, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(58)
							.addComponent(lblNewLabel_1)
							.addGap(46)
							.addComponent(cboScheduleSearch, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addGap(47)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(47)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(txtStuNameSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(cboScheduleSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch)
						.addComponent(btnCancel))
					.addGap(60)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(62, Short.MAX_VALUE))
		);
		
		tblRegistration = new JTable();
		scrollPane.setViewportView(tblRegistration);
		setLayout(groupLayout);

	}
}
