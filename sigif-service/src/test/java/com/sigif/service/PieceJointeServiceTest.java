package com.sigif.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sigif.exception.TechnicalException;
import com.sigif.testutil.AbstractDbTestCase;
import com.sigif.util.AttachmentsUtils;
import com.sigif.util.Constantes;

public class PieceJointeServiceTest extends AbstractDbTestCase {
    @Autowired
    PieceJointeService pieceJointeService;

    @Test
    public void testGetPjAndCopyFileInTmp()
            throws ConfigurationException, DatabaseUnitException, SQLException, TechnicalException, IOException {
        this.emptyDatabase();
        this.loadDataFileAndOverWrite("dataTestFiles/PjServiceTest/PjTest.xml");
        String path = "C:\\tmp\\PJ\\DA\\numeroM\\numeroM_10.jpg";
        // Use relative path for Unix systems
        File f = new File(path);
        if (!f.isFile()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        Map<String, String> infosPj = pieceJointeService.getPjAndCopyFileInTmp(10);
        String uploadString = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
        File file = AttachmentsUtils.checkUploadStringIsCorrect(uploadString);
        assertNotNull(file);
        assertEquals("piece jointe M.jpg", file.getName());
        assertEquals(new File("C:\\tmp"), file.getParentFile().getParentFile());
        assertTrue(file.exists());
        
        
        path = "C:\\tmp\\PJ\\DA\\numeroM\\numeroM_13.jpg";
        // Use relative path for Unix systems
        f = new File(path);
        if (!f.isFile()) {
            f.createNewFile();
        }

        infosPj = pieceJointeService.getPjAndCopyFileInTmp(13);
        uploadString = infosPj.get(Constantes.KEY_MAP_UPLOAD_STRING_PJ);
        file = AttachmentsUtils.checkUploadStringIsCorrect(uploadString);
        assertNotNull(file);
        assertEquals("piece pda+6.jpg", file.getName());
        assertEquals(new File("C:\\tmp"), file.getParentFile().getParentFile());
        assertTrue(file.exists());
    }
}
