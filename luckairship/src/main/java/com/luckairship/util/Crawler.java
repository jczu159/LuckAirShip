package com.luckairship.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

public class Crawler {
	private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
	private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";
	WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_10);
	HtmlPage page;
	HtmlForm form;
	String acc = "";
	String pwd = "";

	public static void main(String[] args) throws Exception {

	
		getPeriodNumber();

	}

	public static String getPeriodNumber() throws Exception {
		// Console console = System.console();
		// // 使用者輸入帳密
		// Scanner sc = new Scanner(System.in);
		// System.out.println("歡迎使用學分計算系統! 請輸入校務行政系統帳密");
		//
		//
		// // 開始登入程序
		// System.out.println("-------------登入中-------------");
		// webClient.getOptions().setCssEnabled(false);// 禁用CSS
		//// form = page.getFormByName("f1"); // form的name是f1

		//
		//
		// page = form.getInputByValue("開始查詢").click(); // 按下登入之後的網頁
		//
		// System.out.println(page.asText());
		//
		// submit();

		// 浏览器

		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_10);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getCookieManager().setCookiesEnabled(true);

		HtmlPage page = webClient.getPage("http://www.luckyairship.com/");



		String standardNo = page.getElementById("openedPeriodNumber").asText();
		System.out.println("此次爬蟲到的期數: " + standardNo);
		webClient.closeAllWindows();
		return standardNo;

		// HtmlSelect selectStartStationSelect = (HtmlSelect)
		// page.getElementByName("selectStartStation");
		// HtmlOption startoption =
		// selectStartStationSelect.getOptionByText("台北");
		// selectStartStationSelect.setSelectedAttribute(startoption, true);
		//
		// HtmlSelect destinationStationselect = (HtmlSelect)
		// page.getElementByName("selectDestinationStation");
		// HtmlOption destinationoption =
		// destinationStationselect.getOptionByText("台中");
		// destinationStationselect.setSelectedAttribute(destinationoption,
		// true);
		//
		// HtmlInput htmlInput =
		// page.getFirstByXPath("//input[@name='homeCaptcha:securityCode']");
		// htmlInput.click();
		// htmlInput.setAttribute("value", "1515");
		//
		// // Ryan牌機器人 設定出發日期
		// HtmlInput toTimeInputField =
		// page.getFirstByXPath("//input[@name='toTimeInputField']");
		// toTimeInputField.click();
		// toTimeInputField.setAttribute("value", "2020/11/11");
		//
		// HtmlSelect select = (HtmlSelect)
		// page.getElementByName("toTimeTable");
		// HtmlOption option = select.getOptionByText("07:30");
		// select.setSelectedAttribute(option, true);
		//
		// String imageUrl =
		// page.getElementById("BookingS1Form_homeCaptcha_passCode").getAttribute("src");
		//
		// imageUrl = imageUrl.substring(imageUrl.indexOf("?"),
		// imageUrl.length());
		// imageUrl = "https://irs.thsrc.com.tw/IMINT/" + imageUrl;
		// System.out.println("取得圖片路徑:" + imageUrl);
		//
		// HtmlImage image =
		// page.<HtmlImage>getFirstByXPath("//img[@id='BookingS1Form_homeCaptcha_passCode']");
		// File imageFile = new File("C:\\Downloads\\opef.jpg");
		// image.saveAs(imageFile);
		//
		//// System.out.println("请输入验证码：");
		//// Scanner sc = new Scanner(System.in);
		//// String sCode = sc.nextLine();
		//// System.out.println("你輸入的認證碼為 " + sCode);
		// webClient.waitForBackgroundJavaScriptStartingBefore(5000);
		// HtmlInput securityCode =
		// page.getFirstByXPath("//input[@name='homeCaptcha:securityCode']");
		// securityCode.click();
		// securityCode.setAttribute("value", "15");
		//
		// System.out.println("送出之前的參數");

		// System.out.println("開始進行送出動作");
		// webClient.waitForBackgroundJavaScriptStartingBefore(5000);
		//// HtmlSubmitInput button = (HtmlSubmitInput)
		// page.getElementById("SubmitButton");
		////// page = button.click();
		//// button.click();
		//
		//// HtmlInput SubmitButton =
		// page.getFirstByXPath("//input[@name='SubmitButton']");
		//// HtmlPage mainPage = SubmitButton.click();
		// HtmlSubmitInput loginButton = form.getInputByName("SubmitButton");
		// HtmlPage indexPage = loginButton.click();
		//
		// System.out.println(indexPage.asText());

		// System.out.println(page.asText());
		// if (page.asXml().contains("* Please try again.")) {
		//
		// }

		// 關閉頁面
		// webClient.close();

	}

	public String pageText(String year, String term) throws Exception// 依學年和學期抓取網頁
	{
		HtmlForm form2 = page.getFormByName("form1");
		HtmlSelect state = form2.getSelectByName("cboYear");// 學年
		page = state.setSelectedAttribute(year, true);

		form2 = page.getFormByName("form1");
		HtmlSelect state2 = form2.getSelectByName("cboTerm");// 學期
		page = state2.setSelectedAttribute(term, true);

		// 回傳網頁文字
		String a = page.asText();
		return a;
	}

	// 获取image url地址
	public static List<String> getImageURL(String html) {
		Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
		List<String> list = new ArrayList<>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}

	// 获取image src地址
	public static List<String> getImageSrc(List<String> listUrl) {
		List<String> listSrc = new ArrayList<String>();
		for (String img : listUrl) {
			Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(img);
			while (matcher.find()) {
				listSrc.add(matcher.group().substring(0, matcher.group().length() - 1));
			}
		}
		return listSrc;
	}

	// 下载图片
	private static void Download(List<String> listImgSrc) {
		try {
			// 开始时间
			Date begindate = new Date();
			for (String url : listImgSrc) {
				// 开始时间
				Date begindate2 = new Date();
				String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
				URL uri = new URL(url);
				InputStream in = uri.openStream();
				FileOutputStream fo = new FileOutputStream(new File(imageName));// 文件输出流
				byte[] buf = new byte[1024];
				int length = 0;
				System.out.println("开始下载:" + url);
				while ((length = in.read(buf, 0, buf.length)) != -1) {
					fo.write(buf, 0, length);
				}
				// 关闭流
				in.close();
				fo.close();
				System.out.println(imageName + "下载完成");
				// 结束时间
				Date overdate2 = new Date();
				double time = overdate2.getTime() - begindate2.getTime();
				System.out.println("耗时：" + time / 1000 + "s");
			}
			Date overdate = new Date();
			double time = overdate.getTime() - begindate.getTime();
			System.out.println("总耗时：" + time / 1000 + "s");
		} catch (Exception e) {
			System.out.println("下载失败");
		}
	}

}