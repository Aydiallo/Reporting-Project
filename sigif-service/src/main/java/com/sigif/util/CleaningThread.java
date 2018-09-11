package com.sigif.util;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Classe permettant de lancer un thread de nettoyage du tmp pour éviter de
 * bloquer l'utilisateur pendant ce temps.
 * 
 * @author Mickael Beaupoil
 *
 */
public class CleaningThread implements Runnable {

    /**
     * Dossier tmp de smartguide.
     */
    private String folderUpload;

    /**
     * Constructeur affectant le dossier tmp de msartguide.
     * 
     * @param folderUpload
     *            Dossier tmp de smartguide indiqué dans la conf.
     */
    public CleaningThread(String folderUpload) {
        this.folderUpload = folderUpload;
    }

    @Override
    public void run() {
        if (StringUtils.isNotBlank(this.folderUpload)) {
            File folderUploadSmg = new File(folderUpload);

            // Nettoie les dossiers datant de plus de 4 heures dans le dossier
            // temporaire pour éviter qu'il ne se remplisse trop vite.
            // Tous les dossiers dont le nom commence par 19 chiffres sont
            // concernés.
            // Formats possibles :
            // - 2017072516460254001273
            // - 2017072516464530992181postesDA
            // - 2017072517162300216
            final int nbHoursBeforeExpiration = -4;
            String formatDate = "yyyyMMddHHmmssSSSSS";
            int length = formatDate.length();
            SimpleDateFormat formater = new SimpleDateFormat(formatDate);
            // Regex permettant de ne récupérer que les dossiers dont le nom
            // commence par 19 chiffres
            // (et commençant par un 2 pour éviter les nettoyages trop larges)
            String regex = "^2[0-9]{18}.*";
            RegexFileFilter regexFileFilter = new RegexFileFilter(regex);

            // On ne garde que les dossiers
            AndFileFilter foldersFilter = new AndFileFilter(regexFileFilter, DirectoryFileFilter.DIRECTORY);
            File[] foldersToDelete = folderUploadSmg.listFiles((FileFilter) foldersFilter);
            String formattedDate = formater.format(DateUtils.addHours(new Date(), nbHoursBeforeExpiration));

            for (File folder : foldersToDelete) {
                if (folder.getName().substring(0, length - 1).compareTo(formattedDate) < 0) {
                        FileUtils.deleteQuietly(folder);
                }
            }
        }
    }
}
