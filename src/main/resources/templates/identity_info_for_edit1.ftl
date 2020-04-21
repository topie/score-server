<style>
    .table-list-item {
        padding: 5px 5px;
    }

    .table-list-item .table {
        margin-bottom: 0;
    }
</style>
<div>
    <table style="font-size: 14px;" class="table table-bordered table-hover table-condensed">
        <tr>
            <td colspan="6">居住证号（身份证号）：
                <input data-id="${person.id?c}" disabled data-name="person.idNumber"
                       class="edit input-large" style="height: 25px;"
                       value="${person.idNumber}">
            </td>
            <td colspan="6">居住证申领日期：
                <input data-id="${other.id?c}" disabled data-name="other.applicationDate"
                       class="edit input-large" style="height: 25px;"
                       value="${other.applicationDate}">
            </td>
        </tr>
        <tr>
            <td colspan="4">姓名：<input data-id="${person.id?c}" disabled  data-name="person.name"
                                      class="edit input-large" style="height: 25px;"
                                      value="${person.name}"></td>
            <td colspan="4">性别：
                <select data-id="${person.id?c}" data-name="person.sex" disabled  class="edit"
                        style="height: 25px;">
                    <option <#if person.sex==0>selected</#if> value=0>未选择</option>
                    <option <#if person.sex==1>selected</#if> value=1>男</option>
                    <option <#if person.sex==2>selected</#if> value=2>女</option>
                </select>
            </td>
            <td colspan="4">年龄：
                <input data-id="${person.id?c}"  disabled  data-name="person.age"
                       class="edit input-large" style="height: 25px;"
                       value="${person.age}">
            </td>
        </tr>
        <tr>
            <td colspan="4">出生日期：
                <input data-id="${person.id?c}" disabled  data-name="person.birthday"
                       class="edit input-large" style="height: 25px;"
                       value="${person.birthday}">
            </td>
            <td colspan="4">政治面貌：
                <select data-id="${other.id?c}" disabled  data-name="other.politicalStatus" class="edit"
                        style="height: 25px;">
                    <option <#if other.politicalStatus==1>selected</#if> value="1">中共党员</option>
                    <option <#if other.politicalStatus==2>selected</#if> value="2">中共预备党员
                    </option>
                    <option <#if other.politicalStatus==3>selected</#if> value="3">共青团员</option>
                    <option <#if other.politicalStatus==4>selected</#if> value="4">民革会员</option>
                    <option <#if other.politicalStatus==5>selected</#if> value="5">民盟盟员</option>
                    <option <#if other.politicalStatus==6>selected</#if> value="6">民建会员</option>
                    <option <#if other.politicalStatus==7>selected</#if> value="7">民进会员</option>
                    <option <#if other.politicalStatus==8>selected</#if> value="8">农工党党员
                    </option>
                    <option <#if other.politicalStatus==9>selected</#if> value="9">致公党党员
                    </option>
                    <option <#if other.politicalStatus==10>selected</#if> value="10">九三学社社员
                    </option>
                    <option <#if other.politicalStatus==11>selected</#if> value="11">台盟盟员
                    </option>
                    <option <#if other.politicalStatus==12>selected</#if> value="12">无党派民主人士
                    </option>
                    <option <#if other.politicalStatus==13>selected</#if> value="13">群众</option>
                </select>
            </td>
            <td colspan="4">文化程度：
                <select data-id="${other.id?c}" data-name="other.cultureDegree" class="edit"
                        style="height: 25px;">
                    <option <#if other.cultureDegree==1013>selected</#if> value="1013">无</option>
                    <option <#if other.cultureDegree==4>selected</#if> value="4">本科及以上学历</option>
                    <option <#if other.cultureDegree==5>selected</#if> value="5">大专学历</option>
                    <option <#if other.cultureDegree==1011>selected</#if> value="1011">高级技工学校高级班</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="12">剩余预约次数（正整数）：
                <input data-id="${person.id?c}" disabled  data-name="person.reservationTime"
                       class="edit input-large" style="height: 25px;"
                       value="${person.reservationTime}">
            </td>
        </tr>
        <tr>
            <td colspan="12">公安落户编号（请谨慎修改）：
                <input data-id="${person.id?c}" data-name="person.luohuNumber"
                       class="edit input-large" style="height: 25px;"
                       value="${person.luohuNumber}">
            </td>
        </tr>
        <tr>
            <td colspan="12">专业：
                <input data-id="${other.id?c}" data-name="other.profession" disabled
                       class="edit input-large" style="height: 25px;"
                       value="${other.profession}">
            </td>
        </tr>
        <tr>
            <td colspan="12">实际用工单位名称：
                <input data-id="${other.id?c}" data-name="other.companyName" disabled
                       class="edit input-large" style="height: 25px;width:600px;"
                       value="${other.companyName}">
            </td>
        </tr>
        <tr>
            <td colspan="12">实际用工单位地址：
                <input data-id="${other.id?c}" data-name="other.companyAddress" disabled
                       class="edit input-large" style="height: 25px;width:600px;"
                       value="${other.companyAddress}">
            </td>
        </tr>
        <tr>
            <td colspan="12">单位座机电话：
                <input data-id="${other.id?c}" data-name="other.companyPhone" disabled
                       class="edit input-large" style="height: 25px;"
                       value="${other.companyPhone}">
            </td>
        </tr>
        <tr>
            <td colspan="12">本人手机（接收短信）：
                <input data-id="${other.id?c}" data-name="other.selfPhone" disabled
                       class="edit input-large" style="height: 25px;"
                       value="${other.selfPhone}">
            </td>
        </tr>
        <tr>
            <td colspan="12">迁出地省（市自治区）：
                <select data-id="${move.id?c}" data-name="move.moveProvince" class="edit"
                        style="height: 25px;">
                                    <#list provinceList as item>
                                        <option <#if move.moveProvince==item.id>selected</#if>
                                                value="${item.id?c}">${item.name}</option>
                                    </#list>
                </select>
                市：
                <select data-id="${move.id?c}" data-name="move.moveCity" class="edit"
                        style="height: 25px;">
                                    <#list cityList as item>
                                        <#if item.parentId==move.moveProvince>
                                            <option <#if move.moveCity==item.id>selected</#if>
                                                    value="${item.id?c}">${item.name}</option>
                                        </#if>
                                    </#list>
                </select>
                区县：
                <select data-id="${move.id?c}" data-name="move.moveRegion" class="edit"
                        style="height: 25px;">
                                    <#list areaList as item>
                                        <#if item.parentId==move.moveCity>
                                        <option <#if move.moveRegion==item.id>selected</#if>
                                                value="${item.id?c}">${item.name}</option>
                                        </#if>
                                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="12">迁出详细地址：
                <input data-id="${move.id?c}" data-name="move.currentRegisteredAddress"
                       class="edit input-large" style="height: 25px;width:600px;"
                       value="${move.currentRegisteredAddress}">
            </td>
        </tr>
        <tr>
            <td colspan="6">现户口性质：<select data-id="${move.id?c}" data-name="move.houseNature"
                                          class="edit" style="height: 25px;">
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
            <td colspan="12">现户籍登记机关：
                <input data-id="${move.id?c}" data-name="move.moveRegisteredOffice"
                       class="edit input-large" style="height: 25px;width:600px;"
                       value="${move.moveRegisteredOffice}">
            </td>
        </tr>
        <tr>
            <td colspan="12">迁入地户籍登记机关：
                <select data-id="${move.id?c}" data-name="move.registeredOffice" class="edit"
                        style="height: 25px;">
                                            <#list officeList1 as item>
                                                <option <#if move.registeredOffice==item.id?string>selected</#if>
                                                        value="${item.id?c}">${item.name}</option>
                                            </#list>
                </select>
                <select data-id="${move.id?c}" data-name="move.registeredRegion" class="edit"
                        style="height: 25px;">
                                            <#list officeList2 as item>
                                                <#if item.parentId==move.registeredOffice>
                                                    <option <#if move.registeredRegion==item.id?string>selected</#if>
                                                            value="${item.id?c}">${item.name}</option>
                                                </#if>
                                            </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="12">迁入地详细地址：
                <input data-id="${move.id?c}" data-name="move.address" class="edit input-large"
                       style="height: 25px;width:600px;"
                       value="${move.address}">
            </td>
        </tr>
        <tr>
            <td colspan="6">拟落户地区：
                <select data-id="${move.id?c}" data-name="move.region" class="edit"
                        style="height: 25px;">
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
                </select>
            </td>
            <td colspan="6">收件人：
                <input data-id="${move.id?c}" data-name="move.witness" class="edit input-large"
                       style="height: 25px;"
                       value="${move.witness}">
            </td>
        </tr>
        <tr>
            <td colspan="6">联系电话：
                <input data-id="${move.id?c}" data-name="move.witnessPhone"
                       class="edit input-large" style="height: 25px;"
                       value="${move.witnessPhone}">
            </td>
            <td colspan="6">邮寄地址：
                <input data-id="${move.id?c}" data-name="move.witnessAddress"
                       class="edit input-large" style="height: 25px;width:600px;"
                       value="${move.witnessAddress}">
            </td>
        </tr>
        <!-- 内表格 -->
        <tr>
            <td colspan="12">
                <table id="move-table" class="table table-hover table-bordered table-condensed">
                    <tr class="info">
                        <th>与本人关系</th>
                        <th>姓名</th>
                        <th>身份证号</th>
                        <th>文化程度</th>
                        <th>是否随迁</th>
                    </tr>
                        <#list relation as ritem>
                            <tr>
                                <td>
                                    <input data-id="${ritem.id?c}" data-name="relation.relationship"
                                           class="edit input-large" style="height: 25px;"
                                           value="${ritem.relationship}">
                                </td>
                                <td>
                                    <input data-id="${ritem.id?c}" data-name="relation.name"
                                           class="edit input-large" style="height: 25px;"
                                           value="${ritem.name}">
                                </td>
                                <td>
                                    <input data-id="${ritem.id?c}" data-name="relation.idNumber"
                                           class="edit input-large" style="height: 25px;"
                                           value="${ritem.idNumber}">
                                </td>
                                <td>
                                    <input data-id="${ritem.id?c}" data-name="relation.cultureDegree"
                                           class="edit input-large" style="height: 25px;"
                                           value="${ritem.cultureDegree}">
                                </td>
                                <td>
                                    <select data-name="relation.isRemove" data-id="${ritem.id?c}"
                                            class="edit"">
                                    <option value="1" <#if ritem.isRemove == 1>selected</#if> >
                                        是
                                    </option>
                                    <option value="2" <#if ritem.isRemove == 2>selected</#if> >
                                        否
                                    </option>
                                    <#--<option value=null <#if ritem.isRemove == null>selected</#if> >
                                        否
                                    </option>-->
                                    </select>
                                </td>
                            </tr>
                        </#list>
                </table>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        $("select[data-name=\"move.moveCity\"]").off("change");
        $("select[data-name=\"move.moveCity\"]").on("change", function () {
            var city = $(this).val();
            var regionSelect = $("select[data-name=\"move.moveRegion\"]");
            $.ajax({
                type: "POST",
                dataType: "json",
                url: App.href + "/api/core/region/options",
                data: {
                    parentId: city
                },
                success: function (data) {
                    regionSelect.empty();
                    $.each(data, function (i, d) {
                        var option = $('<option value="' + d.value + '">' + d.text + '</option>')
                        regionSelect.append(option);
                    });
                },
                error: function (e) {
                    console.error("请求异常。");
                }
            });
        });
        $("select[data-name=\"move.moveProvince\"]").off("change");
        $("select[data-name=\"move.moveProvince\"]").on("change", function () {
            var province = $(this).val();
            var citySelect = $("select[data-name=\"move.moveCity\"]");
            $.ajax({
                type: "POST",
                dataType: "json",
                url: App.href + "/api/core/region/options",
                data: {
                    parentId: province
                },
                success: function (data) {
                    citySelect.empty();
                    $.each(data, function (i, d) {
                        var option = $('<option value="' + d.value + '">' + d.text + '</option>')
                        citySelect.append(option);
                    });
                    citySelect.trigger("change");
                },
                error: function (e) {
                    console.error("请求异常。");
                }
            });
        });
        $("select[data-name=\"move.registeredOffice\"]").off("change");
        $("select[data-name=\"move.registeredOffice\"]").on("change", function () {
            var officeId = $(this).val();
            var select = $("select[data-name=\"move.registeredRegion\"]");
            $.ajax({
                type: "POST",
                dataType: "json",
                url: App.href + "/api/score/info/identityInfo/officeOption",
                data: {
                    parentId: officeId
                },
                success: function (data) {
                    select.empty();
                    $.each(data, function (i, d) {
                        var option = $('<option value="' + d.value + '">' + d.text + '</option>')
                        select.append(option);
                    });
                },
                error: function (e) {
                    console.error("请求异常。");
                }
            });
        });
    </script>
</div>

