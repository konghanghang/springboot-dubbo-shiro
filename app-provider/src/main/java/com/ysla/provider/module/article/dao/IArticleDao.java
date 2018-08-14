package com.ysla.provider.module.article.dao;

import com.alibaba.fastjson.JSONObject;
import com.ysla.api.auto.mapper.ArticleMapper;
import com.ysla.api.auto.model.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 文章mapper扩展类
 * @author konghang
 */
@Mapper
public interface IArticleDao extends ArticleMapper {

    /**
     * 获取文章详细
     * @param articleId
     * @return
     */
    @Results(id = "articleInfo",value = {
            @Result(column="article_id", property="articleId", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="ref_article_id", property="refArticleId", jdbcType=JdbcType.VARCHAR),
            @Result(column="ref_user_id", property="refUserId", jdbcType=JdbcType.VARCHAR),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
            @Result(column="cover_image", property="coverImage", jdbcType=JdbcType.VARCHAR),
            @Result(column="count_view", property="countView", jdbcType=JdbcType.INTEGER),
            @Result(column="count_comment", property="countComment", jdbcType=JdbcType.INTEGER),
            @Result(column="count_collection", property="countCollection", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
            @Result(column="create_ip", property="createIp", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_date", property="createDate", jdbcType=JdbcType.BIGINT),
            @Result(column="update_date", property="updateDate", jdbcType=JdbcType.BIGINT),
            @Result(column="md_content", property="mdContent", jdbcType=JdbcType.LONGVARCHAR)
    })
    @Select({"select a.article_id,a.ref_article_id,u.nickname author,a.title,a.description,a.count_view,a.count_comment," +
            " a.count_collection,a.type,a.create_ip,a.status,a.create_date,a.content,a.cover_image,a.md_content " +
            " from t_article a left join t_user u on a.ref_user_id = u.ref_user_id " +
            " where a.ref_article_id = #{articleId}"})
    JSONObject getArticleByRef(String articleId);

    /**
     * 获取所有文章
     * @param username
     * @return
     */
    @ResultMap(value = "articleInfo")
    @Select({"select a.article_id,a.ref_article_id,u.nickname author,a.title,a.count_view,a.count_comment," +
            " a.count_collection,a.type,a.create_date,a.cover_image, " +
            " a.description from t_article a left join t_user u on a.ref_user_id = u.ref_user_id" +
            " where status = 0 and (#{username} is null or u.username = #{username}) order by a.create_date desc"})
    List<JSONObject> articles(String username);

    /**
     * 根据refArticleId更新文章
     * @param article
     * @return
     */
    @UpdateProvider(type = ArticleProvider.class, method = "updateByArticleRefSelective")
    int updateByRefSelective(Article article);

    /**
     * 精选文章
     * @return
     */
    @Select({"select cover_image coverImage,title,ref_article_id articleId " +
            " from t_article where status = 0 order by count_view desc,count_comment desc limit 0,5"})
    List<JSONObject> chosen();

    /**
     * 更新文章统计数据
     * @param article
     * @return
     */
    @UpdateProvider(type = ArticleProvider.class, method = "updateStatistics")
    int updateStatistics(Article article);

}
