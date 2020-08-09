<%--
  Created by IntelliJ IDEA.
  User: ouyong
  Date: 2020/07/27
  Time: 19:39
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
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/top.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">

        <jsp:include page="/WEB-INF/common/menu.jsp"></jsp:include>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>


            <div class="panel panel-default">
                <div class="panel-body">

                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select id="leftRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${unassign}" var="role">
                                    <option value="${role.name}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="rightRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">

                                <c:forEach items="${assign}" var="role">
                                    <option value="${role.name}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>

                </div>
            </div>

        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
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


    $("#leftToRightBtn").click(function(){
        var items = $("#leftRoleList :selected");
        if(items.length==0){
            layer.msg("请选择要分配的角色", {time:1000, icon:6});
        }else{
            var data = {adminId:"${param.adminId}"};
            $.each(items,function(i,n){
                data["ids["+i+"]"] = n.value ;
            });
            $.ajax({
                url : "${PATH}/admin/assign",
                type : "post",
                data : data ,
                success : function(result){
                    if(result=="ok"){
                        layer.msg("分配的角色成功", {time:1000, icon:6},function(){
                            $("#rightRoleList").append(items.clone());
                            items.remove();
                        });
                    }else{
                        layer.msg("分配的角色失败", {time:1000, icon:5});
                    }
                }
            });
        }
    });
    $("#rightRightBtn").click(function(){
        var items = $("#rightRoleList :selected");
        if(items.length==0){
            layer.msg("请选择要取消分配的角色", {time:1000, icon:6});
        }else{
            var data = {"adminId":"${param.adminId}"};
            $.each(items,function(i,n){
                data["ids["+i+"]"] = n.value ;
            });
            $.ajax({
                url : "${PATH}/admin/unassign",
                type : "post",
                data : data ,
                success : function(result){
                    if(result=="ok"){
                        layer.msg("取消分配角色成功", {time:1000, icon:6},function(){
                            $("#leftRoleList").append(items.clone());
                            items.remove();
                        });
                    }else{
                        layer.msg("取消分配角色失败", {time:1000, icon:5});
                    }
                }
            });
        }
    });
/*

    //分配角色
    $("#leftToRightBtn").click(function(){
        var leftOptionSelected = $("#leftRoleList option:selected");

        if(leftOptionSelected.lenght==0){
            layer.msg("请先选择分配角色");
            return false ;
        }

        var str = "adminId=${param.id}" ; //   adminId=${param.id} &roleId=1 &roleId=2 &roleId=3

        $.each(leftOptionSelected,function(i,e){ // e ==>>>  <option value="${role.id}">${role.name}</option>
            var roleId = e.value ;
            str += "&";
            str += "roleId="+roleId;
        });

        $.ajax({
            type:"post",
            url:"${PATH}/admin/doAssignRoleToAdmin",
            data:str,
            success:function(result){
                if("ok"==result){
                    $("#rightRoleList").append(leftOptionSelected.clone());
                    leftOptionSelected.remove();
                    layer.msg("分配成功");
                }else{
                    layer.msg("分配失败");
                }
            }
        });
    });



    //取消分配角色
    $("#rightToLeftBtn").click(function(){
        var rightOptionSelected = $("#rightRoleList option:selected");

        if(rightOptionSelected.lenght==0){
            layer.msg("请先选择要取消分配的角色");
            return false ;
        }

        var str = "adminId=${param.id}" ; //   adminId=${param.id} &roleId=1 &roleId=2 &roleId=3

        $.each(rightOptionSelected,function(i,e){ // e ==>>>  <option value="${role.id}">${role.name}</option>
            var roleId = e.value ;
            str += "&";
            str += "roleId="+roleId;
        });

        $.ajax({
            type:"post",
            url:"${PATH}/admin/doUnAssignRoleToAdmin",
            data:str,
            success:function(result){
                if("ok"==result){
                    $("#leftRoleList").append(rightOptionSelected.clone());
                    rightOptionSelected.remove();
                    layer.msg("取消分配成功");
                }else{
                    layer.msg("取消分配失败");
                }
            }
        });
    });
*/

</script>
</body>
</html>

