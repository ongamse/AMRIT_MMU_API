/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.mmu.data.location;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.iemr.mmu.utils.mapper.OutputMapper;

@Entity
@Table(name = "m_DistrictBranchMapping")
public class DistrictBranchMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DistrictBranchID")
	@Expose
	private Integer districtBranchID;
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "m_districtbranchmapping")
	// @Transient
	// private Set<I_BenDemographics> I_bendemographics;
	@Column(name = "BlockID")
	@Expose
	private Integer blockID;
	@Column(name = "PanchayatName")
	@Expose
	private String panchayatName;
	@Column(name = "VillageName")
	@Expose
	private String villageName;
	@Column(name = "Habitat")
	@Expose
	private String habitat;
	@Column(name = "PinCode")
	@Expose
	private String pinCode;
	@Column(name = "Deleted", insertable = false, updatable = true)
	@Expose
	private Boolean deleted;
	@Column(name = "CreatedBy")
	@Expose
	private String createdBy;
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;
	/*@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(unique = true, insertable = false, name = "blockID", updatable = false)
	@JsonIgnore
	private DistrictBlock districtblock;*/
	
	@Expose
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false, insertable = false, name = "blockID")
	private DistrictBlock districtBlock;
	@Expose
	@Transient
	String blockName;
	
	@Transient
	private OutputMapper outputMapper = new OutputMapper();

	public DistrictBranchMapping() {
	}

	public DistrictBranchMapping(Integer DistrictBranchID, String VillageName) {
		this.districtBranchID = DistrictBranchID;
		this.villageName = VillageName;
	}
	/*public DistrictBranchMapping(String blockName,Integer blockID ) {
		this.blockName=blockName;
		this.blockID=blockID;
	}*/
	

	public static String getVillageList(ArrayList<Object[]> resList) {
		DistrictBranchMapping villOBJ = null;
		ArrayList<DistrictBranchMapping> villList = new ArrayList<>();
		for (Object[] obj : resList) {
			villOBJ = new DistrictBranchMapping((Integer) obj[0], (String) obj[1]);
			villList.add(villOBJ);
		}
		return new Gson().toJson(villList);

	}

	public DistrictBranchMapping(Integer DistrictBranchID, String VillageName, String PanchayatName, String Habitat,
			String PinCode) {
		this.districtBranchID = DistrictBranchID;
		this.villageName = VillageName;
		this.panchayatName = PanchayatName;
		this.habitat = Habitat;
		this.pinCode = PinCode;
	}

	public Integer getDistrictBranchID() {
		return this.districtBranchID;
	}

	public void setDistrictBranchID(int districtBranchID) {
		this.districtBranchID = Integer.valueOf(districtBranchID);
	}

	public Integer getBlockID() {
		return this.blockID;
	}

	public void setBlockID(int blockID) {
		this.blockID = Integer.valueOf(blockID);
	}

	public String getPanchayatName() {
		return this.panchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		this.panchayatName = panchayatName;
	}

	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getHabitat() {
		return this.habitat;
	}

	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	public String getPinCode() {
		return this.pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = Boolean.valueOf(deleted);
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return this.lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	@Override
	public String toString() {
		return outputMapper.gson().toJson(this);
	}
}
