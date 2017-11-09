package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.LabTestMaster;

@Repository
public interface LabTestMasterRepo extends CrudRepository<LabTestMaster, Integer> {
	@Query("SELECT c FROM LabTestMaster c where c.deleted != 1")
	public ArrayList<LabTestMaster> getLabTestMaster();
}