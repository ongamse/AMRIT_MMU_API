package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.institution.Institute;
import com.iemr.mmu.data.masterdata.doctor.PreMalignantLesion;
import com.iemr.mmu.repo.masterrepo.doctor.InstituteRepo;
import com.iemr.mmu.repo.masterrepo.doctor.PreMalignantLesionMasterRepo;

@Service
public class DoctorMasterDataServiceImpl implements DoctorMasterDataService {

	@Autowired
	private PreMalignantLesionMasterRepo preMalignantLesionMasterRepo;

	private InstituteRepo instituteRepo;

	@Autowired
	public void setInstituteRepo(InstituteRepo instituteRepo) {
		this.instituteRepo = instituteRepo;
	}

	public String getCancerScreeningMasterDataForDoctor(int psmID) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> preMalignantLesionTypes = preMalignantLesionMasterRepo.getPreMalignantLesionMaster();
		ArrayList<Object[]> instituteDetails = instituteRepo.getInstituteDetails(psmID);

		try {
			Institute institute = new Institute();
			resMap.put("preMalignantLesionTypes",
					PreMalignantLesion.getPreMalignantLesionMasterData(preMalignantLesionTypes));

			resMap.put("higherHealthCare", institute.getinstituteDetails(instituteDetails));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Gson().toJson(resMap);

	}
}