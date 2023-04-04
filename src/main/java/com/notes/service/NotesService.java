package com.notes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.notes.domain.Notes;
import com.notes.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 所有方法都需要用try-catch包裹
 */
@Service
public class NotesService {


    @Autowired
    NotesMapper notesMapper;

    @Autowired
    PhotoService photoService;

    /**
     * 插入数据
     * @param account 发布者账号（当前用户）
     * @param notes 待插入的笔记
     * @return 是否插入成功
     */
    public boolean insert(String account,Notes notes) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取笔记详情（需要切换图片源）
     *
     * @param notesId 笔记Id
     * @return 是否插入成功
     */
    @Cacheable(value = "getNotesById",key = "#notesId")
    public Notes getNotesById(int notesId) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件获取用户笔记（用于表单显示
     *
     * @param account        当前用户账号
     * @param condition      查询条件（key包括category,content[关键字匹配标题],notesGroup,priority),条件为空则表示全查询
     * @param order          排序（0表示不排序，1表示升序，2表示降序）
     * @param orderCondition 排序条件
     * @return 是否插入成功
     */
    @Cacheable("getNotes")
    public IPage<Notes> getNotes(int account, Map<String, String> condition, int order, String orderCondition) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新Notes
     */
    @CachePut(value = "getNotesById",key = "#notes.notesId")
    public Notes updateNotes(Notes notes) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除笔记
     *
     * @return 是否删除成功
     */
    @CacheEvict(value = "getNotesById",key = "#notesId")
    public boolean delete(int notesId) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除笔记(调用delete方法,用于删除缓存）
     *
     * @param notesIds 要删除的notesId集合
     * @return 是否删除成功
     */
    public boolean delete(List<Integer> notesIds) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 恢复笔记
     *
     * @return 是否恢复成功
     */
    public boolean restore(int notesId) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量恢复笔记
     *
     * @param notesIds 要恢复的notesId集合
     * @return 是否恢复成功
     */
    public boolean restore(List<Integer> notesIds) {
        try {
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
