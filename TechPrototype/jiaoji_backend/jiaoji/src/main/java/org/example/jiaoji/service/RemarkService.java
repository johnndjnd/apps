package org.example.jiaoji.service;

import java.util.List;

import org.example.jiaoji.pojo.Remark;

import org.example.jiaoji.pojo.User;

public interface RemarkService {

    public Integer addRemark(Remark data);

    public List<Remark> SelectByObject(Integer objectId);

    public List<Remark> SelectById(Integer id);

    public void changeLike(Integer id, Integer change, Integer uid);

    public void deleteRemark(Integer id);

    public List<User> getAllUser();

    public Boolean isLike(Integer remarkId, Integer uid);
}
