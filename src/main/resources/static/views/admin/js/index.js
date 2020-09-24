layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'form'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const form = layui.form;
    const layer = layui.layer;
    const element = layui.element;
    const fn = {
        //请求左侧导航
        side: function () {
            admin.req({
                url: '../../admin/menu/side.json',
                type: 'get',
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        fn.tree(data.data);
                    }
                }
            });
        }
        //根据返回的数据生成左侧导航
        , tree: function (data) {
            const side = [];
            layui.each(data, function (index, item) {
                if (index === 0) {
                    side.push('<li data-name="nav-item' + item.id + '" class="layui-nav-item layui-nav-itemed">');
                } else {
                    side.push('<li data-name="nav-item' + item.id + '" class="layui-nav-item">');
                }
                side.push('<a href="javascript:;" lay-tips="' + item.title + '">');
                side.push('<i class="' + item.iconCls + '"></i>');
                side.push('<cite>' + item.title + '</cite>');
                side.push('</a>');
                side.push('<dl class="layui-nav-child">');
                layui.each(item.children, function (index, item) {
                    side.push('<dd data-name="nav-item' + item.id + '">');
                    side.push('<a lay-href="../..' + item.address + '">');
                    side.push('<i class="' + item.iconCls + '"></i>');
                    side.push('<cite>' + item.title + '</cite>');
                    side.push('</a>');
                    side.push('</dd>');
                });
                side.push('</dl>');
                side.push('</li>');
            });
            $('#LAY-system-side-menu').html(side.join(''));
            element.render('nav', 'layadmin-system-side-menu');
        }
        //修改密码
        , pwd: function (data, fn) {
            const load = layer.load();
            admin.req({
                url: '../../admin/user/password.json'
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: 'post'
                , data: JSON.stringify(data)
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
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
        //打开修改密码窗口
        , open: function () {
            admin.popup({
                type: 1
                , title: '修改密码'
                , area: '100'
                , content: $('#password-form')
                , btn: ['确定', '取消']
                , yes: function (index) {
                    //绑定用户编辑数据提交
                    form.on('submit(password-submit)', function (data) {
                        fn.pwd(data.field, function () {
                            layer.close(index);
                        });
                    });
                    $('#password-submit').trigger('click');
                }
            });
        }
        //锁屏
        , lock: function () {
            layui.data('lockScreen', {key: 'lockStatus', value: true});
            layer.open({
                type: 1
                , title: '锁屏'
                , area: '100'
                , closeBtn: 0
                , skin: 'layui-layer-admin'
                , content: $('#lock-form')
                , btn: ['解锁', '退出']
                , yes: function (index) {
                    fn.unlock($('#lockPassword').val(), function () {
                        layui.data('lockScreen', {key: 'lockStatus', value: false});
                        layer.close(index);
                    })
                }
                , btn2: function () {
                    fn.logout();
                    return false;
                }
            });
        }
        //解锁
        , unlock: function (lockPassword, fn) {
            const load = layer.load();
            admin.req({
                url: '../../admin/user/unlock.json'
                , dataType: 'json'
                , type: 'get'
                , data: {lockPassword: lockPassword}
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
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
        //退出
        , logout: function () {
            layer.confirm('是否退出系统？', {
                title: '退出系统'
                , icon: 3
                , closeBtn: 0
                , skin: 'layui-layer-admin'
            }, function (index) {
                location.href = '../../views/admin/logout.html';
                layer.close(index);
            });
        }
        , avatar: function () {
            layer.open({
                type: 2
                , skin: 'layui-layer-molv'
                , title: '修改头像'
                , area: ['460px', '355px']
                , shadeClose: true
                , content: '../../views/admin/avatar.html'
            });
        }
        , load: function () {
            fn.side();
            if (layui.data('lockScreen').lockStatus) {
                fn.lock();
            }
        }
    };
    $('#edit-avatar').on('click', function () {
        fn.avatar();
    });
    $('#edit-password').on('click', function () {
        $('#password-form')[0].reset();//清空表单
        fn.open();
    });
    $('#lock-screen').on('click', function () {
        $('#lock-form')[0].reset();//清空表单
        fn.lock();
    });
    $('#logout').on('click', function () {
        fn.logout();
    });
    fn.load();
    $(document).attr("title", config.platform.title + ' ' + config.platform.version);
});