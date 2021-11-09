import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class DAO {
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public DAO() // ������
	{
		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company?serverTimezone=UTC", "root", "wnsgnl1214");

		} catch (SQLException e) {
			System.out.println(e + "=> ���� fail");
		}
	}

	public void dbClose() { /// DB �ݱ�
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}

	public int userListInsert(InsertDialog user) // Insert �� �޼ҵ�( InsertDialog���� ����� ����)
	{
		int result = 0;
		try {
			ps = con.prepareStatement("insert into EMPLOYEE values (?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, user.Fname.getText());
			ps.setString(2, user.Minit.getText());
			ps.setString(3, user.Lname.getText());
			ps.setString(4, user.Ssn.getText());
			ps.setString(5, user.Bdate.getText());
			ps.setString(6, user.Address.getText());
			ps.setString(7, user.Sex.getSelectedItem().toString());
			ps.setString(8, user.Salary.getText());
			ps.setString(9, user.Super_ssn.getText());
			ps.setString(10, user.Dno.getText());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> userListInsert fail");
		} finally {
			dbClose();
		}

		return result;

	}

	public void userSelectAll(DefaultTableModel t_model, String ord) // ��� attribute ���
	{
		try {
			st = con.createStatement();

			String sql = "SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
					+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn";

			if (ord == "���� ����") {
				rs = st.executeQuery(sql);
			} else if (ord == "��������") {
				rs = st.executeQuery(sql + " ORDER BY NAME");
			} else if (ord == "��������") {
				rs = st.executeQuery(sql + " ORDER BY NAME DESC");
			}

			for (int i = 0; i < t_model.getRowCount();) // ���� tuple �����
			{
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), Boolean.FALSE };
				t_model.addRow(data); // DefaultTableModel�� tuple �߰�
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelectAll fail");
		} finally {
			dbClose();
		}
	}

	// �˻� ����
	public void userSelect(DefaultTableModel t_model, String sel, String Text, String ord) {
		try {
			st = con.createStatement();

			String sql = "SELECT CONCAT(e1.Fname, \" \", e1.Minit, \" \", e1.Lname) AS NAME, e1.SSN, e1.BDATE, e1.ADDRESS, e1.SEX, e1.SALARY, CONCAT(e2.Fname, \" \", e2.Minit, \" \", e2.Lname) AS SUPERVISOR, d.DNAME\r\n"
					+ "FROM  employee e1 inner join department d on e1.Dno = d.Dnumber left outer join employee e2 on e1.super_ssn = e2.ssn";

			if (sel == "�μ�") {
				if (ord == "���� ����") {
					rs = st.executeQuery(sql + " WHERE d.DNAME = '" + Text + "'");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE d.DNAME = '" + Text + "'" + " ORDER BY NAME");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE d.DNAME = '" + Text + "'" + " ORDER BY NAME DESC");
				}
			} else if (sel == "����") {
				if (ord == "���� ����") {
					rs = st.executeQuery(sql + " WHERE e1.SEX = '" + Text + "'");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE e1.SEX = '" + Text + "'" + " ORDER BY NAME");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE e1.SEX = '" + Text + "'" + " ORDER BY NAME DESC");
				}
			} else if (sel == "����") {
				if (ord == "���� ����") {
					rs = st.executeQuery(sql + " WHERE e1.SALARY > '" + Text + "'");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE e1.SALARY > '" + Text + "'" + " ORDER BY SALARY");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE e1.SALARY > '" + Text + "'" + " ORDER BY SALARY DESC");
				}
			} else if (sel == "����") {
				if (ord == "���� ����") {
					rs = st.executeQuery(sql + " WHERE DATE_FORMAT(e1.BDATE, '%m') = '" + Text + "'");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE DATE_FORMAT(e1.BDATE, '%m') = '" + Text + "'" + " ORDER BY BDATE");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE DATE_FORMAT(e1.BDATE, '%m') = '" + Text + "'" + " ORDER BY BDATE DESC");
				}
			} else if (sel == "��������") { // Fname, Lname, Full name �� �ϳ� �Է��ϸ� �˻���
				if (ord == "���� ����") {
					rs = st.executeQuery(sql + " WHERE CONCAT(e2.Fname, ' ', e2.Minit, ' ', e2.Lname) = '" + Text + "'" + " or e2.Fname = '" + Text + "'" + " or e2.Lname = '" + Text + "'");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE CONCAT(e2.Fname, ' ', e2.Minit, ' ', e2.Lname) = '" + Text + "'" + " or e2.Fname = '" + Text + "'" + " or e2.Lname = '" + Text + "'" + " ORDER BY SSN");
				} else if (ord == "��������") {
					rs = st.executeQuery(sql + " WHERE CONCAT(e2.Fname, ' ', e2.Minit, ' ', e2.Lname) = '" + Text + "'" + " or e2.Fname = '" + Text + "'" + " or e2.Lname = '" + Text + "'" + " ORDER BY SSN DESC");
				}

			}

			for (int i = 0; i < t_model.getRowCount();) // ���� tuple �����
			{
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), Boolean.FALSE };
				t_model.addRow(data); // DefaultTableModel�� tuple �߰�
			}

		} catch (SQLException e) {
			System.out.println(e + "=> userSelect fail");
		} finally {
			dbClose();
		}
	}

	
	
	public int userDelete(String Name) // �������� ����
	{
		int result = 0;
		try {
			String query = "delete from employee where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " �������� ������");
		} catch (SQLException e) {
			System.out.println(e + "=> userDelete fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_add(String Name, String Text) // �������� ���� - ���� �ּ�, ����, �޿��� ?�� �Է¹޾Ƽ� ���ϴ� �ϳ��� �Լ��� ���������, �̷����
														// 'address' �������� �ԷµǼ� �����߻� -> ��¿������ ������ ����(������ ����1.png ����)
	{
		int result = 0;
		try {
			String query = "update employee set address = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " �ּ� �����");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_sex(String Name, String Text) {
		int result = 0;
		try {
			String query = "update employee set sex = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " ���� �����");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

	public int userUpdate_sal(String Name, String Text) {
		int result = 0;
		try {
			String query = "update employee set salary = ? where Fname = ?";
			ps = con.prepareStatement(query);
			String[] splitname = Name.split(" ");
			ps.setString(1, Text);
			ps.setString(2, splitname[0]);
			result = ps.executeUpdate();
			InsertDialog.messageBox(null, Name + " �޿� �����");
		} catch (SQLException e) {
			System.out.println(e + "=> userUpdate fail");
		} finally {
			dbClose();
		}

		return result;
	}

}
