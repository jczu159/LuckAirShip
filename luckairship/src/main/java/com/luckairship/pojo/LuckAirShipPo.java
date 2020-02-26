package com.luckairship.pojo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bt_luckairship")
public class LuckAirShipPo implements Serializable {

	private static final long serialVersionUID = -1798070786983154676L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id; // ID 自增

	@Column(name = "PERIOD")
	private Integer period; // 期數

	@Column(name = "EDUCT")
	private String educt; // 下單結果

	@Column(name = "WINORLOSE")
	private Integer winorlose; // 贏或輸 TYPE = 1 等於贏 0 等於書

	@Column(name = "STRATEGYPATTERN")
	private Integer strategyPattern; // 策略模式

	@Column(name = "CREATE_DATE")
	private Date create_date; // 建立時間

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getEduct() {
		return educt;
	}

	public void setEduct(String educt) {
		this.educt = educt;
	}

	public Integer getWinorlose() {
		return winorlose;
	}

	public void setWinorlose(Integer winorlose) {
		this.winorlose = winorlose;
	}

	public Integer getStrategyPattern() {
		return strategyPattern;
	}

	public void setStrategyPattern(Integer strategyPattern) {
		this.strategyPattern = strategyPattern;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

}
