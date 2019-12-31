/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/12/2 14:56:20                           */
/*==============================================================*/


drop table if exists sys_config;

drop table if exists sys_dict_data;

drop table if exists sys_log;

drop table if exists sys_organization;

drop table if exists sys_permission;

drop table if exists sys_post;

drop table if exists sys_resouce;

drop table if exists sys_role;

drop table if exists sys_role_permission;

drop table if exists sys_tenant;

drop table if exists sys_user_action;

drop table if exists sys_user_info;

drop table if exists sys_user_organization;

drop table if exists sys_user_post;

drop table if exists sys_user_role;

drop table if exists um_permission;

drop table if exists um_role;

drop table if exists um_role_permission;

drop table if exists um_user;

drop table if exists um_user_role;

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
   id                   int not null auto_increment comment '主键',
   config_code          varchar(32) comment '编码',
   config_name          varchar(100) comment '名称',
   config_key           varchar(100) comment '参数键',
   config_value         varchar(1000) comment '参数值',
   is_sys               tinyint comment '系统内置
            0是 1否',
   sort                 int comment '排序',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_config comment '系统配置表';

/*==============================================================*/
/* Table: sys_dict_data                                         */
/*==============================================================*/
create table sys_dict_data
(
   id                   int not null auto_increment comment '主键',
   pid                  int comment '父级id',
   sort                 int comment '排序',
   level                int comment '层次级别',
   dict_code            varchar(32) comment '编码',
   dict_name            varchar(100) comment '名称',
   dict_type            tinyint comment '类型',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_dict_data comment '字典数据表';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   int not null auto_increment comment '主键',
   log_url              varchar(50) comment '链接',
   log_user             varchar(25) comment '用户',
   log_content          varchar(250) comment '内容描述',
   log_json             varchar(500) comment '内容',
   opration_time        datetime comment '操作时间',
   ip_address           varchar(50) comment 'IP地址',
   result               int comment '结果',
   primary key (id)
);

alter table sys_log comment '操作日志';

/*==============================================================*/
/* Table: sys_organization                                      */
/*==============================================================*/
create table sys_organization
(
   id                   int not null auto_increment comment '主键',
   pid                  int comment '父级id',
   uid                  int comment '上级id',
   sort                 int comment '排序',
   org_code             varchar(32) comment '机构编码',
   org_name             varchar(50) comment '机构名称',
   org_type             tinyint comment '机构类型',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   level                int comment '层次级别',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_organization comment '组织机构表';

/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
   id                   int not null auto_increment comment '主键',
   act_id               int comment '操作用户表id',
   res_id               int comment '资源表id',
   tenant_id            int comment '租户id',
   primary key (id)
);

alter table sys_permission comment '权限表';

/*==============================================================*/
/* Table: sys_post                                              */
/*==============================================================*/
create table sys_post
(
   id                   int not null auto_increment comment '主键',
   organization_id      int comment '组织机构表id',
   post_code            varchar(32) comment '职务编码',
   post_name            varchar(100) comment '职务名称',
   post_type            tinyint comment '职务分类
            0高管
            1中层
            2基层
            ',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   sort                 int comment '排序',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_post comment '职务表';

/*==============================================================*/
/* Table: sys_resouce                                           */
/*==============================================================*/
create table sys_resouce
(
   id                   int not null auto_increment comment '主键',
   pid                  int comment '父级id',
   uid                  int comment '上级id',
   sort                 int comment '排序',
   level                int comment '层次级别',
   res_code             varchar(32) comment '资源编码',
   res_name             varchar(50) comment '资源名称',
   res_type             tinyint comment '资源类型',
   res_href             varchar(150) comment '资源链接',
   permission           varchar(150) comment '权重标识',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_resouce comment '资源表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int not null auto_increment comment '主键',
   role_code            varchar(32) comment '角色编号',
   role_name            varchar(25) comment '角色名称',
   tenant_id            int comment '租户id',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   sort                 int comment '排序',
   remark               varchar(500) comment '备注',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_role comment '角色表';

/*==============================================================*/
/* Table: sys_role_permission                                   */
/*==============================================================*/
create table sys_role_permission
(
   id                   int not null auto_increment comment '主键',
   permission_id        int comment '权限id',
   role_id              int comment '角色id',
   tenant_id            int comment '租户id',
   primary key (id)
);

alter table sys_role_permission comment '角色与权限关系表';

/*==============================================================*/
/* Table: sys_tenant                                            */
/*==============================================================*/
create table sys_tenant
(
   id                   int not null auto_increment comment '主键',
   tenant_code          varchar(32) comment '租户代码',
   tenant_name          varchar(250) comment '租户名称',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   remark               varchar(500) comment '备注',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_tenant comment '租户表';

/*==============================================================*/
/* Table: sys_user_action                                       */
/*==============================================================*/
create table sys_user_action
(
   id                   int not null auto_increment comment '主键',
   act_code             varchar(32) comment '编码',
   act_name             varchar(100) comment '名称',
   permission           varchar(150) comment '权重标识',
   act_href             varchar(150),
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   sort                 int comment '排序',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_user_action comment '用户操作表';

/*==============================================================*/
/* Table: sys_user_info                                         */
/*==============================================================*/
create table sys_user_info
(
   id                   int not null auto_increment comment '主键',
   user_code            varchar(32) comment '用户编码',
   login_code           varchar(25) comment '登录账号',
   user_name            varchar(50) comment '用户昵称',
   pwd                  varchar(32) comment '登录密码',
   email                varchar(50) comment '邮箱',
   mobile               varchar(20) comment '手机号',
   phone                varchar(20) comment '办公电话',
   sex                  tinyint comment '性别
            0男 1女',
   avatar               varchar(100) comment '头像路径',
   sign                 varchar(250) comment '个性签名',
   wx_openid            varchar(50) comment '微信号',
   qq_openid            varchar(25) comment 'qq号',
   address              varchar(500) comment '地址',
   remark               varchar(500) comment '备注',
   tenant_id            int comment '租户id',
   status               char(10) comment '审批状态
            0-审批通过
            1-审批中
            2-待审批',
   mgr_type             tinyint comment '管理员类型
            0非管理员
            1系统管理员
            ',
   pwd_question         varchar(50) comment '保密问题',
   pwd_question_answer  varchar(25) comment '保密问题答案',
   pwd_question2        varchar(50) comment '保密问题2',
   pwd_question_answer2 varchar(25) comment '保密问题答案2',
   pwd_question3        varchar(50) comment '保密问题3',
   pwd_question_answer3 varchar(25) comment '保密问题答案3',
   enable               tinyint comment '是否有效
            0有效  
            1无效
            ',
   create_time          datetime comment '创建时间',
   create_user_id       int comment '创建人id',
   create_user_name     varchar(25) comment '创建人姓名',
   update_time          datetime comment '修改时间',
   update_user_id       int comment '修改人id',
   update_user_name     varchar(25) comment '修改人姓名',
   primary key (id)
);

alter table sys_user_info comment '用户资料表';

/*==============================================================*/
/* Table: sys_user_organization                                 */
/*==============================================================*/
create table sys_user_organization
(
   id                   int not null auto_increment comment '主键',
   user_id              int comment '用户资源id',
   organization_id      int comment '组织机构表id',
   tenant_id            int comment '租户id',
   primary key (id)
);

alter table sys_user_organization comment '用户组织机构表';

/*==============================================================*/
/* Table: sys_user_post                                         */
/*==============================================================*/
create table sys_user_post
(
   id                   int not null auto_increment comment '主键',
   user_id              int comment '用户资源表id',
   post_id              int comment '职务表id',
   tenant_id            int comment '租户id',
   primary key (id)
);

alter table sys_user_post comment '用户职务表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   int not null auto_increment comment '主键',
   role_id              int comment '角色id',
   user_id              int comment '用户id',
   tenant_id            int comment '租户id',
   primary key (id)
);

alter table sys_user_role comment '用户角色表';

/*==============================================================*/
/* Table: um_permission                                         */
/*==============================================================*/
create table um_permission
(
   um_id                int not null comment '主键',
   um_resource_id       varchar(50) comment '资源id',
   um_action            varchar(100) comment '功能',
   um_tenant_id2        int comment '租户id',
   um_module_id         int comment 'um_module_id',
   primary key (um_id)
);

alter table um_permission comment '权限表（wso2）';

/*==============================================================*/
/* Table: um_role                                               */
/*==============================================================*/
create table um_role
(
   um_id                int not null comment '主键',
   um_role_name         varchar(32) comment '角色名称',
   um_tenant_id         int comment '租户id',
   um_shared_role       tinyint comment '共享角色',
   primary key (um_id)
);

alter table um_role comment '角色表（wso2）';

/*==============================================================*/
/* Table: um_role_permission                                    */
/*==============================================================*/
create table um_role_permission
(
   um_id                int not null comment '主键',
   um_permission_id     int comment '权限id',
   um_role_name         varchar(32) comment '角色名称',
   um_is_allowed        smallint comment '是否容许',
   um_tenant_id         int comment '租户id',
   um_domain_id         int comment '产业id',
   primary key (um_id)
);

alter table um_role_permission comment '角色与权限关系表（wso2）';

/*==============================================================*/
/* Table: um_user                                               */
/*==============================================================*/
create table um_user
(
   um_id                int not null comment '主键',
   um_user_name         varchar(25) comment '用户名',
   um_user_password     varchar(32) comment '密码',
   um_salt_value        varchar(32) comment '盐',
   um_require_change    varchar(20) comment '是否更改',
   um_changed_time      datetime comment '更改时间',
   um_tenant_id         int comment '租户id',
   primary key (um_id)
);

alter table um_user comment '用户表（wso2）';

/*==============================================================*/
/* Table: um_user_role                                          */
/*==============================================================*/
create table um_user_role
(
   um_id                int not null comment '主键',
   um_role_id           int comment '角色id',
   um_user_id           int comment '用户id',
   um_tenant_id         int comment '租户id',
   primary key (um_id)
);

alter table um_user_role comment '用户与角色表（WSO2）';

