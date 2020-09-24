layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index'], function () {
    const $ = layui.$;
    const layer = layui.layer;
    $('#login').on('click', function () {
        top.location.href = '../../views/admin/login.html';
    });
    $('#error').on('click', function () {
        layer.open({
            type: 1
            , shade: false
            , title: false
            , content: $('.layui-card').html()
            , area: ['600px', '400px']
        });
    });
});