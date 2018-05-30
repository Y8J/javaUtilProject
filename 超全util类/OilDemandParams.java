package com.zjht.youoil.util;

import java.io.Serializable;
import java.math.BigDecimal;

public class OilDemandParams implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3764332189320980845L;
	private Long pId;
	private Long cId;
	private String areaids;
	private String oilType;
	private Integer buyCount;
	private BigDecimal amount;
	private Integer discount;
	private Long[] stationIds;
	private Long addressId;
	private String postType;
	public OilDemandParams(){}
	/**
	 * @param pId
	 * @param cId
	 * @param areaids
	 * @param oilType
	 * @param buyCount
	 * @param amount
	 * @param discount
	 * @param stationIds
	 * @param addressId
	 */
	public OilDemandParams(Long pId, Long cId, String areaids, String oilType,
			Integer buyCount, BigDecimal amount, Integer discount,
			Long[] stationIds,String postType){
		super();
		this.pId = pId;
		this.cId = cId;
		this.areaids = areaids;
		this.oilType = oilType;
		this.buyCount = buyCount;
		this.amount = amount;
		this.discount = discount;
		this.stationIds = stationIds;
		this.postType = postType;
	}
	/**
	 * @param pId
	 * @param cId
	 * @param areaids
	 * @param oilType
	 * @param buyCount
	 * @param amount
	 * @param discount
	 * @param stationIds
	 * @param addressId
	 */
	public OilDemandParams(Long pId, Long cId, String areaids, String oilType,
			Integer buyCount, BigDecimal amount, Integer discount,
			Long[] stationIds, Long addressId) {
		super();
		this.pId = pId;
		this.cId = cId;
		this.areaids = areaids;
		this.oilType = oilType;
		this.buyCount = buyCount;
		this.amount = amount;
		this.discount = discount;
		this.stationIds = stationIds;
		this.addressId = addressId;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Long getcId() {
		return cId;
	}
	public void setcId(Long cId) {
		this.cId = cId;
	}
	public String getAreaids() {
		return areaids;
	}
	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}
	public String getOilType() {
		return oilType;
	}
	public void setOilType(String oilType) {
		this.oilType = oilType;
	}
	public Integer getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Long[] getStationIds() {
		return stationIds;
	}
	public void setStationIds(Long[] stationIds) {
		this.stationIds = stationIds;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
}
