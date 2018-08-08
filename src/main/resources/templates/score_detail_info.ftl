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
    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <div class="row">
                    <div class="col-md-12 col-sx-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">申请人信息</div>
                            <table style="font-size: 14px;" class="table table-hover table-condensed">
                                <tr>
                                    <td colspan="12">居住证号（身份证号）：<strong>${person.idNumber}</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">
                                        申请人类型：<strong>${other.applicantType}</strong></td>
                                    <td colspan="6">
                                        居住证申领日期：<strong><#if other.applicationDate??>${other.applicationDate?string("yyyy-MM-dd")}</#if></strong></td>
                                </tr>
                                <!-- 三组数据信息的 -->
                                <tr>
                                    <td colspan="6">姓名：<strong>${person.name}</strong></td>
                                    <td colspan="6">性别：<strong>${person.sex}</strong></td>
                                </tr>
                            </table>
                        </div>
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
                                    <th colspan="2"><#if item.submitDate??>${item.submitDate?string("yyyy-MM-dd")}</#if></th>
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
                                                       value="${item.opRoleId?c}_${item.indicator.id?c}_${sitem.id?c}"
                                                       name="score">
                                            </td>
                                            <td style="width: 60%" colspan="3">${sitem.content}</td>
                                            <td class="text-danger">${sitem.score}分</td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr class="info">
                                        <th colspan="4">输入框</th>
                                        <th>分值</th>
                                    </tr>
                                    <tr>
                                        <td colspan="4" class="text-danger">
                                            <input type="text" value=""
                                                   d-indicator="${item.opRoleId?c}_${item.indicator.id?c}"
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
            </div>
        </div>
    </div>
</div>
</div>

