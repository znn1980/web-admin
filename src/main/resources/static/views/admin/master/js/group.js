layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'form', 'table'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const form = layui.form;
    const layer = layui.layer;
    const table = layui.table;
    const fn = {
        dump: function (item) {
            $('#id').val(0);
            if (item) {
                form.val('group-form', item);//表单赋值
            } else {
                $('#group-form')[0].reset();//清空表单
            }
            fn.role(item);
        }
        //打开编辑窗口
        , edit: function (item) {
            fn.dump(item);
            admin.popup({
                type: 1
                , title: (item) ? '分组编辑' : '分组添加'
                , area: '100'
                , content: $('#group-form')
                , btn: ['确定', '取消']
                , yes: function (index) {
                    //绑定编辑数据提交
                    form.on('submit(group-submit)', function (data) {
                        fn.save(data.field, function () {
                            layer.close(index);
                        });
                    });
                    $('#group-submit').trigger('click');
                }
            });
        }
        //删除
        , del: function (item) {
            layer.confirm('是否删除【' + item.groupName + '】分组？', {
                title: '删除'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                layer.close(index);
                const load = layer.load();
                admin.req({
                    url: '../../admin/group/delete.json'
                    , dataType: 'json'
                    , type: 'delete'
                    , data: {id: item.id}
                    , success: function (data) {
                        layer.msg(data.message);
                        layer.close(load);
                        tableRender.reload();
                    }
                    , error: function () {
                        layer.msg('请求异常，请重试！');
                        layer.close(load);
                    }
                });
            });
        }
        //保存（POST：添加；PUT：修改）
        , save: function (data, fn) {
            data.roleId = groupRole.getValue('value');
            const load = layer.load();
            admin.req({
                url: '../../admin/group/' + ((data.id === '0') ? 'save.json' : 'edit.json')
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: (data.id === '0') ? 'post' : 'put'
                , data: JSON.stringify(data)
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
                    tableRender.reload();
                    if (data.success) {
                        typeof fn === 'function' && fn();
                    }
                }
                , error: function () {
                    layer.msg('请求异常，请重试！');
                    layer.close(load);
                }
            });
        }
        //加载分组角色
        , role: function (item) {
            admin.req({
                url: '../../admin/role/list.json'
                , dataType: 'json'
                , type: 'get'
                , success: function (data) {
                    if (data.success) {
                        groupRole.update({data: data.data});
                        if (item) {
                            groupRole.setValue(item.roleId);
                        }
                    }
                }
            });
        }
        //加载状态
        , status: function () {
            admin.req({
                url: '../../admin/group/status.json'
                , dataType: 'json'
                , type: 'get'
                , success: function (data) {
                    if (data.success) {
                        layui.each(data.data, function (index, item) {
                            $('#statusId').append(fn.option(item.statusExplain, item.statusId));
                        });
                        form.render("select");
                    }
                }
            });
        }
        , option: function (text, value) {
            const option = new Option();
            option.text = text;
            option.value = value;
            option.disabled = value === 0;
            return option;
        }
        , load: function () {
            fn.status();
        }
    };
    const groupRole = xmSelect.render({
        el: '#groupRole'
        , prop: {
            name: 'roleName',
            value: 'id',
        }
        , toolbar: {
            show: true,
        }
        , autoRow: true
        , direction: 'down'
    });
    const tableRender = table.render({
        id: 'group-table'
        , elem: '#group-table'
        , toolbar: '#group-toolbar'
        , url: '../../admin/group/list.json'
        , response: {
            statusName: 'code'
            , statusCode: 0
            , msgName: 'message'
            , countName: 'total'
            , dataName: 'data'
        }
        , cols: [
            [
                {type: 'numbers', title: '编号'}
                , {type: 'radio', title: '选择'}
                , {field: 'groupName', title: '名称', width: 200}
                , {field: 'groupExplain', title: '说明'}
                , {field: 'statusId', title: '状态', align: 'center', width: 80, templet: '#status-tpl'}
                , {field: 'createUser', title: '创建用户', width: 90, hide: true}
                , {field: 'updateUser', title: '修改用户', width: 100, hide: true}
                , {field: 'createTime', title: '创建时间', width: 120, hide: true}
                , {field: 'updateTime', title: '修改时间', width: 120, hide: true}
                , {title: '操作', align: 'center', toolbar: '#group-tool', width: 140}
            ]
        ]
    });
    //绑定工具栏事件
    table.on('toolbar(group-table)', function (obj) {
        const items = table.checkStatus('group-table').data;
        if (obj.event === 'add-toolbar') {
            fn.edit();
        } else if (obj.event === 'edit-toolbar' || obj.event === 'del-toolbar') {
            if (items.length <= 0) {
                layer.msg("请选择一条纪录！");
            } else {
                if (obj.event === 'edit-toolbar') {
                    fn.edit(items[0]);
                } else if (obj.event === 'del-toolbar') {
                    fn.del(items[0]);
                }
            }
        } else if (obj.event === 'refresh-toolbar') {
            tableRender.reload();
        }
    });
    //绑定表格数据行操作事件
    table.on('tool(group-table)', function (obj) {
        const item = obj.data;
        if (obj.event === 'edit-tool') {
            fn.edit(item);
        } else if (obj.event === 'del-tool') {
            fn.del(item);
        }
    });
    fn.load();
});