<table class="table table-scrollable table-striped table-bordered table-hover dataTable no-footer">
    <tr>
        <th rowspan="3">受理日期</th>
        <th colspan="4">市级预约人数</th>
        <th colspan="4">滨海预约人数</th>
    </tr>
    <tr>
        <th colspan="2">上午</th>
        <th colspan="2">下午</th>
        <th colspan="2">上午</th>
        <th colspan="2">下午</th>
    </tr>
    <tr>
        <th>剩余人数</th>
        <th>人数上限</th>
        <th>剩余人数</th>
        <th>人数上限</th>
        <th>剩余人数</th>
        <th>人数上限</th>
        <th>剩余人数</th>
        <th>人数上限</th>
    </tr>
<#list dateList as day>
    <tr>
        <td>${day?string('yyyy-MM-dd')}</td>
        <#assign flag=true>
        <#list list as item>
            <#if item.acceptDate?string('yyyy-MM-dd')==day?string('yyyy-MM-dd') && item.addressId==1 >
                <#assign flag=false>
                <td>${item.amRemainingCount}</td>
                <td>${item.amUserCount}</td>
                <td>${item.pmRemainingCount}</td>
                <td>${item.pmUserCount}</td>
            </#if>
        </#list>
        <#if flag>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
        </#if>
        <#assign flag=true>
        <#list list as item>
            <#if item.acceptDate?string('yyyy-MM-dd')==day?string('yyyy-MM-dd') && item.addressId==2 >
                <#assign flag=false>
                <td>${item.amRemainingCount}</td>
                <td>${item.amUserCount}</td>
                <td>${item.pmRemainingCount}</td>
                <td>${item.pmUserCount}</td>
            </#if>
        </#list>
        <#if flag>
            <td>-</td>
            <td>-</td>
            <td>-</td>
            <td>-</td>
        </#if>
    </tr>
</#list>

</table>
