<%--
  Created by IntelliJ IDEA.
  User: ouyong
  Date: 2020/07/27
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/common/css.jsp"%>

    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/top.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">

        <jsp:include page="/WEB-INF/common/menu.jsp"></jsp:include>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form id="conditionForm" class="form-inline" role="form" style="float:left;" action="${PATH}/admin/index" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="condition" value="${param.condition}" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="conditionBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="deleteBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button  type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${PATH}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="theadCheckbox" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>

                            <tbody>

                            <c:forEach items="${page.list}" var="admin" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td><input class="itemCheckboxClass" type="checkbox" adminId="${admin.id}"></td>
                                    <td>${admin.loginacct}</td>
                                    <td>${admin.username}</td>
                                    <td>${admin.email}</td>
                                    <td>
                                        <button type="button" class="btn btn-success btn-xs" onclick="window.location.href = '${PATH}/admin/toAddAdminRole?id=${admin.id}'"><i class=" glyphicon glyphicon-check"></i></button>
                                        <button type="button" class="btn btn-primary btn-xs" onclick="window.location.href = '${PATH}/admin/toUpdate?id=${admin.id}&pageNum=${page.pageNum}'"><i class=" glyphicon glyphicon-pencil"></i></button>
                                        <button type="button" class="btn btn-danger btn-xs" onclick="deleteAdmin('${admin.loginacct}',${admin.id})"><i class=" glyphicon glyphicon-remove"></i></button>
                                    </td>
                                </tr>
                            </c:forEach>


                            </tbody>
                                    <tfoot>
                                    <tr >
                                        <td colspan="6" align="center">
                                            <ul class="pagination">
                                                <c:if test="${page.isFirstPage}">
                                                    <li class="disabled"><a href="#">上一页</a></li>
                                                </c:if>
                                                <c:if test="${!page.isFirstPage}">
                                                    <li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum - 1}">上一页</a></li>
                                                </c:if>

                                                <c:forEach items="${page.navigatepageNums}" var="num">
                                                    <c:if test="${num == page.pageNum}">
                                                        <li class="active"><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num}</a></li>
                                                    </c:if>
                                                    <c:if test="${num != page.pageNum}">
                                                        <li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num}</a></li>
                                                    </c:if>
                                                </c:forEach>

                                                <c:if test="${page.isLastPage}">
                                                    <li class="disabled"><a href="#">下一页</a></li>
                                                </c:if>
                                                <c:if test="${!page.isLastPage}">
                                                    <li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum + 1}">下一页</a></li>
                                                </c:if>
                                            </ul>
                                        </td>
                                    </tr>
                                    </tfoot>




                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/common/js.jsp"%>


<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });

    // 删除一条数据
    function deleteAdmin(loginacct,id) {
        // 使用layer的弹层组件
        layer.confirm("你确定删除【" + loginacct + "】用户吗?",{btn:['确定','取消']},
            function () {
                window.location.href = "${PATH}/admin/doDelete?id=" + id + "&pageNum=${page.pageNum}";
            },function () {

            }

        )
    }

    $("#conditionBtn").click(function () {
        $("#conditionForm").submit();
    });

    $("#theadCheckbox").click(function(){
        var theadChecked = $(this).prop("checked");

        $(".itemCheckboxClass").prop("checked",theadChecked);
    });

    // 批量删除
    $("#deleteBatchBtn").click(function(){
        var checkedboxList = $(".itemCheckboxClass:checked");
        if (checkedboxList.length == 0 ){
            layer.msg("请选择要删除数据!");
            return false;
        }

        var idArray = new Array();
        var c = '';
        $.each(checkedboxList,function(i,e){ //i 索引    e 元素

            var adminId = $(e).attr("adminId"); //将dom对象转换为jquery对象，调用attr()函数获取自定义属性，就可以了
            idArray.push(adminId);
        });
        idstr = idArray.join(","); //将数组中元素循环获取并拼成串，并使用逗号进行分割

        layer.confirm("您确定要删除这些数据吗?",{btn:['确定','取消']},function(){
            window.location.href = "${PATH}/admin/deleteBatch?ids="+idstr+"&pageNum=${page.pageNum}";
        },function(){

        });
    });
</script>
</body>
</html>
