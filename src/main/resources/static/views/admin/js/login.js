layui.config({
    base: config.base + 'layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'form'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const form = layui.form;
    const layer = layui.layer;
    const fn = {
        //登陆
        login: function (field) {
            const load = layer.load();
            admin.req({
                url: config.base + 'admin/login.json'
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: 'post'
                , data: JSON.stringify(field)
                , success: function (data) {
                    if (data.success) {
                        layer.msg('登入成功', {offset: '15px', icon: 1, time: 1000}, function () {
                            layui.data('lockScreen', {key: 'lockStatus', value: false});
                            location.href = config.base + 'views/admin/index.html';
                        });
                    } else {
                        layer.msg(data.message, {offset: '15px', icon: 2});
                        layer.close(load);
                    }
                }
                , error: function () {
                    layer.msg('请求异常，请重试！');
                    layer.close(load);
                }
            });
        }
    };
    form.on('submit(login-submit)', function (obj) {
        fn.login(obj.field);
    });
    $('#login-captcha').on('click', function () {
        this.src = config.base + 'views/admin/captcha.jpg?t=' + (new Date).getTime();
    });
    $(document).attr("title", config.platform.title + ' ' + config.platform.version);
    $('#copyright').attr('href', config.platform.url);
    $('#copyright').html(config.platform.copyright);
});