package com.iemr.mmu.service.dataSyncActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.syncActivity_syncLayer.SyncUtilityClass;

@Service
public class UploadDataToServerImpl implements UploadDataToServer {

	@Autowired
	private DataSyncRepository dataSyncRepository;

	private static final String SCHEMA_IEMR = "db_iemr";
	// private static final String SCHEMA_IDENTIRY = "db_identity";
	private static final int BATCH_SIZE = 50;

	public String getDataToSyncToServer(String groupName) {

		String syncData = null;
		switch (groupName) {
		case "benDetails": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benImages": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benVisitDetails": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benVitals": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benHistory": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benExamination": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benDiagnosis": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		case "benReferral": {
			syncData = syncBenVisitDetailsData();
		}
			break;

		default:
			break;
		}
		return syncData;
	}

	private String syncBenVisitDetailsData() {
		List<Integer> syncTableDetailsIDs = new ArrayList<>();
		// 1 = Beneficiary visit details : "t_benvisitdetail"
		syncTableDetailsIDs.add(1);
		// Start data sync process for this group
		String serverAcknowledgement = startDataSync(syncTableDetailsIDs);
		System.out.println(serverAcknowledgement);
		return null;
	}

	private String startDataSync(List<Integer> syncTableDetailsIDs) {
		// fetch table-name, van-side-columns, server-side-columns
		List<SyncUtilityClass> syncUtilityClassList = getVanAndServerColumns(syncTableDetailsIDs);
		for (SyncUtilityClass obj : syncUtilityClassList) {
			List<Map<String, Object>> syncData = getDataToSync(obj.getTableName());
			if (syncData != null && syncData.size() > 0) {
				int dataSize = syncData.size();
				int startIndex = 0;
				int fullBatchCount = dataSize / BATCH_SIZE;
				int remainder = dataSize % BATCH_SIZE;

				for (int i = 0; i < fullBatchCount; i++) {
					List<Map<String, Object>> syncDataBatch = getBatchOfAskedSizeDataToSync(syncData, startIndex,
							BATCH_SIZE);
					String serverAcknowledgement = syncDataToServer(SCHEMA_IEMR, obj.getTableName(),
							obj.getServerColumnName(), syncDataBatch);
					startIndex += BATCH_SIZE;
					System.out.println(serverAcknowledgement);
				}
				if (remainder > 0) {
					List<Map<String, Object>> syncDataBatch = getBatchOfAskedSizeDataToSync(syncData, startIndex,
							remainder);
					String serverAcknowledgement = syncDataToServer(SCHEMA_IEMR, obj.getTableName(),
							obj.getServerColumnName(), syncDataBatch);
					System.out.println(serverAcknowledgement);
				}

			}
		}
		return null;
	}

	private List<SyncUtilityClass> getVanAndServerColumns(List<Integer> syncTableDetailsIDs) {
		List<SyncUtilityClass> syncUtilityClassList = dataSyncRepository.getVanAndServerColumnList(syncTableDetailsIDs);
		return syncUtilityClassList;
	}

	private List<Map<String, Object>> getDataToSync(String tableName) {
		List<Map<String, Object>> resultSetList = dataSyncRepository.getDataForGivenSchemaAndTable(SCHEMA_IEMR,
				tableName);
		return resultSetList;
	}

	private List<Map<String, Object>> getBatchOfAskedSizeDataToSync(List<Map<String, Object>> syncData, int startIndex,
			int size) {
		List<Map<String, Object>> syncDataOfBatchSize = syncData.subList(startIndex, (startIndex + size));
		return syncDataOfBatchSize;
	}

	private String syncDataToServer(String schemaName, String tableName, String serverColumns,
			List<Map<String, Object>> dataToBesync) {
		System.out.println("kamariya karela baloop LOLLYPOP lagelu ........");
		return null;
	}

}
