package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    /**
     * 查询推荐状态（state为2），按时间降序排序前4条记录
      */
	List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 查询最新列表：state不为0，按时间降序排序前12条记录
     */
    List<Recruit> findTop12ByStateIsNotOrderByCreatetimeDesc(String state);
}
