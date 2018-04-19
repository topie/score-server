<style>
    .table-list-item {
        padding: 5px 5px;
    }

    .table-list-item .table {
        margin-bottom: 0;
    }
</style>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            申请人信息
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" id="recent-tab">
                <li class="active">
                    <a data-toggle="tab" href="#info-tab" aria-expanded="true">信息</a>
                </li>

                <li class="">
                    <a data-toggle="tab" href="#material-tab" aria-expanded="false">材料</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <div id="info-tab" class="row tab-pane active">
                    <div class="col-md-12 col-sx-12">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">用人单位信息</div>
                            <!-- Table -->
                            <table style="font-size: 12px;" class="table table-hover table-condensed">
                                <!-- 两组数据信息的 -->
                                <tr>
                                    <td colspan="2">单位名称：</td>
                                    <td colspan="4">
                                        <strong>${company.companyName}</strong>
                                    </td>
                                    <td colspan="2">机构代码：</td>
                                    <td colspan="4">
                                        <strong>${company.societyCode}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">联系人：</td>
                                    <td colspan="4">
                                        <strong>${company.operator}</strong>
                                    </td>
                                    <td colspan="2">联系电话：</td>
                                    <td colspan="4">
                                        <strong>${company.operatorMobile}</strong>
                                    </td>
                                </tr>
                                <!-- 一组数据信息的 -->
                                <tr>
                                    <td colspan="2">联系地址：</td>
                                    <td colspan="10">
                                        <strong>${company.operatorAddress}</strong>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">申请人及随迁子女信息</div>
                            <table style="font-size: 12px;" class="table table-hover table-condensed">
                                <tr>
                                    <td colspan="2">居住证号（身份证号）：
                                    </td>
                                    <td colspan="10">
                                        <strong>${person.idNumber}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">申请人类型：</td>
                                    <td colspan="4">
                                        <strong>${other.applicantTypeStr}</strong>
                                    </td>
                                    <td colspan="2">居住证申领日期：</td>
                                    <td colspan="4">
                                        <strong>${other.applicationDate}</strong>
                                    </td>
                                </tr>
                                <!-- 三组数据信息的 -->
                                <tr>
                                    <td colspan="2">姓名：</td>
                                    <td colspan="2">
                                        <strong>${person.name}</strong>
                                    </td>
                                    <td colspan="2">性别：</td>
                                    <td colspan="2">
                                        <strong>${person.sex}</strong>
                                    </td>
                                    <td colspan="2">民族：</td>
                                    <td colspan="2">
                                        <strong>${person.nation}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="1">出生日期：</td>
                                    <td colspan="3">
                                        <strong>${person.birthday}</strong>
                                    </td>
                                    <td colspan="1">政治面貌：</td>
                                    <td colspan="3">
                                        <strong>${other.politicalStatusStr}</strong>
                                    </td>
                                    <td colspan="1">婚姻状况：</td>
                                    <td colspan="3">
                                        <strong>${move.marriageStatusStr}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="1">文化程度：</td>
                                    <td colspan="3">
                                        <strong>${other.cultureDegreeStr}</strong>
                                    </td>
                                    <td colspan="1">学位：</td>
                                    <td colspan="3">
                                        <strong>${other.degreeStr}</strong>
                                    </td>
                                    <td colspan="1">年龄：</td>
                                    <td colspan="3">
                                        <strong>${person.age}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">专业：
                                    </td>
                                    <td colspan="10">
                                        <strong>${other.profession}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">职业资格：</td>
                                    <td colspan="4">
                                        <strong>${profession.professionTypeStr}</strong>
                                    </td>
                                    <td colspan="2">工种：</td>
                                    <td colspan="4">
                                        <strong>${profession.jobTypeStr}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">户口所在地：</td>
                                    <td colspan="4">
                                        <strong>${move.moveRegisteredOffice}</strong>
                                    </td>
                                    <td colspan="2">户口性质：</td>
                                    <td colspan="4">
                                        <strong>${move.houseNatureStr}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">单位名称：</td>
                                    <td colspan="4">
                                        <strong>${other.companyName}</strong>
                                    </td>
                                    <td colspan="2">单位电话：</td>
                                    <td colspan="4">
                                        <strong>${other.companyPhone}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">单位地址：</td>
                                    <td colspan="4">
                                        <strong>${other.companyAddress}</strong>
                                    </td>
                                    <td colspan="2">本人电话：</td>
                                    <td colspan="4">
                                        <strong>${other.selfPhone}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">拟落户地址：
                                    </td>
                                    <td colspan="10">
                                        <strong></strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">拟落户地区：</td>
                                    <td colspan="4">
                                        <strong>${move.region}</strong>
                                    </td>
                                    <td colspan="4">是否社保缴纳：</td>
                                    <td colspan="2">
                                        <strong>${other.socialSecurityPayStr}</strong>
                                    </td>
                                </tr>
                                <!-- 标题特变长的分为特殊的两组 -->
                                <tr>
                                    <td colspan="4">积分期间有无行政拘留记录：</td>
                                    <td colspan="2">
                                        <strong>${other.detentionStr}</strong>
                                    </td>
                                    <td colspan="4">积分期间有无行政拘留记录：</td>
                                    <td colspan="2">
                                        <strong>${other.penaltyStr}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">资格证书级别：</td>
                                    <td colspan="4">
                                        <strong>${profession.jobLevelStr}</strong>
                                    </td>
                                    <td colspan="4">证书编号：</td>
                                    <td colspan="2">
                                        <strong>${profession.certificateCode}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">发证机关：</td>
                                    <td colspan="4">
                                        <strong>${profession.issuingAuthority}</strong>
                                    </td>
                                    <td colspan="4">发证日期：</td>
                                    <td colspan="2">
                                        <strong>${profession.issuingDate}</strong>
                                    </td>
                                </tr>
                                <!-- 内表格 -->
                                <tr>
                                    <td colspan="12">
                                        <table class="table table-hover table-bordered table-condensed">
                                            <tr class="info">
                                                <th>与本人关系</th>
                                                <th>姓名</th>
                                                <th>身份证号</th>
                                                <th>文化程度</th>
                                            </tr>
                                        <#list relation as ritem>
                                            <tr>
                                                <td>${ritem.relationship}</td>
                                                <td>${ritem.name}</td>
                                                <td>${ritem.idNumber}</td>
                                                <td>${ritem.cultureDegree}</td>
                                            </tr>
                                        </#list>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="material-tab" class="main-cont clearfix tab-pane">
                    <div>
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                申请人材料提交情况
                            </div>
                            <!-- Table 多个表格列表组合 -->
                            <div class="table-list-item">
                                <table class="table table-hover table-bordered table-condensed">
                                    <tr class="info">
                                        <th colspan="2" class="text-info">选中表示已经提交过该材料</th>
                                    </tr>
                                    <tr class="info">
                                        <th>确认</th>
                                        <th class="text-info">材料名称</th>
                                    </tr>
                                <#list materialInfos as item>
                                    <tr>
                                        <td class="text-center">
                                            <input name="material" value="${item.id}" type="checkbox">
                                        </td>
                                        <td>${item.name}</td>
                                    </tr>
                                </#list>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

