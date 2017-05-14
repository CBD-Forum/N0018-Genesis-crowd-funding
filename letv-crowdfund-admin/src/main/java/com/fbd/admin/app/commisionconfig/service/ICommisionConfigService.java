package com.fbd.admin.app.commisionconfig.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.fbd.core.app.commisionconfig.model.CommisionConfigModel;
import com.fbd.core.common.model.SearchResult;


public interface ICommisionConfigService {

	SearchResult<CommisionConfigModel> getCommisionConfigPage(CommisionConfigModel commisionConfigModel);
	
    int saveCommisionConfig(CommisionConfigModel commisionConfigModel);

    List<CommisionConfigModel> getCommisionConfigList(CommisionConfigModel commisionConfigModel);

    int modifyCommisionConfig(CommisionConfigModel commisionConfigModel);
    @PreAuthorize("hasPermission(null, 'security.operation.commision_config_delete')")
    int removeCommisionConfig(String id);

	CommisionConfigModel getCommisionConfigById(String id);
}
