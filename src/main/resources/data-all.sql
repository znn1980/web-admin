INSERT INTO `master_user`
VALUES (1, '超级管理员', 'admin', '$123456$', '18888888888', 'admin@ifacebox.com', 1, 1, NULL, NULL, 'admin', NULL, now(),
        NULL);
INSERT INTO `master_user`
VALUES (2, '来宾', 'guest', '$123456$', '13333333333', 'guest@ifacebox.com', 1, 1, NULL, NULL, 'admin', NULL, now(),
        NULL);

INSERT INTO `chat_user`
VALUES (1, 'admin（超级管理员）', 'online', '', '../../views/admin/img/logo_32x32.png');
INSERT INTO `chat_user`
VALUES (2, 'guest（来宾）', 'online', '', '../../views/admin/img/logo_32x32.png');

INSERT INTO `master_menu`
VALUES (7000, 0, '用户管理', '', 1, 'layui-icon layui-icon-user', 1, 7000, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (7100, 7000, '用户管理', '/views/admin/user.html', 1, '', 1, 7100, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (7200, 7000, '分组管理', '/views/admin/group.html', 1, '', 1, 7200, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (7300, 7000, '角色管理', '/views/admin/role.html', 1, '', 1, 7300, 'admin', NULL, now(), NULL);

INSERT INTO `master_menu`
VALUES (8000, 0, '权限管理', '', 1, 'layui-icon layui-icon-auz', 1, 8000, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (8100, 8000, '菜单权限', '/views/admin/role_menu.html', 1, '', 1, 8100, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (8200, 8000, '操作权限', '/views/admin/role_operate.html', 1, '', 1, 8200, 'admin', NULL, now(), NULL);

INSERT INTO `master_menu`
VALUES (9000, 0, '系统管理', '', 1, 'layui-icon layui-icon-set', 1, 9000, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (9100, 9000, '菜单管理', '/views/admin/menu.html', 1, '', 1, 9100, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (9200, 9000, '操作管理', '/views/admin/operate.html', 1, '', 1, 9200, 'admin', NULL, now(), NULL);
INSERT INTO `master_menu`
VALUES (9300, 9000, '日志管理', '/views/admin/logger.html', 1, '', 1, 9300, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (7100, 0, '用户管理', '', '', 1, '', 1, 7100, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7101, 7100, '用户查询', '/admin/user/list.json', 'GET', 1, '', 1, 7101, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7102, 7100, '用户保存', '/admin/user/save.json', 'POST', 1, '', 1, 7102, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7103, 7100, '用户编辑', '/admin/user/edit.json', 'PUT', 1, '', 1, 7103, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7104, 7100, '用户删除', '/admin/user/delete.json', 'DELETE', 1, '', 1, 7104, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7105, 7100, '重置密码', '/admin/user/reset.json', 'GET', 1, '', 1, 7105, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7106, 7100, '修改密码', '/admin/user/password.json', 'POST', 1, '', 1, 7106, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (7200, 0, '分组管理', '', '', 1, '', 1, 7200, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7201, 7200, '分组查询', '/admin/group/list.json', 'GET', 1, '', 1, 7201, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7202, 7200, '分组保存', '/admin/group/save.json', 'POST', 1, '', 1, 7202, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7203, 7200, '分组编辑', '/admin/group/edit.json', 'PUT', 1, '', 1, 7203, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7204, 7200, '分组删除', '/admin/group/delete.json', 'DELETE', 1, '', 1, 7204, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (7300, 0, '角色管理', '', '', 1, '', 1, 7300, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7301, 7300, '角色查询', '/admin/role/list.json', 'GET', 1, '', 1, 7301, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7302, 7300, '角色保存', '/admin/role/save.json', 'POST', 1, '', 1, 7302, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7303, 7300, '角色编辑', '/admin/role/edit.json', 'PUT', 1, '', 1, 7303, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7304, 7300, '角色删除', '/admin/role/delete.json', 'DELETE', 1, '', 1, 7304, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7305, 7300, '角色菜单保存', '/admin/role/menu/save.json', 'POST', 1, '', 1, 7305, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (7306, 7300, '角色操作保存', '/admin/role/operate/save.json', 'POST', 1, '', 1, 7306, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (9100, 0, '菜单管理', '', '', 1, '', 1, 9100, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9101, 9100, '菜单查询', '/admin/menu/list.json', 'GET', 1, '', 1, 9101, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9102, 9100, '菜单保存', '/admin/menu/save.json', 'POST', 1, '', 1, 9102, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9103, 9100, '菜单编辑', '/admin/menu/edit.json', 'PUT', 1, '', 1, 9103, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9104, 9100, '菜单删除', '/admin/menu/delete.json', 'DELETE', 1, '', 1, 9104, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (9200, 0, '操作管理', '', '', 1, '', 1, 9200, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9201, 9200, '操作查询', '/admin/operate/list.json', 'GET', 1, '', 1, 9201, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9202, 9200, '操作保存', '/admin/operate/save.json', 'POST', 1, '', 1, 9202, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9203, 9200, '操作编辑', '/admin/operate/edit.json', 'PUT', 1, '', 1, 9203, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9204, 9200, '操作删除', '/admin/operate/delete.json', 'DELETE', 1, '', 1, 9204, 'admin', NULL, now(), NULL);

INSERT INTO `master_operate`
VALUES (9300, 0, '日志管理', '', '', 1, '', 1, 9300, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9301, 9300, '操作查询', '/admin/logger/list.json', 'GET', 1, '', 1, 9301, 'admin', NULL, now(), NULL);
INSERT INTO `master_operate`
VALUES (9302, 9300, '操作删除', '/admin/logger/delete.json', 'DELETE', 1, '', 1, 9302, 'admin', NULL, now(), NULL);
