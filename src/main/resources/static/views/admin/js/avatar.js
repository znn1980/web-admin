layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const layer = layui.layer;
    const p$ = parent.layui.$;
    const player = parent.layer;
    const index = player.getFrameIndex(window.name);
    $('img').on('click', function () {
        const load = layer.load();
        const avatar = $(this).attr('src');
        admin.req({
            url: '../../admin/im/chat/user/edit.json'
            , contentType: "application/json;charset=UTF-8"
            , dataType: 'json'
            , type: 'put'
            , data: JSON.stringify({avatar: avatar})
            , success: function (data) {
                layer.msg(data.message);
                layer.close(load);
                if (data.success) {
                    p$('#edit-avatar').attr('src', avatar);
                    player.close(index);//关闭窗口
                }
            }
            , error: function () {
                layer.msg('请求异常，请重试！');
                layer.close(load);
            }
        });
    });
});