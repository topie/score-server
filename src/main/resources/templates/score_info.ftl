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
                    <a data-toggle="tab" href="#material-tab" aria-expanded="false">材料提交</a>
                </li>

                <li class="">
                    <a data-toggle="tab" href="#online-tab" aria-expanded="false">材料上传</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <div id="info-tab" class="row tab-pane active">
                    <div class="col-md-7 col-sx-12">
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
                                    <td colspan="4">性别：<strong><#if person.sex == 1>男<#else>女</#if></strong></td>
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
                                    <td colspan="12"><strong>专业：${other.profession}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">职业资格：<strong>${profession.professionTypeStr}</strong></td>
                                    <td colspan="6">工种：<strong>${profession.jobTypeStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">现户口性质：<strong>${move.houseNatureStr}</strong></td>
                                    <td colspan="6">落户性质：<strong>${move.settledNatureStr}</strong></td>
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
                                    <td colspan="6">拟落户地区：<strong>${move.regionStr}</strong></td>
                                    <td colspan="6">是否社保缴纳：<strong>${other.socialSecurityPayStr}</strong></td>
                                </tr>
                                <!-- 标题特变长的分为特殊的两组 -->
                                <tr>
                                    <td colspan="6">积分期间有无行政拘留记录：<strong>${other.detentionStr}</strong></td>
                                    <td colspan="6">积分期间有无行获刑记录：<strong>${other.penaltyStr}</strong></td>
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
                    <div class="col-md-5 col-sx-12">
                    <#list scoreList as item>
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">部门审核打分</div>
                            <!-- Table -->
                            <table class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>指标序号：</th>
                                    <th>${item.indicator.indexNum}</th>
                                    <th>指标类别：</th>
                                    <th colspan="2">${item.indicator.category}</th>
                                </tr>
                                <tr class="info">
                                    <th>指标名称：</th>
                                    <th colspan="4">${item.indicator.name}</th>
                                </tr>
                                <tr class="info">
                                    <th>打分部门：</th>
                                    <th colspan="4">${item.opRole}</th>
                                </tr>
                                <tr class="info">
                                    <th>打分人：</th>
                                    <th>${item.opUser}</th>
                                    <th>打分时间：</th>
                                    <th colspan="2"><#if item.submitDate??>${item.submitDater?string("yyyy-MM-dd")}</#if></th>
                                </tr>
                                <tr class="info">
                                    <td>状态：</td>
                                    <td style="color: red">${item.scoreStatus}</td>
                                    <td>打分结果：</td>
                                    <td style="color: red" colspan="2">${item.scoreValue?default("-")}</td>
                                </tr>
                            </table>
                            <table class="table table-hover table-bordered table-condensed">
                                <#if item.indicator.itemType==0>
                                    <tr class="info">
                                        <th>选择</th>
                                        <th style="width: 60%" colspan="3">指标选项</th>
                                        <th>分值</th>
                                    </tr>
                                    <#list item.indicatorItems as sitem>
                                        <tr>
                                            <td class="text-center">
                                                <input type="radio"
                                                       value="${item.opRoleId}_${item.indicator.id}_${sitem.id}"
                                                       name="score">
                                            </td>
                                            <td style="width: 60%" colspan="3">${sitem.content}</td>
                                            <td class="text-danger">${sitem.score}分</td>
                                        </tr>
                                    </#list>
                                    <tr>
                                        <td class="text-center">
                                            <input type="radio"
                                                   value="${item.opRoleId}_${item.indicator.id}_0"
                                                   name="score">
                                        </td>
                                        <td style="width: 60%" colspan="3" class="text-danger">不属于上述情况，此指标不得分</td>
                                        <td class="text-danger">0分</td>
                                    </tr>
                                <#else>
                                    <tr class="info">
                                        <th colspan="4">输入框</th>
                                        <th>分值</th>
                                    </tr>
                                    <tr>
                                        <td colspan="4" class="text-danger">
                                            <input type="text" value=""
                                                   d-indicator="${item.opRoleId}_${item.indicator.id}"
                                                   name="score">
                                        </td>
                                        <td class="text-danger">手动输入</td>
                                    </tr>
                                </#if>
                                <tr>
                                    <td class="check_desc" colspan="5">
                                        <div class="text-info">审核打分说明：</div>
                                        <textarea class="form-control" rows="3" disabled>
                                            ${item.indicator.note}
                                        </textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text-danger fontweight600" colspan="5">
                                        <div class="alert alert-warning">请您按照指标体系要求打分</div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </#list>
                    </div>
                </div>
                <div id="material-tab" class="main-cont clearfix tab-pane">
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
                <div id="online-tab" class="main-cont clearfix tab-pane">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人材料上传情况
                        </div>
                        <!-- Table 多个表格列表组合 -->
                        <div class="table-list-item">
                            <table class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>预览(点击放大)</th>
                                    <th class="text-info">材料名称</th>
                                </tr>
                                <#list onlinePersonMaterials as item>
                                    <#if item.materialInfoId gt 0>
                                        <tr>
                                            <td class="text-center">
                                                <img class="p-img" id="img_${item.id}" style="cursor: pointer"
                                                     width="100" height="100" src="${item.materialUri}">
                                            </td>
                                            <td>${item.materialInfoName}
                                                <br>
                                                <button class="download btn btn-mini btn-info" type="button"
                                                        data-uri="${item.materialUri}"
                                                        data-name="${item.materialInfoName}_${item.personId}">下载
                                                </button>
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </table>
                            <script type="text/javascript">
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true
                                    }).show().$body.html(img);
                                });
                                $(".download").off("click");
                                $(".download").on("click", function () {
                                    var uri = $(this).attr("data-uri");
                                    var name = $(this).attr("data-name");
                                    var type = uri.substring(uri.lastIndexOf("."));
                                    var img = $("<a></a>").attr("href", uri).attr("download", name + type);
                                    img[0].click();
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

