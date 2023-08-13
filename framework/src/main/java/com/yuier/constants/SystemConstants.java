package com.yuier.constants;

import java.util.List;

/**
 * @Author: Yui
 * @Description: TODO
 * @DateTime: 2023/8/2 15:48
 **/
public class SystemConstants {
    // 文章状态为草稿
    public static final int ARTICLE_STATUS_DRAFT = 1;

    // 文章状态为正常发布
    public static final int ARTICLE_STATUS_NORMAL = 0;

    // 标签状态为正常状态
    public static final String CATEGORY_STATUS_NORMAL = "0";

    // 友链审核通过
    public static final String LINK_APPROVED = "0";

    // 评论为根评论
    public static final Long ROOT_COMMENT = -1L;

    // 评论为文章评论，非友链评论
    public static final String ARTICLE_COMMENT = "0";

    // 评论为友链评论而非文章评论
    public static final String LINK_COMMENT = "1";

    // 合法图片类型
    public static final List<String> VALID_IMG_TYPE = List.of(
            "png", "jpg", "jpeg"
    );

    // 最大头像大小
    public static final Long MAX_AVATAR_BYTE = 1048576L;

    // 由用户自己创建
    public static final Long USER_SELF = 0L;

    // Redis 键值
    public static final class REDIS_KEYS {

        // 文章浏览量
        public static final String ARTICLE_VIEW_COUNT ="article:viewCount";

        // 前台用户标识
        public static final String BLOG_USER = "blogLogin:";

        // 后台管理员标识
        public static final String ADMIN_USER = "adminLogin:";
    }

    // 文章浏览量自增长 1
    public static final long ARTICLE_VIEW_COUNT_INCREMENT = 1;

    // 系统超级管理员 ID
    public static final Long SUPER_ADMIN_ID = 1L;
    // 系统超级管理员角色 ID
    public static final Long SUPER_ADMIN_ROLE_ID = 1L;

    // 菜单状态正常
    public static final String MENU_STATUS_NORMAL = "0";

    // 菜单类型
    public static final class MENU_TYPE {
        // 主路径菜单
        public static final String MENU = "M";
        // 子路径目录
        public static final String CATEGORY = "C";
        // 按钮
        public static final String FUNCTION = "F";
    }

    // 超级管理员 roleKey
    public static final String ADMIN_ROLE_KEY = "admin";

    // 根菜单 ID
    public static final Long ROOT_MENU_ID = 0L;

}
