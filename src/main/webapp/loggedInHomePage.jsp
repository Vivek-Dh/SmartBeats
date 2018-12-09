<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
.clearfix{
    clear : both;
}

.clearfix .pull-left{
    float:left;
}

.clearfix .pull-right{
    float:right;
} </style>
<title>Logged in</title>
</head>
<body>
<%List<List<String>> videos = (List<List<String>>)request.getAttribute("videos");
for(int i=0;i<videos.size();i++){ for(int j=0;j<videos.get(i).size();j++){ %>
<div class="clearfix">
    <div class="pull-left">
        <iframe id="player" type="text/html" width="640" height="390"
  src="http://www.youtube.com/embed/<%=videos.get(i).get(j)%>?enablejsapi=1"
  frameborder="0"></iframe>
    </div>
    <div class="pull-left">
    Kappapaapaedkemeklfsenfefn
    <div>dawodkadpoaodkok</div>
    </div>
</div><%} } %>
</body>
</html>