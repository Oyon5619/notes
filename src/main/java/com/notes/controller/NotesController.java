package com.notes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.notes.domain.Notes;
import com.notes.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("/notes")
public class NotesController {

    @Autowired
    NotesService notesService;

    /**
     * 获取错题详情（需要切换图片源）
     *
     * @param notesId 笔记Id
     * @return 是否插入成功
     */
    @GetMapping("/getNotesById/{notesId}")
    public Notes getNotesById(@PathVariable String notesId) {
        return null;
    }

    /**
     * 条件获取用户错题（用于列表显示
     *
     * @param account        当前用户账号
     * @param condition      查询条件（key包括category,content[关键字匹配标题],notesGroup,priority),条件为空则表示全查询
     * @param order          排序（0表示不排序，1表示升序，2表示降序）
     * @param orderCondition 排序条件
     * @return 分页结果
     */
    @PostMapping("/getNotes")
    public IPage<Notes> getNotes(@RequestParam("account") int account, @RequestParam("condition") Map<String, String> condition, @RequestParam("order") int order, @RequestParam("orderCondition") String orderCondition) {
        return null;
    }

    /**
     * 插入数据
     *
     * @param account 发布者账号（当前用户）
     * @param notes   待插入的笔记
     * @return 是否插入成功
     */
    @PostMapping("/addNotes")
    public boolean addNotes(@RequestParam("account") String account, @RequestParam("notes") Notes notes) {
        return notesService.insert(account, notes);
    }

    /**
     * 更新Notes
     */
    @PutMapping("/updateNotes")
    public Notes updateNotes(@RequestBody Notes notes) {
        return notesService.update(notes);
    }


    /**
     * 删除错题
     *
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteNotes")
    public boolean deleteNotes(@RequestBody Integer notesId) {
        return notesService.delete(notesId);
    }

    /**
     * 批量删除错题(调用delete方法,用于删除缓存）
     *
     * @param selectedNotesId 要删除的notesId集合
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteSelectedNotes")
    public boolean deleteNotes(@RequestBody List<Integer> selectedNotesId) {
        return notesService.delete(selectedNotesId);
    }
}
