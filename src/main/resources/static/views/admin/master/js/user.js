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
                form.val('user-form', item);//表单赋值
            } else {
                $('#user-form')[0].reset();//清空表单
            }
            fn.group(item);
        }
        //打开编辑窗口
        , edit: function (item) {
            fn.dump(item);
            admin.popup({
                type: 1
                , title: (item) ? '用户编辑' : '用户添加'
                , area: '100'
                , content: $('#user-form')
                , btn: ['确定', '取消']
                , yes: function (index) {
                    //绑定用户编辑数据提交
                    form.on('submit(user-submit)', function (data) {
                        fn.save(data.field, function () {
                            layer.close(index);
                        });
                    });
                    $('#user-submit').trigger('click');
                }
            });
        }
        //删除
        , del: function (item) {
            layer.confirm('是否删除【' + item.username + '（' + item.name + '）】用户？', {
                title: '删除'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                layer.close(index);
                const load = layer.load();
                admin.req({
                    url: '../../admin/user/delete.json'
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
            data.groupId = userGroup.getValue('value');
            const load = layer.load();
            admin.req({
                url: '../../admin/user/' + ((data.id === '0') ? 'save.json' : 'edit.json')
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
        //重置用户密码
        , reset: function (item) {
            layer.confirm('是否重置【' + item.username + '（' + item.name + '）】用户密码？', {
                title: '重置密码'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                layer.close(index);
                const load = layer.load();
                admin.req({
                    url: '../../admin/user/reset.json'
                    , dataType: 'json'
                    , type: 'get'
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
        //加载用户分组
        , group: function (item) {
            admin.req({
                url: '../../admin/group/list.json'
                , dataType: 'json'
                , type: 'get'
                , success: function (data) {
                    if (data.success) {
                        userGroup.update({data: data.data});
                        if (item) {
                            userGroup.setValue(item.groupId);
                        }
                    }
                }
            });
        }
        //加载属性
        , attr: function () {
            admin.req({
                url: '../../admin/user/attr.json'
                , dataType: 'json'
                , type: 'get'
                , success: function (data) {
                    if (data.success) {
                        layui.each(data.data, function (index, item) {
                            $('#attrId').append(fn.option(item.attrExplain, item.attrId));
                        });
                        form.render("select");
                    }
                }
            });
        }
        //加载状态
        , status: function () {
            admin.req({
                url: '../../admin/user/status.json'
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
            fn.attr();
            fn.status();
        }
    };
    const userGroup = xmSelect.render({
        el: '#userGroup'
        , prop: {
            name: 'groupName',
            value: 'id',
        }
        , toolbar: {
            show: true,
        }
        , autoRow: true
        , direction: 'down'
    });
    const tableRender = table.render({
        id: 'user-table'
        , elem: '#user-table'
        , toolbar: '#user-toolbar'
        , url: '../../admin/user/list.json'
        , page: true
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
                , {field: 'name', title: '姓名', width: 120}
                , {field: 'username', title: '用户名称', width: 120}
                , {field: 'mobile', title: '手机号码', width: 120}
                , {field: 'email', title: '邮箱地址'}
                , {field: 'attrId', title: '属性', width: 80, templet: '#attr-tpl'}
                , {field: 'statusId', title: '状态', align: 'center', width: 80, templet: '#status-tpl'}
                , {field: 'loginIp', title: '登陆IP', width: 120, hide: true}
                , {field: 'loginTime', title: '登陆时间', width: 120, hide: true}
                , {field: 'createUser', title: '创建用户', width: 90, hide: true}
                , {field: 'updateUser', title: '修改用户', width: 100, hide: true}
                , {field: 'createTime', title: '创建时间', width: 120, hide: true}
                , {field: 'updateTime', title: '修改时间', width: 120, hide: true}
                , {title: '操作', align: 'center', toolbar: '#user-tool', width: 140}
            ]
        ]
    });
    //绑定工具栏事件
    table.on('toolbar(user-table)', function (obj) {
        const items = table.checkStatus('user-table').data;
        if (obj.event === 'add-toolbar') {
            fn.edit();
        } else if (obj.event === 'edit-toolbar' || obj.event === 'del-toolbar' || obj.event === 'reset-toolbar') {
            if (items.length <= 0) {
                layer.msg("请选择一条纪录！");
            } else {
                if (obj.event === 'edit-toolbar') {
                    fn.edit(items[0]);
                } else if (obj.event === 'del-toolbar') {
                    fn.del(items[0]);
                } else if (obj.event === 'reset-toolbar') {
                    fn.reset(items[0]);
                }
            }
        } else if (obj.event === 'refresh-toolbar') {
            tableRender.reload();
        }
    });
    //绑定表格数据行操作事件
    table.on('tool(user-table)', function (obj) {
        const item = obj.data;
        if (obj.event === 'edit-tool') {
            fn.edit(item);
        } else if (obj.event === 'del-tool') {
            fn.del(item);
        }
    });
    fn.load();
});