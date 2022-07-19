package com.flameking.community.mapper;

import com.flameking.community.entity.DiscussPost;
import com.flameking.community.entity.DiscussPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscussPostMapper {
  /**
   * 查询status!=2的所有帖子记录
   * @return 返回order by `type`后的结果
   */
  List<DiscussPost> selectDiscussPosts();

  int countByExample(DiscussPostExample example);

  int deleteByExample(DiscussPostExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(DiscussPost record);

  int insertSelective(DiscussPost record);

  List<DiscussPost> selectByExampleWithBLOBs(DiscussPostExample example);

  List<DiscussPost> selectByExample(DiscussPostExample example);

  DiscussPost selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") DiscussPost record, @Param("example") DiscussPostExample example);

  int updateByExampleWithBLOBs(@Param("record") DiscussPost record, @Param("example") DiscussPostExample example);

  int updateByExample(@Param("record") DiscussPost record, @Param("example") DiscussPostExample example);

  int updateByPrimaryKeySelective(DiscussPost record);

  int updateByPrimaryKeyWithBLOBs(DiscussPost record);

  int updateByPrimaryKey(DiscussPost record);
}