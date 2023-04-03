package com.notes.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错题
 */
@Data
@TableName("t_notes")
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
    @TableId(type = IdType.ASSIGN_ID)
    public int notesId; // 错题编号
    public String notesTitle; //错题标题
    public String promulgator; //发布者账号
    public String category; //错题科目
    public String priority; // 错题优先级
    public String notesContent; //错题内容
    public String notesImages; //错题中的图片
    public String notesGroup; //错题分组
    public boolean deleted; //是否删除
    public String updateTime;//更新时间
}
