package com.test.bookjuck.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.test.bookjuck.DBUtil;
import com.test.bookjuck.dto.RealTimeSearchDTO;


public class RealTimeSearchDAO {
	
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;

	
	
	
	public RealTimeSearchDAO() {
		//DB 연결
		conn = DBUtil.open();
	}
	
	public void close() {
		try {
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//검색어 DB 삽입 --이현우
	public int insertWord(RealTimeSearchDTO dto) {
		
		
		try {
			
			String sql = "insert into tblsearchrank (seq, searchdate, searchword) values (seqsearchrank.nextval, to_date(sysdate,'yyyy.mm.dd hh24:mi'), ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getSearchword());
			
			System.out.println(sql);
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return 0;
				
		
	}
	
	
	
	
	//검색어 순위 - 이현우
	public ArrayList<RealTimeSearchDTO> getSearchRank() {
		
		try {
			
			String sql = "select * from vwrealtimesearch";
			pstat = conn.prepareStatement(sql);
			
			rs = pstat.executeQuery(sql);
			
			ArrayList<RealTimeSearchDTO> list = new ArrayList<RealTimeSearchDTO>();
			
			while(rs.next()) {
				
				RealTimeSearchDTO dto = new RealTimeSearchDTO();

				dto.setSearchword(rs.getString("searchword"));
				dto.setRank(rs.getString("rank"));
				dto.setSearchcount(rs.getInt("searchcount"));
				System.out.println(rs.getString("searchword"));
				list.add(dto);
				
			}
			
			System.out.println(sql);
			System.out.println(list);
			System.out.println(list.size());
			return list;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		return null;
		
	}
	
	
	
	

}
