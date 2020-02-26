package com.luckairship.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author IMI-JAVA-Ryan 讀光 OCR 辨別文字使用
 *
 */
public class DuguangOCR {

	public static void main(String[] args) throws Exception {
		jsonParse(null);
	}

	public static ArrayList<String> jsonParse(JSONObject data) {

		ArrayList<String> result = new ArrayList<>();
		String content = data.getString("content");
		String[] sptb = content.split(" ");

		for (String LotteryResults : sptb) {
			if (LotteryResults.length() >= 10) {
				result.add(LotteryResults);
			}
		}
		return result;

	}

	public static ArrayList<String> DuguangOCR(String imagesPath) {
		ArrayList<String> resultList = new ArrayList<>();
//		String imagesPath = "C:/luckAirShipimg/NO01.png";
		Base64Util base64Utils = new Base64Util();
		String base64image = base64Utils.ImageToBase64(imagesPath);

//		System.out.println(base64image);
		String host = "https://ocrapi-advanced.taobao.com";
		String path = "/ocrservice/advanced";
		String method = "POST";
		String appcode = "c33258964ce841c19b5bab736a35d244";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		// 根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();

		JSONObject bodyjs = new JSONObject();

		bodyjs.put("img", base64image);
		bodyjs.put("url", "");
		bodyjs.put("prob", "true");
		bodyjs.put("charInfo", "false");
		bodyjs.put("rotate", "false");
		bodyjs.put("table", "true");
		bodyjs.put("sortPage", "true");

		try {

			HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodyjs.toString());

			System.out.println(response.toString());
			// 获取response的body

			JSONObject json;
			json = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
			System.out.println(json);

			resultList = jsonParse(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
