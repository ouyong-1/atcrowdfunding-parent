<%--
  Created by IntelliJ IDEA.
  User: ouyong
  Date: 2020/07/28
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <form id="conditionForm" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="condition" value="${param.condition}" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="conditionBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="deleteBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button  id="saveBtn" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="theadCheckbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>

                            <tbody>


                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

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



<!-- 添加模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <form id="addForm" role="form">
                    <div class="form-group">
                        <label>名称</label>
                        <input type="text" class="form-control" name="name" placeholder="请输入角色名称">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveModalBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改角色</h4>
            </div>
            <div class="modal-body">
                <form id="updateForm" role="form">
                    <div class="form-group">
                        <label>名称</label>
                        <input type="hidden" name="id">
                        <input type="text" class="form-control" name="name" placeholder="请输入角色名称">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="updateModalBtn" type="button" class="btn btn-primary">修改</button>
            </div>
        </div>
    </div>
</div>




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

        showData(1);
    });
    // 设置json
    var json = {
        pageNum:1,
        pageSize:2,
        condition:'',

    }

    function showData(pageNum) {

        json.pageNum = pageNum;

        // 发送ajax请求
        $.ajax({
            url:"${PATH}/role/loadPage",
            type:"post",
            data:json,
            success:function (result) {
                // alert("xxx")
                json.totalPages = result.pages;
                // 显示列表数据
                showTable(result.list);

                // 显示列表数据
                showNavg(result);
            }
        });

    }

    // 显示列表数据
    function showTable(list) {
        // 拼接字符串
        var content = '';
        // 循环
        $.each(list,function (i,e) {// i为索引 e为role对象
            content+='<tr>';
            content+='  <td>'+(i+1)+'</td>';
            content+='  <td><input type="checkbox" class="itemCheckboxClass" roleId="'+e.id+'" ></td>';
            content+='  <td>'+e.name+'</td>';
            content+='  <td>';
            content+='	  <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            content+='	  <button type="button" roleId="'+e.id+'" class="updateBtnClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
            content+='	  <button type="button" roleId="'+e.id+'" class="deleteBtnClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            content+='  </td>';
            content+='</tr>';

        });
        // 刷新页面
        $("tbody").html(content)

    }


    // 显示列表数据
    function showNavg(pageInfo) {
        var content = '';
        if(pageInfo.isFirstPage){
            content+='<li class="disabled"><a href="#">上一页</a></li>';
        } else {
            content+='<li><a onclick="showData(' + (pageInfo.pageNum - 1) + ')">上一页</a></li>';
        }

        $.each(pageInfo.navigatepageNums,function (i, num) {
            if(num == pageInfo.pageNum){
                content+='<li class="active"><a onclick="showData(' + num + ')">' + num + ' <span class="sr-only">(current)</span></a></li>';
            }else{
                content+='<li><a onclick="showData(' + num + ')">' + num + ' </a></li>';
            }
        });

        if(pageInfo.isLastPage){
            content+='<li class="disabled"><a href="#">下一页</a></li>  ';
        }else{
            content+='<li><a onclick="showData(' + (pageInfo.pageNum + 1) + ')">下一页</a></li>  ';
        }
        // 刷新页面
        $(".pagination").html(content);
    }

    // 条件查询
    $("#conditionBtn").click(function () {
        var condition = $("#conditionForm input[name='condition']").val();

        json.condition = condition;

        showData(1);

    });

    // 添加角色
    $("#saveBtn").click(function () {
        // 弹出模态框
        $("#addModal").modal({
            show:true,
            backdrop:"static",
            keyboard:false
        });
    });

    $("#saveModalBtn").click(function () {
        // 获参数
        var name = $("#addModal input[name='name']").val();

        // 发送ajax请求
        $.ajax({
            type:"post",
            url:"${PATH}/role/addRole",
            data:{name:name},
            success:function (result) {
                if(result == "ok"){
                    // 添加完成，关闭模态框
                    $("#addModal").modal("hide");
                    $("#addModal input[name='name']").val("");
                    layer.msg("添加成功");
                    // 添加完成刷新
                    showData(json.totalPages + 1);
                }else {
                    layer.msg("添加失败");
                }
            }
        })
    });

    // 修改数据
    // 1、添加点击事件
    $("tbody").on("click",".updateBtnClass",function () {
        // 获取id
        var roleId = $(this).attr("roleId");
        // 发送ajax请求查询数据
        $.get("${PATH}/role/getRoleById",{id:roleId},function (result) {
            // 回显数据
            $("#updateModal input[name='name']").val(result.name);
            $("#updateModal input[name='id']").val(result.id);
            // 弹出模态框
            $("#updateModal").modal({
                show:true,
                backdrop:"static",
                keyboard:false
            });

        })

    });

    // 2、修改数据
    $("#updateModalBtn").click(function () {
        // 获取修改参数
        var name = $("#updateModal input[name='name']").val();
        var id = $("#updateModal input[name='id']").val();
        // 发送ajax请求
        $.ajax({
            type:"post",
            url:"${PATH}/role/updateRole",
            data:{id:id,name:name},
            success:function (result) {
                // 关闭模态框
                $("#updateModal").modal("hide");
                if(result == "ok"){
                    layer.msg("修改成功");
                    // 刷新页面到修改之处
                    showData(json.pageNum);
                }else {
                    layer.msg("修改失败")
                }
            }
        });

    });

    // 删除
    $("tbody").on("click",".deleteBtnClass",function () {
        var roleId = $(this).attr("roleId");
        // 询问是否删除
        layer.confirm("您确定删除吗？",{btn:['确定','取消']},function () {
            // 确定删除，发送ajax请求
            $.get("${PATH}/role/deleteRole",{id:roleId},function(result) {
                if(result == 'ok'){
                    layer.msg("删除成功");
                    // 刷新页面
                    showData(json.pageNum);
                }else{
                    layer.msg("删除失败");
                }
            })
        },function () {})
    })

    // 批量删除
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

            var roleId = $(e).attr("roleId"); //将dom对象转换为jquery对象，调用attr()函数获取自定义属性
            idArray.push(roleId);
        });
        idstr = idArray.join(","); //将数组中元素循环获取并拼成串，并使用逗号进行分割

        layer.confirm("您确定要删除这些数据吗?",{btn:['确定','取消']},function(){
            // 发送请求删除
            $.get("${PATH}/role/deleteBatch",{ids:idstr},function(result) {
                if(result == 'ok'){
                    layer.msg("删除成功");
                    // 刷新页面
                    showData(json.pageNum);
                }else{
                    layer.msg("删除失败");
                }
            })
        },function(){

        });
    });
</script>
</body>
</html>