package com.luckairship.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luckairship.pojo.LuckAirShipPo;

public class AirShipCollectData {
	private static Connection conn = null;
	private static ArrayList<String> numberOneList = new ArrayList<>();
	private static ArrayList<String> numbertwoList;
	private static ArrayList<String> numberthreeList;
	private static ArrayList<String> numberfourList;
	private static ArrayList<String> numberfiveList;
	private static ArrayList<String> numbersexList;
	private static ArrayList<String> numbersevenList;
	private static ArrayList<String> numbereightList;
	private static ArrayList<String> numbernigtList;

	public static void main(String[] args) throws SQLException {

		mondel4();
		// connectMariaDB("003");
	}

	/**
	 * @author admin 模式1 運行方式獲取結果 自動進入DB擴展
	 */
	public static void mondel4() {

		BufferedReader br;
		List<String> luckShipdbList = new ArrayList<>();
		LuckAirShipPo luckPo = new LuckAirShipPo();

		int i = 0;
		try {
			String str = null;
			br = new BufferedReader(new FileReader("C:/Users/IMI-JAVA-Ryan/Documents/202006.txt"));

			while ((str = br.readLine()) != null) {
				// System.out.println("显示数据 :" + str.toString());
				if (str.contains("期") && str.contains("彩票GG")) {
					if (str.contains("冠军大小")) {
						// 把资料结果 一个一个拆分
						if (str != null && !str.isEmpty()) {
							numberOneList.add(str.toString());
						}
					} else if (str.contains("亚军大小")) {

					}

					// luckPo.setStrategyPattern(strategyPattern); // 設定模式
					// // System.out.println(str); // 列印所有旗號
					// Integer periodindex = str.indexOf("期");
					// if (periodindex != -1) {
					// period = str.substring(0, periodindex);
					// }
					// luckPo.setPeriod(Integer.valueOf(period)); // 設定第幾期
					//
					// // 切割 雙結果

					// if (educt.contains("双")) {
					// luckPo.setEduct("even"); //
					// } else if (educt.contains("单")) {
					// luckPo.setEduct("odd"); //
					// }
					// winorlose = str.substring(eductIndexlest + 1,
					// str.length());
					// winorlose = winorlose.replace(" ", "");
					// if (winorlose.contains("挂")) {
					// luckPo.setWinorlose(0); // 輸錢等於0
					// } else if (winorlose.contains("中")) {
					// luckPo.setWinorlose(1); // 中獎等於1
					// } else if (winorlose.contains("等开")) {
					// luckPo.setWinorlose(-1); // 待開
					// }
					// luckShipdbList.add(luckPo);
					//
					// i++;
					// // luckShipdbList.add(luckPo);
					// System.out.println("第幾期" + period + "單雙結果:" + educt + "
					// 贏或輸:" + winorlose);
				}
			}
			boolean ex = dataProcessing01(numberOneList);
			// 判断转换一 是否成功

			// DBService.saveLuckeyAirShipData(luckShipdbList);
			//
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 整行讀取
	}

	public static boolean dataProcessing01(ArrayList<String> numberOneList) {

		String numberStr = numberOneList.get(numberOneList.size() - 1);

		System.out.println("取得最新一期【冠軍】的資料:" + numberStr);
		// 取得 039-041期 数量
		String opex = numberStr.substring(0, numberStr.indexOf("期 "));
		String[] journal = opex.split("-");

		int jouOne = Integer.valueOf(journal[0]);
		int joutwo = jouOne + 1;
		int jouthree = Integer.valueOf(journal[1]);

		ArrayList<Integer> number = new ArrayList<>();
		number.add(jouOne);
		number.add(joutwo);
		number.add(jouthree);

		Integer eductIndexfirst = numberStr.indexOf("【") + 1;
		Integer eductIndexlest = numberStr.indexOf("】");
		String educt = numberStr.substring(eductIndexfirst, eductIndexlest);

		System.out.println("第一期間:" + jouOne + "單雙結果:" + educt);
		System.out.println("第二期間:" + joutwo + "單雙結果:" + educt);
		System.out.println("第二期間:" + jouthree + "單雙結果:" + educt);

		int betResult = educt.equals("大") ? 1 : 2;
		number.forEach(sNo -> {
			System.out.print("開" + sNo);
			try {
				connectMariaDB(String.valueOf(sNo), sNo, betResult, 1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return false;

	}

	/**
	 * @author IMI-JAVA-Ryan
	 * @param onlypid
	 * @param period
	 * @param betResult
	 * @throws SQLException
	 */
	public static void connectMariaDB(String onlypid, int period, int betResult, int bet_number) throws SQLException {
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
