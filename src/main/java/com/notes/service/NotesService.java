package com.notes.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.notes.domain.Notes;
import com.notes.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.HashMap;
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
     * @param notes 待插入的笔记
     * @return 是否插入成功
     */
    public boolean insert(Notes notes) {
        int count = 0;
        try {
            count = notesMapper.insert(notes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count != 0;
    }

    /**
     * 获取错题详情（需要切换图片源）
     *
     * @param notesId 笔记Id
     * @return 是否插入成功
     */
    public Notes getNotesById(int notesId) {
        try {
            return notesMapper.selectById(notesId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 条件获取用户错题（用于列表显示
     *
     * @param account        当前用户账号
     * @param condition      查询条件（key包括content[关键字匹配标题],notesGroup,priority),条件为空则表示全查询
     * @param order          排序（0表示不排序，1表示升序，-1表示降序）
     * @param orderCondition 排序条件(priority,notesGroup,updateTime 其中一个)
     * @return 分页结果
     */
    // @Cacheable("getNotes")
    public IPage<Notes> getNotes(long currentPage,long pageSize,
                                 String account, Map<String, String> condition,
                                 int order, String orderCondition) {
        try {
            if(condition.isEmpty()){
                QueryWrapper<Notes> wrapper = new QueryWrapper<>();
                wrapper.eq("promulgator",account);
                wrapper.eq("deleted", false);
                IPage<Notes> page = new Page<>(currentPage, pageSize);
                notesMapper.selectPage(page,wrapper);
                return page;
            }
            else {
                QueryWrapper<Notes> wrapper = new QueryWrapper<>();
                wrapper.eq("promulgator",account);
                wrapper.eq("deleted", false);
                if (!condition.get("priority").isEmpty())
                    wrapper.eq("priority", condition.get("priority"));
                if (!condition.get("notesGroup").isEmpty())
                    wrapper.eq("notes_group", condition.get("notesGroup"));
                if (!condition.get("content").isEmpty())
                    wrapper.like("notes_title", "%"+condition.get("content")+"%");
                IPage<Notes> page = new Page<>(currentPage, pageSize);
                notesMapper.selectPage(page,wrapper);
                return page;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新Notes
     */
    public Notes update(Notes notes) {
        try {
            //TODO
            notesMapper.updateById(notes);
            return notes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除错题
     *
     * @return 是否删除成功
     */
    public boolean delete(int notesId) {
        try {
            //TODO
            Notes notes = getNotesById(notesId);
            notes.setDeleted(true);
            notesMapper.updateById(notes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量删除错题(调用delete方法,用于删除缓存）
     *
     * @param notesIds 要删除的notesId集合
     * @return 是否删除成功
     */
    public boolean delete(List<Integer> notesIds) {
        try {
            //TODO
            for (int i = 0; i < notesIds.size(); i++) {
                Notes notes = notesMapper.selectById(notesIds.get(i));
                notes.setDeleted(true);
                notesMapper.updateById(notes);
            }
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
            Notes notes = notesMapper.selectById(notesId);
            notes.setDeleted(false);
            notesMapper.updateById(notes);
            return true;
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
            for (int i = 0; i < notesIds.size(); i++) {
                Notes notes = notesMapper.selectById(notesIds.get(i));
                notes.setDeleted(false);
                notesMapper.updateById(notes);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Map<Object, Object> getNotesGroupStatistic() {
        // SELECT notes_group, COUNT(notes_group) FROM t_notes GROUP BY notes_group;
        try {
            QueryWrapper<Notes> qw = new QueryWrapper<>();
            qw.select("DISTINCT notes_group");
            List<Object> notesGroupColumn = notesMapper.selectObjs(qw);
            qw.clear();
            qw.select("COUNT(notes_group)").groupBy("notes_group");
            List<Object> notesGroupColumnCount = notesMapper.selectObjs(qw);

            Map<Object, Object> resMap = new HashMap<>();

            for (int i = 0; i < notesGroupColumn.size(); ++i) {
                resMap.put(notesGroupColumn.get(i), notesGroupColumnCount.get(i));
            }
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Object, Object> getNotesPriorityStatistic() {
        // SELECT priority, COUNT(priority) FROM t_notes GROUP BY priority ORDER BY priority ASC;
        try {
            QueryWrapper<Notes> qw = new QueryWrapper<>();
            qw.select("DISTINCT priority").orderByAsc("priority");
            List<Object> notesPriorityColumn = notesMapper.selectObjs(qw);
            qw.clear();
            qw.select("COUNT(priority)").groupBy("priority").orderByAsc("priority");
            List<Object> notesPriorityColumnCount = notesMapper.selectObjs(qw);

            Map<Object, Object> resMap = new HashMap<>();
            for (int i = 0; i < notesPriorityColumn.size(); ++i) {
                resMap.put(notesPriorityColumn.get(i), notesPriorityColumnCount.get(i));
            }
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNotesPublishDateStatistic(Integer count) {
        // SELECT DATE_FORMAT(update_time,'%Y-%m-%d'), COUNT(DATE_FORMAT(update_time,'%Y-%m-%d')) FROM t_notes GROUP BY DATE_FORMAT(update_time,'%Y-%m-%d') ORDER BY DATE_FORMAT(update_time,'%Y-%m-%d') DESC LIMIT #{count};
        try {
            String updateDate = "DATE_FORMAT(update_time,'%Y-%m-%d')";
            String lastSql = count == null ? "LIMIT 5" : "LIMIT " + count; // 默认为最近五次
            QueryWrapper<Notes> qw = new QueryWrapper<>();
            qw.select("DISTINCT " + updateDate).orderByDesc(updateDate).last(lastSql);
            List<Object> notesPublishDateColumn = notesMapper.selectObjs(qw);
            qw.clear();
            qw.select("COUNT(" + updateDate + ")").groupBy(updateDate).orderByDesc(updateDate).last(lastSql);
            List<Object> notesPublishDateColumnCount = notesMapper.selectObjs(qw);

            String resJson = "{";
            int i = 0;
            for (; i < notesPublishDateColumn.size() - 1; ++i) {
                resJson += "\"" + notesPublishDateColumn.get(i) + "\":" + notesPublishDateColumnCount.get(i) + ",";
            }
            resJson += "\"" + notesPublishDateColumn.get(i) + "\":" + notesPublishDateColumnCount.get(i) + "}";
            // System.out.println(resJson);
            return resJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getTotalNumStatistic() {
        try {
            return notesMapper.selectCount(null);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return -1;
    }
}
