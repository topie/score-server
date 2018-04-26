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
                                    <td colspan="6">单位名称：${company.companyName}</td>
                                    <td colspan="6">机构代码：<strong>${company.societyCode}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">联系人：<strong>${company.operator}</strong></td>
                                    <td colspan="6">联系电话：<strong>${company.operatorMobile}</strong></td>
                                </tr>
                                <!-- 一组数据信息的 -->
                                <tr>
                                    <td colspan="12">联系地址：<strong>${company.operatorAddress}</strong></td>
                                </tr>
                            </table>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">申请人及随迁子女信息</div>
                            <table style="font-size: 12px;" class="table table-hover table-condensed">
                                <tr>
                                    <td colspan="12">居住证号（身份证号）：<strong>${person.idNumber}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">申请人类型：<strong>${other.applicantTypeStr}</strong></td>
                                    <td colspan="6">居住证申领日期：<strong>${other.applicationDate}</strong></td>
                                </tr>
                                <!-- 三组数据信息的 -->
                                <tr>
                                    <td colspan="4">姓名：<strong>${person.name}</strong></td>
                                    <td colspan="4">性别：<strong>${person.sex}</strong></td>
                                    <td colspan="4">民族：<strong>${person.nation}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="4">出生日期：<strong>${person.birthday}</strong></td>
                                    <td colspan="4">政治面貌：<strong>${other.politicalStatusStr}</strong></td>
                                    <td colspan="4">婚姻状况：<strong>${move.marriageStatusStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="4">文化程度：<strong>${other.cultureDegreeStr}</strong></td>
                                    <td colspan="4">学位：<strong>${other.degreeStr}</strong></td>
                                    <td colspan="4">年龄：<strong>${person.age}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12"><strong>${other.profession}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">职业资格：<strong>${profession.professionTypeStr}</strong></td>
                                    <td colspan="6">工种：<strong>${profession.jobTypeStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">户口所在地：<strong>${move.moveRegisteredOffice}</strong></td>
                                    <td colspan="6">户口性质：<strong>${move.houseNatureStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">单位名称：<strong>${other.companyName}</strong></td>
                                    <td colspan="6">单位电话：<strong>${other.companyPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">单位地址：<strong>${other.companyAddress}</strong></td>
                                    <td colspan="6">本人电话：<strong>${other.selfPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">拟落户地址：<strong></strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">拟落户地区：<strong>${move.region}</strong></td>
                                    <td colspan="6">是否社保缴纳：<strong>${other.socialSecurityPayStr}</strong></td>
                                </tr>
                                <!-- 标题特变长的分为特殊的两组 -->
                                <tr>
                                    <td colspan="6">积分期间有无行政拘留记录：<strong>${other.detentionStr}</strong></td>
                                    <td colspan="6">积分期间有无行政拘留记录：<strong>${other.penaltyStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">资格证书级别：<strong>${profession.jobLevelStr}</strong></td>
                                    <td colspan="6">证书编号：<strong>${profession.certificateCode}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">发证机关：<strong>${profession.issuingAuthority}</strong></td>
                                    <td colspan="6">发证日期：<strong>${profession.issuingDate}</strong></td>
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

