package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Schedule;

public class ScheduleDAO {

   // 데이터 입력 (insert)
   public int getScheduleRegist(Schedule schedule) throws Exception {
      // ② 데이터 처리를 위한 SQL 문
      // 해당된 필드 no부분은 자동으로 증가되므로 필드를 줄 필요가 없음.
      String dml = "insert into scheduletbl values (?,?,?,?,?,?,?,?) ";

      Connection con = null;
      PreparedStatement pstmt = null;
      int count = 0;

      try {
         // ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
         con = DBUtil.getConnection();

         // ④ 입력받은 학생 정보를 처리하기 위하여 SQL문장을 생성
         pstmt = con.prepareStatement(dml); // 보안을 위해 사용
         pstmt.setString(1, schedule.getMonth());
         pstmt.setString(2, schedule.getWeek());
         pstmt.setString(3, schedule.getMon());
         pstmt.setString(4, schedule.getTue());
         pstmt.setString(5, schedule.getWen());
         pstmt.setString(6, schedule.getThur());
         pstmt.setString(7, schedule.getFri());
         pstmt.setString(8, schedule.getSat());

         // ⑤ SQL문을 수행후 처리 결과를 얻어옴
         count = pstmt.executeUpdate(); // mysql에서 번개모양(업데이트)를 하면 이문장을 실행

      } catch (SQLException e) {
         System.out.println("e=[" + e + "]");
      } catch (Exception e) {
         System.out.println("e=[" + e + "]");
      } finally {
         try {
            // ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
            if (pstmt != null)
               pstmt.close();
            if (con != null)
               con.close();
         } catch (SQLException e) {
         }
      }
      return count;
   }

   // 원아 전체 리스트 (select)
   public ArrayList<Schedule> getChildrenTotal() {
      ArrayList<Schedule> list = new ArrayList<Schedule>();
      String dml = "select * from scheduletbl";

      Connection con = null;
      PreparedStatement pstmt = null;

      // 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
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

         // 데이터베이스에서 값을 임시로 저장하는 장소를 제공하는 객체
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

   // 수정 기능 (update tableName set 필드명 = 수정내용 where 조건내용)
   public Schedule getScheduleUpdate(Schedule schedule) throws Exception {
      // ② 데이터 처리를 위한 SQL 문
      String dml = "update scheduletbl set " + " mon=?, tue=?, wen=?, thur=?, fri=?, sat=? where month=? and week= ?";
      Connection con = null;
      PreparedStatement pstmt = null;

      try {
         // ③ DBUtil이라는 클래스의 getConnection( )메소드로 데이터베이스와 연결
         con = DBUtil.getConnection();

         // ④ 수정한 원아 정보를 수정하기 위하여 SQL문장을 생성
         pstmt = con.prepareStatement(dml);
         pstmt.setString(1, schedule.getMon());
         pstmt.setString(2, schedule.getTue());
         pstmt.setString(3, schedule.getWen());
         pstmt.setString(4, schedule.getThur());
         pstmt.setString(5, schedule.getFri());
         pstmt.setString(6, schedule.getSat());
         pstmt.setString(7, schedule.getMonth());
         pstmt.setString(8, schedule.getWeek());
         // ⑤ SQL문을 수행후 처리 결과를 얻어옴
         int i = pstmt.executeUpdate();

         if (i == 1) {
            ShareMethod.alertDisplay(1, "수정 완료!", schedule.getWeek() + " 주차 수정이 완료되었습니다!", "성공 (*'ㅂ'*)");

         } else {
            ShareMethod.alertDisplay(1, "수정 실패!", schedule.getWeek() + " 주차 수정이 실패하였습니다!", "실패 (;ㅅ;)");
            return null;
         }

      } catch (SQLException e) {
         System.out.println("e=[" + e + "]");
      } catch (Exception e) {
         System.out.println("e=[" + e + "]");
      } finally {
         try {
            // ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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