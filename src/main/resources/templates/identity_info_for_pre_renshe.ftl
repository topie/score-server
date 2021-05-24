<style>
    .table-list-item {
        padding: 5px 5px;
    }

    .table-list-item .table {
        margin-bottom: 0;
    }
    td{
        border: 1px solid #000;
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
                    <a data-toggle="tab" href="#online-tab" aria-expanded="false">材料上传</a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#archiving-tab" aria-expanded="false">存档材料</a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#renshe-tab" aria-expanded="false">人社材料</a>
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
                                <div>
                                    <span>下载时间：${downloadDate}</span>
                                </div>
                                <!-- Default panel contents -->
                                <div class="panel-heading" style="text-align: center;font-size: large ;font-weight: bolder">注册单位信息</div>
                                <!-- Table -->
                                <table style="font-size: 14px;" class="table table-hover table-condensed">
                                    <!-- 两组数据信息的 -->
                                    <tr>
                                        <td colspan="6">单位名称：<strong>${company.companyName}</strong></td>
                                        <td colspan="6">机构代码：<strong>${company.societyCode}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="4">联系人1：<strong>${company.operator}</strong><#if renshe==true>
                                            <button role="msm_btn" phone="${other.selfPhone}" class="btn btn-mini btn-info"
                                                    type="button">发送短信
                                            </button></#if></td>
                                        <td colspan="4">身份证1：<strong>${company.idCardNumber_1}</strong></td>
                                        <td colspan="4">联系电话1：<strong>${company.operatorMobile}</strong>
                                        </td>
                                    </tr>
                                    <!-- 一组数据信息的 -->
                                    <tr>
                                        <td colspan="12">实际经营地址：<strong>${company.operatorAddress}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">核准日期：${company.apprdate}</td>
                                        <td colspan="6">住所：<strong>${company.dom}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">企业类型：${company.enttype}</td>
                                        <td colspan="6">成立日期：<strong>${company.estdate}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">法定代表人：${company.lerep}</td>
                                        <td colspan="6">经营期限自：<strong>${company.opfrom}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="12">经营范围：<strong>${company.opscope}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">经营期限至：${company.opto}</td>
                                        <td colspan="6">注册资本：<strong>${company.regcap}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">注册资本币种：${company.regcapcur}</td>
                                        <td colspan="6">注册号：<strong>${company.regno}</strong></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">登记机关：${company.regorg}</td>
                                        <td colspan="6">登记状态：<strong>${company.regstate}</strong></td>
                                    </tr>
                                </table>
                            </div>
                            <div class="panel panel-default">
                            <div class="panel-heading" style="text-align: center;font-size: large ;font-weight: bolder">申请人信息</div>
                            <table style="font-size: 14px;" class="table table-hover table-condensed" border="1px solid #ccc" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td colspan="12">居住证号（身份证号）：<strong id="infotab_idnumber">${person.idNumber}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">申请人类型：<strong>${other.applicantTypeStr}</strong></td>
                                </tr>
                                <!-- 三组数据信息的 -->
                                <tr>
                                    <td colspan="6">姓名：<strong id="infotab_name">${person.name}</strong></td>
                                    <td colspan="6">性别：<strong><#if person.sex == 1>男<#else>女</#if></strong></td>
                                    <#--<td colspan="4">民族：<strong>${person.nation}</strong></td>-->
                                </tr>
                                <tr>
                                    <td colspan="6">是否于2020年4月2日之前购买住房：<strong>
                                    <#if person.ourBuyHouse == 1>是<#elseif person.ourBuyHouse == 2>否<#else></#if></strong></td>
                                    <td colspan="6">是否按照“津发改社会〔2018〕26号”文件计算：<strong>
                                    <#if person.is201826Doc == 1>是<#elseif person.is201826Doc == 2>否<#else></#if></strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">不动产权证取得日期：<strong>${person.houseOurDate}</strong></td>
                                    <td colspan="6">购房合同签署日期：<strong>${person.housePactDate}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">租赁房屋地址：<strong>${move.rentHouseAddress}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">租赁登记备案证明编号：<strong>${move.rentIdNumber}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">租赁备案起始日：<strong>${move.rentHouseStartDate}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">租赁合同终止日：<strong>${move.rentHouseEndDate}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">是否缴存住房公积金：<strong><#if other.providentFund == 1>是<#else>否</#if></strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">是否在开发区缴纳外资企业住房公积金：<strong><#if other.isInsurance == 1>是<#else>否</#if></strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">2014年以前是否缴存开发区社保中心公积金：<strong><#if other.kaifaquFund == 1>是<#else>否</#if></strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">落户类别：<strong><#if move.settledNature == 1>持有不动产权证或者房屋所有权证</#if>
                                    <#if move.settledNature == 2>购买住房还未办理不动产权证，但持有购房合同和税收缴款书（此选项不允许子女随迁）</#if>
                                    <#if move.settledNature == 3>无住房，但单位已设立集体户口（此选项不允许子女随迁）</#if>
                                    <#if move.settledNature == 4>无住房，且单位也未设立集体户口，自愿落户在市、区政府下设人力资源中介机构集体户（此选项不允许子女随迁）</#if></strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">出生日期：<strong>${person.birthday}</strong></td>
                                    <td colspan="6">政治面貌：<strong>${other.politicalStatusStr}</strong></td>
                                </tr>
                                <tr>

                                    <td colspan="6">婚姻状况：<strong>${move.marriageStatusStr}</strong></td>
                                    <td colspan="6">年龄：<strong>${person.age}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">文化程度：<strong>${other.cultureDegreeStr}</strong></td>
                                    <td colspan="6">学位：<strong>${other.degreeStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">职业资格：<strong>${profession.professionTypeStr}</strong></td>
                                    <td colspan="6">职业名称：<strong>${profession.jobNameStr}</td>
                                </tr>

                                <tr>
                                    <td colspan="6">现户口性质：<select disabled style="height: 25px;">
                                        <option <#if move.houseNature==1>selected</#if> value="1">未落常住户口</option>
                                        <option <#if move.houseNature==2>selected</#if> value="2">非农业家庭户口</option>
                                        <option <#if move.houseNature==3>selected</#if> value="3">农业家庭户口</option>
                                        <option <#if move.houseNature==4>selected</#if> value="4">非农业集体户口</option>
                                        <option <#if move.houseNature==5>selected</#if> value="5">农业集体户口</option>
                                        <option <#if move.houseNature==6>selected</#if> value="6">自理口粮户口</option>
                                        <option <#if move.houseNature==7>selected</#if> value="7">寄住户口</option>
                                        <option <#if move.houseNature==8>selected</#if> value="8">暂住户口</option>
                                        <option <#if move.houseNature==9>selected</#if> value="9">地方城镇居民户口</option>
                                        <option <#if move.houseNature==10>selected</#if> value="10">其他户口</option>
                                    </select></td>
                                    <td colspan="6">落户性质：
                                        <select data-id="${move.id?c}" data-name="move.settledNature" class="edit"
                                                style="height: 25px;">
                                            <option <#if move.settledNature==1>selected</#if> value="1">持有不动产权证或者房屋所有权证</option>
                                            <option <#if move.settledNature==2>selected</#if> value="2">购买住房还未办理不动产权证，但持有购房合同和税收缴款书（此选项不允许子女随迁）</option>
                                            <option <#if move.settledNature==3>selected</#if> value="3">无住房，但单位已设立集体户口（此选项不允许子女随迁）</option>
                                            <option <#if move.settledNature==4>selected</#if> value="4">无住房，且单位也未设立集体户口，自愿落户在市、区政府下设人力资源中介机构集体户（此选项不允许子女随迁）</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">联系电话：<strong>${move.witnessPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">是否为派遣制用工：<strong>${other.dispatchStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">是否具有劳务派遣经营许可证：<strong>${other.isDispatchLicenseStr}</strong></td>
                                    <td colspan="6">劳务派遣经营许可证编号：<strong>${other.dispatchLicenseNum}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">劳务派遣经营许可证是否在有效期内：<strong>${other.isDispatchLicenseDateStr}</strong></td>
                                    <td colspan="6">是否签订派遣协议：<strong>${other.isSignDispatchStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">实际用工单位名称：<strong>${other.companyName}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">申请人实际办公地址：<strong>${other.applyOfficeAddress}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">单位电话：<strong>${other.companyPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">实际用工单位现经营地址：<strong>${other.companyAddress}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">本人电话：<strong>${other.selfPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">是否社保缴纳：<strong>${other.socialSecurityPayStr}</strong></td>
                                </tr>

                                <tr>
                                    <td colspan="6">异地保险：<strong>${other.isAnotherInsuranceStr}</strong></td>
                                    <td colspan="6">就业登记：<strong>${other.isApplyRegisteStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">开发区：<strong>${other.isInsuranceStr}</strong></td>
                                    <td colspan="6">配偶：<strong>${other.isSpouseInsuranceStr}</strong></td>
                                </tr>

                                <tr>
                                    <td colspan="6">资格证书级别：<strong>${profession.jobLevelStr}</strong></td>
                                    <td colspan="6">证书编号：<strong>${profession.certificateCode}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">发证机关：<strong>${profession.issuingAuthority}</strong></td>
                                    <td colspan="6">发证日期：<strong>${profession.issuingDate}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">当前工作岗位：<strong>${other.jobTitle}</strong></td>
                                    <td colspan="6">主要从事工作内容：<strong>${other.jobContent}</strong></td>
                                </tr>
                                <#--<tr>
                                    <td colspan="12">
                                        <table style="font-size: 14px;"
                                               class="table table-hover table-bordered table-condensed">
                                            <tr class="info">
                                                <th>与本人关系</th>
                                                <th>姓名</th>
                                                <th>身份证号</th>
                                                <th>文化程度</th>
                                                <th>是否随迁</th>
                                            </tr>
                                        <#list relation as ritem>
                                            <tr>
                                                <td>${ritem.relationship}</td>
                                                <td>${ritem.name}</td>
                                                <td>${ritem.idNumber}</td>
                                                <td>${ritem.cultureDegree}</td>
                                                <td> <#if ritem.isRemove == 1>是<#else>否</#if></td>
                                            </tr>
                                        </#list>
                                        </table>
                                    </td>
                                </tr>-->
                                <tr>
                                    <td colspan="12"><hr></strong></td>
                                </tr>
                                <#list relation as ritem>
                                    <#if ritem.relationship=="配偶">
                                        <tr>
                                            <td colspan="12">与本人关系：<strong>${ritem.relationship}</strong></td>
                                        </tr>
                                        <tr>
                                            <td colspan="12">姓名：<strong>${ritem.name}</strong></td>
                                        </tr>
                                        <tr>
                                            <td colspan="12">身份证号：<strong>${ritem.idNumber}</strong></td>
                                        </tr>
                                    </#if>
                                </#list>
                            </table>
                            <br>

                        </div>
                    </div>
                </div>

                <div id="online-tab" class="main-cont clearfix tab-pane">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人材料上传情况
                        </div>
                        <div id="to_point" style="display: none;position: absolute">

                        </div>
                        <!-- Table 多个表格列表组合 -->
                        <div class="table-list-item">
                            <table id="picTable" style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>选择</th>
                                    <th>预览</th>
                                    <th class="text-info">材料名称</th>
                                    <th class="text-info">失败原因</th>
                                </tr>
                            <#list materialInfos as item>
                                <#if item.onlinePersonMaterial??>
                                    <#if item.onlinePersonMaterial.materialUri=="" && item.onlinePersonMaterial.materialId!=0>
                                        <tr>
                                            <td class="text-center">
                                                未上传
                                            </td>
                                            <td>${item.name}</td>
                                            <td></td>
                                        </tr>
                                    <#else>
                                        <tr>
                                            <td class="text-center">
                                                <input name="checkMaterial" type="checkbox" checked="checked">
                                            </td>
                                            <td style="width:100px;height:100px" class="text-center">
                                                <img name="picpic" class="p-img" id="img_${item.onlinePersonMaterial.id?c}"
                                                     style="cursor: pointer;border: 1px solid gray;"
                                                     width="100" height="100"
                                                     src="${item.onlinePersonMaterial.materialUri}">
                                            </td>
                                            <td>${item.onlinePersonMaterial.materialInfoName}
                                                <br>
                                                <a class="download btn btn-mini btn-info" target="_blank"
                                                   href="${item.onlinePersonMaterial.materialUri}"
                                                   download="${item.onlinePersonMaterial.materialInfoName}_${item.onlinePersonMaterial.personId?c}">
                                                    下载
                                                </a>
                                            </td>
                                            <td>${item.onlinePersonMaterial.reason}</td>
                                        </tr>
                                    </#if>
                                <#else>
                                    <tr>
                                        <td class="text-center">
                                            未上传
                                        </td>
                                        <td>${item.name}</td>
                                        <td></td>
                                    </tr>
                                </#if>
                            </#list>
                            </table>
                            <script type="text/javascript">
                                var hostName = window.location.host;
                                if (hostName == "172.16.200.68") {
                                    $(".p-img").each(function () {
                                        var src = $(this).attr("src");
                                        var newSrc = src.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("src", newSrc);
                                    });
                                    $("a.download").each(function () {
                                        var href = $(this).attr("href");
                                        var newHref = href.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("href", newHref);
                                    });
                                }
                                $("button[role=msm_btn]").on("click", function () {
                                    var name = $(this).prev().text();
                                    var phone = $(this).attr("phone");
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: App.href + "/api/score/approve/renshePrevApprove/sendCompanyMsg",
                                        data: {
                                            name: name,
                                            phone: phone
                                        },
                                        success: function (data) {
                                            alert("发送成功!")
                                        },
                                        error: function (e) {
                                            console.error("请求异常。");
                                        }
                                    });
                                });
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true,
                                        buttons: [
                                            {
                                                text: '旋转',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var transform = $(this).css('transform');

                                                        if (transform == "none") {
                                                            $(this).css("transform", 'rotate(90deg)');
                                                        } else {
                                                            function getmatrix(a, b, c, d, e, f) {
                                                                var aa = Math.round(180 * Math.asin(a) / Math.PI);
                                                                var bb = Math.round(180 * Math.acos(b) / Math.PI);
                                                                var cc = Math.round(180 * Math.asin(c) / Math.PI);
                                                                var dd = Math.round(180 * Math.acos(d) / Math.PI);
                                                                var deg = 0;
                                                                if (aa == bb || -aa == bb) {
                                                                    deg = dd;
                                                                } else if (-aa + bb == 180) {
                                                                    deg = 180 + cc;
                                                                } else if (aa + bb == 180) {
                                                                    deg = 360 - cc || 360 - dd;
                                                                }
                                                                return deg >= 360 ? 0 : deg;
                                                            }

                                                            var deg = eval('get' + transform);
                                                            var step = 90;//每次旋转多少度
                                                            $(this).css({'transform': 'rotate(' + (deg + step) % 360 + 'deg)'});
                                                        }
                                                    });
                                                }
                                            },
                                            {
                                                text: '放大',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 1.1);
                                                        $(this).css("width", $(that).width() * 1.1);
                                                    });
                                                }
                                            }, {
                                                text: '缩小',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 0.9);
                                                        $(this).css("width", $(that).width() * 0.9);
                                                    });
                                                }
                                            }
                                        ]
                                    }).show().$body.html(img);
                                });
                            </script>
                        </div>
                    </div>
                </div>
                <!-- 2019年6月19日 添加“材料存档”tab页 -->
                <div id="archiving-tab" class="main-cont clearfix tab-pane">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人材料上传情况
                        </div>
                        <div id="to_point" style="display: none;position: absolute">

                        </div>
                        <!-- Table 多个表格列表组合 -->
                        <div class="table-list-item">
                            <table id="picTable" style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>选择</th>
                                    <th>预览</th>
                                    <th class="text-info">材料名称</th>
                                    <th class="text-info">失败原因</th>
                                </tr>
                            <#list materialInfos_2 as item>
                                <#if item.onlinePersonMaterial??>
                                    <#if item.onlinePersonMaterial.materialUri=="" && item.onlinePersonMaterial.materialId!=0>
                                        <tr>
                                            <td class="text-center">
                                                未上传
                                            </td>
                                            <td>${item.name}</td>
                                            <td></td>
                                        </tr>
                                    <#else>
                                        <tr>
                                            <td class="text-center">
                                                <input name="checkMaterial" type="checkbox" checked="checked">
                                            </td>
                                            <td style="width:100px;height:100px" class="text-center">
                                                <img name="picpic_2" class="p-img" id="img_${item.onlinePersonMaterial.id?c}"
                                                     style="cursor: pointer;border: 1px solid gray;"
                                                     width="100" height="100"
                                                     src="${item.onlinePersonMaterial.materialUri}">
                                            </td>
                                            <td>${item.onlinePersonMaterial.materialInfoName}
                                                <br>
                                                <a class="download btn btn-mini btn-info" target="_blank"
                                                   href="${item.onlinePersonMaterial.materialUri}"
                                                   download="${item.onlinePersonMaterial.materialInfoName}_${item.onlinePersonMaterial.personId?c}">
                                                    下载
                                                </a>
                                            </td>
                                            <td>${item.onlinePersonMaterial.reason}</td>
                                        </tr>
                                    </#if>
                                <#else>
                                    <tr>
                                        <td class="text-center">
                                            未上传
                                        </td>
                                        <td>${item.name}</td>
                                        <td></td>
                                    </tr>
                                </#if>
                            </#list>
                            </table>
                            <script type="text/javascript">
                                var hostName = window.location.host;
                                if (hostName == "172.16.200.68") {
                                    $(".p-img").each(function () {
                                        var src = $(this).attr("src");
                                        var newSrc = src.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("src", newSrc);
                                    });
                                    $("a.download").each(function () {
                                        var href = $(this).attr("href");
                                        var newHref = href.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("href", newHref);
                                    });
                                }
                                $("button[role=msm_btn]").on("click", function () {
                                    var name = $(this).prev().text();
                                    var phone = $(this).attr("phone");
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: App.href + "/api/score/approve/renshePrevApprove/sendCompanyMsg",
                                        data: {
                                            name: name,
                                            phone: phone
                                        },
                                        success: function (data) {
                                            alert("发送成功!")
                                        },
                                        error: function (e) {
                                            console.error("请求异常。");
                                        }
                                    });
                                });
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true,
                                        buttons: [
                                            {
                                                text: '旋转',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var transform = $(this).css('transform');

                                                        if (transform == "none") {
                                                            $(this).css("transform", 'rotate(90deg)');
                                                        } else {
                                                            function getmatrix(a, b, c, d, e, f) {
                                                                var aa = Math.round(180 * Math.asin(a) / Math.PI);
                                                                var bb = Math.round(180 * Math.acos(b) / Math.PI);
                                                                var cc = Math.round(180 * Math.asin(c) / Math.PI);
                                                                var dd = Math.round(180 * Math.acos(d) / Math.PI);
                                                                var deg = 0;
                                                                if (aa == bb || -aa == bb) {
                                                                    deg = dd;
                                                                } else if (-aa + bb == 180) {
                                                                    deg = 180 + cc;
                                                                } else if (aa + bb == 180) {
                                                                    deg = 360 - cc || 360 - dd;
                                                                }
                                                                return deg >= 360 ? 0 : deg;
                                                            }

                                                            var deg = eval('get' + transform);
                                                            var step = 90;//每次旋转多少度
                                                            $(this).css({'transform': 'rotate(' + (deg + step) % 360 + 'deg)'});
                                                        }
                                                    });
                                                }
                                            },
                                            {
                                                text: '放大',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 1.1);
                                                        $(this).css("width", $(that).width() * 1.1);
                                                    });
                                                }
                                            }, {
                                                text: '缩小',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 0.9);
                                                        $(this).css("width", $(that).width() * 0.9);
                                                    });
                                                }
                                            }
                                        ]
                                    }).show().$body.html(img);
                                });
                            </script>
                        </div>
                    </div>
                </div>

                <!-- 2019年6月20日 添加“人社材料”tab页 -->
                <div id="renshe-tab" class="main-cont clearfix tab-pane">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人材料上传情况
                        </div>
                        <div id="to_point" style="display: none;position: absolute">

                        </div>
                        <!-- Table 多个表格列表组合 -->
                        <div class="table-list-item">
                            <table id="picTable" style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>选择</th>
                                    <th>预览</th>
                                    <th class="text-info">材料名称</th>
                                    <th class="text-info">失败原因</th>
                                </tr>
                            <#list materialInfos_3 as item>
                                <#if item.onlinePersonMaterial??>
                                    <#if item.onlinePersonMaterial.materialUri=="" && item.onlinePersonMaterial.materialId!=0>
                                        <tr>
                                            <td class="text-center">
                                                未上传
                                            </td>
                                            <td>${item.name}</td>
                                            <td></td>
                                        </tr>
                                    <#else>
                                        <tr>
                                            <td class="text-center">
                                                <input name="checkMaterial" type="checkbox" checked="checked">
                                            </td>
                                            <td style="width:100px;height:100px" class="text-center">
                                                <img name="picpic_3" class="p-img" id="img_${item.onlinePersonMaterial.id?c}"
                                                     style="cursor: pointer;border: 1px solid gray;"
                                                     width="100" height="100"
                                                     src="${item.onlinePersonMaterial.materialUri}">
                                            </td>
                                            <td>${item.onlinePersonMaterial.materialInfoName}
                                                <br>
                                                <a class="download btn btn-mini btn-info" target="_blank"
                                                   href="${item.onlinePersonMaterial.materialUri}"
                                                   download="${item.onlinePersonMaterial.materialInfoName}_${item.onlinePersonMaterial.personId?c}">
                                                    下载
                                                </a>
                                            </td>
                                            <td>${item.onlinePersonMaterial.reason}</td>
                                        </tr>
                                    </#if>
                                <#else>
                                    <tr>
                                        <td class="text-center">
                                            未上传
                                        </td>
                                        <td>${item.name}</td>
                                        <td></td>
                                    </tr>
                                </#if>
                            </#list>
                            </table>
                            <script type="text/javascript">
                                var hostName = window.location.host;
                                if (hostName == "172.16.200.68") {
                                    $(".p-img").each(function () {
                                        var src = $(this).attr("src");
                                        var newSrc = src.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("src", newSrc);
                                    });
                                    $("a.download").each(function () {
                                        var href = $(this).attr("href");
                                        var newHref = href.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("href", newHref);
                                    });
                                }
                                $("button[role=msm_btn]").on("click", function () {
                                    var name = $(this).prev().text();
                                    var phone = $(this).attr("phone");
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: App.href + "/api/score/approve/renshePrevApprove/sendCompanyMsg",
                                        data: {
                                            name: name,
                                            phone: phone
                                        },
                                        success: function (data) {
                                            alert("发送成功!")
                                        },
                                        error: function (e) {
                                            console.error("请求异常。");
                                        }
                                    });
                                });
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true,
                                        buttons: [
                                            {
                                                text: '旋转',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var transform = $(this).css('transform');

                                                        if (transform == "none") {
                                                            $(this).css("transform", 'rotate(90deg)');
                                                        } else {
                                                            function getmatrix(a, b, c, d, e, f) {
                                                                var aa = Math.round(180 * Math.asin(a) / Math.PI);
                                                                var bb = Math.round(180 * Math.acos(b) / Math.PI);
                                                                var cc = Math.round(180 * Math.asin(c) / Math.PI);
                                                                var dd = Math.round(180 * Math.acos(d) / Math.PI);
                                                                var deg = 0;
                                                                if (aa == bb || -aa == bb) {
                                                                    deg = dd;
                                                                } else if (-aa + bb == 180) {
                                                                    deg = 180 + cc;
                                                                } else if (aa + bb == 180) {
                                                                    deg = 360 - cc || 360 - dd;
                                                                }
                                                                return deg >= 360 ? 0 : deg;
                                                            }

                                                            var deg = eval('get' + transform);
                                                            var step = 90;//每次旋转多少度
                                                            $(this).css({'transform': 'rotate(' + (deg + step) % 360 + 'deg)'});
                                                        }
                                                    });
                                                }
                                            },
                                            {
                                                text: '放大',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 1.1);
                                                        $(this).css("width", $(that).width() * 1.1);
                                                    });
                                                }
                                            }, {
                                                text: '缩小',
                                                cls: 'btn btn-info',
                                                handle: function (m) {
                                                    m.$body.find("img").each(function (i, d) {
                                                        var that = this;
                                                        $(this).css("height", $(that).height() * 0.9);
                                                        $(this).css("width", $(that).width() * 0.9);
                                                    });
                                                }
                                            }
                                        ]
                                    }).show().$body.html(img);
                                });
                            </script>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

