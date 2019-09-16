<style>
    .table-list-item {
        padding: 5px 5px;
    }

    .table-list-item .table {
        margin-bottom: 0;
    }

    .garyB {
        background-color: #EEEEEE;
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
                    <a data-toggle="tab" href="#material-tab" aria-expanded="false">材料提交</a>
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
                            <table style="font-size: 14px;" class="table table-hover table-condensed">
                                <!-- 两组数据信息的 -->
                                <tr>
                                    <td colspan="6">申报单位名称：${company.companyName}</td>
                                    <td colspan="6">机构代码：<strong>${company.societyCode}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="4">联系人1：<strong>${company.operator}</strong></td>
                                    <td colspan="4">身份证1：<strong>${company.idCardNumber_1}</strong></td>
                                    <td colspan="4">联系电话1：<strong>${company.operatorMobile}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="4">联系人2：<strong>${company.operator2}</strong></td>
                                    <td colspan="4">身份证2：<strong>${company.idCardNumber_2}</strong></td>
                                    <td colspan="4">联系电话2：<strong>${company.operatorMobile2}</strong></td>
                                </tr>
                                <!-- 一组数据信息的 -->
                                <tr>
                                    <td colspan="12">联系地址：<strong>${company.operatorAddress}</strong></td>
                                </tr>
                            </table>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">申请人及随迁子女信息</div>
                            <table style="font-size: 14px;" class="table table-hover table-condensed">
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
                                    <td colspan="4"><strong>专业：${other.profession}</strong>
                                    </td>
                                    <td colspan="8"><strong>曾用名：${person.formerName}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">本人及配偶承诺目前
                                        <select disabled style="height: 25px;">
                                            <option <#if person.pregnantPromise==0>selected</#if> value="0">
                                                &nbsp;&nbsp;
                                            </option>
                                            <option <#if person.pregnantPromise==1>selected</#if> value="1">已怀孕</option>
                                            <option <#if person.pregnantPromise==2>selected</#if> value="2">未怀孕</option>
                                        </select>已怀孕&nbsp;&nbsp;${person.pregnantWeek}&nbsp;&nbsp;周
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">本人及配偶承诺目前
                                        <select disabled style="height: 25px;">
                                            <option <#if person.thirdPregnantPromise==0>selected</#if> value="0">&nbsp;&nbsp;</option>
                                            <option <#if person.thirdPregnantPromise==1>selected</#if> value="1">处于
                                            </option>
                                            <option <#if person.thirdPregnantPromise==2>selected</#if> value="2">未处于
                                            </option>
                                        </select>政策外第三个及以上子女怀孕期间
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">职业资格：<strong>${profession.professionTypeStr}</strong></td>
                                    <td colspan="6">职业名称：<strong>${profession.jobNameStr}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">迁出地省（市自治区）：
                                        <select disabled style="height: 25px;">
                                        <#list provinceList as item>
                                            <option <#if move.moveProvince==item.id>selected</#if>
                                                    value="${item.id}">${item.name}</option>
                                        </#list>
                                        </select>
                                        市：
                                        <select disabled style="height: 25px;">
                                        <#list cityList as item>
                                            <#if item.parentId==move.moveProvince>
                                                <option <#if move.moveCity==item.id>selected</#if>
                                                        value="${item.id}">${item.name}</option>
                                            </#if>
                                        </#list>
                                        </select>
                                        区县：
                                        <select disabled style="height: 25px;">
                                        <#list areaList as item>
                                            <#if item.parentId==move.moveCity>
                                                <option <#if move.moveRegion==item.id>selected</#if>
                                                        value="${item.id}">${item.name}</option>
                                            </#if>
                                        </#list>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">迁出详细地址：<strong>${move.moveAddress}</strong></td>
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
                                        <select disabled style="height: 25px;">
                                            <option <#if move.settledNature==1>selected</#if> value="1">本单位集体户口</option>
                                            <option <#if move.settledNature==2>selected</#if> value="2">非本单位集体户口
                                            </option>
                                            <option <#if move.settledNature==3>selected</#if> value="3">家庭户口</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">现户籍登记机关：<strong>${move.moveRegisteredOffice}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="12">迁入地户籍登记机关：
                                        <select disabled style="height: 25px;">
                                        <#list officeList1 as item>
                                            <option <#if move.registeredOffice==item.id?string>selected</#if>
                                                    value="${item.id}">${item.name}</option>
                                        </#list>
                                        </select>
                                        <select disabled style="height: 25px;">
                                        <#list officeList2 as item>
                                            <option <#if move.registeredRegion==item.id?string>selected</#if>
                                                    value="${item.id?c}">${item.name}</option>
                                        </#list>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="12">迁入地详细地址：<strong>${move.address}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">拟落户地区：
                                        <select disabled style="height: 25px;">
                                        <#list officeList1 as item>
                                            <option <#if move.region==21>selected</#if>
                                                    value="21">和平区
                                            </option>
                                            <option <#if move.region==22>selected</#if>
                                                    value="22">河东区
                                            </option>
                                            <option <#if move.region==23>selected</#if>
                                                    value="23">河西区
                                            </option>
                                            <option <#if move.region==24>selected</#if>
                                                    value="24">南开区
                                            </option>
                                            <option <#if move.region==25>selected</#if>
                                                    value="25">河北区
                                            </option>
                                            <option <#if move.region==26>selected</#if>
                                                    value="26">红桥区
                                            </option>
                                            <option <#if move.region==27>selected</#if>
                                                    value="27">东丽区
                                            </option>
                                            <option <#if move.region==28>selected</#if>
                                                    value="28">西青区
                                            </option>
                                            <option <#if move.region==29>selected</#if>
                                                    value="29">津南区
                                            </option>
                                            <option <#if move.region==30>selected</#if>
                                                    value="30">北辰区
                                            </option>
                                            <option <#if move.region==31>selected</#if>
                                                    value="31">武清区
                                            </option>
                                            <option <#if move.region==32>selected</#if>
                                                    value="32">宝坻区
                                            </option>
                                            <option <#if move.region==33>selected</#if>
                                                    value="33">滨海新区
                                            </option>
                                            <option <#if move.region==34>selected</#if>
                                                    value="34">宁河区
                                            </option>
                                            <option <#if move.region==35>selected</#if>
                                                    value="35">静海区
                                            </option>
                                            <option <#if move.region==36>selected</#if>
                                                    value="36">蓟州区
                                            </option>
                                        </#list>
                                        </select>
                                    </td>
                                    <td colspan="6">收件人：<strong>${move.witness}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">联系电话：<strong>${move.witnessPhone}</strong></td>
                                    <td colspan="6">邮寄地址：<strong>${move.witnessAddress}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">实际用工单位：<strong>${other.companyName}</strong></td>
                                    <td colspan="6">单位电话：<strong>${other.companyPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="6">单位地址：<strong>${other.companyAddress}</strong></td>
                                    <td colspan="6">本人电话：<strong>${other.selfPhone}</strong></td>
                                </tr>
                                <tr>
                                    <td colspan="4">是否社保缴纳：<strong>${other.socialSecurityPayStr}</strong></td>
                                    <td colspan="4">有无行政拘留记录：<strong>${other.detentionStr}</strong></td>
                                    <td colspan="4">有无行获刑记录：<strong>${other.penaltyStr}</strong></td>
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
                                    <#list relation as ritem>
                                        <#if ritem.relationship=="配偶">
                                            <table style="font-size: 14px;"
                                                   class="table table-bordered table-condensed">
                                                <tr class="active">
                                                    <th colspan="5">与本人关系:${ritem.relationship}</th>
                                                </tr>
                                                <tr>
                                                    <td class="garyB" style="width: 25%;">姓名:</td>
                                                    <td style="width: 75%;">${ritem.name}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">身份证号:</td>
                                                    <td>${ritem.idNumber}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">文化程度:</td>
                                                    <td>${ritem.cultureDegree}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">是否随迁:</td>
                                                    <td>
                                                        <#if ritem.isRemove == 1>是</#if>
                                                        <#if ritem.isRemove == 2>否</#if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">配偶曾用名:</td>
                                                    <td>${ritem.formerName}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">配偶户籍地详细地址:</td>
                                                    <td>${ritem.spouse_HJAddress}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">配偶现居住地详细地址:</td>
                                                    <td>${ritem.spouse_LivingAddress}</td>
                                                </tr>
                                            </table>
                                        <#else>
                                            <table style="font-size: 14px;"
                                                   class="table table-bordered table-condensed">
                                                <tr class="active">
                                                    <th colspan="2">与本人关系:${ritem.relationship}</th>
                                                </tr>
                                                <tr>
                                                    <td class="garyB" style="width: 25%;">姓名:</td>
                                                    <td style="width: 75%;">${ritem.name}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">身份证号:</td>
                                                    <td>${ritem.idNumber}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">文化程度:</td>
                                                    <td>${ritem.cultureDegree}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">是否随迁:</td>
                                                    <td><#if ritem.isRemove == 1>是</#if>
                                                        <#if ritem.isRemove == 2>否</#if></td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">曾用名:</td>
                                                    <td>${ritem.formerName}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">政策属性:</td>
                                                    <td><select disabled>
                                                        <option <#if ritem.policyAttribute=='一孩'>selected</#if>
                                                                value="一孩">一孩
                                                        </option>
                                                        <option <#if ritem.policyAttribute=='政策内二孩'>selected</#if>
                                                                value="政策内二孩">政策内二孩
                                                        </option>
                                                        <option <#if ritem.policyAttribute=='政策外二孩'>selected</#if>
                                                                value="政策外二孩">政策外二孩
                                                        </option>
                                                        <option <#if ritem.policyAttribute=='政策内多孩'>selected</#if>
                                                                value="政策内多孩">政策内多孩
                                                        </option>
                                                        <option <#if ritem.policyAttribute=='政策外多孩'>selected</#if>
                                                                value="政策外多孩">政策外多孩
                                                        </option>
                                                    </select></td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">出生医学证明编号:</td>
                                                    <td>${ritem.medical_number}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">出生医学证明签证机构:</td>
                                                    <td>${ritem.medical_authority}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">收养子女:</td>
                                                    <td><#if ritem.isAdopt == 1>是<#else>否</#if></td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">出生地:</td>
                                                    <td>${ritem.birthplace}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">与第几任妻子/丈夫所生:</td>
                                                    <td>与第${ritem.approval_index}
                                                        任${ritem.approval_spouse} ${ritem.approval_which}所生
                                                    </td>
                                                <#--<td>${ritem.approval_which}</td>-->
                                                </tr>
                                                <tr>
                                                    <td class="garyB">抚养权归属:</td>
                                                    <td>${ritem.approval_custody}</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="garyB" style="text-align: center">
                                                        再生育审批情况:
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">审批时间:</td>
                                                    <td>${ritem.approval_time}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">审批证明编号:</td>
                                                    <td>${ritem.approval_number}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">审批单位名称:</td>
                                                    <td>${ritem.approval_companyName}</td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">审批条例适用:</td>
                                                    <td>${ritem.approval_rules}</td>
                                                </tr>

                                            </table>
                                        </#if>
                                    </#list>
                                    </td>
                                </tr>
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
                            <table style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>预览</th>
                                    <th class="text-info">材料名称</th>
                                </tr>
                            <#list materialInfos as item>
                                <#if item.onlinePersonMaterial??>
                                    <#if item.onlinePersonMaterial.materialUri=="">
                                        <tr>
                                            <td class="text-center">
                                                未上传
                                            </td>
                                            <td>${item.name}</td>
                                        </tr>
                                    <#else>
                                        <tr>
                                            <td style="width:100px;height:100px;" class="text-center">
                                                <img class="p-img" id="img_${item.onlinePersonMaterial.id?c}"
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
                                                <input type="file" id="${item.id}" name="${item.id}" onchange="fileChange(this)">
                                            </td>
                                        </tr>
                                    </#if>
                                <#else>
                                    <tr>
                                        <td class="text-center">
                                            未上传
                                        </td>
                                        <td>${item.name}<input type="file" value="${person.id}" id="${item.id}" name="${item.id}" onchange="fileChange(this)"></td>
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
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img style="height: 99%;width:99%;" src="' + $(this).attr("src") + '">');
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

                                /*
                                2019年9月11日 后台图片上传
                                 */
                                function fileChange(ele) {
                                    var identityInfoId = ${person.id?c};
                                    console.log("ele:"+ele);
                                    console.log(ele.id);
                                    var file = document.getElementById(ele.id);
                                    $.ajaxFileUpload({
                                        url: App.href + "/api/score/info/identityInfo/adminUploadImage",
                                        type: "POST",
                                        secureuri: false,
                                        fileElementId: ele.id,
                                        dataType: "json",
                                        data:{
                                            'id' : ele.id,
                                            'identityInfoId':identityInfoId
                                        },
                                        success:function (data) {
                                            alert("接收到后台的成功提示！")
                                        }
                                    });
                                }
                            </script>
                        </div>
                    </div>
                </div>
                <div id="material-tab" class="main-cont clearfix tab-pane">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人提交材料确认（<span class="text-danger">请勾选确认申请人送达的材料</span>）
                        </div>
                        <div class="table-list-item">
                            <table style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>确认</th>
                                    <th class="text-info">材料名称</th>
                                </tr>
                            <#list materialInfos as item>
                                <tr>
                                    <td class="text-center">
                                        <input name="material" value="${item.id?c}"
                                               type="checkbox"/>
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

