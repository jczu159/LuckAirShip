package com.luckairship.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class Screenshot {

	/**
	 * 截圖
	 * 
	 * @param filePath 截圖儲存資料夾路徑
	 * @param fileName 截圖檔名稱
	 * @throws Exception
	 */
	public static void captureScreen(String filePath, String fileName, String xyloaction) throws Exception {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		// 截圖儲存的路徑
		File screenFile = new File(filePath + fileName);
		// 如果資料夾路徑不存在，則建立
		if (!screenFile.getParentFile().exists()) {
			screenFile.getParentFile().mkdirs();
		}
		String[] split_line = xyloaction.split(",");

		// 指定螢幕區域，引數為截圖左上角座標(100,100)+右下角座標(500,500)
		BufferedImage subimage = image.getSubimage(Integer.valueOf(split_line[0]), Integer.valueOf(split_line[1]),
				Integer.valueOf(split_line[2]), Integer.valueOf(split_line[3]));
		ImageIO.write(subimage, "png", screenFile);

	}

	public static void scanImg(scanmondel data) throws Exception {
		Date now = new Date();
		SimpleDateFormat sdfPath = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfName = new SimpleDateFormat("yyyyMMddHHmmss");
		String path = sdfPath.format(now);
		String name = sdfName.format(now);

		String outImgpath = data.getOutImgpath().replace('\\', '/');

		captureScreen(outImgpath + "/", data.getOutImgName() + ".png", data.getScreenCoordinates());
	}

	public static void main(String[] args) throws Exception {
		Date now = new Date();
		SimpleDateFormat sdfPath = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfName = new SimpleDateFormat("yyyyMMddHHmmss");
		String path = sdfPath.format(now);
		String name = sdfName.format(now);

	}
}