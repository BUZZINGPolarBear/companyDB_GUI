import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;

public class JDBCGUI extends JFrame implements ActionListener {

	String searchRangeOptions[] = { "��ü", "�μ�", "����", "����", "����", "��������" };
	String checkBoxOptions[] = { "Name", "Ssn", "Bdate", "Address", "Sex", "Salary", "Supervisor", "Department" };
	String selectedSearchRangeOptions = "��ü";
	String genderOptions[] = { "M", "F" };
	JComboBox<String> genderComboBox = new JComboBox<String>(genderOptions);
	String departmentOptions[] = { "Headquarters", "Administration", "Research" };
	JComboBox<String> departmentComboBox = new JComboBox<String>(departmentOptions);
	String birthOptions[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	JComboBox<String> birthComboBox = new JComboBox<String>(birthOptions);
	JTextField salaryTextField = new JTextField();
	JTextField subordinateTextField = new JTextField();
	JCheckBox[] checkBoxesAttributes = new JCheckBox[checkBoxOptions.length];
	ArrayList<Integer> selectedCheckBoxList = new ArrayList<Integer>(); // ���̺��� Ŭ���� row�� �����ϱ� ���� ����Ʈ
	JLabel orderLabel = new JLabel("����:");
	JComboBox<String> searchRangeComboBox = new JComboBox<String>(searchRangeOptions); // searchRangeComboBOx
	String orderOptions[] = { "���� ����", "��������", "��������" };
	JComboBox<String> orderComboBox = new JComboBox<String>(orderOptions);

	String[] attribute = {"NAME", "SSN", "BDATE", "ADDRESS", "SEX", "SALARY", "SUPERVISOR", "DEPARTMENT", "����"}; // ����� attribute��
	//String[] attribute = new String[10];
	DefaultTableModel dft = new DefaultTableModel(attribute, 0); // DefaultTableModel �̿��Ͽ� jtable�� ������ ����
	JTable jt = new JTable(dft);
	JScrollPane jsp = new JScrollPane(jt);

	JCheckBox jc = new JCheckBox();
	JLabel selectperson;

	String comboOptions[] = { "Address", "Sex", "Salary" };

	JComboBox combo = new JComboBox(comboOptions);
	JTextField upText = new JTextField(20);
	JButton update = new JButton("UPDATE");
	JButton insert = new JButton("���� �߰�");
	JButton delete = new JButton("������ ������ ����");
	JButton search = new JButton("�˻�");

	DAO dao = new DAO();

	public void mainscreen() // GUI ( JComboBox�� GUI �ڵ� �״�� �����ͼ� �̺�Ʈ ��ϵ� �̾ȿ� �ֽ��ϴ�!)
	{
		setLayout(null);// ���� ��ġ�� ���� ����(���� ��ġ���־�� ��.
		// =================================================================
		// ��: "�˻� ����"
		JLabel labelSearchRange = new JLabel();
		// �޺��ڽ�: searchRangeOptions�� �ɼ����� ���� �޺��ڽ� ����

		searchRangeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSearchRangeOptions = searchRangeComboBox.getSelectedItem().toString();
				Component subComponentSet[] = { departmentComboBox, genderComboBox, salaryTextField, birthComboBox,
						subordinateTextField };
				//System.out.println(selectedSearchRangeOptions);

				if (selectedSearchRangeOptions == "��ü") {
					for (int i = 0; i < subComponentSet.length; i++)
						remove(subComponentSet[i]);
					repaint();
				} else if (selectedSearchRangeOptions == "�μ�") {
					// ComboBox �ɼ��� �ٲܶ��� ����ϱ� ���� �ٸ� Component�� ����� �ڵ�
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == departmentComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					// ��ġ ���� �� �߰�
					departmentComboBox.setBounds(180, 7, 100, 25);
					add(departmentComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "����") {
					// ComboBox �ɼ��� �ٲܶ��� ����ϱ� ���� �ٸ� Component�� ����� �ڵ�
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == genderComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					genderComboBox.setBounds(180, 7, 120, 25);
					add(genderComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "����") {
					// ComboBox �ɼ��� �ٲܶ��� ����ϱ� ���� �ٸ� Component�� ����� �ڵ�
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == salaryTextField)
							continue;
						remove(subComponentSet[i]);
					}
					salaryTextField.setBounds(180, 7, 120, 25);
					add(salaryTextField);
					salaryTextField.setVisible(true);
					repaint();
				} else if (selectedSearchRangeOptions == "����") {
					// ComboBox �ɼ��� �ٲܶ��� ����ϱ� ���� �ٸ� Component�� ����� �ڵ�
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == birthComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					birthComboBox.setBounds(180, 7, 60, 25);
					add(birthComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "��������") {
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == subordinateTextField)
							continue;
						remove(subComponentSet[i]);
					}
					subordinateTextField.setBounds(180, 7, 120, 25);
					add(subordinateTextField);
					repaint();
				}
			}
		});
		// ��: "�˻� �׸�"
		JLabel labelSearchObject = new JLabel();
		// üũ�ڽ�

		int oldXposition = 80;
		for (int i = 0; i < checkBoxOptions.length; i++) {
			checkBoxesAttributes[i] = new JCheckBox(checkBoxOptions[i], true);
			if(i == 0) {
				checkBoxesAttributes[i].setBounds(80, 40, 90, 20);
				add(checkBoxesAttributes[i]);
			} else {
				oldXposition += 95;
				checkBoxesAttributes[i].setBounds(oldXposition, 40, 95, 20);
				add(checkBoxesAttributes[i]);
				setVisible(true);
			}
		}
		//üũ�ڽ� ���� �׸� Ȯ���ϱ�


		// =================================================================
		// Component ���빰 ����
		JLabel selectperson = new JLabel("���õ� ���:");
		selectperson.setBounds(10, 650, 100, 25);
		add(selectperson);

		labelSearchRange.setText("�˻� ����");
		labelSearchObject.setText("�˻� �׸�");

		labelSearchRange.setBounds(10, 10, 70, 25);
		labelSearchObject.setBounds(10, 40, 70, 25);
		searchRangeComboBox.setBounds(70, 7, 100, 25);

		add(labelSearchRange);
		add(labelSearchObject);
		add(searchRangeComboBox);

		/* ���⼭���� �߰����� */
		jsp.setBounds(0, 75, 1000, 500);
		combo.setBounds(300, 600, 75, 30);
		insert.setBounds(700, 600, 90, 30);
		upText.setBounds(380, 600, 180, 30);
		update.setBounds(565, 600, 85, 30);
		delete.setBounds(800, 600, 150, 30);
		search.setBounds(880, 40, 100, 25);
		orderLabel.setBounds(330, 10, 40, 25);
		orderComboBox.setBounds(380, 10, 90, 25);

		add(jsp); // ������ ���
		add(combo); // �����׸�
		add(insert); // �����߰� ��ư
		add(upText); // ���ų��� ���� �ؽ�Ʈ�ڽ�
		add(update); // UPDATE ��ư
		add(delete); // ������ ư
		add(search); // �˻� ��ư
		add(orderLabel);//���� ��
		add(orderComboBox);//���� �޺��ڽ�




		super.setSize(1000, 800);
		super.setVisible(true);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		insert.addActionListener(this);
		update.addActionListener(this);
		search.addActionListener(this);
		delete.addActionListener(this);
		combo.addActionListener(this);

	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new JDBCGUI();
//	}

	// ���� ���� üũ �ڽ�
	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
													   int row, int column) {
			JCheckBox jc = new JCheckBox();
			jc.setSelected(((Boolean) value).booleanValue());
			jc.setHorizontalAlignment(JLabel.CENTER);
			return jc;
		}
	};


	public void tableColumnSize(int a) {
		jt.getColumnModel().getColumn(a).setWidth(0);
	}
	public String[] getAttribute(){ return attribute;}

	@Override
	public void actionPerformed(ActionEvent e) {

		jc.setHorizontalAlignment(JLabel.CENTER);


		if (e.getSource() == insert) // �����߰� ��ư Ŭ���� �����߰� ���̾�α� â ����
		{
			new InsertDialog(this, "���� �߰�");
		}

		else if (e.getSource() == search) // �˻���ư Ŭ���� attribute ���
		{
			String range = searchRangeComboBox.getSelectedItem().toString();
			int selectedAttributeCnt=0;
			List<String> dynamicAttributes = new ArrayList<String>();
			for(int i=0; i<checkBoxesAttributes.length; i++)
			{
				if(checkBoxesAttributes[i].isSelected()==true)
				{
					dynamicAttributes.add(checkBoxesAttributes[i].getText());
					//System.out.println(checkBoxesAttributes[i].getText());
					selectedAttributeCnt++;
				}
			}
			dynamicAttributes.add("����");
			attribute = new String[dynamicAttributes.size()];
			for(int i=0; i < attribute.length; i++)
			{
				attribute[i] = dynamicAttributes.get(i);
			}
			//attribute[dynamicAttributes.size()+1] = "����";

			remove(jsp);//���ο� checkbox Attribute ����� ���̺��� �߰��ϱ� ���� ����
			dft = new DefaultTableModel(attribute, 0); // DefaultTableModel �̿��Ͽ� jtable�� ������ ����
			jt = new JTable(dft);
			jsp = new JScrollPane(jt);

			jsp.setBounds(0, 75, 1000, 500);
			add(jsp); // ������ ���

			jt.getColumn("����").setCellRenderer(dcr);
			jt.getColumn("����").setCellEditor(new DefaultCellEditor(jc));    // false üũ�ڽ� �ǵ��� ����

			if (range.equals("��ü")) { // �̸� �� ���� ����
				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("�μ�")) { // �̸� �� ���� ����
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						departmentComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("����")) { // �̸� �� ���� ����
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						genderComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("����")) { // ���� �� ���� ����
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(), salaryTextField.getText(),
						orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("����")) { // ���� �� ���� ����
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						birthComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("��������")) { // ���� ��ȣ �� ���� ����
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(), subordinateTextField.getText(),
						orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			}

		}

		else if (e.getSource() == delete) {// ���� �޴������� Ŭ��
			//int[] rows = jt.getSelectedRows(); // ������ ���� ����

			int rowCnt = dao.getCountOfUserSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
			System.out.println(rowCnt);
			for (int i=0; i<rowCnt; i++) {
//				Object str = jt.getValueAt(i, 1).toString();
				if(jt.getValueAt(i, attribute.length-1).toString() == "true")
				{
					System.out.println(jt.getValueAt(i, 1).toString());
					dao.userDelete(jt.getValueAt(i, 1).toString(), jt.getValueAt(i, 0).toString() );
				}
				//dao.userDelete(str.toString());
			} // ������ jt.getSelectedRow �޼ҵ带 ��ü�Ͽ� ����Ʈ�� ���� ����

			dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute); // �������� ���� �� ���̺� �ٽ� ���
//			if (dft.getRowCount() > 0)
//				jt.setRowSelectionInterval(0, 0);
		}

		else if (e.getSource() == update) {

			String Item = combo.getSelectedItem().toString(); // �ּ�, ����, �޿� �߿� ���õ� �� ����

			if (Item.equals("Address")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_add(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute); // �������� ���� �� ���̺� �ٽ� ���
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (Item.equals("Sex")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_sex(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (Item.equals("Salary")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_sal(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			}

		} // update �̺�Ʈ ��
//		jt.addMouseListener(new MouseListener() {
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						int row = jt.getSelectedRow();
//						boolean isSelected = (boolean)jt.getValueAt(row, 8);
//				if(isSelected) {
//					selectedCheckBoxList.add(row);
//				}
//				else {
//					selectedCheckBoxList.remove(row);
//				}
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		/* 2������(�˻�����, �˻��׸�) ���� �̺�Ʈ �ʿ� */

	}




}