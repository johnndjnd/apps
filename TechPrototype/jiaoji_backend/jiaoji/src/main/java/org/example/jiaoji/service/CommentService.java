package org.example.jiaoji.service;

import java.util.List;

import org.example.jiaoji.pojo.Comment;

public interface CommentService {

    public Integer addComment(Comment data);

    public List<Comment> SelectByRemark(Integer remarkId);

    public List<Comment> SelectById (Integer id);

    public void deleteById(Integer id);
}
