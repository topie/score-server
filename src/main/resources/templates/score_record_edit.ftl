<div>
    <form id="editScore">
        <table border="3" cellspacing="0" cellpadding="0">
            <tr>
                <th>指标名称</th>
                <th>申请人姓名</th>
                <th>申请人身份证号</th>
                <th>材料打分状态</th>
                <th>得分</th>
                <th>部门名称</th>
            </tr>
            <#list scoreRecords as item>
                <tr>
                    <div style="display: none">
                        ID：<input readonly="readonly" type="text" value="${item.id}" class="id" name="id">
                        指标ID：<input readonly="readonly" type="text" value="${item.indicatorId}" class="indicatorId" name="indicatorId">
                        部门ID：<input readonly="readonly" type="text" value="${item.opRoleId}" class="indicatorId" name="indicatorId">
                    </div>
                    <td><input readonly="readonly" type="text" value="${item.indicatorName}" class="indicatorName" name="indicatorName"></td>
                    <td><input readonly="readonly" type="text" value="${item.personName}" name="personName"></td>
                    <td><input readonly="readonly" type="text" value="${item.personIdNum}" name="personIdNum"></td>
                    <td>
                        <select style="background-color: greenyellow" class="status" name="status" id="">
                            <option <#if item.status==1>selected</#if> value="1">没有材料接收选项</option>
                            <option <#if item.status==2>selected</#if> value="2">有材料接收选项,且材料未提交</option>
                            <option <#if item.status==3>selected</#if> value="3">材料已提交，未打分</option>
                            <option <#if item.status==4>selected</#if> value="4">已打分</option>
                        </select>
                    </td>
                    <td><input style="background-color: greenyellow" type="number" min="-10000" max="10000" step="0.01" value="${item.scoreValue}" class="scoreValue" name="scoreValue"></td>
                    <td><input readonly="readonly" type="text" value="${item.opRole}" class="opRole" name="opRole"></td>
                </tr>
            </#list>
        </table>
    </form>
</div>