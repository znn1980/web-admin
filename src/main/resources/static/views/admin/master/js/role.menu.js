layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'treeTable'], function () {
    const admin = layui.admin;
    const layer = layui.layer;
    const treeTable = layui.treeTable;
    const fn = {
        save: function (item) {
            const load = layer.load();
            admin.req({
                url: '../../admin/role/menu/save.json'
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: 'post'
                , data: JSON.stringify(item)
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
                    if (data.success) {
                        fn.roleId = item.id;
                        fn.menuId = item.menuId;
                        roleRender.reload();
                        menuRender.reload();
                    }
                }
                , error: function () {
                    layer.msg('请求异常，请重试！');
                    layer.close(load);
                }
            });
        }
        , roleId: 0
        , menuId: []
    };
    const roleRender = treeTable.render({
        id: 'role-table'
        , elem: '#role-table'
        , toolbar: '#role-toolbar'
        , url: '../../admin/role/list.json'
        , defaultToolbar: []
        , tree: {
            iconIndex: 2
            , idName: 'id'
            , childName: 'children'
            , getIcon: function () {
                return '<i class="layui-icon layui-icon-triangle-r"></i>';
            }
        }
        , parseData: function (data) {
            return {
                "code": data.code
                , "msg": data.message
                , "count": data.total
                , "data": data.data
            };
        }
        , response: {
            statusName: 'code'
            , statusCode: 0
            , msgName: 'message'
            , countName: 'total'
            , dataName: 'data'
        }
        , cols: [
            [
                {type: 'numbers', title: '编号', width: 60}
                , {type: 'radio', title: '选择', width: 60}
                , {field: 'roleName', title: '角色名称', width: 160}
                , {field: 'roleExplain', title: '角色说明'}
            ]
        ]
        , done: function () {
            roleRender.setChecked([fn.roleId]);
        }
    });
    const menuRender = treeTable.render({
        id: 'menu-table'
        , elem: '#menu-table'
        , toolbar: '#menu-toolbar'
        , url: '../../admin/menu/list.json'
        , defaultToolbar: []
        , tree: {
            iconIndex: 2
            , idName: 'id'
            , childName: 'children'
            , getIcon: function (d) {
                return '<i class="' + d.iconCls + '"></i>';
            }
        }
        , parseData: function (data) {
            return {
                "code": data.code
                , "msg": data.message
                , "count": data.total
                , "data": data.data
            };
        }
        , response: {
            statusName: 'code'
            , statusCode: 0
            , msgName: 'message'
            , countName: 'total'
            , dataName: 'data'
        }
        , cols: [
            [
                {type: 'numbers', title: '编号', width: 60}
                , {type: 'checkbox', title: '选择', width: 60}
                , {field: 'title', title: '菜单标题', width: 160}
                , {field: 'address', title: '菜单地址'}
            ]
        ]
        , done: function () {
            menuRender.expandAll();
            menuRender.setChecked(fn.menuId);
        }
    });
    treeTable.on('toolbar(role-table)', function (obj) {
        if (obj.event === 'refresh-toolbar') {
            roleRender.reload();
        }
    });
    treeTable.on('checkbox(role-table)', function (obj) {
        menuRender.removeAllChecked();
        fn.roleId = obj.data.id;
        fn.menuId = obj.data.menuId;
        menuRender.setChecked(obj.data.menuId);
    });
    treeTable.on('toolbar(menu-table)', function (obj) {
        if (obj.event === 'save-toolbar') {
            const roleMenu = {id: null, menuId: []};
            const role = roleRender.checkStatus();
            const menu = menuRender.checkStatus();
            if (role.length <= 0) {
                layer.msg("请选择一个角色！");
            } else {
                roleMenu.id = role[0].id;
                layui.each(menu, function (index, item) {
                    roleMenu.menuId.push(item.id);
                })
                fn.save(roleMenu);
            }
        } else if (obj.event === 'refresh-toolbar') {
            menuRender.reload();
        } else if (obj.event === 'expand-toolbar') {
            menuRender.expandAll();
        } else if (obj.event === 'fold-toolbar') {
            menuRender.foldAll();
        }
    });
});