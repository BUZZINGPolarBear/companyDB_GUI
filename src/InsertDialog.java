import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
public class InsertDialog extends JDialog implements ActionListener   // ���ο� ������ ���� �߰��� ���̾�α�
{ 
	  JPanel pw = new JPanel(new GridLayout(10,1));
	    JPanel pc = new JPanel(new GridLayout(10,1));
	    JPanel ps = new JPanel();
	    
	    JLabel label_Fname = new JLabel("First Name: ");
	    JLabel label_Minit = new JLabel("Middle init: ");
	    JLabel label_Lname = new JLabel("Last Name: ");
	    JLabel label_Ssn = new JLabel("Ssn: ");
	    JLabel label_Bdate = new JLabel("Birthdate: ");
	    JLabel label_Address = new JLabel("Address: ");
	    JLabel label_Sex = new JLabel("Sex: ");
	    JLabel label_Salary = new JLabel("Salary: ");
	    JLabel label_Super_ssn =new JLabel("Super_ssn: ");
	    JLabel label_Dno = new JLabel("Dno: ");
	 
	 
	    JTextField Fname = new JTextField();
	    JTextField Minit = new JTextField();
	    JTextField Lname = new JTextField();
	    JTextField Ssn = new JTextField();
	    JTextField Bdate = new JTextField();
	    JTextField Address = new JTextField();
	    //JTextField Sex = new JTextField();
	    String genderOptions[] ={"M", "F"};
	    JComboBox<String> Sex = new JComboBox<String>(genderOptions);
	   
	    JTextField Salary = new JTextField();
	    JTextField Super_ssn =new JTextField();
	    JTextField Dno = new JTextField();
	   
	    JButton insert;
	    JButton reset= new JButton("���");
	 
	   DAO dao =new DAO();
	   JDBCGUI me;
	 
	    public InsertDialog(JDBCGUI me, String index)
	    {
	        super(me,"���ο� �������� �߰�");
	        this.me=me;
	        if(index.equals("���� �߰�"))
	        {
	            insert =new JButton(index);
	        }
	        
	       
	        pw.add(label_Fname);    // �� �߰�
	        pw.add(label_Minit);
	        pw.add(label_Lname);
	        pw.add(label_Ssn);
	        pw.add(label_Bdate);
	        pw.add(label_Address);
	        pw.add(label_Sex);
	        pw.add(label_Salary);
	        pw.add(label_Super_ssn);
	        pw.add(label_Dno);
	        
	        
	   
	        pc.add(Fname);     //  �ؽ�Ʈ�ڽ� �߰�
	        pc.add(Minit);
	        pc.add(Lname);
	        pc.add(Ssn);
	        pc.add(Bdate);
	        pc.add(Address);
	        pc.add(Sex);
	        pc.add(Salary);
	        pc.add(Super_ssn);
	        pc.add(Dno);
	        
	        
	       
	       
	        ps.add(insert);   
	        ps.add(reset);
	   
	        add(pw,"West");
	        add(pc,"Center");
	        add(ps,"South");
	       
	        setSize(400,400);
	        setVisible(true);
	 
	        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);   // ����� ���̾�α� â�� ����
	       
	        insert.addActionListener(this); //�߰� �̺�Ʈ���
	        reset.addActionListener(this); //��� �̺�Ʈ���     
	    }
	   
	    
	    public static void messageBox(Object obj , String message)     // �޼����ڽ� ���
	    {
	        JOptionPane.showMessageDialog( (Component)obj , message);
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
	       String Bt =e.getActionCommand(); // ��� ��ư ������ �Ǵ�
	      
	        if(Bt.equals("���� �߰�"))
	       {
	        	
	        	 if(dao.userListInsert(this) > 0 )
	        	 {
	               messageBox(this , "�� ���� " + Fname.getText()+"�� ���� �߰���");
	               dispose();         // ���̾�α� â �ݱ�
	             }
	        	 else 
	           {
	               messageBox(this,"�������� �Է� ����");
	           }
	       }
	        else if(Bt.equals("���"))
	        {
	            dispose();
	        }
	        
	       
	    }
	   
	    
	    

}
