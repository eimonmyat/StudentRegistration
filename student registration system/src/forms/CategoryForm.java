package forms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import entities.Category;
import services.CategoryService;
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

public class CategoryForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtCategory;
	private Category category;
	private CategoryService categoryService;
	private DefaultTableModel dtm=new DefaultTableModel();
	private List<Category> origianlCategoryList = new ArrayList<>();
	private JTable tblCategory;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryForm frame = new CategoryForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public CategoryForm() {
        initialize();
        initializeDependency();
        this.setTableDesign();
        this.loadAllCategories(Optional.empty());
    }
	
	private void loadAllCategories(Optional<List<Category>> optionalCategories) {
        this.dtm.getDataVector().removeAllElements();
        this.dtm.fireTableDataChanged();

        this.origianlCategoryList = this.categoryService.findAllCategories();
        List<Category> categoryList = optionalCategories.orElseGet(() -> origianlCategoryList);

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
        dtm.addColumn("Category");
        this.tblCategory.setModel(dtm);
    }

    private void initializeDependency() {
        this.categoryService = new CategoryService();
        //this.categoryService.setProductRepo(new ProductService());
    }
	private void resetFormData() {
        txtCategory.setText("");
    }
	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Category ");
		
		txtCategory = new JTextField();
		txtCategory.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (null != category && category.getId() != 0) {
                    category.setName(txtCategory.getText());

                    if (!category.getName().isBlank()) {
                        categoryService.updateCategory(String.valueOf(category.getId()), category);
                        resetFormData();
                        //JOptionPane.showMessageDialog(null, "Update successful");
                        loadAllCategories(Optional.empty());
                        category = null;
                    } else {
                        JOptionPane.showMessageDialog(null, "Enter required field");
                    }
            	}
                    else {
                    	Category category = new Category();
                        category.setName(txtCategory.getText());

                        if (null != category.getName() && !category.getName().isBlank()) {

                            categoryService.saveCategory(category);
                            resetFormData();
                            JOptionPane.showMessageDialog(null, "Completely Save");
                            loadAllCategories(Optional.empty());
                        } else {
                            JOptionPane.showMessageDialog(null, "Enter Required Field!");
                        }
                    }
                }
        });
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(null!=category) {
					categoryService.deleteCategory(String.valueOf(category.getId()));
					resetFormData();
					JOptionPane.showMessageDialog(null, "Delete successful");
					loadAllCategories(Optional.empty());
				}
				else {
					JOptionPane.showMessageDialog(null, "Enter Required Field!");
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel)
							.addGap(32)
							.addComponent(txtCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnSave)
									.addGap(35)
									.addComponent(btnDelete)))))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSave)
						.addComponent(btnDelete))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		
		tblCategory = new JTable();
		scrollPane.setViewportView(tblCategory);
		this.tblCategory.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!tblCategory.getSelectionModel().isSelectionEmpty()) {

                String id = tblCategory.getValueAt(tblCategory.getSelectedRow(), 0).toString();

                category = categoryService.findById(id);

                txtCategory.setText(category.getName());

            }
        });
		contentPane.setLayout(gl_contentPane);
	}
}
