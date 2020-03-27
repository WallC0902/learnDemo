<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: SimSun;
        }

        section {
            display: block;
            margin: 20px 10px;
        }

        .title {
            text-align: center;
        }

        .preface p {
            line-height: 30px;
        }

        .preface p.content {
            text-indent: 2em;
        }

        section > table {
            table-layout: fixed;
            width: 100%;
            margin: 20px 0px;
            text-align: center;
            word-wrap: break-word;
        }

        section table td {
            padding: 5px 0px;
        }
    </style>
</head>
<body>
<!-- 标题 start -->
<section class="title">
    <h2>${title}</h2>
</section>
<!-- 标题 end -->

<!-- 前言 start -->
<section class="preface">
    <p>${userName}</p>
    <p class="content">${content}</p>
</section>
<!-- 前言 end -->

<!-- 汇总统计信息 start -->
<section class="count-info">
    <h4>汇总统计信息</h4>
    <table border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td>本月分数线</td>
            <td>近三个月分数对比</td>
        </tr>
        <tr>
            <td>${curr}</td>
            <td>
                <table width="80%" border="1" cellspacing="0" cellpadding="0" style="margin: 5px auto;">
                    <tr>
                        <td>${one}</td>
                        <td>${two}</td>
                        <td>${three}</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</section>
<!-- 汇总统计信息 end -->

<!-- 成绩单 start -->
<section class="detail">
    <h4>详情</h4>
    <table border="1" cellspacing="0" cellpadding="0">
        <tr>
            <td width="5%">序号</td>
            <td width="15%">姓名</td>
            <td width="12%">年龄</td>
            <td width="12%">性别</td>
            <td width="12%">分数</td>
            <td>地址</td>
        </tr>
        <#list dataList as al>
            <tr>
                <td>${al_index+1}</td>
                <td>${al.name}</td>
                <td>${al.age}</td>
                <td>${al.gender}</td>
                <td>${al.point}</td>
                <td>${al.address}</td>
            </tr>
        </#list>
    </table>
</section>
<!-- 成绩单 end -->

<!-- 可以写入的图片 start -->
<section>
    <h4>如下是图片信息</h4>
    <div>
        <img src="${imageUrl}" width="249" height="249"/>
    </div>
</section>
<!-- 可以写入的图片 end -->
</body>
</html>