package com.github.joraclista.kraken;

import com.github.joraclista.kraken.api.exceptions.KrakenApiException;
import com.github.joraclista.kraken.model.request.KrakenRequest;
import com.github.joraclista.kraken.model.request.MultipleResizeRequestImpl;
import com.github.joraclista.kraken.model.request.OptimizeRequestImpl;
import com.github.joraclista.kraken.model.request.ResizeRequestImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.MultipleResizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.OptimizeResponseImpl;
import com.github.joraclista.kraken.model.response.AbstractKrakenResponse.SingleResizeResponseImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jmimemagic.MagicMatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

import static net.sf.jmimemagic.Magic.getMagicMatch;
import static org.springframework.http.MediaType.*;

/**
 * Created by Alisa
 * version 1.0.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KrakenDirectUploader {

    private KrakenDirectUploadApiImpl api;


    public OptimizeResponseImpl upload(OptimizeRequestImpl request, MultipartFile file) {
        return upload(request, file, OptimizeResponseImpl.class);
    }

    public SingleResizeResponseImpl upload(ResizeRequestImpl request, MultipartFile file) {
        return upload(request, file, SingleResizeResponseImpl.class);
    }

    public MultipleResizeResponseImpl upload(MultipleResizeRequestImpl request, MultipartFile file) {
        return upload(request, file, MultipleResizeResponseImpl.class);
    }

    /**
     * Checks file media type,
     * copies file bytes to tmp file
     * uploads tmp file to kraken
     * @param request
     * @param file
     * @param responseClass
     * @param <T>
     * @return
     */
    private <T extends AbstractKrakenResponse> T upload(KrakenRequest request, MultipartFile file, Class<T> responseClass) {
        if (api == null) throw new KrakenApiException("Api shouldn't be null");
        File tmpFile = null;
        try {
            MagicMatch magicMatch = getMagicMatch(file.getBytes());

            if (!magicMatch.getMimeType().equalsIgnoreCase(IMAGE_GIF_VALUE)
                    && !magicMatch.getMimeType().equalsIgnoreCase(IMAGE_JPEG_VALUE)
                    && !magicMatch.getMimeType().equalsIgnoreCase(IMAGE_PNG_VALUE)) {
                throw new KrakenApiException("Only image formats (*.gif, *.jpg, *.png) are acceptable, " +
                        "Uploaded file mime-type: " + magicMatch.getMimeType());
            }

            String tmpFileName = System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename();
            tmpFile = new File(tmpFileName);
            byte[] imageAsArray = file.getBytes();

            Files.write(tmpFile.toPath(), imageAsArray);
            return api.post(request, tmpFile, responseClass);
        } catch (Exception e) {
            log.error("Couldn't process file upload to kraken : err.msg = {}", e.getMessage());
            throw new KrakenApiException("Couldn't upload file due to :" + e.getMessage());
        } finally {
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }

}