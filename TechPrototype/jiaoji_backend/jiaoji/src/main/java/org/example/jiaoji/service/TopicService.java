package org.example.jiaoji.service;


import org.example.jiaoji.pojo.RetType;
import org.example.jiaoji.pojo.Topic;
import java.util.List;

public interface TopicService {

    public RetType insertTopic(Topic data);

    public List<Topic> SelectAll();

    public Topic SelectById(Integer Id);

    public  List<Topic> SelectByClassId(Integer id);

    public List<Topic> search(String keyword);

    public RetType setFollow(Integer topicId, Integer userId);

    public Boolean findFollow(Integer topicId, Integer userId);

    public RetType deleteTopic(Integer topicId);

    public List<Topic> hotTopic();
}