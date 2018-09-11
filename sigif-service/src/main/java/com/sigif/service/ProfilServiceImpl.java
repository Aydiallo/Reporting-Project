package com.sigif.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigif.dto.ProfilDTO;
import com.sigif.modele.Profil;

/**
 * Implémentation du service d'accès aux profils
 */
@Service("profilService")
@Transactional
public class ProfilServiceImpl extends AbstractServiceImpl<Profil, ProfilDTO> implements ProfilService {
}
