package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Schedule;

public class ScheduleDAO {

   // ������ �Է� (insert)
   public int getScheduleRegist(Schedule schedule) throws Exception {
      // �� ������ ó���� ���� SQL ��
      // �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
      String dml = "insert into scheduletbl values (?,?,?,?,?,?,?,?) ";

      Connection con = null;
      PreparedStatement pstmt = null;
      int count = 0;

      try {
         // �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
         con = DBUtil.getConnection();

         // �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
         pstmt = con.prepareStatement(dml); // ������ ���� ���
         pstmt.setString(1, schedule.getMonth());
         pstmt.setString(2, schedule.getWeek());
         pstmt.setString(3, schedule.getMon());
         pstmt.setString(4, schedule.getTue());
         pstmt.setString(5, schedule.getWen());
         pstmt.setString(6, schedule.getThur());
         pstmt.setString(7, schedule.getFri());
         pstmt.setString(8, schedule.getSat());

         // �� SQL���� ������ ó�� ����� ����
         count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

      } catch (SQLException e) {
         System.out.println("e=[" + e + "]");
      } catch (Exception e) {
         System.out.println("e=[" + e + "]");
      } finally {
         try {
            // �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
            if (pstmt != null)
               pstmt.close();
            if (con != null)
               con.close();
         } catch (SQLException e) {
         }
      }
      return count;
   }

   // ���� ��ü ����Ʈ (select)
   public ArrayList<Schedule> getChildrenTotal() {
      ArrayList<Schedule> list = new ArrayList<Schedule>();
      String dml = "select * from scheduletbl";

      Connection con = null;
      PreparedStatement pstmt = null;

      // �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
      ResultSet rs = null;
      Schedule scheduleVO = null;

      try {
         con = DBUtil.getConnection();
         pstmt = con.prepareStatement(dml);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            scheduleVO = new Schedule(rs.getString(3), rs.getString(4), rs.getString(5),
                  rs.getString(6), rs.getString(7), rs.getString(8));
            list.add(scheduleVO);
         }

      } catch (SQLException se) {
         System.out.println(se);
      } catch (Exception e) {
         System.out.println(e);
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (pstmt != null)
               pstmt.close();
            if (con != null)
               con.close();
         } catch (SQLException se) {
         }
      }
      return list;
   }
   
   
   public ArrayList<Schedule> getDuplicateCheck(String month,String week) {
         ArrayList<Schedule> list = new ArrayList<Schedule>();
         String dml = "select * from scheduletbl where month=? and week=?";

         Connection con = null;
         PreparedStatement pstmt = null;

         // �����ͺ��̽����� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
         ResultSet rs = null;
         Schedule scheduleVO = null;

         try {
            con = DBUtil.getConnection();
            pstmt = con.prepareStatement(dml);
            pstmt.setString(1, month);
            pstmt.setString(2, week);
            rs = pstmt.executeQuery();

            while (rs.next()) {
               scheduleVO = new Schedule(rs.getString(3), rs.getString(4), rs.getString(5),
                     rs.getString(6), rs.getString(7), rs.getString(8));
               list.add(scheduleVO);
            }

         } catch (SQLException se) {
            System.out.println(se);
         } catch (Exception e) {
            System.out.println(e);
         } finally {
            try {
               if (rs != null)
                  rs.close();
               if (pstmt != null)
                  pstmt.close();
               if (con != null)
                  con.close();
            } catch (SQLException se) {
            }
         }
         return list;
      }

   // ���� ��� (update tableName set �ʵ�� = �������� where ���ǳ���)
   public Schedule getScheduleUpdate(Schedule schedule) throws Exception {
      // �� ������ ó���� ���� SQL ��
      String dml = "update scheduletbl set " + " mon=?, tue=?, wen=?, thur=?, fri=?, sat=? where month=? and week= ?";
      Connection con = null;
      PreparedStatement pstmt = null;

      try {
         // �� DBUtil�̶�� Ŭ������ getConnection( )�޼ҵ�� �����ͺ��̽��� ����
         con = DBUtil.getConnection();

         // �� ������ ���� ������ �����ϱ� ���Ͽ� SQL������ ����
         pstmt = con.prepareStatement(dml);
         pstmt.setString(1, schedule.getMon());
         pstmt.setString(2, schedule.getTue());
         pstmt.setString(3, schedule.getWen());
         pstmt.setString(4, schedule.getThur());
         pstmt.setString(5, schedule.getFri());
         pstmt.setString(6, schedule.getSat());
         pstmt.setString(7, schedule.getMonth());
         pstmt.setString(8, schedule.getWeek());
         // �� SQL���� ������ ó�� ����� ����
         int i = pstmt.executeUpdate();

         if (i == 1) {
            ShareMethod.alertDisplay(1, "���� �Ϸ�!", schedule.getWeek() + " ���� ������ �Ϸ�Ǿ����ϴ�!", "���� (*'��'*)");

         } else {
            ShareMethod.alertDisplay(1, "���� ����!", schedule.getWeek() + " ���� ������ �����Ͽ����ϴ�!", "���� (;��;)");
            return null;
         }

      } catch (SQLException e) {
         System.out.println("e=[" + e + "]");
      } catch (Exception e) {
         System.out.println("e=[" + e + "]");
      } finally {
         try {
            // �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
            if (pstmt != null)
               pstmt.close();
            if (con != null)
               con.close();
         } catch (SQLException e) {
         }
      }
      return schedule;
   }
}