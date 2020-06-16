package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setComment(0);
        spit.setPublishtime(new Date());
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setState("1");
        spit.setVisits(0);
        // 如果是下级吐槽,则上级吐槽回复量加1
        if (StringUtils.isEmpty(spit.getParentid())) {
            Spit parentSpit = spitDao.findById(spit.getParentid()).get();
            parentSpit.setComment(parentSpit.getComment() + 1);
            spitDao.save(parentSpit);
        }
        spitDao.save(spit);
    }

    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public void update(Spit spit) {
        spitDao.save(spit);
    }

    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 分页查询
     * @param id
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentId(String id, Pageable pageable) {
        return spitDao.findByParentid(id, pageable);
    }

    /**
     * 为吐槽点赞
     * @param spitId
     */
    public void updateThumbUp(String spitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
