layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'table', 'laydate'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const layer = layui.layer;
    const table = layui.table;
    const laydate = layui.laydate;
    const util = layui.util;
    const date = {
        //获取当前时间段
        now: function (fmt) {
            const now = new Date();
            const date1 = util.toDateString(now, fmt);
            now.setDate(now.getDate() + 1);
            const date2 = util.toDateString(now, fmt);
            return {date1: date1, date2: date2};
        }
        //获取要查询的时间范围
        , range: function (item) {
            const date = $(item).val().split("~");
            if (date.length === 2) {
                return {date1: date[0].trim(), date2: date[1].trim()};
            } else if (date.length === 1) {
                return {date1: date[0].trim(), date2: null};
            }
            return {date1: null, date2: null};
        }
    }
    const fn = {
        //查询
        query: function () {
            tableRender.reload({
                where: {
                    startTime: date.range('#createTime').date1
                    , endTime: date.range('#createTime').date2
                }
                , page: {
                    curr: 1
                }
            });
        }
        //删除
        , del: function (items) {
            layer.confirm('是否删除【' + items.length + '】条日志？', {
                title: '删除'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                layer.close(index);
                const load = layer.load();
                admin.req({
                    url: '../../admin/logger/delete.json'
                    , contentType: "application/json;charset=UTF-8"
                    , dataType: 'json'
                    , type: 'delete'
                    , data: JSON.stringify(items)
                    , success: function (data) {
                        layer.msg(data.message);
                        layer.close(load);
                        if (data.success) {
                            tableRender.reload();
                        }
                    }
                });
            });
        }
    };
    const tableRender = table.render({
        id: 'logger-table'
        , elem: '#logger-table'
        , toolbar: '#logger-toolbar'
        , url: '../../admin/logger/list.json'
        , page: true
        , where: {
            startTime: date.now('yyyy-MM-dd').date1
            , endTime: date.now('yyyy-MM-dd').date2
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
                {type: 'numbers', title: '编号'}
                , {type: 'checkbox', title: '选择'}
                , {field: 'userId', title: '用户', width: 80}
                , {field: 'ip', title: '地址', width: 120}
                , {field: 'os', title: '系统', width: 100}
                , {field: 'browser', title: '浏览器', width: 100}
                , {field: 'userAgent', title: '代理', hide: true}
                , {field: 'message', title: '信息'}
                , {field: 'costMillis', title: '耗时', align: 'right', width: 80, templet: '#millis-tpl'}
                , {field: 'createTime', title: '创建时间', width: 120}
                , {title: '操作', align: 'center', toolbar: '#logger-tool', width: 80}
            ]
        ]
    });
    //绑定工具栏事件
    table.on('toolbar(logger-table)', function (obj) {
        const items = table.checkStatus('logger-table').data;
        if (obj.event === 'query-toolbar') {
            fn.query();
        } else if (obj.event === 'del-toolbar') {
            if (items.length <= 0) {
                layer.msg("请选择一条纪录！");
            } else {
                const ids = [];
                layui.each(items, function (index, item) {
                    ids.push(item.id);
                });
                fn.del(ids);
            }
        } else if (obj.event === 'refresh-toolbar') {
            tableRender.reload();
        }
    });
    //绑定表格数据行操作事件
    table.on('tool(logger-table)', function (obj) {
        const item = obj.data;
        if (obj.event === 'del-tool') {
            fn.del([item.id]);
        }
    });
    laydate.render({
        elem: '#createTime'
        , range: '~'
        , format: 'yyyy-MM-dd'
        , btns: ['confirm']
        , value: date.now('yyyy-MM-dd').date1 + ' ~ ' + date.now('yyyy-MM-dd').date2
    });
    if ($("div[name='fieldBoxQuery']").length > 0) {
        $('#fieldQuery').show(function () {
            $('#fieldQuery').on('click', function () {
                $("div[name='fieldBoxQuery']").toggle();
            });
        });
    }
    $('#query-field').on('click', function () {
        fn.query();
    });
});