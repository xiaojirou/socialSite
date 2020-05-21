package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public void updateById(Label label) {
        labelDao.save(label);
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public List<Label> getTopList() {
        Map<String, String> map = new HashMap<>(1);
        map.put("recommend", "1");
        return labelDao.findAll(getSpecification(map));
    }

    public Page<Label> search(Map map, Pageable pageable) {
        return labelDao.findAll(getSpecification(map), pageable);
    }

    /**
     * 构造查询条件
     */
    private Specification getSpecification(Map map) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                if (map == null || map.isEmpty()) {
                    return null;
                }
                List<Predicate> predicateList = new ArrayList<>();

                if (!StringUtils.isEmpty(map.get("labelname"))) {
                    Predicate p1 = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + map.get("labelname") + "%");
                    predicateList.add(p1);
                }
                if (!StringUtils.isEmpty(map.get("state"))) {
                    Predicate p2 = criteriaBuilder.equal(root.get("state").as(String.class), map.get("state"));
                    predicateList.add(p2);
                }
                if (!StringUtils.isEmpty(map.get("recommend"))) {
                    Predicate p3 = criteriaBuilder.equal(root.get("recommend").as(String.class), map.get("recommend"));
                    predicateList.add(p3);
                }

                if (predicateList.size() == 0) {
                    return null;
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                predicates = predicateList.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        };
    }
}
