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
                            <table style="font-size: 14px;" class="table table-hover table-condensed">
                                <!-- 两组数据信息的 -->
                                <tr>
                                    <td colspan="6">单位名称：${company.companyName}</td>
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
                                    <td colspan="4">专业：<strong>${other.profession}</strong>
                                    </td>
                                    <td colspan="8">曾用名：<strong>${person.formerName}</strong>
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
                                        </select>&nbsp;&nbsp;${person.pregnantWeek}&nbsp;&nbsp;周
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
                                    <td colspan="6">职业名称：<strong>${profession.jobNameStr}</td>
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
                                    <td colspan="12">现居住地址：<strong>${move.moveNowAddress}</strong></td>
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
                                    <td colspan="6">单位名称：<strong>${other.companyName}</strong></td>
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
                                                    <td><#if ritem.isRemove == 1>是</#if>
                                                        <#if ritem.isRemove == 2>否</#if></td>
                                                </tr>
                                                <tr>
                                                    <td class="garyB">配偶婚姻状况:</td>
                                                    <td><select disabled>
                                                        <option <#if ritem.marriageStatus=='0'>selected</#if> value="0">请选择</option>
                                                        <option <#if ritem.marriageStatus=='7'>selected</#if> value="7">初婚</option>
                                                        <option <#if ritem.marriageStatus=='8'>selected</#if> value="8">复婚</option>
                                                        <option <#if ritem.marriageStatus=='9'>selected</#if> value="9">再婚</option>
                                                    </select></td>
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
                                                        <option <#if ritem.policyAttribute=='一孩'>selected</#if> value="一孩">一孩</option>
                                                        <option <#if ritem.policyAttribute=='政策内二孩'>selected</#if> value="政策内二孩">政策内二孩</option>
                                                        <option <#if ritem.policyAttribute=='政策外二孩'>selected</#if> value="政策外二孩">政策外二孩</option>
                                                        <option <#if ritem.policyAttribute=='政策内多孩'>selected</#if> value="政策内多孩">政策内多孩</option>
                                                        <option <#if ritem.policyAttribute=='政策外多孩'>selected</#if> value="政策外多孩">政策外多孩</option>
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
                                                    <td>与第${ritem.approval_index}任${ritem.approval_spouse} ${ritem.approval_which}所生</td>
                                                <#--<td>${ritem.approval_which}</td>-->
                                                </tr>
                                                <tr>
                                                    <td class="garyB">抚养权归属:</td>
                                                    <td>${ritem.approval_custody}</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="garyB" style="text-align: center">再生育审批情况:</td>
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
                    <div class="col-md-5 col-sx-12">
                    <#list scoreList as item>
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading">部门审核打分</div>
                            <!-- Table -->
                            <table style="font-size: 14px;"
                                   class="table table-hover table-bordered table-condensed table_needScore">
                                <tr class="info">
                                    <th>指标序号：</th>
                                    <th>${item.indicator.indexNum}</th>
                                    <th>指标类别：</th>
                                    <th colspan="2">${item.indicator.category}</th>
                                </tr>
                                <tr class="info">
                                    <th>指标名称：</th>
                                    <th colspan="4" class="th_needScore">${item.indicator.name}</th>
                                </tr>
                                <tr class="info">
                                    <th>打分部门：</th>
                                    <th colspan="4">${item.opRole}</th>
                                </tr>
                            </table>
                            <table style="font-size: 14px;"
                                   class="table table-hover table-bordered table-condensed">
                                <#if item.indicator.itemType==0>
                                    <tr class="info">
                                        <th>选择</th>
                                        <th style="width: 60%" colspan="3">指标选项</th>
                                        <th>分值</th>
                                    </tr>
                                    <#list item.indicatorItems as sitem>
                                        <tr>
                                            <td class="text-center">
                                                <input type="radio" class="needscore_checkBoxValue" id="${item.indicator.id?c}_${sitem.id?c}_${item.roleId?c}"
                                                       value="${item.indicator.id?c}_${sitem.id?c}_${item.roleId?c}"
                                                       name="score_${item.indicator.id?c}_${item.roleId?c}">
                                            </td>
                                            <td style="width: 60%" colspan="3"><label for="${item.indicator.id?c}_${sitem.id?c}_${item.roleId?c}">${sitem.content}</label></td>
                                            <td class="text-danger">${sitem.score}分</td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr class="info">
                                        <th colspan="4">输入框</th>
                                        <th>分值</th>
                                    </tr>
                                    <tr>
                                        <#if item.indicator.id==7 && view!=1>
                                            <td colspan="2" class="text-danger">
                                                <input type="text" readonly="readonly" value=""
                                                       d-indicator="${item.indicator.id?c}"
                                                       d-roleId="${item.roleId?c}"
                                                       d-name="manScore"
                                                       name="score_${item.indicator.id?c}_${item.roleId?c}">

                                                <button id="social_btn" data-person="${person.id?c}" type="button">
                                                    获取分数
                                                </button>
                                            </td>
                                            <td colspan="2" class="text-danger">
                                                <input type="text" value=""
                                                       d-indicator="${item.indicator.id?c}"
                                                       d-roleId="${item.roleId?c}"
                                                       d-name="manScore" class="needscore_inputValue"
                                                       name="score_${item.indicator.id?c}_${item.roleId?c}">
                                            </td>
                                        <#else>
                                            <td colspan="4" class="text-danger">
                                                <input type="text" value="" class="needscore_inputValue"
                                                       d-indicator="${item.indicator.id?c}"
                                                       d-roleId="${item.roleId?c}"
                                                       name="score_${item.indicator.id?c}_${item.roleId?c}">
                                            </td>
                                        </#if>
                                        <td class="text-danger">手动输入</td>
                                    </tr>
                                </#if>
                                <tr>
                                    <#if item.indicator.id==1003 && view!=1>
                                        <td class="check_desc" colspan="5">
                                            <div class="text-info">审核打分说明：</div>
                                            <textarea d-name="reason" d-roleId="${item.roleId?c}"
                                                      class="form-control"
                                                      d-indicator="${item.indicator.id?c}" rows="3"></textarea>
                                        </td>
                                    <#else>
                                        <td class="check_desc" colspan="5">
                                            <div class="text-info">审核打分说明：</div>
                                            <textarea class="form-control" rows="3"
                                                      disabled>${item.indicator.note}</textarea>
                                        </td>
                                    </#if>
                                </tr>
                                <tr>
                                    <td class="text-danger fontweight600" colspan="4">
                                        <div class="alert alert-warning">请您按照指标体系要求打分</div>
                                    </td>
                                    <td class="" style="vertical-align: middle;" colspan="1">
                                        <a class="btn btn-mini btn-danger" style="display: none;"
                                           href="javascript:void(0);"
                                           id="button_${item.indicator.id?c}_${item.roleId?c}"
                                           scoreRecord="${item.scoreRecordId?c}"
                                           role="apply"
                                           type="button">重新打分
                                        </a>
                                        <#if item.jiaowei != -1 && item.jiaowei != -2 >
                                            <div class="alert alert-info">教委打分: ${item.jiaowei}</div>
                                        </#if>
                                        <#if item.jiaowei == -1 >
                                            <div class="alert alert-info">教委未打分</div>
                                        </#if>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </#list>

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
                                    <th>预览(点击放大)</th>
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
                                            <td style="width:100px;height:100px" class="text-center">
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
                                            </td>
                                        </tr>
                                    </#if>
                                <#else>
                                    <tr>
                                        <td class="text-center">
                                            未上传
                                        </td>
                                        <td>${item.name}</td>
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
                                $(".download").off("click");
                                $(".download").on("click", function () {
                                    var uri = $(this).attr("data-uri");
                                    var name = $(this).attr("data-name");
                                    var type = uri.substring(uri.lastIndexOf("."));
                                    var img = $("<a></a>").attr("href", uri).attr("download", name + type);
                                    img[0].click();
                                });
                                $("#social_btn").off("click");
                                $("#social_btn").on("click", function () {
                                    var personId = $(this).attr("data-person");
                                    var that = this;
                                    $(that).parent().find("input").val(0);
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: App.href + "/api/score/scoreRecord/rensheAutoScore",
                                        data: {
                                            "personId": personId
                                        },
                                        success: function (data) {
                                            if (isNaN(data.score)) {
                                                $(that).parent().find("input").val(data.score);
                                            } else {
                                                $(that).parent().find("input").val(0);
                                            }
                                        },
                                        error: function (e) {
                                            $(that).parent().find("input").val(0);
                                            console.error("请求异常。");
                                        }
                                    });
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

