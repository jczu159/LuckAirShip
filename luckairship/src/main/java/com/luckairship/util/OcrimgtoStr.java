package com.luckairship.util;

import java.io.File;
import java.util.ArrayList;

import com.luckairship.bo.LineNotify;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

public class OcrimgtoStr {
	/**
	 * tesseract for java， ocr（Optical Character Recognition，光学字符识别） 工具类
	 * 
	 * @author wind
	 */
	/**
	 * 从图片中提取文字,默认设置英文字库,使用classpath目录下的训练库
	 * 
	 * @param path
	 * @return
	 */

	private static String[] iosNumber = { "0⃣", "1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣", "9⃣", "🔟" };
	private static String emojumber = "";

	public static String readChar(String path) {
		// JNA Interface Mapping
		ITesseract instance = new Tesseract();
		// JNA Direct Mapping
		// ITesseract instance = new Tesseract1();
		File imageFile = new File(path);
		// In case you don't have your own tessdata, let it also be extracted
		// for you
		// 这样就能使用classpath目录下的训练库了
		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
		// Set the tessdata path
		instance.setDatapath(tessDataFolder.getAbsolutePath());
		// 英文库识别数字比较准确
		instance.setLanguage("eng");
		return getOCRText(instance, imageFile);
	}

	/**
	 * 从图片中提取文字
	 * 
	 * @param path     图片路径
	 * @param dataPath 训练库路径
	 * @param language 语言字库
	 * @return
	 */
	public static String readChar(String path, String dataPath, String language) {
		File imageFile = new File(path);
		ITesseract instance = new Tesseract();
		instance.setDatapath(dataPath);
		// 英文库识别数字比较准确
		instance.setLanguage(language);
		return getOCRText(instance, imageFile);
	}

	/**
	 * 识别图片文件中的文字
	 * 
	 * @param instance
	 * @param imageFile
	 * @return
	 */
	private static String getOCRText(ITesseract instance, File imageFile) {
		String result = null;
		try {
			result = instance.doOCR(imageFile);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void runOCR(scanmondel data) throws Exception {

		String path = data.getOutImgpath().replace('\\', '/');
		path = path + "/" + data.getOutImgName() + ".png";

		String periodNumber = Crawler.getPeriodNumber();

		File imageFile = new File(path);
		ITesseract instance = new Tesseract(); // JNA Interface Mapping
		// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		instance.setDatapath("C:/tessdata-master");
		instance.setLanguage("eng");

		String localStr = getOCRText(instance, imageFile);

		// try {
		// 判斷一下是否有資料 否則不必每一個都上網去讀光查詢所需要的資料回來 浪費錢
		if (!localStr.isEmpty() && localStr.length() >= 10) {
			ArrayList<String> duguanResult = DuguangOCR.DuguangOCR(path);
			System.out.println("取得的回傳值:" + duguanResult);

			String winningNumbers = duguanResult.get(0);
			if (!winningNumbers.isEmpty()) {
				ArrayList<String> numberList = SpulitBallNumber.spBall(winningNumbers, data.getMaxError());
				emojumber = "";
				numberList.forEach(number -> {
					switch (number) {
					case "0":
						emojumber += iosNumber[0].toString();
						break;
					case "01":
						emojumber += iosNumber[1].toString();
						break;
					case "02":
						emojumber += iosNumber[2].toString();
						break;
					case "03":
						emojumber += iosNumber[3].toString();
						break;
					case "04":
						emojumber += iosNumber[4].toString();
						break;
					case "05":
						emojumber += iosNumber[5].toString();
						break;
					case "06":
						emojumber += iosNumber[6].toString();
						break;
					case "07":
						emojumber += iosNumber[7].toString();
						break;
					case "08":
						emojumber += iosNumber[8].toString();
						break;
					case "09":
						emojumber += iosNumber[9].toString();
						break;
					case "10":
						emojumber += iosNumber[10].toString();
						break;

					default:
						break;
					}

				});
				
				
				periodNumber = periodNumber.substring(periodNumber.length()-3, periodNumber.length());
				int openPeriod = Integer.valueOf(periodNumber) ;
				openPeriod = openPeriod + 1 ;
				System.out.println("開講期次:" + openPeriod);
				System.out.println("預測開獎號碼為:" + emojumber);
				
				StringBuffer sb = new StringBuffer();
				sb.append("                              　　 ");// 15個空格
				sb.append("🚤 🚤 幸 運 飛 艇 🚤 🚤");
				sb.append("  🏁  "+openPeriod+"期  🏁   ");
				sb.append("               ");
				sb.append("  📣  第"+data.getGnumber()+"名");
				sb.append("  ");
				sb.append(emojumber);
				sb.append("🏅5碼二關公式🏅");

				LineNotify.callEvent("Ku80L6O7PTSnVOaUg4cV24wMebfXN3tQfxPxYWlgnhI", sb.toString());
			}
			// String[] arr2 = result.split(" ");
			// for (String sNmber : arr2) {
			// if (sNmber.length() >= 6 && !sNmber.contains("%")) {
			// sNmber = sNmber.replaceAll("\\s*", "");
			// if (sNmber.matches("^[0-9_]+$")) {
			//
			// StringBuffer sb = new StringBuffer();
			//
			// System.out.println(sNmber);
			// ArrayList<String> numberList = SpulitBallNumber.spBall(sNmber,
			// data.getMaxError());
			// String opennumner = "";
			// for (String element : numberList) {
			// opennumner = opennumner + " " + element;
			// }
			// sb.append(" ");
			// sb.append("🚤🚤幸運飛艇🚤🚤");
			// sb.append(" 🏁" + periodNumber + "期🏁");
			// sb.append("------------------------");
			// sb.append("📣" + data.getGnumber() + "名");
			// sb.append(opennumner);
			// sb.append("------------------------");
			// sb.append("🏅" + data.getMaxError() + "碼二關公式🏅");
			// sb.append("🏅測試中請勿隨意跟單!!🏅");
			// LineNotify.callEvent(data.getLineToken(), sb.toString());
			// }
			//
			// }
		}
		// }else {
		// System.out.println("解析不到任何資料");
		// }
		// } catch (TesseractException e) {
		// System.err.println(e.getMessage());
		// }

	}

	public static void main(String[] args) {

		
		
		String exge = "20200220132";
		
		String numner = exge.substring(exge.length()-3, exge.length());
		int ext = Integer.valueOf(numner);
		System.out.println(ext);
//		File imageFile = new File("C:/luckAirShipimg/NO01.png");
//		ITesseract instance = new Tesseract(); // JNA Interface Mapping
//		// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
//		instance.setDatapath("C:/tessdata-master");
//		instance.setLanguage("eng");
//
//		try {
//			String result = instance.doOCR(imageFile);
//
//			String[] arr2 = result.split(" ");
//			for (String ss : arr2) {
//				if (ss.length() >= 6 && !ss.contains("%")) {
//					ss = ss.replaceAll("\\s*", "");
//					if (ss.matches("^[0-9_]+$")) {
//						System.out.println(ss);
//					} else {
//						System.out.println("排除:" + ss);
//					}
//
//				}
//			}
//			System.out.println("結束");
//			// System.out.println(pencentblack);
//		} catch (TesseractException e) {
//			System.err.println(e.getMessage());
//		}
		// excute();
		/*
		 * String path = "src/main/resources/image/text.png";
		 * System.out.println(readChar(path));
		 */

		// String ch = "C:/Users/admin/Documents/oCam/001.png";
		// System.out.println(readChar(ch, "C:/tessdata-master", "eng"));
	}

}
