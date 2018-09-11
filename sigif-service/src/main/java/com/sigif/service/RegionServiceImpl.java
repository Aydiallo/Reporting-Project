package com.sigif.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigif.dao.RegionDAO;
import com.sigif.dto.RegionDTO;
import com.sigif.exception.TechnicalException;
import com.sigif.modele.Region;
import com.sigif.util.FunctionsUtils;

/**
 * Implémentation de la classe d'accès aux régions.
 * 
 * @author Meissa Beye
 *
 */
@Service("regionsService")
@Transactional
public class RegionServiceImpl extends AbstractServiceImpl<Region, RegionDTO> implements RegionService {

	/**
	 * DAO pour Region.
	 */
	@Autowired
	private RegionDAO regionDAO;

	@Override
	public Map<String, String> getAllAreas() throws TechnicalException {

		List<Region> regions = regionDAO.getAllRegionActifs();
		Map<String, String> resultat = new HashMap<String, String>();
		if (regions != null) {
			for (Region region : regions) {
				resultat.put(region.getCode(), region.getDesignation());
			}
		}
		return FunctionsUtils.sortByValue(resultat);
	}

}
