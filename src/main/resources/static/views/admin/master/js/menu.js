layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'form', 'treeTable'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const form = layui.form;
    const layer = layui.layer;
    const treeTable = layui.treeTable;
    const fn = {
        dump: function (item) {
            $('#id').val(0);
            if (item) {
                form.val('menu-form', item);//表单赋值
            } else {
                $('#menu-form')[0].reset();//清空表单
            }
            $('#iconClsClear').attr('class', $('#iconCls').val());
        }
        //打开编辑窗口
        , edit: function (item) {
            fn.dump(item);
            admin.popup({
                type: 1
                , title: (item) ? '菜单编辑' : '菜单添加'
                , area: '100'
                , content: $('#menu-form')
                , btn: ['确定', '取消']
                , yes: function (index) {
                    //绑定菜单编辑数据提交
                    form.on('submit(menu-submit)', function (data) {
                        fn.save(data.field, function () {
                            layer.close(index);
                            fn.root();
                        });
                    });
                    $('#menu-submit').trigger('click');
                }
            });
        }
        //删除
        , del: function (item) {
            layer.confirm('是否删除【' + item.title + '】菜单？', {
                title: '删除'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                layer.close(index);
                const load = layer.load();
                admin.req({
                    url: '../../admin/menu/delete.json'
                    , dataType: 'json'
                    , type: 'delete'
                    , data: {id: item.id}
                    , success: function (data) {
                        layer.msg(data.message);
                        layer.close(load);
                        treeTableRender.reload();
                        fn.root();
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
            const load = layer.load();
            admin.req({
                url: '../../admin/menu/' + ((data.id === '0') ? 'save.json' : 'edit.json')
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: (data.id === '0') ? 'post' : 'put'
                , data: JSON.stringify(data)
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
                    treeTableRender.reload();
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
        //打开选择菜单图标窗口
        , icon: function () {
            layer.open({
                type: 2
                , skin: 'layui-layer-molv'
                , title: '图标'
                , area: ['800px', '400px']
                , shadeClose: true
                , content: '../../views/admin/icon.html'
            });
        }
        //加载顶级菜单
        , root: function () {
            admin.req({
                url: '../../admin/menu/root.json'
                , dataType: 'json'
                , type: 'get'
                , success: function (data) {
                    if (data.success) {
                        $('#parentId').empty().append(new Option('顶级菜单', '0'));
                        layui.each(data.data, function (index, item) {
                            $('#parentId').append(new Option(item.title, item.id));
                        });
                        form.render("select");
                    }
                }
            });
        }
        //加载属性
        , attr: function () {
            admin.req({
                url: '../../admin/menu/attr.json'
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
                url: '../../admin/menu/status.json'
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
            fn.root();
            fn.attr();
            fn.status();
        }
    };
    //生成树形表格
    const treeTableRender = treeTable.render({
        id: 'menu-table'
        , elem: '#menu-table'
        , toolbar: '#menu-toolbar'
        , url: '../../admin/menu/list.json'
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
                , {type: 'radio', title: '选择', width: 60}
                , {field: 'title', title: '标题', width: 160}
                , {field: 'address', title: '地址'}
                , {field: 'attrId', title: '属性', align: 'center', width: 60, templet: '#attr-tpl'}
                , {field: 'statusId', title: '状态', align: 'center', width: 60, templet: '#status-tpl'}
                , {field: 'orderId', title: '排序', align: 'right', width: 80, edit: 'number'}
                , {field: 'createUser', title: '创建用户', width: 90, hide: true}
                , {field: 'updateUser', title: '修改用户', width: 100, hide: true}
                , {field: 'createTime', title: '创建时间', width: 120, hide: true}
                , {field: 'updateTime', title: '修改时间', width: 120, hide: true}
                , {title: '操作', align: 'center', toolbar: '#menu-tool', width: 140}
            ]
        ]
        , done: function () {
            treeTableRender.expandAll();
        }
    });
    //绑定工具栏事件
    treeTable.on('toolbar(menu-table)', function (obj) {
        const items = treeTableRender.checkStatus();
        if (obj.event === 'add-toolbar') {
            if (items.length > 0 && items[0].parentId > 0) {
                $('#parentId').val(items[0].parentId);
            }
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
            treeTableRender.reload();
        } else if (obj.event === 'expand-toolbar') {
            treeTableRender.expandAll();
        } else if (obj.event === 'fold-toolbar') {
            treeTableRender.foldAll();
        }
    });
    //绑定表格数据行操作事件
    treeTable.on('tool(menu-table)', function (obj) {
        const item = obj.data;
        if (obj.event === 'edit-tool') {
            fn.edit(item);
        } else if (obj.event === 'del-tool') {
            fn.del(item);
        }
    });
    //监听单元格编辑
    treeTable.on('edit(menu-table)', function (obj) {
        obj.data[obj.field] = obj.value
        fn.save(obj.data);
    });
    //打开选择菜单图标窗口
    $('#iconCls').on('click', function () {
        fn.icon();
    });
    $('#iconClsClear').on('click', function () {
        $('#iconCls').val('');
        $('#iconClsClear').attr('class', '');
    });
    fn.load();
});