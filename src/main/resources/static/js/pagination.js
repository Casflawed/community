/**
 * 返回pageInfo的JSON对象
 * @return {JSON对象}
 */
function getPageInfoJsonObject() {
    //从隐藏域中获取的数据是json字符串，解析成json对象才能访问内部属性
    return JSON.parse($('.page >input[name="pageInfo"]').val());
}

/**
 * 返回请求路径（不包含查询参数）
 * @return {string}
 */
function getUrl(){
    // console.log("baseUrl", window.location.origin + window.location.pathname)
    return window.location.origin + window.location.pathname
}

/**
 * 分页条渲染函数，在页面加载时执行
 */
layui.use(['laypage', 'layer'], function () {
    var laypage = layui.laypage, layer = layui.layer;
    var pageInfo = getPageInfoJsonObject()

    // console.log("pageInfo:", pageInfo)
    laypage.render({
        elem: 'pagination',
        count: pageInfo.total,                              //数据总数
        limit: pageInfo.pageSize,                           //每页的数量
        curr: pageInfo.pageNum,
        theme: 'pagination',                                //自定义样式
        jump: function (laypage, first) {                   //切换分页的回调
            if (!first) {                                   //如果不加这个条件判断，回调会反复触发执行
                window.location.href = getUrl() + "?pageNum=" + laypage.curr
            }
        }
    });
})