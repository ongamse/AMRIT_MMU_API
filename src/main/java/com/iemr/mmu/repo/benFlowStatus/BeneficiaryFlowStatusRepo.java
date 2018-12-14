package com.iemr.mmu.repo.benFlowStatus;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.benFlowStatus.BeneficiaryFlowStatus;

/***
 * 
 * @author NE298657
 *
 */
@Repository
public interface BeneficiaryFlowStatusRepo extends CrudRepository<BeneficiaryFlowStatus, Long> {
	@Query("SELECT  t from BeneficiaryFlowStatus t WHERE (t.nurseFlag = 1 OR t.nurseFlag = 100) AND t.deleted = false "
			+ " AND Date(t.visitDate)  = curdate() AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.visitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getNurseWorklistNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.benVisitID = :benVisitID, t.VisitReason = :visitReason, "
			+ " t.VisitCategory = :visitCategory, t.nurseFlag = :nurseFlag, t.doctorFlag = :docFlag, "
			+ " t.labIteration = :labIteration, t.lab_technician_flag = 0, t.radiologist_flag = :radiologistFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.benVisitDate = now(), "
			+ " t.visitCode = :benVisitCode, t.processed = 'U', t.vanID =:vanID "
			+ "  WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID " + " AND nurseFlag = 1  ")
	public int updateBenFlowStatusAfterNurseActivity(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benVisitID") Long benVisitID,
			@Param("visitReason") String visitReason, @Param("visitCategory") String visitCategory,
			@Param("nurseFlag") Short nurseFlag, @Param("docFlag") Short docFlag,
			@Param("labIteration") Short labIteration, @Param("radiologistFlag") Short radiologistFlag,
			@Param("oncologistFlag") Short oncologistFlag, @Param("benVisitCode") Long benVisitCode,
			@Param("vanID") Integer vanID);

	@Query("SELECT  t.benFlowID, t.beneficiaryRegID, t.visitDate, t.benName, t.age, t.ben_age_val, t.genderID, t.genderName, "
			+ " t.villageName, t.districtName, t.beneficiaryID, t.servicePointName, t.VisitReason, t.VisitCategory, t.benVisitID,  "
			+ " t.registrationDate, t.benVisitDate, t.visitCode, t.consultationDate FROM BeneficiaryFlowStatus t "
			+ " Where t.beneficiaryRegID = :benRegID AND t.benFlowID = :benFlowID ")
	public ArrayList<Object[]> getBenDetailsForLeftSidePanel(@Param("benRegID") Long benRegID,
			@Param("benFlowID") Long benFlowID);

	// MMU doc work-list
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE (t.doctorFlag = 1 OR t.doctorFlag = 2 OR "
			+ " t.doctorFlag = 3 OR t.nurseFlag = 2 OR t.doctorFlag = 9) AND t.deleted = false "
			+ " AND t.providerServiceMapId = :providerServiceMapId " + " ORDER BY benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	// TC doc work-list, 04-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t WHERE (t.doctorFlag = 1 OR t.doctorFlag = 2 OR "
			+ " t.doctorFlag = 3 OR t.nurseFlag = 2 OR t.doctorFlag = 9 ) "
			+ " OR ( t.doctorFlag = 4 AND t.tCRequestDate is not null AND DATE(t.tCRequestDate) = curdate() ) "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNewTC(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	// TC doc work-list, future scheduled 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.doctorFlag = 4 AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND DATE(t.tCRequestDate) > curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getDocWorkListNewFutureScheduledTC(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	// TC Specialist work-list, 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.doctorFlag = 4 AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.tCSpecialistUserID =:tCSpecialistUserID AND t.specialist_flag != 11 "
			+ " AND DATE(t.tCRequestDate) = curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getTCSpecialistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID);

	// TC Specialist work-list, future scheduled, 13-12-2018
	@Query("SELECT t from BeneficiaryFlowStatus t "
			+ " WHERE t.doctorFlag = 4 AND t.tCRequestDate is not null AND t.tCSpecialistUserID is not null "
			+ " AND t.tCSpecialistUserID =:tCSpecialistUserID AND t.specialist_flag != 11 "
			+ " AND DATE(t.tCRequestDate) > curdate() "
			+ " AND t.deleted = false AND t.providerServiceMapId = :providerServiceMapId "
			+ " ORDER BY t.benVisitDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getTCSpecialistWorkListNewFutureScheduled(
			@Param("providerServiceMapId") Integer providerServiceMapId,
			@Param("tCSpecialistUserID") Integer tCSpecialistUserID);

	@Query("SELECT  t.benFlowID from BeneficiaryFlowStatus t WHERE t.beneficiaryRegID = :benRegID "
			+ "AND t.providerServiceMapId = :provoderSerMapID AND "
			+ " (t.nurseFlag = 1 OR t.nurseFlag = 100) AND Date(t.visitDate)  = curdate() AND t.deleted = false")
	public ArrayList<Long> checkBenAlreadyInNurseWorkList(@Param("benRegID") Long benRegID,
			@Param("provoderSerMapID") Integer provoderSerMapID);

	@Query("SELECT t from BeneficiaryFlowStatus t WHERE (t.nurseFlag = 2 OR t.doctorFlag = 2) AND t.deleted = false "
			+ "AND t.providerServiceMapId = :providerServiceMapId ORDER BY consultationDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getLabWorklistNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag, t.consultationDate = now(), t.processed = 'U'  "
			+ " WHERE t.benFlowID = :benFlowID AND " + " t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivity(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.doctorFlag = :docFlag , t.pharmacist_flag = :pharmaFlag, "
			+ " t.oncologist_flag = :oncologistFlag , t.processed = 'U' " + " WHERE t.benFlowID = :benFlowID AND "
			+ " t.beneficiaryRegID = :benRegID AND t.beneficiaryID = :benID ")
	public int updateBenFlowStatusAfterDoctorActivityUpdate(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("benID") Long benID, @Param("docFlag") Short docFlag,
			@Param("pharmaFlag") Short pharmaFlag, @Param("oncologistFlag") Short oncologistFlag);

	@Query("SELECT t from BeneficiaryFlowStatus t WHERE t.radiologist_flag = 1 AND t.providerServiceMapId= :providerServiceMapId")
	public ArrayList<BeneficiaryFlowStatus> getRadiologistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	@Query("SELECT t from BeneficiaryFlowStatus t WHERE t.oncologist_flag = 1 AND t.providerServiceMapId= :providerServiceMapId")
	public ArrayList<BeneficiaryFlowStatus> getOncologistWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	@Query("SELECT t from BeneficiaryFlowStatus t WHERE t.pharmacist_flag = 1 "
			+ "  AND t.providerServiceMapId= :providerServiceMapId AND doctorFlag = 9 "
			+ "  ORDER BY consultationDate DESC ")
	public ArrayList<BeneficiaryFlowStatus> getPharmaWorkListNew(
			@Param("providerServiceMapId") Integer providerServiceMapId);

	@Query("SELECT t.pharmacist_flag from BeneficiaryFlowStatus t WHERE t.benFlowID = :benFlowID")
	public Short getPharmaFlag(@Param("benFlowID") Long benFlowID);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.nurseFlag = :nurseFlag, t.processed = 'U' "
			+ " where t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID ")
	public int updateBenFlowStatusAfterNurseDataUpdateNCD_Screening(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("nurseFlag") Short nurseFlag);

	@Transactional
	@Modifying
	@Query("UPDATE BeneficiaryFlowStatus t set t.nurseFlag = :nurseFlag, t.doctorFlag = :doctorFlag, "
			+ " t.lab_technician_flag = :labFlag, t.processed = 'U' "
			+ " WHERE t.benFlowID = :benFlowID AND t.beneficiaryRegID = :benRegID ")
	public int updateBenFlowStatusAfterLabResultEntry(@Param("benFlowID") Long benFlowID,
			@Param("benRegID") Long benRegID, @Param("nurseFlag") Short nurseFlag,
			@Param("doctorFlag") Short doctorFlag, @Param("labFlag") Short labFlag);

	// beneficiary previous visit history
	@Query("SELECT benFlowID, beneficiaryRegID, visitCode, "
			+ " benVisitDate, benVisitNo, VisitReason, VisitCategory  from BeneficiaryFlowStatus "
			+ "  WHERE beneficiaryRegID = :beneficiaryRegID AND ((doctorFlag = 9) "
			+ " OR (nurseFlag = 9 AND doctorFlag = 0))  ORDER BY benVisitDate DESC ")
	public ArrayList<Object[]> getBenPreviousHistory(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query(" SELECT COUNT(benFlowID) FROM BeneficiaryFlowStatus "
			+ " WHERE beneficiaryRegID = :beneficiaryRegID AND VisitCategory = 'NCD screening' ")
	public Long getNcdScreeningVisitCount(@Param("beneficiaryRegID") Long beneficiaryRegID);

}
