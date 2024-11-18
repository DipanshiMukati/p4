package com.rays.pro4.Model;

import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rays.pro4.Bean.StaffBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class StaffModel {

	public int nextPk() throws DatabaseException {

		String sql = "SELECT MAX (ID) FROM st_staff";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(StaffBean bean) throws ApplicationException, DuplicateRecordException {
		String sql = "INSERT INTO ST_STAFF VALUES (?,?,?,?,?)";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getFullName());
			pstmt.setDate(3, new java.sql.Date(bean.getJoiningDate().getTime()));
			pstmt.setString(4, bean.getDivision());
			pstmt.setString(5, bean.getPreviousEmployer());

			int a = pstmt.executeUpdate();
			System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiik");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());

			}

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void delete(StaffBean bean) throws ApplicationException, DuplicateRecordException {

		String sql = "DELETE FROM ST_STAFF WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void update(StaffBean bean) throws ApplicationException, DuplicateRecordException {
		String sql = "UPDATE ST_STAFF SET ";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getFullName());
			pstmt.setDate(2, new java.sql.Date(bean.getJoiningDate().getTime()));
			pstmt.setString(3, bean.getDivision());
			pstmt.setString(4, bean.getPreviousEmployer());

			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			int i = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
	
	
	public List search(StaffBean bean int pageSize ,int pageNo)  throws {
		
	}

}
