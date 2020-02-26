package com.luckairship.util;

import java.util.ArrayList;

public class SpulitBallNumber {

	public static void main(String[] args) {

		String ball = "10020405070306";
		spBall(ball, "7");
	}

	public static ArrayList<String> spBall(String ball, String ballCount) {
		ArrayList<String> numberList = new ArrayList<>();
		switch (ballCount) {
		case "5":
			if (ball.length() == 10) {
				while (true) {
					String n = ball.substring(0, 2);
					numberList.add(n);
					ball = ball.replace(n, "");
					System.out.println("獲得的 :" + n);
					if (ball.length() == 0) {
						break;
					}
				}
			}
			break;

		case "6":
			if (ball.length() == 12) {
				while (true) {
					String n = ball.substring(0, 2);
					numberList.add(n);
					ball = ball.replace(n, "");
					System.out.println("獲得的 :" + n);
					if (ball.length() == 0) {
						break;
					}
				}
			}
			break;
		case "7":
			if (ball.length() == 14) {
				while (true) {
					String n = ball.substring(0, 2);
					numberList.add(n);
					ball = ball.replace(n, "");
					System.out.println("獲得的 :" + n);
					if (ball.length() == 0) {
						break;
					}
				}
			}
			break;
		default:
			break;
		}
		return numberList;

	}

}