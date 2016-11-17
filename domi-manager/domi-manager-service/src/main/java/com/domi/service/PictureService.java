package com.domi.service;

import com.domi.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * Created by jing on 2016/11/16.
 */
public interface PictureService {

    PictureResult uploadPic(MultipartFile picFile);
}
