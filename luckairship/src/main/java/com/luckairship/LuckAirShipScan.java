package com.luckairship;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.luckairship.util.OcrimgtoStr;
import com.luckairship.util.Screenshot;
import com.luckairship.util.scanmondel;

public class LuckAirShipScan {

	private JFrame frame;
	private static String logStr = "";
	private static String maxError = ""; // 最大錯誤次數
	private static String outImgpath = ""; // 輸出IMG
	private static String lineToken = ""; // LINE TOKEN
	private static String screenCoordinates = ""; // 螢幕座標
	private static String outImgName = ""; // 輸出圖片名稱
	private static String gnumber = "";
	private static String numberofseconds = ""; // 時間偏移輛

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LuckAirShipScan window = new LuckAirShipScan();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LuckAirShipScan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 607, 471);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTextArea textArea = new JTextArea(); // 設定log區域
		JFormattedTextField formattedTextField_1 = new JFormattedTextField(); // 設定最大錯誤次數
		JFormattedTextField frmtdtxtfldCusersadmindocumentsheidisql = new JFormattedTextField(); // 設定導出圖片路徑
		JFormattedTextField frmtdtxtfldLinetoken = new JFormattedTextField(); // LINE TOKEN
		JFormattedTextField formattedTextField_4 = new JFormattedTextField(); // 螢幕截圖座標
		JFormattedTextField frmtdtxtfldNo = new JFormattedTextField(); // 輸出圖片名稱
		JFormattedTextField formattedTextField_5 = new JFormattedTextField(); // 程式上的名次
		JFormattedTextField formattedTextField_2 = new JFormattedTextField(); // 設定上偏移秒數
		scanmondel setting = new scanmondel();

		JButton btnNewButton = new JButton("測試擷取圖片");
		btnNewButton.setBounds(342, 107, 105, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("--測試擷取通道開始--");

				try {
					Screenshot.scanImg(setting);
					textArea.setText("保存圖片成功");

				} catch (Exception e1) {
					textArea.setText(e1.toString());
				}
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnNewButton);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(219, 6, -144, 21);
		frame.getContentPane().add(formattedTextField);

		JButton btnNewButton_1 = new JButton("開始運行");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("--R牌程式開始運行--");

				try {

					Boolean result = false;
					int count = 0;
					while (!result) {
						try {
							Thread.sleep(5 * 1000); // 设置暂停的时间 5 秒
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							String time = sdf.format(new Date());
							String[] timelist = time.split(":");
							String realtime = timelist[1];
							System.out.println("目前分鐘數:" + realtime);

							if (Integer.valueOf(realtime) % 5 == 0) {
								Screenshot.scanImg(setting);
								textArea.setText("保存圖片成功 -- >> 正在開始解析");
								Thread.sleep(1500);
								textArea.setText("正在開始解析");
								OcrimgtoStr.runOCR(setting);
								Thread.sleep(60 * 1000);

							} else {
								System.out.println("未達進入判斷分鐘數");
							}

					

						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}

				} catch (Exception e1) {
					textArea.setText(e1.toString());
				}

			}
		});
		btnNewButton_1.setBounds(355, 374, 83, 23);
		frame.getContentPane().add(btnNewButton_1);

		// 設定幾個開獎號碼
		formattedTextField_1.setText("5");
		formattedTextField_1.setBounds(118, 37, 32, 21);
		frame.getContentPane().add(formattedTextField_1);
		// 設定最大錯誤次數

		// 設定導出圖片路徑
		frmtdtxtfldCusersadmindocumentsheidisql.setText("C:\\luckAirShipimg");
		frmtdtxtfldCusersadmindocumentsheidisql.setBounds(118, 108, 203, 21);
		frame.getContentPane().add(frmtdtxtfldCusersadmindocumentsheidisql);
		// 設定導出圖片路徑

		JLabel label = new JLabel("幾個號碼(必填)");
		label.setBounds(10, 36, 98, 23);
		frame.getContentPane().add(label);

		JButton button = new JButton("保存設定");
		button.setBounds(258, 374, 87, 23);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("保存設定");
				textArea.setText(logStr + "保存設定已完成");

				maxError = formattedTextField_1.getText();
				outImgpath = frmtdtxtfldCusersadmindocumentsheidisql.getText();
				lineToken = frmtdtxtfldLinetoken.getText();
				screenCoordinates = formattedTextField_4.getText();
				outImgName = frmtdtxtfldNo.getText();
				gnumber = formattedTextField_5.getText();
				numberofseconds = formattedTextField_2.getText();

				setting.setGnumber(gnumber);
				setting.setLineToken(lineToken);
				setting.setMaxError(maxError);
				setting.setOutImgName(outImgName);
				setting.setOutImgpath(outImgpath);
				setting.setScreenCoordinates(screenCoordinates);
				setting.setNumberofseconds(numberofseconds);

				StringBuilder logStr = new StringBuilder();
				logStr.append("保存數據完成如下");
				logStr.append("maxError :" + maxError);
				logStr.append("outImgpath :" + outImgpath);
				logStr.append("lineToken :" + lineToken);
				logStr.append("screenCoordinates :" + screenCoordinates);
				logStr.append("outImgName :" + outImgName);
				logStr.append("gnumber :" + gnumber);
				logStr.append("numberofseconds :" + numberofseconds);

				textArea.setText(logStr.toString());

			}
		});
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("重設條件");
		button_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				formattedTextField_1.setText("");
				maxError = "";

				frmtdtxtfldCusersadmindocumentsheidisql.setText("");
				outImgpath = "";

				frmtdtxtfldLinetoken.setText("");
				lineToken = "";

				formattedTextField_4.setText("");
				screenCoordinates = "";

				frmtdtxtfldNo.setText("");
				outImgName = "";

				formattedTextField_5.setText("");
				gnumber = "";

				formattedTextField_2.setText("");
				numberofseconds = "";
			}
		});
		button_1.setBounds(161, 374, 87, 23);
		frame.getContentPane().add(button_1);

		JLabel label_1 = new JLabel("圖片輸出路徑");
		label_1.setBounds(10, 111, 114, 15);
		frame.getContentPane().add(label_1);

		JLabel lblLineToken = new JLabel("LINE TOKEN");
		lblLineToken.setBounds(10, 150, 81, 15);
		frame.getContentPane().add(lblLineToken);

		// LINE TOKEN
		frmtdtxtfldLinetoken.setText("1FbLHmxqvLA7i87pX8u4Ni7M6PS8zxwDWceUQLrNUda");
		frmtdtxtfldLinetoken.setBounds(118, 147, 203, 21);
		frame.getContentPane().add(frmtdtxtfldLinetoken);

		formattedTextField_4.setText("713,348,666,147");
		formattedTextField_4.setBounds(118, 190, 203, 21);
		frame.getContentPane().add(formattedTextField_4);

		JLabel label_3 = new JLabel("螢幕截圖座標");
		label_3.setBounds(10, 193, 81, 15);
		frame.getContentPane().add(label_3);

		JLabel lblXy = new JLabel("XY座標之間請使用逗點隔開");
		lblXy.setBounds(342, 193, 165, 15);
		frame.getContentPane().add(lblXy);

		formattedTextField_5.setText("1");
		formattedTextField_5.setBounds(118, 234, 53, 21);
		frame.getContentPane().add(formattedTextField_5);

		JLabel label_5 = new JLabel("程式抓取名次");
		label_5.setBounds(10, 237, 81, 15);
		frame.getContentPane().add(label_5);

		JLabel label_7 = new JLabel("輸出圖片名稱");
		label_7.setBounds(10, 80, 94, 15);
		frame.getContentPane().add(label_7);

		frmtdtxtfldNo.setText("NO01");
		frmtdtxtfldNo.setBounds(118, 77, 203, 21);
		frame.getContentPane().add(frmtdtxtfldNo);

		JButton button_2 = new JButton("結束運行");
		button_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				System.exit(0);

			}
		});
		button_2.setBounds(448, 374, 94, 23);
		frame.getContentPane().add(button_2);

		JLabel lblryan = new JLabel("此版本 V1.0.0.0");
		lblryan.setBounds(448, 407, 122, 15);
		frame.getContentPane().add(lblryan);
		textArea.setBounds(118, 265, 349, 93);
		frame.getContentPane().add(textArea);

		JLabel lbllog = new JLabel("日誌LOG");
		lbllog.setBounds(10, 341, 81, 15);
		frame.getContentPane().add(lbllog);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(219, 343, 2, 2);
		frame.getContentPane().add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(246, 343, 2, 2);
		frame.getContentPane().add(scrollPane_1);

		JLabel label_4 = new JLabel("設定開獎偏移秒");
		label_4.setBounds(274, 237, 105, 15);
		frame.getContentPane().add(label_4);

		formattedTextField_2.setText("0");
		formattedTextField_2.setBounds(400, 234, 67, 21);
		frame.getContentPane().add(formattedTextField_2);
	}

	public static void runTimer() {

	}
}
