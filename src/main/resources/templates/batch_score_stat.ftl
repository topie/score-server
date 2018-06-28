<style type="text/css">
    tr th {
        white-space: nowrap;
    }

    tr td {
        white-space: nowrap;
        text-align: left;
    }
</style>
<table class="table table-scrollable table-striped table-bordered table-hover dataTable no-footer">
    <tr>
        <th style="width: 15%">打分事项</th>
        <th style="width: 55%">指标</th>
        <th style="width: 15%">人数</th>
        <th style="width: 15%">比例</th>
    </tr>
<#list iList as item>
    <tr>
        <th style="width: 15%">${item.name}</th>
        <th style="width: 55%">-</th>
        <th style="width: 15%">${item.total}</th>
        <th style="width: 15%">-</th>
    </tr>
    <#list item.items as it>
        <tr>
            <td style="width: 15%"></td>
            <td style="width: 55%">${it.name}</td>
            <td style="width: 15%">${it.total}</td>
            <td style="width: 15%"><#if item.total gt 0>${it.total/item.total*100}%<#else>-</#if></td>
        </tr>
    </#list>
</#list>
</table>
