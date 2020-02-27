package com.luckairship.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author IMI-JAVA-Ryan 用来判断输赢的软件
 *
 */
public class JudgeWinorLose {
	private static Connection conn = null;

	public static Hashtable<String, Object> JudgeBigSmall(String numbersp[], String openPeriod) {
		HashMap<String, String> bigSmall = new HashMap<>();
		HashMap<String, String> singularMap = new HashMap<>();
		openPeriod = openPeriod.trim();
		openPeriod = openPeriod.substring(openPeriod.length() - 3, openPeriod.length());
		System.out.println("取得最新開獎期數:" + openPeriod);
		int i = 1;
		for (String bsize : numbersp) {
			String biStr = Integer.valueOf(bsize) > 6 ? "大" : "小";
			String singular = Integer.valueOf(bsize) % 2 == 0 ? "雙" : "單";
			singularMap.put(String.valueOf(i), singular);
			bigSmall.put(String.valueOf(i), biStr);
			i++;
		}

		try {
			updateMariaDB(openPeriod, bigSmall);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bigSmall);
		System.out.println(singularMap);
		return null;

	}

	public static void updateMariaDB(String openPeriod, HashMap<String, String> bigSmall) throws SQLException {
		System.out.println("start connect");
		String conn_str = "jdbc:mysql://45.32.49.87:3306/luckairship?" + "user=mike&password=mike123"
				+ "&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(conn_str);
			Statement stmt = conn.createStatement();

			Date now = new Date();

			SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
			String sqD = sqlDate.format(now);
			int pid = Integer.valueOf(openPeriod);
			for (int i = 1; i <= 10; i++) {
				String sql = "SELECT * FROM bet_order WHERE  IDENTIFICATION_PERIODS = '" + pid + "' AND BET_NUMBER = "
						+ i + " AND CREATE_DATE = '" + sqD + "'";
				System.out.println("打印SQL:" + sql);
				ResultSet rs = stmt.executeQuery(sql);

				String sqlpidResult = "";
				while (rs.next()) {
					sqlpidResult = rs.getString(3);
				}
				String result = bigSmall.get(String.valueOf(i)).equals("大") ? "1" : "2";
				if (!sqlpidResult.equals(result) && !sqlpidResult.isEmpty()) {

				}

			}
			System.out.println("MariaDB/MySQL connect success");
		} catch (Exception e) {
			System.out.println("錯誤:" + e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
