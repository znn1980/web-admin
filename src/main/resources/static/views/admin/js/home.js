layui.config({
    base: '../../layuiadmin/'
}).extend({
    index: 'lib/index'
}).use(['index', 'element'], function () {
    const $ = layui.$;
    const admin = layui.admin;
    const layer = layui.layer;
    const element = layui.element;
    const chart = {
        now: new Date()
        , timeout: 1000
        , refresh: 60
        , osCpu: []
        , osMemory: []
        , jvmMemory: []
        , osChart: null
        , osInterval: null
        , option: {
            title: {
                text: ''
            }
            , tooltip: {
                trigger: 'axis'
                , axisPointer: {
                    animation: false
                }
            }
            , legend: {
                data: ['CPU', '内存', 'Jvm内存']
            }
            , toolbox: {
                show: true
                , feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    }
                    , dataView: {readOnly: false}
                    , magicType: {type: ['line', 'bar']}
                    , restore: {},
                    saveAsImage: {}
                }
            }
            , xAxis: {
                type: 'time'
            }
            , yAxis: {
                name: '占用率（%）'
                , type: 'value'
                , min: 0
                , max: 100
                , axisLabel: {
                    show: true
                    , interval: 'auto'
                    , formatter: '{value} %'
                }
            }
            , series: [
                {
                    name: 'CPU'
                    , type: 'line'
                    , showSymbol: false
                    , hoverAnimation: false
                }
                , {
                    name: '内存'
                    , type: 'line'
                    , showSymbol: false
                    , hoverAnimation: false
                }
                , {
                    name: 'Jvm内存'
                    , type: 'line'
                    , showSymbol: false
                    , hoverAnimation: false
                }]
        }
        , init: function () {
            for (let i = 0; i < chart.refresh; i++) {
                chart.now = new Date(chart.now + chart.timeout);
                const name = layui.util.toDateString(chart.now, 'yyyy/MM/dd HH:mm:ss');
                chart.osCpu.push({name: chart.now.toString(), value: [name, 0]});
                chart.osMemory.push({name: chart.now.toString(), value: [name, 0]});
                chart.jvmMemory.push({name: chart.now.toString(), value: [name, 0]});
            }
            chart.osChart.setOption({
                series: [{data: chart.osCpu}, {data: chart.osMemory}, {data: chart.jvmMemory}]
            });
        }
        , stop: function () {
            $('#onMonitor').removeClass('layui-btn-disabled');
            $('#offMonitor').addClass('layui-btn-disabled');
            clearInterval(chart.osInterval);
        }
        , start: function () {
            $('#onMonitor').addClass('layui-btn-disabled');
            $('#offMonitor').removeClass('layui-btn-disabled');
            let showDisk = true;
            chart.osInterval = setInterval(function () {
                admin.req({
                    url: '../../admin/os/monitor.json',
                    type: 'get',
                    dataType: 'json',
                    success: function (data) {
                        const now = new Date();
                        const name = layui.util.toDateString(now, 'yyyy/MM/dd HH:mm:ss');
                        if (data.success) {
                            const osCpu = data.data.osCpu;
                            chart.osCpu.shift();
                            chart.osCpu.push({name: now.toString(), value: [name, osCpu.rate.toFixed(2)]});
                            chart.progress({
                                id: 'osCpu'
                                , name: ''
                                , rate: osCpu.rate.toFixed(2)
                                , total: 0
                                , free: 0
                                , max: 0
                            });
                            const osMemory = data.data.osMemory;
                            chart.osMemory.shift();
                            chart.osMemory.push({name: now.toString(), value: [name, osMemory.rate.toFixed(2)]});
                            chart.progress({
                                id: 'osMemory'
                                , name: ''
                                , rate: osMemory.rate.toFixed(2)
                                , total: (osMemory.total / 1024 / 1024).toFixed(2)
                                , free: (osMemory.free / 1024 / 1024).toFixed(2)
                                , max: 0
                            });
                            const jvmMemory = data.data.jvmMemory;
                            chart.jvmMemory.shift();
                            chart.jvmMemory.push({name: now.toString(), value: [name, jvmMemory.rate.toFixed(2)]});
                            chart.progress({
                                id: 'jvmMemory'
                                , name: ''
                                , rate: jvmMemory.rate.toFixed(2)
                                , total: (jvmMemory.total / 1024 / 1024).toFixed(2)
                                , free: (jvmMemory.free / 1024 / 1024).toFixed(2)
                                , max: (jvmMemory.max / 1024 / 1024).toFixed(2)
                            });
                            chart.osChart.setOption({series: [{data: chart.osCpu}, {data: chart.osMemory}, {data: chart.jvmMemory}]});
                            if (showDisk) {
                                chart.disk(data.data.osDiskSpace, data.data.osDiskSpaceList);
                                showDisk = false;
                            }
                        } else {
                            layer.msg(data.message);
                            chart.stop();
                        }
                    }
                    , error: function () {
                        layer.msg('请求异常，请重试！');
                        chart.stop();
                    }
                });
            }, chart.timeout);
        }
        , load: function () {
            chart.osChart = echarts.init(document.getElementById('osChart'), 'light');
            admin.resize(function () {
                chart.osChart.resize();
            });
            chart.osChart.setOption(chart.option);
            chart.init();
            chart.start();
        }
        , progress: function (os) {
            $('#' + os.id + 'Name').html(os.name);
            $('#' + os.id + 'Rate').html(os.rate);
            if (os.rate >= 80) {
                $('#' + os.id + 'Warn').show();
            } else {
                $('#' + os.id + 'Warn').hide();
            }
            $('#' + os.id + 'Total').html(os.total);
            $('#' + os.id + 'Free').html(os.free);
            $('#' + os.id + 'Max').html(os.max);
            const barCls = os.rate >= 80 ? 'red' : os.rate >= 60 ? 'orange' : os.rate >= 40 ? 'cyan' : os.rate >= 20 ? 'blue' : 'green';
            $('#' + os.id + 'Progress').attr('class', 'layui-progress-bar layui-bg-' + barCls);
            element.progress(os.id, os.rate + '%');
        }
        , disk: function (osDiskSpace, osDiskSpaceList) {
            chart.progress({
                id: 'osDiskSpace'
                , name: osDiskSpace.name
                , rate: osDiskSpace.rate.toFixed(2)
                , total: (osDiskSpace.total / 1024 / 1024 / 1024).toFixed(2)
                , free: (osDiskSpace.free / 1024 / 1024 / 1024).toFixed(2)
                , max: 0
            });
            const osDiskSpaceOpen = $('#osDiskSpaceOpen');
            if (osDiskSpaceList.length <= 1) {
                osDiskSpaceOpen.hide();
                return;
            }
            osDiskSpaceOpen.show();
            osDiskSpaceOpen.on('click', function () {
                $('#osDiskSpaceList').toggle();
                osDiskSpaceOpen.toggleClass('layui-edge-top');
                osDiskSpaceOpen.toggleClass('layui-edge-bottom');
            });
            $('#osDiskSpaceList').empty();
            layui.each(osDiskSpaceList, function (index, item) {
                const disk = [];
                disk.push('<div id="osDiskSpace' + index + '" class="layuiadmin-card-list">');
                disk.push('<p class="layuiadmin-normal-font">');
                disk.push('磁盘<span id="osDiskSpace' + index + 'Name"></span>占用率<span id="osDiskSpace' + index + 'Rate">0.00</span>%');
                disk.push('<span id="osDiskSpace' + index + 'Warn" class="layui-badge">告警</span>');
                disk.push('</p>');
                disk.push('<span>');
                disk.push('空间总量<span id="osDiskSpace' + index + 'Total">0.00</span>GB 可以空间<span id="osDiskSpace' + index + 'Free">0.00</span>GB');
                disk.push('</span>');
                disk.push('<div class="layui-progress" lay-filter="osDiskSpace' + index + '">');
                disk.push('<div id="osDiskSpace' + index + 'Progress" class="layui-progress-bar" lay-percent="0%"></div>');
                disk.push('</div>');
                disk.push('</div>');
                $('#osDiskSpaceList').append(disk.join(''));
                chart.progress({
                    id: 'osDiskSpace' + index
                    , name: item.name
                    , rate: item.rate.toFixed(2)
                    , total: (item.total / 1024 / 1024 / 1024).toFixed(2)
                    , free: (item.free / 1024 / 1024 / 1024).toFixed(2)
                    , max: 0
                });
            });
        }
    };
    $('#onMonitor').on('click', function () {
        chart.start();
    });
    $('#offMonitor').on('click', function () {
        chart.stop();
    });
    chart.load();
});