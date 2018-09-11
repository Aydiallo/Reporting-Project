package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.ServiceDepensierDAO;
import com.sigif.dto.ServiceDepensierDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.ServiceDepensier;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation du sService d'accès aux service dépensiers
 * @author Mamadou Ndir
 * @since 17 mai 2017
 */
@Service("serviceDepensierService")
@Transactional
public class ServiceDepensierServiceImpl extends AbstractServiceImpl<ServiceDepensier, ServiceDepensierDTO>
        implements ServiceDepensierService {

    @Override
    protected ServiceDepensierDAO getDao() {
        return (ServiceDepensierDAO) super.getDao();
    }

    @Override
    public Map<String, String> getAllSpendingServicesOfUserByLoginRoleAndMinistery(String login, String ministery,
            String role, String statut) throws TechnicalException {
        login = FunctionsUtils.trimOrNullifyString(login);
        ministery = FunctionsUtils.trimOrNullifyString(ministery);
        role = FunctionsUtils.trimOrNullifyString(role);
        statut = FunctionsUtils.trimOrNullifyString(statut);
        List<ServiceDepensier> servDepListQuery =
                this.getDao().getAllSpendingServicesOfUserByLoginRoleAndMinistery(login, ministery, role, statut);

        Map<String, String> serviceDepensierMap = new HashMap<String, String>();

        if (servDepListQuery.size() > 0) {
            for (ServiceDepensier s : servDepListQuery) {
                serviceDepensierMap.put(s.getCode(), s.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(serviceDepensierMap);
    }

    @Override
    public Map<String, String> getAllSpendingServicesByMinistery(String ministery, String statut)
            throws TechnicalException {
        ministery = FunctionsUtils.trimOrNullifyString(ministery);
        List<ServiceDepensier> servDepListQuery =
                this.getDao().getAllSpendingServicesByMinistery(ministery, statut);

        Map<String, String> serviceDepensierMap = new HashMap<String, String>();

        if (servDepListQuery.size() > 0) {
            for (ServiceDepensier s : servDepListQuery) {
                serviceDepensierMap.put(s.getCode(), s.getDesignation());
            }
        }
        return FunctionsUtils.sortByValue(serviceDepensierMap);
    }
}
