package com.notes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.notes.domain.Photo;
import com.notes.mapper.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PhotoService {
    @Autowired
    PhotoMapper photoMapper;

    /**
     * 获得文件后缀名
     * */
    public String getSuffix(String fileName) {
        try {
            if (fileName.lastIndexOf(".") == -1) {//文件没有后缀名的情况
                return " ";
            }
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    /**
     * 获得文件前缀名
     * */
    public String getPrefix(String fileName) {
        try {
            if (fileName.lastIndexOf(".") == -1) {//文件没有后缀名的情况
                return fileName;
            }
            return fileName.substring(0, fileName.length() - getSuffix(fileName).length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return fileName;
        }
    }

    /**
     * 获取自增id
     * */
    public int getIncrement(){
        return photoMapper.selectList(new QueryWrapper<>()).size() + 1;
    }

    /**
     * 解决重名问题
     * */
    public String repeatedNaming(String fileName,int fileId) { //解决重复命名问题
        String prefix = getPrefix(fileName);
        String suffix = getSuffix(fileName);
        return prefix + "_" + fileId + "." + suffix;
    }

    /**
     * 添加图片
     * */
    public boolean insert(String photoName, String photoPath,String photoType) {
        try {
            Photo photo = new Photo();
            photo.setPhotoName(photoName);
            photo.setPhotoPath(photoPath);
            photo.setPhotoType(photoType);
            photoMapper.insert(photo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
