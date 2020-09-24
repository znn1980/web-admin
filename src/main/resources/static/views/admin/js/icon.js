layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index'], function () {
    const $ = layui.$;
    const p$ = parent.layui.$;
    const player = parent.layer;
    const index = player.getFrameIndex(window.name);
    //匹配图标class，注册click事件
    $("i[class^='layui-icon layui-icon-']").on('click', function () {
        //给父窗口的图标输入框赋值
        p$('#iconCls').val($(this).attr('class'));
        p$('#iconClsClear').attr('class', $(this).attr('class'));
        player.close(index);//关闭窗口
    });
});