package org.example.jiaoji.service.serverimpl;

import java.util.List;

import org.example.jiaoji.mapper.ObjectMapper;
import org.example.jiaoji.mapper.TopicMapper;
import org.example.jiaoji.pojo.Objects;
import org.example.jiaoji.pojo.RetType;
import org.example.jiaoji.pojo.Topic;
import org.example.jiaoji.service.ObjectService;
import org.example.jiaoji.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
public class TopicServiceImpl implements TopicService {
    public static final int viewsRate = 1;
    public static final int remarkRate = 8;
    public static final int favorRate = 15;
    public static final int objectRate = 5;


    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ObjectService objectService;

    public RetType insertTopic(Topic data) {
        RetType ret = new RetType();

        Integer id = topicMapper.selectIdByTitle(data.getTitle());
        if (id != null) {
            ret.setMsg("该话题已存在");
            ret.setOk(false);
            ret.setData(null);
            return ret;
        }
        System.out.println(data);
        System.out.println("=======this is test=====");
        Topic topic = new Topic();
        topic.setTitle(data.getTitle());
        topic.setClassId(data.getClassId());
        topic.setIntroduction(data.getIntroduction());
        topic.setUserId(data.getUserId());
        topic.setHot(0);
        topic.setPicture("this is topic picture");
        topic.setPublicTime(java.time.LocalDateTime.now());
        topic.setBase64(data.getBase64());
        topicMapper.insert(topic);
        ret.setMsg("上传成功");
        ret.setOk(true);
        ret.setData(topicMapper.selectByTitle(topic.getTitle()));
        return ret;
    }


    public List<Topic> SelectAll() {
        List<Topic> topics = topicMapper.selectAll();
        for (Topic topic : topics) {
            int remarkNum = topic.getRemarkNum() * remarkRate;
            int favor = topic.getFavor() * favorRate;
            int views = topic.getViews() * viewsRate;
            int objectNum = topic.getObjectNum() * objectRate;
            LocalDateTime publicTime = topic.getPublicTime();
            LocalDateTime now = LocalDateTime.now();
            double hours = ChronoUnit.HOURS.between(publicTime, now) / 24;
            double hot = (remarkNum + favor + views + objectNum) / (Math.pow(hours + 2, 1.2));
            topic.setHot((int) hot);
        }
        return topics;
    }

    public List<Topic> SelectByClassId(Integer id) {
        List<Topic> topics = topicMapper.selectByClassId(id);
        for (Topic topic : topics) {
            int remarkNum = topic.getRemarkNum() * remarkRate;
            int favor = topic.getFavor() * favorRate;
            int views = topic.getViews() * viewsRate;
            int objectNum = topic.getObjectNum() * objectRate;
            LocalDateTime publicTime = topic.getPublicTime();
            LocalDateTime now = LocalDateTime.now();
            double hours = ChronoUnit.HOURS.between(publicTime, now) / 24;
            double hot = (remarkNum + favor + views + objectNum) / (Math.pow(hours + 2, 1.2));
            topic.setHot((int) hot);
        }
        return topics;
    }


    @Override
    public Topic SelectById(Integer Id) {
        return topicMapper.selectById(Id);
    }

    @Override
    public List<Topic> search(String keyword) {
        keyword = "%" + keyword + "%";
        return topicMapper.search(keyword);
    }


    @Override
    @Transactional
    public RetType setFollow(Integer topicId, Integer userId) {
        RetType ret = new RetType();
        Boolean follow = topicMapper.findFollow(topicId, userId);
        if (follow) {
            topicMapper.deleteFollow(topicId, userId);
            ret.setMsg("取消关注成功");
            ret.setOk(true);
            ret.setData(null);
        } else {
            topicMapper.insertFollow(topicId, userId);
            ret.setMsg("关注成功");
            ret.setOk(true);
            ret.setData(null);
        }
        return ret;
    }

    @Override
    public Boolean findFollow(Integer topicId, Integer userId) {
        return topicMapper.findFollow(topicId, userId);
    }

    @Override
    public RetType deleteTopic(Integer topicId) {
        RetType ret = new RetType();
        List<Objects> toDelete = objectMapper.selectAllInTopic(topicId);
        for (Objects objects : toDelete) {
            objectService.deleteObject(objects.getId());
        }

        topicMapper.deleteTopic(topicId);

        if (topicMapper.selectById(topicId) == null) {
            ret.setMsg("删除成功");
            ret.setOk(true);
            ret.setData(null);
        } else {
            ret.setMsg("删除失败");
            ret.setOk(false);
            ret.setData(null);
        }
        return ret;
    }

    public List<Topic> hotTopic() {
        List<Topic> topics = topicMapper.selectAll();
        for (Topic topic : topics) {
            int remarkNum = topic.getRemarkNum() * remarkRate;
            int favor = topic.getFavor() * favorRate;
            int views = topic.getViews() * viewsRate;
            int objectNum = topic.getObjectNum() * objectRate;
            LocalDateTime publicTime = topic.getPublicTime();
            LocalDateTime now = LocalDateTime.now();
            double hours = ChronoUnit.HOURS.between(publicTime, now) / 24;
            double hot = (remarkNum + favor + views + objectNum) / (Math.pow(hours + 2, 1.2));
            topic.setHot((int) hot);
        }

        List<Topic> topThreeHotTopics = topics.stream()
                .sorted((t1, t2) -> Integer.compare(t2.getHot(), t1.getHot())) // 按 hot 值降序排序
                .limit(3) // 取前 3 个元素
                .collect(Collectors.toList());

        return topThreeHotTopics;
    }
}
