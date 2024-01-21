package pe.pjh.ws.application.service.dataset;

import pe.pjh.ws.adapter.out.DataSetManager;
import pe.pjh.ws.adapter.out.datasource.DataSource;
import pe.pjh.ws.application.service.StatusService;
import pe.pjh.ws.application.service.setting.DataSetSetting;

public abstract class AbstractDataSourceService {
    StatusService statusService;
    DataSetManager dataSetManager;

    public AbstractDataSourceService(StatusService statusService, DataSetManager dataSetManager) {
        this.statusService = statusService;
        this.dataSetManager = dataSetManager;
    }

    protected DataSource getDataSource() {
        DataSetSetting dataSetSetting = statusService.getCurrentDataSetSetting();
        DataSetManager.DataSet dataSet = dataSetManager.makeDateSet(dataSetSetting);
        return dataSet.dataSource();
    }
}
