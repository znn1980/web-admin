layui.use('layim', function (layim) {
    let ws;
    const $ = layui.$;
    const layer = layui.layer;
    const fn = {
        edit: function (data) {
            const load = layer.load();
            $.ajax({
                url: '../../admin/im/chat/user/edit.json'
                , contentType: "application/json;charset=UTF-8"
                , dataType: 'json'
                , type: 'put'
                , data: JSON.stringify(data)
                , success: function (data) {
                    layer.msg(data.message);
                    layer.close(load);
                }
                , error: function () {
                    layer.msg('请求异常，请重试！');
                    layer.close(load);
                }
            });
        }
    };
    const ping = {
        timeout: 1000 * 30//心跳间隔
        , pong: null
        //重置心跳时间
        , reset: function () {
            ping.stop().start();
        }
        //停止心跳
        , stop: function () {
            clearInterval(ping.pong);
            return ping;
        }
        //开始发送心跳
        , start: function () {
            ping.pong = setInterval(function () {
                console.log('ping');
                ws.send('ping');
            }, ping.timeout);
        }
    };
    const socket = {
        timeout: 1000 * 10//连接重试间隔
        , lock: false
        //连接
        , connect: function (url) {
            console.log(url);
            ws = new WebSocket(url);
            ws.onopen = function () {
                layer.msg('IM连接成功！');
                ping.reset();//重置心跳
            };
            ws.onclose = function () {
                console.log('IM连接关闭，' + (socket.timeout / 1000) + '秒后重试！')
                layer.msg('IM连接关闭，' + (socket.timeout / 1000) + '秒后重试！');
                ping.stop();//停止心跳
                socket.lock = false;
                socket.reconnect(url);
            };
            ws.onerror = function () {
                console.log('IM连接异常，' + (socket.timeout / 1000) + '秒后重试！')
                layer.msg('IM连接异常，' + (socket.timeout / 1000) + '秒后重试！');
                ping.stop();//停止心跳
                socket.lock = false;
                socket.reconnect(url);
            };
            ws.onmessage = function (ev) {
                ping.reset();//重置心跳
                if (ev.data === 'pong') {
                    return;
                }
                const data = JSON.parse(ev.data);
                if (data.type === 'systemMessage') {
                } else if (data.type === 'statusMessage') {
                } else if (data.type === 'onlineMessage') {
                    layim.setFriendStatus(data.data.id, 'online')
                } else if (data.type === 'offlineMessage') {
                    layim.setFriendStatus(data.data.id, 'offline')
                }
                layim.getMessage(data.data);
            };
        }
        //重试
        , reconnect: function (url) {
            setTimeout(function () {
                if (!socket.lock) {
                    socket.lock = true;
                    socket.connect(url);
                }
            }, socket.timeout);
        }
    };
    layim.config({
        title: '我的IM'
        , init: {
            url: '../../admin/im/chat/user/list.json'
        }
        , members: {
            url: '../../admin/im/chat/user/members.json'
        }
        , copyright: true
    });
    layim.on('ready', function (options) {
        $('#edit-avatar').attr('src', options.mine.avatar);
        const url = 'ws://' + window.location.host + config.base + 'admin/im/chat/' + options.mine.id;
        socket.connect(url);
    });
    layim.on('online', function (status) {
        fn.edit({status: status});
    });
    layim.on('sign', function (value) {
        fn.edit({sign: value});
    });

    layim.on('sendMessage', function (data) {
        ws.send(JSON.stringify({type: 'chatMessage', data: data}));
    });
    layim.on('chatChange', function (data) {
        console.log(JSON.stringify(data.data));
    });
    window.onbeforeunload = function () {
        ws.close();
    }
});