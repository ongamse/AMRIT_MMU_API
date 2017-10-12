package com.iemr.mmu.repo.registrar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.registrar.BeneficiaryData;

@Repository
public interface RegistrarRepoBenData extends CrudRepository<BeneficiaryData, Long> {
	// @Query(" SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
	// + " concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',
	// IFNULL(bd.lastName,'')) as benName, "
	// + " bd.dob, bd.genderID, bd.fatherName, bdd.servicePointID,
	// bdd.villageID, bpm.phoneNo "
	// + " FROM BeneficiaryData bd INNER JOIN bd.benDemoData bdd INNER JOIN
	// bd.benPhoneMap bpm "
	// + " WHERE bdd.servicePointID = :spID ")
	// public List<Object[]> getRegistrarWorkList(@Param("spID") int spID);

	@Query(" SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
			+ " UPPER( concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',IFNULL(bd.lastName,''))) as benName, "
			+ " bd.dob, bd.genderID,UPPER(bd.fatherName), bdd.servicePointID, bdd.districtBranchID, bpm.phoneNo "
			+ " FROM BeneficiaryData bd INNER JOIN  bd.benDemoData bdd INNER JOIN bd.benPhoneMap bpm "
			+ " WHERE bdd.servicePointID = :spID  ")
	public List<Object[]> getRegistrarWorkList(@Param("spID") int spID);

	@Query("  SELECT bd.beneficiaryRegID, bd.beneficiaryID, "
			+ " UPPER(concat(IFNULL(bd.firstName, ''), ' ', IFNULL(bd.middleName, ''), ' ',IFNULL(bd.lastName,''))) as benName, "
			+ " bd.dob, bd.genderID,UPPER(bd.fatherName), bdd.servicePointID, bdd.districtBranchID, bpm.phoneNo "
			+ " FROM BeneficiaryData bd INNER JOIN  bd.benDemoData bdd INNER JOIN bd.benPhoneMap bpm "
			+ " WHERE bd.beneficiaryID = :benID ")
	public List<Object[]> getQuickSearch(@Param("benID") String benID);
	
	@Query("SELECT i.beneficiaryRegID, i.beneficiaryID,"
			+ "concat(IFNULL(i.firstName, ''), ' ', IFNULL(i.middleName, ''), ' ',IFNULL(i.lastName,'')) as benName, Date(i.dob), i.genderID, i.createdDate"
			+ " from BeneficiaryData i WHERE i.beneficiaryRegID =:beneficiaryRegID")
	public List<Object[]> getBenDetailsByRegID(@Param("beneficiaryRegID") Long beneficiaryRegID);

}
