package com.iemr.mmu.data.anc;

import java.sql.Timestamp;
import java.util.ArrayList;

public class WrapperChildOptionalVaccineDetail {

	private Long beneficiaryRegID;
	private Long benVisitID;
	private Integer providerServiceMapID;
	private String createdBy;

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	private ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineList;

	public ArrayList<ChildOptionalVaccineDetail> getChildOptionalVaccineList() {
		return childOptionalVaccineList;
	}

	public void setChildOptionalVaccineList(ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineList) {
		this.childOptionalVaccineList = childOptionalVaccineList;
	}

	public ArrayList<ChildOptionalVaccineDetail> getChildOptionalVaccineDetails() {

		ArrayList<ChildOptionalVaccineDetail> childOptionalVaccineListTMP = new ArrayList<>();
		if (null != childOptionalVaccineList && childOptionalVaccineList.size() > 0) {
			for (ChildOptionalVaccineDetail childOptionalVaccine : childOptionalVaccineList) {

				if (childOptionalVaccine.getVaccineName() != null) {
					childOptionalVaccine.setBeneficiaryRegID(beneficiaryRegID);
					childOptionalVaccine.setBenVisitID(benVisitID);
					childOptionalVaccine.setProviderServiceMapID(providerServiceMapID);
					childOptionalVaccine.setCreatedBy(createdBy);

					childOptionalVaccineListTMP.add(childOptionalVaccine);
				}
			}
		}
		// else {
		// ChildOptionalVaccineDetail childOptionalVaccine = new
		// ChildOptionalVaccineDetail();
		// childOptionalVaccine.setBeneficiaryRegID(beneficiaryRegID);
		// childOptionalVaccine.setBenVisitID(benVisitID);
		// childOptionalVaccine.setProviderServiceMapID(providerServiceMapID);
		// childOptionalVaccine.setCreatedBy(createdBy);
		//
		// childOptionalVaccineList = new
		// ArrayList<ChildOptionalVaccineDetail>();
		// childOptionalVaccineList.add(childOptionalVaccine);
		// }
		return childOptionalVaccineListTMP;
	}

	public static WrapperChildOptionalVaccineDetail getChildOptionalVaccineDetail(
			ArrayList<Object[]> childOptionalVaccineDetail) {
		WrapperChildOptionalVaccineDetail WCO = new WrapperChildOptionalVaccineDetail();
		WCO.childOptionalVaccineList = new ArrayList<ChildOptionalVaccineDetail>();

		if (null != childOptionalVaccineDetail && childOptionalVaccineDetail.size() > 0) {
			Object[] obj1 = childOptionalVaccineDetail.get(0);
			WCO.beneficiaryRegID = (Long) obj1[0];
			WCO.benVisitID = (Long) obj1[1];
			WCO.providerServiceMapID = (Integer) obj1[2];
			for (Object[] obj : childOptionalVaccineDetail) {

				ChildOptionalVaccineDetail obstetricHistory = new ChildOptionalVaccineDetail((String) obj[3],
						(String) obj[4], (String) obj[5], (Timestamp) obj[6], (String) obj[7], (String) obj[8]);

				WCO.childOptionalVaccineList.add(obstetricHistory);

			}
		}
		return WCO;
	}
}
