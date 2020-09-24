###项目说明

* 本项目是一个基于url的权限管理系统。
* 本项目使用WebSocket实现了一个在线用户的即时通讯功能，使用的即时通讯前端框架为LayIM最新版。
* 本项目以Maven构建，使用SpringBootMvc 、Thymeleaf、MyBatis。
* 本项目使用的前端框架为layuiAdmin2020最新版。
* 本项目配置在集群环境下可使用SpringRedisSession，可在【application.yml】配置文件中开启，【pom.xml】添加相关依赖。
* 本项使用https访问,可在【application.yml】配置文件中开启，本项目自带一个证书文件【keystore.jks】。
* 【pom.xml】为Maven的配置文件。
* 【src/main/resources/schema-all.sql】为数据库建表脚本。
* 【src/main/resources/data-all.sql】为数据库初始数据脚本。
* 【src/main/resources/keystore.jks】为https的证书文件。
* 【src/main/resources/application.yml】为SpringBoot的配置文件，请修改相应的数据库配置。

### 更新日志

* 2020-07-29
    * 界面美化是永久不变的主题。
    * 增加【chat_user】表，保存用户头像、签名等信息。
    * 使用LayIM、WebSocket实现在线用户的即时通讯功能。
    * 增加登陆用户修改头像的功能。

* 2020-07-22
    * 界面美化是永久不变的主题。
    * 【master_operate】表增加【method】字段，标识操作请求的方法【GET\POST\PUT\DELETE】。
    * 【master_group】表增加【status_id】字段，表示分组是否使用。
    * 【master_role】表增加【status_id】字段，表示角色是否使用。
    * 操作的权限控制判断请求地址与请求方法。
    * 权限控制判断分组状态、角色状态、菜单状态、菜单属性、操作状态、操作属性。
    