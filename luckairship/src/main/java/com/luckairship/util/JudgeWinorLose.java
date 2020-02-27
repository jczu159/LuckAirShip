package com.luckairship.util;

import java.sql.Connection;
import java.sql.DriverManager;
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
		System.out.println(bigSmall);
		System.out.println(singularMap);
		return null;

	}

	public static void updateMariaDB(String onlypid, int period, int betResult, int bet_number) throws SQLException {
		System.out.println("start connect");
		String conn_str = "jdbc:mysql://45.32.49.87:3306/luckairship?" + "user=mike&password=mike123"
				+ "&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(conn_str);
			Statement stmt = conn.createStatement();
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");// dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			SimpleDateFormat sqlDate = new SimpleDateFormat("yyyy-MM-dd");// dd/MM/yyyy
			String sqD = sqlDate.format(now);
			System.out.println("現在時間:" + strDate);
			onlypid = strDate + onlypid;

			String sql = "INSERT INTO bet_order (`PERIOD`, `BET_RESULT`, `BET_NUMBER`, `CREATE_DATE` , `IDENTIFICATION_PERIODS`) VALUES ('"
					+ onlypid + "', '" + betResult + "', '" + bet_number + "', '" + sqD + "','" + period
					+ "') ON DUPLICATE KEY UPDATE BET_RESULT = '" + betResult + "';";
			int execode = stmt.executeUpdate(sql);

			System.out.println("MariaDB/MySQL connect success");
		} catch (Exception e) {
			System.out.println("錯誤:" + e);
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

}
