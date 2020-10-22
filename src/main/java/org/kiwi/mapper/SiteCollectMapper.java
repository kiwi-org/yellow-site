package org.kiwi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.kiwi.model.entity.SiteCollectDO;

import java.util.List;

/**
 * @author YanAnHuaZai
 * created at：2020-09-02 01:06
 */
public interface SiteCollectMapper extends BaseMapper<SiteCollectDO> {

    /**
     * 查询所有的标题
     * @author YanAnHuaZai
     * create 2020年09月02日04:22:25
     * @return 标题集合
     */
    @Select("select title from t_site_collect")
    List<String> selectTitleList();

}
